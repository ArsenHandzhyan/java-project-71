package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
