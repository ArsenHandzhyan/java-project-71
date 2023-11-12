package hexlet.code.formatters;

import java.util.*;

public class PlainFormatter {
    public static String format(String diff) {
        String[] lines = diff.split("\n");
        LinkedHashMap<String, String> mapBefore = new LinkedHashMap<>();
        LinkedHashMap<String, String> mapAfter = new LinkedHashMap<>();

        for (String line : lines) {
            if (line.trim().isEmpty() || line.contains("{") || line.contains("}")) {
                continue;
            }

            String modifiedLine = line.startsWith("-") || line.startsWith("+") ? line.substring(1)
                    .trim() : line;
            String[] splitLine = modifiedLine.split(": ");

            if (splitLine.length < 2) {
                continue;
            }

            String property = splitLine[0].trim();
            String changedValue = splitLine[1].trim();

            if (line.startsWith("-")) {
                mapBefore.put(property, changedValue);
            } else if (line.startsWith("+")) {
                mapAfter.put(property, changedValue);
            }
        }

        StringBuilder plainFormattedDiff = new StringBuilder();

        for (var entry : mapBefore.entrySet()) {
            String property = entry.getKey();

            if (!mapAfter.containsKey(property)) {
                plainFormattedDiff.append(String.format("Property '%s' was removed%n", property));
                continue;
            }

            String oldValue = mapBefore.get(property);
            String newValue = mapAfter.get(property);
            String formatOldValue = isComplexValue(oldValue) ? "[complex value]" : oldValue;
            String formatNewValue = isComplexValue(newValue) ? "[complex value]" : newValue;

            plainFormattedDiff.append(String.format("Property '%s' was updated. From %s to %s%n",
                    property, formatOldValue, formatNewValue));
            mapAfter.remove(property);
        }

        for (var entry : mapAfter.entrySet()) {
            String property = entry.getKey();
            plainFormattedDiff.append(String.format("Property '%s' was added with value: %s%n",
                    property, isComplexValue(entry.getValue()) ? "[complex value]" : entry.getValue()));
        }

        return plainFormattedDiff.toString();
    }

    private static boolean isComplexValue(String value) {
        return value.startsWith("[") || value.startsWith("{");
    }
}
