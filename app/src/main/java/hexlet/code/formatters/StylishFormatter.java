package hexlet.code.formatters;

import hexlet.code.KeyComparatorForStylishAndPlainFormats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StylishFormatter {

    public static String format(List<Map<String, Object>> diff) {
        List<String> lines = new ArrayList<>();

        for (Map<String, Object> change : diff) {
            String changeType = change.keySet().stream()
                    .filter(key -> key.matches("added|removed|updated|unchanged"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Invalid change type"));

            String key = change.get(changeType).toString();
            Object value = change.get("value");
            Object oldValue = change.get("oldValue");
            Object newValue = change.get("newValue");

            switch (changeType) {
                case "added":
                    lines.add("  + " + key + ": " + getFormattedValue(value));
                    break;
                case "removed":
                    lines.add("  - " + key + ": " + getFormattedValue(value));
                    break;
                case "updated":
                    lines.add("  - " + key + ": " + getFormattedValue(oldValue));
                    lines.add("  + " + key + ": " + getFormattedValue(newValue));
                    break;
                case "unchanged":
                    lines.add("    " + key + ": " + getFormattedValue(value));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown change type: " + changeType);
            }
        }

        lines.sort(new KeyComparatorForStylishAndPlainFormats());

        StringBuilder sortedSB = new StringBuilder("{\n");
        for (String line : lines) {
            sortedSB.append(line).append("\n");
        }
        sortedSB.append("}");

        return sortedSB.toString();
    }

    private static String getFormattedValue(Object value) {
        if (value instanceof Object[] || value instanceof List) {
            return value.toString();
        } else {
            return String.valueOf(value).replace("'", "");
        }
    }
}
