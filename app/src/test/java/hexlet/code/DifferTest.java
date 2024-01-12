package hexlet.code;

import hexlet.code.formatters.UnsupportedFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    private static final String NESTED_STRUCTURES1 = "src/test/resources/fixtures/nestedStructures1.json";
    private static final String NESTED_STRUCTURES2 = "src/test/resources/fixtures/nestedStructures2.json";
    private static final String EMPTY_PATH = "";
    private static final String PLAIN_RESULT = "src/test/resources/fixtures/nestedStructuresPlainResult.txt";
    private static final String JSON_RESULT = "src/test/resources/fixtures/nestedStructuresJsonResult.json";
    private static final String STYLISH_RESULT = "src/test/resources/fixtures/nestedStructuresStylishResult.txt";

    private static String resultPlain;
    private static String resultStylish;
    private static String resultJson;

    private static String generateStylishDiffWithJson;
    private static String generateStylishDiffWithYml;

    @BeforeEach
    public void setUp() throws Exception {
        resultPlain = readResourceFile(PLAIN_RESULT);
        resultStylish = readResourceFile(STYLISH_RESULT);
        resultJson = readResourceFile(JSON_RESULT);

        generateStylishDiffWithJson = generate(NESTED_STRUCTURES1, NESTED_STRUCTURES2, "stylish");
        generateStylishDiffWithYml = generate(YML_1_PATH, YML_2_PATH, "stylish");
    }

    @Test
    public void testNestedStructuresPlainFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("plain", resultPlain);
    }

    @Test
    public void testNestedStructuresStylishFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("stylish", resultStylish);
    }

    @Test
    public void testNestedStructuresJsonFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("json", resultJson);
    }

    @Test
    public void testYamlComparison() {
        assertFalse(generateStylishDiffWithJson.isEmpty());
    }

    @Test
    public void testWithoutJson() {
        assertEquals(resultStylish, generateStylishDiffWithJson);
    }

    @Test
    public void testWithoutYaml() {
        assertEquals(resultStylish, generateStylishDiffWithYml);
    }

    @Test
    public void testTwoArguments() {
        assertEquals(resultStylish, generateStylishDiffWithJson);
    }

    @Test
    public void testMixedStructuresStylishFormat() throws Exception {
        String actual = generate(YML_1_PATH, NESTED_STRUCTURES2, "stylish");
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testEmptyPath() {
        assertThrows(IOException.class, () -> generate(YML_1_PATH, EMPTY_PATH, "stylish"));
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
    public void testGenerate() {
        assertDoesNotThrow(() -> generate(YML_1_PATH, YML_2_PATH, "json"));
    }

    @Test
    public void testMixedStructuresStylishFormatWithJson() throws Exception {
        String actual = generate(YML_1_PATH, NESTED_STRUCTURES2, "stylish");
        assertEquals(resultStylish, actual);
    }

    @Test
    public void testMixedStructuresStylishFormatWithYaml() throws Exception {
        String actual = generate(NESTED_STRUCTURES1, YML_2_PATH, "stylish");
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
        String actual = generate(NESTED_STRUCTURES1, NESTED_STRUCTURES2, format);
        assertEquals(expected, actual);
    }

    private String readResourceFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
