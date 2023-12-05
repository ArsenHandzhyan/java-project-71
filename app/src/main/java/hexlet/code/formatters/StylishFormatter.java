package hexlet.code.formatters;

import hexlet.code.KeyComparator;
import hexlet.code.OldAndNewValue;

import java.util.Arrays;
import java.util.Map;

import static hexlet.code.TreeBuilder.*;

public class StylishFormatter {

    public static String format(Map<String, Object> diff) {
        StringBuilder formatted = new StringBuilder();

        for (Map.Entry<String, Object> entry : diff.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String[] parts = key.split(" ");
            String updatedKey = parts[1];
            if (key.startsWith(ADDITION)) {
                formatted
                        .append("  + ")
                        .append(updatedKey)
                        .append(": ")
                        .append(getFormattedValue(value))
                        .append(System.lineSeparator());
            } else if (key.startsWith(DELETION)) {
                formatted
                        .append("  - ")
                        .append(updatedKey)
                        .append(": ")
                        .append(getFormattedValue(value))
                        .append(System.lineSeparator());
            } else if (key.startsWith(CHANGE)) {
                OldAndNewValue oldAndNewValue = (OldAndNewValue) value;
                formatted
                        .append("  - ")
                        .append(updatedKey)
                        .append(": ")
                        .append(getFormattedValue(oldAndNewValue.getValue1()))
                        .append(System.lineSeparator())
                        .append("  + ")
                        .append(updatedKey)
                        .append(": ")
                        .append(getFormattedValue(oldAndNewValue.getValue2()))
                        .append(System.lineSeparator());
            } else if (key.startsWith(NOT_CHANGE)) {
                formatted
                        .append("    ")
                        .append(updatedKey)
                        .append(": ")
                        .append(getFormattedValue(value))
                        .append(System.lineSeparator());
            }
        }
        return getSortedDiff(formatted);
    }

    private static String getFormattedValue(Object value) {
        if (value instanceof Object[] || value instanceof Arrays) {
            return value.toString();
        } else {
            return String.valueOf(value).replace("'", "");
        }
    }

    private static String getSortedDiff(StringBuilder formatted) {
        String[] lines = formatted.toString().split(System.lineSeparator());
        Arrays.sort(lines, new KeyComparator());
        return String.join(System.lineSeparator(), lines);
    }
}
