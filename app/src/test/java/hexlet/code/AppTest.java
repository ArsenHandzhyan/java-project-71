package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AppTest {
    public static String yaml1Path;
    public static String yaml2Path;
    public static String json1Path;
    public static String json2Path;
    public static String emptyJson;
    public static String singleKeyJson;
    public static String jsonDiff;
    public static String yamlDiff;



    @BeforeEach
    void setUp() {
        json1Path = "file1.json";
        json2Path = "file2.json";
        yaml1Path = "file1.yaml";
        yaml2Path = "file2.yaml";
        emptyJson = "emptyJson.json";
        singleKeyJson = "singleKeyJson.json";
        jsonDiff = Differ.generate(json1Path, json2Path);
        yamlDiff = Differ.generate(yaml1Path, yaml2Path);
    }

    @Test
    public void testYamlComparison() {
        assertFalse(yamlDiff.isEmpty());
    }

    @Test
    public void testWithout() {
        String expected = """
                {
                  chars1: [a, b, c]
                - chars2: [d, e, f]
                + chars2: false
                - checked: false
                + checked: true
                - default: null
                + default: [value1, value2]
                - id: 45
                + id: null
                - key1: value1
                + key2: value2
                  numbers1: [1, 2, 3, 4]
                - numbers2: [2, 3, 4, 5]
                + numbers2: [22, 33, 44, 55]
                - numbers3: [3, 4, 5]
                + numbers4: [4, 5, 6]
                + obj1: {nestedKey=value, isNested=true}
                - setting1: Some value
                + setting1: Another value
                - setting2: 200
                + setting2: 300
                - setting3: true
                + setting3: none
                }
                """;
        assertEquals(expected, Formatter.formatterSelection("stylish", jsonDiff));
    }

    @Test
    public void testEmptyFile() {
        String expected = """
                {
                - chars1: [a, b, c]
                - chars2: [d, e, f]
                - checked: false
                - default: null
                - id: 45
                - key1: value1
                - numbers1: [1, 2, 3, 4]
                - numbers2: [2, 3, 4, 5]
                - numbers3: [3, 4, 5]
                - setting1: Some value
                - setting2: 200
                - setting3: true
                }
                """;
        String diff = Differ.generate(json1Path, emptyJson);
        assertEquals(expected, Formatter.formatterSelection("plain", diff));
    }

    @Test
    public void testSingleKeyValuePair() {
        String actual = Differ.generate(json1Path, singleKeyJson);
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testSameFiles() {
        String expected = """
                {
                - chars1: [a, b, c]
                - chars2: [d, e, f]
                - checked: false
                - default: null
                - id: 45
                - key1: value1
                + key2: value2
                - numbers1: [1, 2, 3, 4]
                - numbers2: [2, 3, 4, 5]
                - numbers3: [3, 4, 5]
                - setting1: Some value
                - setting2: 200
                - setting3: true
                }
                """;
        String diff = Differ.generate(json1Path, singleKeyJson);
        assertEquals(expected, Formatter.formatterSelection("plain", diff));
    }

    @Test
    public void testCompletelyDifferentFiles() {
        String actual = Differ.generate(json1Path, singleKeyJson);
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testPlainFormat() {
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'
                            """;
        String diff = Differ.generate(json1Path, json2Path);
        assertEquals(expected, Formatter.formatterSelection("plain", diff));
    }
}
