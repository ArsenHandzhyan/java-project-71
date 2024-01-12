package hexlet.code;

import hexlet.code.formatters.UnsupportedFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.*;

public final class DifferTest {
    private static final String YML_1_PATH = "src/test/resources/fixtures/file1.yml";
    private static final String YML_2_PATH = "src/test/resources/fixtures/file2.yml";
    private static final String JSON_1_PATH = "src/test/resources/fixtures/nestedStructures1.json";
    private static final String JSON_2_PATH = "src/test/resources/fixtures/nestedStructures2.json";
    private static final String EMPTY_PATH = "";
    private static final String SINGLE_KEY_JSON_PATH = "src/test/resources/fixtures/singleKeyJson.json";
    private static final String PLAIN_RESULT_PATH = "src/test/resources/fixtures/nestedStructuresPlainResult.txt";
    private static final String JSON_RESULT_PATH = "src/test/resources/fixtures/nestedStructuresJsonResult.json";
    private static final String STYLISH_RESULT_PATH = "src/test/resources/fixtures/nestedStructuresStylishResult.txt";
    private static final String SINGLE_KEY_RESULT = "src/test/resources/fixtures/singleKeyResult.txt";

    private static String resultPlain;
    private static String resultStylish;
    private static String resultJson;

    private static String singleKeyResult;

    private static String stylishDiffWithJson;
    private static String stylishDiffWithYml;

    @BeforeEach
    public void setUp() throws Exception {
        resultPlain = readResourceFile(PLAIN_RESULT_PATH);
        resultStylish = readResourceFile(STYLISH_RESULT_PATH);
        resultJson = readResourceFile(JSON_RESULT_PATH);
        singleKeyResult = readResourceFile(SINGLE_KEY_RESULT);

        stylishDiffWithJson = generate(JSON_1_PATH, JSON_2_PATH, "stylish");
        stylishDiffWithYml = generate(YML_1_PATH, YML_2_PATH, "stylish");
    }

    @Test
    public void testWithoutJson() {
        assertEquals(resultStylish, stylishDiffWithJson);
    }

    @Test
    public void testWithoutYaml() {
        assertEquals(resultStylish, stylishDiffWithYml);
    }

    @Test
    public void testMixedStructuresStylishFormat() throws Exception {
        String actual = generate(YML_1_PATH, JSON_2_PATH, "stylish");
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testStylishFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("stylish", resultStylish);
    }

    @Test
    public void testPlainFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("plain", resultPlain);
    }

    @Test
    public void testJsonFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("json", resultJson);
    }

    @Test
    public void testSingleKeyValuePair() throws Exception {
        String actual = generate(YML_1_PATH, SINGLE_KEY_JSON_PATH, "stylish");
        assertEquals(singleKeyResult, actual);
    }

    @Test
    public void testCompletelyDifferentFiles() throws Exception {
        String actual = generate(YML_1_PATH, SINGLE_KEY_JSON_PATH, "plain");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testGenerate() {
        assertDoesNotThrow(() -> generate(YML_1_PATH, YML_2_PATH, "json"));
    }

    @Test
    public void testEmptyPath() {
        assertThrows(IOException.class, () -> generate(YML_1_PATH, EMPTY_PATH, "stylish"));
    }

    @Test
    public void testMixedStructuresStylishFormatWithJson() throws Exception {
        String actual = generate(YML_1_PATH, JSON_2_PATH, "stylish");
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testMixedStructuresStylishFormatWithYaml() throws Exception {
        String actual = generate(JSON_1_PATH, YML_2_PATH, "stylish");
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testGenerateWithInvalidFormat() {
        assertThrows(UnsupportedFormatException.class, () -> generate(YML_1_PATH, YML_2_PATH, "invalidFormat"));
    }

    @Test
    public void testGenerateWithNullFormat() {
        assertThrows(UnsupportedFormatException.class, () -> generate(YML_1_PATH, YML_2_PATH, null));
    }

    @Test
    public void testGenerateWithEmptyFormat() {
        assertThrows(UnsupportedFormatException.class, () -> generate(YML_1_PATH, YML_2_PATH, ""));
    }

    @Test
    public void testGenerateWithNullPath() {
        assertThrows(NullPointerException.class, () -> generate(null, YML_2_PATH, "stylish"));
    }

    @Test
    public void testGenerateWithEmptyPath() {
        assertThrows(IOException.class, () -> generate("", YML_2_PATH, "stylish"));
    }

    @Test
    public void testGenerateWithNonexistentPath() {
        assertThrows(IOException.class, () -> generate("nonexistent.yml", YML_2_PATH, "stylish"));
    }

    @Test
    public void testGenerateWithNullPaths() {
        assertThrows(NullPointerException.class, () -> generate(null, null, "stylish"));
    }

    @Test
    public void testGenerateWithEmptyPaths() {
        assertThrows(IOException.class, () -> generate("", "", "stylish"));
    }


    private void assertGeneratedOutputMatchesExpected(String format, String expected) throws Exception {
        String actual = generate(JSON_1_PATH, JSON_2_PATH, format);
        assertEquals(expected, actual);
    }

    private String readResourceFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
