package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DifferTest {

    public static String yaml1Path;
    public static String yaml2Path;
    public static String json1Path;
    public static String json2Path;
    public static String emptyJson;
    public static String singleKeyJson;
    public static String jsonDiff;
    public static String yamlDiff;



    @BeforeEach
    void setUp() throws IOException {
        json1Path = "file1.json";
        json2Path = "file2.json";
        yaml1Path = "file1.yaml";
        yaml2Path = "file2.yaml";
        emptyJson = "emptyJson.json";
        singleKeyJson = "singleKeyJson.json";
        jsonDiff = Differ.generate(json1Path, json2Path, "stylish");
        yamlDiff = Differ.generate(yaml1Path, yaml2Path, "stylish");
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
        assertEquals(expected, jsonDiff);
    }

    @Test
    public void testEmptyFile() throws IOException {
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
        String actual = Differ.generate(json1Path, emptyJson, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    public void testSingleKeyValuePair() throws IOException {
        String actual = Differ.generate(json1Path, singleKeyJson, "plain");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testSameFiles() throws IOException {
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
        String actual = Differ.generate(json1Path, singleKeyJson, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    public void testCompletelyDifferentFiles() throws IOException {
        String actual = Differ.generate(json1Path, singleKeyJson, "plain");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testPlainFormat() throws IOException {
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
        String actual = Differ.generate(json1Path, json2Path, "plain");
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerate() {
        String testFile1 = Paths.get("src", "test", "resources", "file1.json").toString();
        String testFile2 = Paths.get("src", "test", "resources", "file2.json").toString();

        assertDoesNotThrow(() -> {
            Differ.generate(testFile1, testFile2, "plain");
        });
    }

    @Test
    public void testGenerateEquals() {
        String testFile1 = Paths.get("src", "test", "resources", "file1.json").toString();

        assertDoesNotThrow(() -> {
            String result = Differ.generate(testFile1, testFile1, "plain");

            assertEquals("", result);
        });
    }
}
