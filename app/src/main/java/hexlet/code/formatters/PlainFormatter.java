package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlainFormatter {
    public static String format(List<Map<String, Object>> diff) {
        StringBuilder formatted = new StringBuilder();
        for (Map<String, Object> entry : diff) {
            formatted.append(formatEntry(entry));
        }
        return formatted.toString().trim();
    }

    private static String formatEntry(Map<String, Object> entry) {
        String name = "'" + entry.get("key") + "'";
        String changeType = (String) entry.get("type");
        String formattedValue = getFormattedValue(entry.get("value"));
        String formattedOldValue = getFormattedValue(entry.get("oldValue"));
        String formattedNewValue = getFormattedValue(entry.get("newValue"));

        return switch (changeType) {
            case "added" -> String.format("Property %s was added with value: %s%n",
                    name, formattedValue);
            case "removed" -> String.format("Property %s was removed%n",
                    name);
            case "updated" -> String.format("Property %s was updated. From %s to %s%n",
                    name, formattedOldValue, formattedNewValue);
            case "unchanged" -> ""; // don't use in this formatter
            default -> throw new IllegalArgumentException("Unknown change type: " + changeType);
        };
    }

    private static String getFormattedValue(Object value) {
        return (Objects.isNull(value)) ? null : (isComplexValue(value.toString())
                ? "[complex value]" : value.toString());
    }

    private static boolean isComplexValue(String value) {
        return value.startsWith("[") || value.startsWith("{");
    }
}
