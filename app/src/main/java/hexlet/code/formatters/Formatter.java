package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String formatterSelection(String format, List<Map<String, Object>> diff)
            throws JsonProcessingException, UnsupportedFormatException {
        if (format == null) {
            throw new UnsupportedFormatException("Null format");
        }

        return switch (format.toLowerCase()) {
            case "stylish" -> StylishFormatter.format(diff);
            case "plain" -> PlainFormatter.format(diff);
            case "json" -> JsonFormatter.format(diff);
            default -> throw new UnsupportedFormatException(format);
        };
    }
}