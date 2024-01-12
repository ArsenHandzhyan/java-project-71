package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlainFormatter {
    public static String format(List<Map<String, Object>> diff) {
        StringBuilder formatted = new StringBuilder();

        for (Map<String, Object> entry : diff) {
            String name = (String) entry.get("key");
            String changeType = (String) entry.get("type");
            String formattedValue = getFormattedValue(entry.get("value"));
            String formattedOldValue = getFormattedValue(entry.get("oldValue"));
            String formattedNewValue = getFormattedValue(entry.get("newValue"));
            switch (changeType) {
                case "added":
                    formatted.append(String.format("Property " + name + " was added with value: " + formattedValue)).append("\n");
                    break;
                case "removed":
                    formatted.append(String.format("Property " + name + " was removed", name)).append("\n");
                    break;
                case "updated":
                    formatted.append(String.format("Property " + name + " was updated. From " + formattedOldValue + " to " + formattedNewValue)).append("\n");
                    break;
                case "unchanged":
                    break;
                default:
                    throw new IllegalArgumentException("Unknown change type: " + changeType);
            }
        }
        return formatted.toString().trim();
    }

    private static String getFormattedValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        } else {
            return isComplexValue(value.toString()) ? "[complex value]" : value.toString();
        }
    }

    private static boolean isComplexValue(String value) {
        return value.startsWith("[") || value.startsWith("{");
    }
}
