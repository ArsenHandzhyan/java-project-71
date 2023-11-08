package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

public class StylishFormatter {
    public static String format(JsonNode node) {
        if (node.isObject() || node.isArray()) {
            return node.toString().replaceAll("\"", "").replaceAll(",", ", ").replaceAll(":", "=");
        } else {
            return node.asText();
        }
    }
}

