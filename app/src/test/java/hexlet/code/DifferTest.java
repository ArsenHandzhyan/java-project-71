package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public final class DifferTest {

    private static final String JSON_1_PATH = "src/test/resources/fixtures/file1.json";
    private static final String JSON_2_PATH = "src/test/resources/fixtures/file2.json";
    private static final String EMPTY_JSON_PATH = "src/test/resources/fixtures/emptyJson.json";
    private static final String SINGLE_KEY_JSON_PATH = "src/test/resources/fixtures/singleKeyJson.json";
    private static final String EMPTY_PATH = "";

    private static String resultPlain;
    private static String resultStylish;
    private static String resultJson;
    private static String resultStylishEmpty;

    private static String jsonDiff;
    private static String yamlDiff;

    @BeforeEach
    public void setUp() throws IOException {
        String yaml1Path = "src/test/resources/fixtures/file1.yml";
        String yaml2Path = "src/test/resources/fixtures/file2.yml";
        String resultPlainPath = "src/test/resources/fixtures/result_plain.txt";
        String resultJsonPath = "src/test/resources/fixtures/result_json.txt";
        String resultStylishPath = "src/test/resources/fixtures/result_stylish.txt";
        String resultStylishEmptyPath = "src/test/resources/fixtures/result_stylish_withEmptyFile.txt";

        resultPlain = Files.readString(Paths.get(resultPlainPath));
        resultStylish = Files.readString(Paths.get(resultStylishPath));
        resultJson = Files.readString(Paths.get(resultJsonPath));
        resultStylishEmpty = Files.readString(Paths.get(resultStylishEmptyPath));

        jsonDiff = generate(JSON_1_PATH, JSON_2_PATH, "stylish");
        yamlDiff = generate(yaml1Path, yaml2Path, "stylish");
    }

    @Test
    public void testYamlComparison() {
        assertFalse(yamlDiff.isEmpty());
    }

    @Test
    public void testWithout() {
        assertEquals(resultStylish, jsonDiff);
    }

    @Test
    public void testEmptyFile() throws IOException {
        String actual = generate(JSON_1_PATH, EMPTY_JSON_PATH, "stylish");
        assertEquals(resultStylishEmpty, actual);
    }

    @Test
    public void testTwoArguments() throws IOException {
        String actual = generate(JSON_1_PATH, JSON_2_PATH);
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testEmptyPath() {
        assertThrows(IllegalArgumentException.class, () -> generate(JSON_1_PATH, EMPTY_PATH, "stylish"));
    }

    @Test
    public void testSingleKeyValuePair() throws IOException {
        String actual = generate(JSON_1_PATH, SINGLE_KEY_JSON_PATH, "stylish");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testCompletelyDifferentFiles() throws IOException {
        String actual = generate(JSON_1_PATH, SINGLE_KEY_JSON_PATH, "plain");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testPlainFormat() throws IOException {
        String actual = generate(JSON_1_PATH, JSON_2_PATH, "plain");
        assertEquals(resultPlain, actual);
    }

    @Test
    public void testJsonFormat() throws IOException {
        String actual = generate(JSON_1_PATH, JSON_2_PATH, "json");
        assertEquals(resultJson, actual);
    }

    @Test
    public void testGenerate() {
        assertDoesNotThrow(() -> {
            generate(JSON_1_PATH, JSON_2_PATH, "plain");
        });
    }
}
