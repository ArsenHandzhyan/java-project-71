package hexlet.code.formatters;

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
                formatted.append("  + ").append(updatedKey).append(": ");
                formatted.append(getFormattedValue(value)).append("\n");
            } else if (key.startsWith("removed")) {
                formatted.append("  - ").append(updatedKey).append(": ");
                formatted.append(getFormattedValue(value)).append("\n");
            } else if (key.startsWith("updated")) {
                OldAndNewValue oldAndNewValue = (OldAndNewValue) value;
                formatted.append("  - ").append(updatedKey).append(": ");
                formatted.append(getFormattedValue(oldAndNewValue.getValue1())).append("\n");
                formatted.append("  + ").append(updatedKey).append(": ");
                formatted.append(getFormattedValue(oldAndNewValue.getValue2())).append("\n");
            } else if (key.startsWith("unchanged")) {
                formatted.append("    ").append(updatedKey).append(": ");
                formatted.append(getFormattedValue(value)).append("\n");
            }
        }
        String[] lines = formatted.toString().split(System.lineSeparator());
        Arrays.sort(lines, (sb1, sb2) -> {
            String[] parts1 = sb1.split(":");
            String updatedKey1 = parts1[0];
            String[] parts2 = sb2.split(":");
            String updatedKey2 = parts2[0];
            String cleanKey1 = updatedKey1.replaceAll("[\\s\\-+]", "");
            String cleanKey2 = updatedKey2.replaceAll("[\\s\\-+]", "");
            if (cleanKey1.compareTo(cleanKey2) == 0) {
                return sb2.compareTo(sb1);
            }
            return cleanKey1.compareTo(cleanKey2);
        });


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
