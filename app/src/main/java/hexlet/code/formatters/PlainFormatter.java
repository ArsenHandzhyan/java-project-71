package hexlet.code.formatters;

import hexlet.code.KeyComparatorForJsonFormat;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlainFormatter {

    private static final String ADDED = "added";
    private static final String REMOVED = "removed";
    private static final String UPDATED = "updated";

    public static String format(List<Map<String, Object>> diff) {
        StringBuilder formatted = new StringBuilder();

        diff.sort(new KeyComparatorForJsonFormat());

        for (Map<String, Object> entry : diff) {
            String name = clearKeyName(entry);
            if (entry.containsKey(ADDED)) {
                formatted.append(String.format("Property '%s' was added with value: %s%n",
                        name, getFormattedValue(entry.get("value"))));
            } else if (entry.containsKey(REMOVED)) {
                formatted.append(String.format("Property '%s' was removed%n", name));
            } else if (entry.containsKey(UPDATED)) {
                formatted.append(String.format("Property '%s' was updated. From %s to %s%n",
                        name,
                        getFormattedValue(entry.get("oldValue")),
                        getFormattedValue(entry.get("newValue"))));
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

    public static String clearKeyName(Map<String, Object> s) {
        String changeType = s.keySet().stream()
                .filter(key -> key.matches("added|removed|updated|unchanged"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid change type"));

        return s.get(changeType).toString();
    }
}
