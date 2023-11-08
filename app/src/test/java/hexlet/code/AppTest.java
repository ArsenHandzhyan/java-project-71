package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    public static String filepath1;

    static {
        try {
            filepath1 = getFile("/home/arsen/IdeaProjects/java-project-71/app/file1.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String filepath2;

    static {
        try {
            filepath2 = getFile("/home/arsen/IdeaProjects/java-project-71/app/file2.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String diffOutput;

    public static String getFile(String pathFile) throws Exception {
        Path path = Paths.get(pathFile).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' does not exist");
        }
        return Files.readString(path);
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json1 = mapper.readTree(filepath1);
        JsonNode json2 = mapper.readTree(filepath2);
        Map<String, String> diff = App.generateDifference(json1, json2, "");
        diffOutput = App.generateDiffOutput(diff);
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
}
