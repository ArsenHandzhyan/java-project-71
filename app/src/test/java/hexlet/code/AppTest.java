package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import hexlet.code.formatters.Formatter;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AppTest {
    public static String diffJson;
    public static String diffYaml;
    public static File yaml1;
    public static File yaml2;
    public static File json1;
    public static File json2;
    public static File jsonEmptyPath;
    public static File sameFileJsonPath;
    public static JsonNode sameFileJson;
    public static JsonNode parsedJson1;
    public static JsonNode parsedJson2;
    public static JsonNode parsedYaml1;
    public static JsonNode parsedYaml2;
    public static JsonNode jsonEmpty;


    @BeforeEach
    void setUp() {
        Parser parser = new Parser();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        json1 = new File(Objects.requireNonNull(loader.getResource("file1.json")).getFile());
        json2 = new File(Objects.requireNonNull(loader.getResource("file2.json")).getFile());
        yaml1 = new File(Objects.requireNonNull(loader.getResource("file1.yaml")).getFile());
        yaml2 = new File(Objects.requireNonNull(loader.getResource("file2.yaml")).getFile());
        jsonEmptyPath = new File(Objects.requireNonNull(loader.getResource("jsonEmpty.json")).getFile());
        sameFileJsonPath = new File(Objects.requireNonNull(loader.getResource("sameFile.json")).getFile());

        parsedJson1 = parser.parse(json1).orElseThrow();
        parsedJson2 = parser.parse(json2).orElseThrow();
        parsedYaml1 = parser.parse(yaml1).orElseThrow();
        parsedYaml2 = parser.parse(yaml2).orElseThrow();
        jsonEmpty = parser.parse(jsonEmptyPath).orElseThrow();
        sameFileJson = parser.parse(sameFileJsonPath).orElseThrow();
    }

    @Test
    public void testYamlComparison() {
        String diffOutputYaml = Formatter.formatterSelection("plain", diffYaml);
        assertFalse(diffOutputYaml.isEmpty());
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
        String actual = Formatter.formatterSelection("stylish", diffJson);
        assertEquals(expected, actual);
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

        String actual = Differ.generate(parsedJson1, jsonEmpty, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    public void testSingleKeyValuePair() {
        String diff = Differ.generate(parsedJson1, sameFileJson, "plain");
        assertFalse(diff.isEmpty());
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
                  key1: value1
                - numbers1: [1, 2, 3, 4]
                - numbers2: [2, 3, 4, 5]
                - numbers3: [3, 4, 5]
                - setting1: Some value
                - setting2: 200
                - setting3: true
                }
                """;
        String actual = Differ.generate(parsedJson1, sameFileJson, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    public void testCompletelyDifferentFiles() throws Exception {
        String differentFilepath = """
                {
                   "key2": "value2"
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonDifferent = mapper.readTree(differentFilepath);
        String diff = Differ.generate(parsedJson1, jsonDifferent, "plain");
        assertFalse(diff.isEmpty());
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

        String diff = """
                {
                  chars1: ["a","b","c"]
                - chars2: ["d","e","f"]
                + chars2: false
                - checked: false
                + checked: true
                - default: null
                + default: ["value1","value2"]
                - id: 45
                + id: null
                - key1: "value1"
                + key2: "value2"
                  numbers1: [1,2,3,4]
                - numbers2: [2,3,4,5]
                + numbers2: [22,33,44,55]
                - numbers3: [3,4,5]
                + numbers4: [4,5,6]
                + obj1: {"nestedKey":"value","isNested":true}
                - setting1: "Some value"
                + setting1: "Another value"
                - setting2: 200
                + setting2: 300
                - setting3: true
                + setting3: "none"
                }
                """;
        String actual = Formatter.formatterSelection("plain", diff);
        assertEquals(expected, actual);
    }
}
