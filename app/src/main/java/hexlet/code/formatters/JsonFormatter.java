package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Differ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class JsonFormatter {

    private static final Logger LOGGER = LogManager.getLogger(JsonFormatter.class);

    public static String format(String diff) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder jsonOutput = new StringBuilder();
        try {
            jsonOutput = new StringBuilder(objectMapper.writeValueAsString(diff));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error in processing JSON", e);
        }
        return jsonOutput.toString();
    }
    public static void main(String[] args) throws IOException {
        String json1Path = "src/test/resources/fixtures/file1.json";
        String json2Path = "src/test/resources/fixtures/file2.json";
        System.out.println(Differ.generate(json1Path, json2Path, "stylish"));
    }
}
