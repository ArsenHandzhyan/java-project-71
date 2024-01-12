package hexlet.code;

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
    private static final String EMPTY_PATH = "";

    private static final String NESTED_STRUCTURES_DIR = "src/test/resources/fixtures/";
    private static final String NESTED_STRUCTURES_PLAIN_RESULT = "nestedStructuresPlainResult.txt";
    private static final String NESTED_STRUCTURES_JSON_RESULT = "nestedStructuresJsonResult.json";
    private static final String NESTED_STRUCTURES_STYLISH_RESULT = "nestedStructuresStylishResult.txt";

    private static String resultPlain;
    private static String resultStylish;
    private static String resultJson;

    private static String generateStylishDiff;

    @BeforeEach
    public void setUp() throws Exception {
        resultPlain = readResourceFile(NESTED_STRUCTURES_DIR + NESTED_STRUCTURES_PLAIN_RESULT);
        resultStylish = readResourceFile(NESTED_STRUCTURES_DIR + NESTED_STRUCTURES_STYLISH_RESULT);
        resultJson = readResourceFile(NESTED_STRUCTURES_DIR + NESTED_STRUCTURES_JSON_RESULT);

        generateStylishDiff = generate(
                NESTED_STRUCTURES_DIR + "nestedStructures1.json",
                NESTED_STRUCTURES_DIR + "nestedStructures2.json",
                "stylish"
        );
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
        assertFalse(generateStylishDiff.isEmpty());
    }

    @Test
    public void testWithout() {
        assertEquals(resultStylish, generateStylishDiff);
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
    public void testPlainFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("plain", resultPlain);
    }

    @Test
    public void testJsonFormat() throws Exception {
        assertGeneratedOutputMatchesExpected("json", resultJson);
    }

    @Test
    public void testGenerate() {
        assertDoesNotThrow(() -> generate(YML_1_PATH, YML_2_PATH, "plain"));
    }

    private void assertGeneratedOutputMatchesExpected(String format, String expected) throws Exception {
        String actual = generate(
                NESTED_STRUCTURES_DIR + "nestedStructures1.json",
                NESTED_STRUCTURES_DIR + "nestedStructures2.json",
                format
        );
        assertEquals(expected, actual);
    }

    private String readResourceFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
