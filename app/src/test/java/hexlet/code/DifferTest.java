package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DifferTest {

    public static String yaml1Path;
    public static String yaml2Path;
    public static String json1Path;
    public static String json2Path;
    public static String someFilePath;
    public static String resultPlainPath;
    public static String resultStylishPath;
    public static String resultStylishEmptyPath;
    public static String emptyJsonPath;
    public static String singleKeyJsonPath;

    public static String resultPlain;
    public static String resultStylish;
    public static String resultStylishEmpty;

    public static String jsonDiff;
    public static String yamlDiff;

    @BeforeEach
    void setUp() throws IOException {
        json1Path = "src/test/resources/fixtures/file1.json";
        json2Path = "src/test/resources/fixtures/file2.json";
        yaml1Path = "src/test/resources/fixtures/file1.yml";
        yaml2Path = "src/test/resources/fixtures/file2.yml";
        emptyJsonPath = "src/test/resources/fixtures/emptyJson.json";
        singleKeyJsonPath = "src/test/resources/fixtures/singleKeyJson.json";
        someFilePath = "src/test/resources/fixtures/sameFile.txt";
        resultPlainPath = "src/test/resources/fixtures/result_plain.txt";
        resultStylishPath = "src/test/resources/fixtures/result_stylish.txt";
        resultStylishEmptyPath = "src/test/resources/fixtures/result_stylish_withEmptyFile.txt";


        StringBuilder stringBuilder1 = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(resultPlainPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    stringBuilder1.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultPlain = stringBuilder1.toString();

        StringBuilder stringBuilder2 = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(resultStylishPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    stringBuilder2.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultStylish = stringBuilder2.toString();


        StringBuilder stringBuilder3 = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(resultStylishEmptyPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    stringBuilder3.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultStylishEmpty = stringBuilder3.toString();

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
