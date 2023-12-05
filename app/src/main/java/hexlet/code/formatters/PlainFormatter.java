package hexlet.code.formatters;

import hexlet.code.KeyComparator;
import hexlet.code.OldAndNewValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class PlainFormatter {

    public static String format(Map<String, Object> diff) {
        StringBuilder formatted = new StringBuilder();
        for (Map.Entry<String, Object> entry : diff.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String[] parts = key.split(" ");
            String updatedKey = parts[1];
            if (key.startsWith("added")) {
                formatted.append(String.format("Property '%s' was added with value: %s%n",
                        updatedKey, getFormattedValue1(value)));
            } else if (key.startsWith("removed")) {
                formatted.append(String.format("Property '%s' was removed%n", updatedKey));
            } else if (key.startsWith("updated")) {
                OldAndNewValue oldAndNewValue = (OldAndNewValue) value;
                formatted.append(String.format("Property '%s' was updated. From %s to %s%n",
                        updatedKey,
                        getFormattedValue1(oldAndNewValue.getValue1()),
                        getFormattedValue1(oldAndNewValue.getValue2())));
            }
        }
        return getSortedDiff(formatted);
    }

    private static String getSortedDiff(StringBuilder formatted) {
        String[] lines = formatted.toString().split(System.lineSeparator());
        Arrays.sort(lines, new KeyComparator());
        return String.join(System.lineSeparator(), lines);
    }

    private static String getFormattedValue1(Object value) {
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
