package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StylishFormatter {

    static ObjectMapper objectMapper = new ObjectMapper();
    static Pattern p = Pattern.compile("^([^:\\[]*):(.+)$", Pattern.DOTALL);

    public static String format(String diff) {
        StringBuilder builder = new StringBuilder();
        for (String line : diff.split("\n")) {
            Matcher m = p.matcher(line.trim());
            if (m.find()) {
                String key = m.group(1).trim();
                String value = m.group(2).trim();
                if (key.startsWith("-") || key.startsWith("+")) {
                    builder.append(key).append(": ").append(formatValue(value)).append("\n");
                } else {
                    builder.append("  ").append(key).append(": ").append(formatValue(value)).append("\n");
                }
            } else {
                builder.append(line).append("\n");
            }
        }
        return builder.toString();
    }

    private static String formatValue(String value) {
        try {
            JsonNode node = objectMapper.readTree(value);
            if (node.isObject() || node.isArray()) {
                return formatNode(node);
            } else {
                return node.asText();
            }
        } catch (IOException e) {
            return value;
        }
    }

    private static String formatNode(JsonNode node) {
        if (node.isObject() || node.isArray()) {
            return node.toString().replaceAll("\"", "").replaceAll(",", ", ").replaceAll(":", "=");
        } else {
            return node.asText();
        }
    }
}
