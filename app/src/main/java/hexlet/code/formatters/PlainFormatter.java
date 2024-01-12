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
                    formatted.append(String.format("Property %s was added with value: %s%n",
                            name, formattedValue));
                    break;
                case "removed":
                    formatted.append(String.format("Property %s was removed%n",
                            name));
                    break;
                case "updated":
                    formatted.append(String.format("Property %s was updated. From %s to %s%n",
                            name, formattedOldValue, formattedNewValue));
                    break;
                case "unchanged":
                    // don't use in this formatter
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
