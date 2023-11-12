package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormatter {
    public static String format(String diff) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOutput = "";
        try {
            jsonOutput = objectMapper.writeValueAsString(diff);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonOutput;
    }
}
