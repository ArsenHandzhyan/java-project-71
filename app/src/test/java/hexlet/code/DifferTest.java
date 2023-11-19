package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public final class DifferTest {

    private static String json1Path;
    private static String json2Path;
    private static String emptyJsonPath;
    private static String singleKeyJsonPath;
    private static String emptyPath;

    private static String resultPlain;
    private static String resultStylish;
    private static String resultStylishEmpty;

    public static String jsonDiff;
    public static String yamlDiff;

    @BeforeEach
    public void setUp() throws IOException {
        String yaml1Path = "src/test/resources/fixtures/file1.yml";
        String yaml2Path = "src/test/resources/fixtures/file2.yml";

        json1Path = "src/test/resources/fixtures/file1.json";
        json2Path = "src/test/resources/fixtures/file2.json";
        emptyJsonPath = "src/test/resources/fixtures/emptyJson.json";
        singleKeyJsonPath = "src/test/resources/fixtures/singleKeyJson.json";
        emptyPath = "";
        String resultPlainPath = "src/test/resources/fixtures/result_plain.txt";
        String resultStylishPath = "src/test/resources/fixtures/result_stylish.txt";
        String resultStylishEmptyPath = "src/test/resources/fixtures/result_stylish_withEmptyFile.txt";

        resultPlain = Files.readString(Paths.get(resultPlainPath));
        resultStylish = Files.readString(Paths.get(resultStylishPath));
        resultStylishEmpty = Files.readString(Paths.get(resultStylishEmptyPath));

        jsonDiff = Differ.generate(json1Path, json2Path, "stylish");
        yamlDiff = Differ.generate(yaml1Path, yaml2Path, "stylish");
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
        String actual = Differ.generate(json1Path, emptyJsonPath, "stylish");
        assertEquals(resultStylishEmpty, actual);
    }

    @Test
    public void testEmptyPath() {
        assertThrows(IllegalArgumentException.class, () -> Differ.generate(json1Path, emptyPath, "stylish"));
    }

    @Test
    public void testSingleKeyValuePair() throws IOException {
        String actual = Differ.generate(json1Path, singleKeyJsonPath, "stylish");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testCompletelyDifferentFiles() throws IOException {
        String actual = Differ.generate(json1Path, singleKeyJsonPath, "plain");
        assertFalse(actual.isEmpty());
    }

    @Test
    public void testPlainFormat() throws IOException {
        String actual = Differ.generate(json1Path, json2Path, "plain");
        assertEquals(resultPlain, actual);
    }

    @Test
    public void testGenerate() {
        assertDoesNotThrow(() -> {
            Differ.generate(json1Path, json2Path, "plain");
        });
    }
}
