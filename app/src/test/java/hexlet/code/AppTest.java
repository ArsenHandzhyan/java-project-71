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
                    host: hexlet.io
                  + timeout: 20
                  + verbose: true
                  - follow: false
                  - proxy: 123.234.53.22
                  - timeout: 50
                }
                """;
        String actual = diffOutput;
        assertEquals(expected, actual);
    }
}
