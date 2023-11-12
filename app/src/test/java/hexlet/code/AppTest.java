package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import hexlet.code.formatters.Formatter;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AppTest {
    public static String filepath1;


    static {
        try {
            filepath1 = getFile("file1.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String filepath2;

    static {
        try {
            filepath2 = getFile("file2.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String diffOutput;

    public static String getFile(String resourcePath) throws Exception {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("File '" + resourcePath + "' not found");
            }
            return new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
        }
    }

    public static String yamlFilepath1;
    public static String yamlFilepath2;

    public void loadFileContentForYaml(String filePath1, String filePath2) throws Exception {
        yamlFilepath1 = getFile(filePath1);
        yamlFilepath2 = getFile(filePath2);
    }

    @BeforeEach
    void yamlSetUp() throws Exception {
        loadFileContentForYaml("file1.yaml", "file2.yaml");
    }

    @BeforeEach
    void setUp() throws JsonProcessingException, FileNotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream1 = AppTest.class.getResourceAsStream("/file1.json");
        InputStream inputStream2 = AppTest.class.getResourceAsStream("/file2.json");

        if (inputStream1 == null) {
            throw new FileNotFoundException("File '/file1.json' not found");
        }
        if (inputStream2 != null) {
            var fileContent1 = new BufferedReader(new InputStreamReader(inputStream1))
                    .lines().collect(Collectors.joining("\n"));
            var fileContent2 = new BufferedReader(new InputStreamReader(inputStream2))
                    .lines().collect(Collectors.joining("\n"));

            JsonNode json1 = mapper.readTree(fileContent1);
            JsonNode json2 = mapper.readTree(fileContent2);

            diffOutput = Differ.generate(json1, json2, "plain");
        } else {
            throw new FileNotFoundException("File '/file2.json' not found");
        }
    }

    @Test
    public void testYamlComparison() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JsonNode yaml1 = mapper.readTree(yamlFilepath1);
        JsonNode yaml2 = mapper.readTree(yamlFilepath2);
        String diff = Differ.generate(yaml1, yaml2, "plain");
        assertFalse(diff.isEmpty());
    }

    @Test
    public void testEmptyYamlComparison() throws Exception {
        String emptyFilePath = "{}";

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JsonNode yaml1 = mapper.readTree(yamlFilepath1);
        JsonNode jsonEmpty = mapper.readTree(emptyFilePath);
        String actual = Differ.generate(yaml1, jsonEmpty, "plain");
        assertFalse(actual.isEmpty());
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
        String actual = diffOutput;
        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyFile() throws Exception {
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
        String emptyFilePath = "{}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json1 = mapper.readTree(filepath1);
        JsonNode jsonEmpty = mapper.readTree(emptyFilePath);
        String actual = Differ.generate(json1, jsonEmpty, "plain");
        assertEquals(expected, actual);
    }

    @Test
    public void testNonExistentFile() {
        Exception exception = assertThrows(Exception.class, () ->
                getFile("/path/to/nonexistent/file.json"));
        String expectedMessage = "File '/path/to/nonexistent/file.json' not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSingleKeyValuePair() throws Exception {
        String singleKvpFilePath = """
                {
                  "key1": "value1"
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json1 = mapper.readTree(filepath1);
        JsonNode jsonSingleKvp = mapper.readTree(singleKvpFilePath);
        String diff = Differ.generate(json1, jsonSingleKvp, "plain");
        assertFalse(diff.isEmpty());
    }

    @Test
    public void testArrayValue() throws Exception {
        String arrayFilePath = """
                {
                  "array": [1, 2, 3]
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode arrayJson = mapper.readTree(arrayFilePath);
        String arrayValueOutput = arrayJson.get("array").toString();
        assertEquals("[1,2,3]", arrayValueOutput);
    }

    @Test
    public void testSameFiles() throws Exception {
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
        String sameFilepath = """
                {
                   "key1": "value1"
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json1 = mapper.readTree(filepath1);
        JsonNode jsonSame = mapper.readTree(sameFilepath);
        String actual = Differ.generate(json1, jsonSame, "plain");
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
        JsonNode json1 = mapper.readTree(filepath1);
        JsonNode jsonDifferent = mapper.readTree(differentFilepath);
        String diff = Differ.generate(json1, jsonDifferent, "plain");
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
