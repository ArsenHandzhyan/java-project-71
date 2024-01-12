package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class DifferTest {
    private static final String YML_1_PATH = "src/test/resources/fixtures/file1.yml";
    private static final String YML_2_PATH = "src/test/resources/fixtures/file2.yml";
    private static final String EMPTY_JSON_PATH = "src/test/resources/fixtures/emptyJson.json";
    private static final String SINGLE_KEY_JSON_PATH = "src/test/resources/fixtures/singleKeyJson.json";
    private static final String EMPTY_PATH = "";

    private static String resultPlain;
    private static String resultStylish;
    private static String resultJson;
    private static String resultStylishEmpty;

    private static String generateStylishDiff;

    @BeforeEach
    public void setUp() throws Exception {
        String resultPlainPath = "src/test/resources/fixtures/result_plain.txt";
        String resultJsonPath = "src/test/resources/fixtures/result_json.json";
        String resultStylishPath = "src/test/resources/fixtures/result_stylish.txt";
        String resultStylishEmptyPath = "src/test/resources/fixtures/result_stylish_withEmptyFile.txt";

        resultPlain = Files.readString(Paths.get(resultPlainPath));
        resultStylish = Files.readString(Paths.get(resultStylishPath));
        resultJson = Files.readString(Paths.get(resultJsonPath));
        resultStylishEmpty = Files.readString(Paths.get(resultStylishEmptyPath));

        generateStylishDiff = generate(YML_1_PATH, YML_2_PATH, "stylish");
    }

    @Test
    public void testNestedStructuresPlainFormat() throws Exception {
        String actual = generate(
                "src/test/resources/fixtures/nestedStructures1.yml",
                "src/test/resources/fixtures/nestedStructures2.yml",
                "plain");
        String expected = Files.readString(Paths.get(
                "src/test/resources/fixtures/nestedStructuresPlainResult.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void testNestedStructuresStylishFormat() throws Exception {
        String actual = generate(
                "src/test/resources/fixtures/nestedStructures1.yml",
                "src/test/resources/fixtures/nestedStructures2.yml",
                "stylish");
        String expected = Files.readString(Paths.get(
                "src/test/resources/fixtures/nestedStructuresStylishResult.txt"));
        assertEquals(expected, actual);
    }

    @Test
    public void testNestedStructuresJsonFormat() throws Exception {
        String actual = generate(
                "src/test/resources/fixtures/nestedStructures1.yml",
                "src/test/resources/fixtures/nestedStructures2.yml",
                "json");
        String expected = Files.readString(Paths.get(
                "src/test/resources/fixtures/nestedStructuresJsonResult.json"));
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testYamlComparison() {
        assertFalse(generateStylishDiff.isEmpty());
    }

    @Test
    public void testWithout() {
        assertEquals(resultStylish, generateStylishDiff);
    }

    @Test
    public void testEmptyFile() throws Exception {
        String actual = generate(YML_1_PATH, EMPTY_JSON_PATH, "stylish");
        assertEquals(resultStylishEmpty, actual);
    }

    @Test
    public void testTwoArguments() {
        assertEquals(resultStylish, generateStylishDiff);
    }

    @Test
    public void testEmptyPath() {
        assertThrows(IOException.class, () -> generate(YML_1_PATH, EMPTY_PATH, "stylish"));
    }

    @Test
    public void testSingleKeyValuePair() throws Exception {
        String actual = generate(YML_1_PATH, SINGLE_KEY_JSON_PATH, "stylish");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testCompletelyDifferentFiles() throws Exception {
        String actual = generate(YML_1_PATH, SINGLE_KEY_JSON_PATH, "plain");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testPlainFormat() throws Exception {
        String actual = generate(YML_1_PATH, YML_2_PATH, "plain");
        assertEquals(resultPlain, actual);
    }

    @Test
    public void testJsonFormat() throws Exception {
        String actual = generate(YML_1_PATH, YML_2_PATH, "json");
        JSONAssert.assertEquals(resultJson, actual, false);
    }

    @Test
    public void testGenerate() {
        assertDoesNotThrow(() -> {
            generate(YML_1_PATH, YML_2_PATH, "plain");
        });
    }
}
