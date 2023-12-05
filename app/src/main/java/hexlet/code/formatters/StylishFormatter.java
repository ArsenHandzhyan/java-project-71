package hexlet.code.formatters;

import hexlet.code.KeyComparator;
import hexlet.code.OldAndNewValue;

import java.util.Arrays;
import java.util.Map;

public class StylishFormatter {

    public static String format(Map<String, Object> diff) {
        StringBuilder formatted = new StringBuilder();

        for (Map.Entry<String, Object> entry : diff.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String[] parts = key.split(" ");
            String updatedKey = parts[1];
            if (key.startsWith("added")) {
                formatted.append("  + ").append(key.substring(6)).append(": ");
                formatted.append(getFormattedValue(value)).append("\n");
            } else if (key.startsWith("removed")) {
                formatted.append("  - ").append(key.substring(8)).append(": ");
                formatted.append(getFormattedValue(value)).append("\n");
            } else if (key.startsWith("updated")) {
                OldAndNewValue oldAndNewValue = (OldAndNewValue) value;
                formatted.append("  - ").append(updatedKey).append(": ");
                formatted.append(getFormattedValue(oldAndNewValue.getValue1())).append("\n");
                formatted.append("  + ").append(updatedKey).append(": ");
                formatted.append(getFormattedValue(oldAndNewValue.getValue2())).append("\n");
            } else if (key.startsWith("unchanged")) {
                formatted.append("    ").append(key.substring(10)).append(": ");
                formatted.append(getFormattedValue(value)).append("\n");
            }
        }
        String[] lines = formatted.toString().split(System.lineSeparator());
        Arrays.sort(lines, new KeyComparator());


        // Собрать отсортированный результат обратно в StringBuilder
        StringBuilder sortedSB = new StringBuilder("{\n");
        for (String line : lines) {
            sortedSB.append(line).append(System.lineSeparator());
        }
        sortedSB.append("}");

        return sortedSB.toString();
    }

    private static String getFormattedValue(Object value) {
        if (value instanceof Object[] || value instanceof Arrays) {
            return value.toString();
        } else {
            return String.valueOf(value).replace("'", "");
        }
    }
}
