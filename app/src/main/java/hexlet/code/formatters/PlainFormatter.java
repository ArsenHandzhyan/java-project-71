package hexlet.code.formatters;

public class PlainFormatter {
    public static String format(String diff) {
        String[] lines = diff.split("\n");
        StringBuilder plainFormattedDiff = new StringBuilder();

        for (int i = 0; i < lines.length; ++i) {
            String line = lines[i].trim();

            if (line.isEmpty() || line.contains("{   ") || line.contains("}   ")) {
                continue;
            }

            if (line.startsWith("-")) {
                i = handleRemovedProperty(lines, plainFormattedDiff, i);
            } else if (line.startsWith("+")) { // Added properties
                handleAddedProperty(plainFormattedDiff, line.trim());
            }

        }

        return plainFormattedDiff.toString();
    }

    private static int handleRemovedProperty(String[] lines, StringBuilder plainFormattedDiff, int i) {
        String line = lines[i];
        String property = extractProperty(line);
        String oldValue = extractValue(line);
        String formatOldValue = getFormattedValue(oldValue);

        if (i < lines.length - 1 && lines[i + 1].trim().startsWith("+")
                && property.equals(extractProperty(lines[i + 1]))) {
            String newValue = extractValue(lines[i + 1]);
            String formatNewValue = getFormattedValue(newValue);
            plainFormattedDiff.append(String.format("Property '%s' was updated. From %s to %s%n",
                    property, formatOldValue, formatNewValue));
            i++;
        } else {
            plainFormattedDiff.append(String.format("Property '%s' was removed%n", property));
        }
        return i;
    }

    private static void handleAddedProperty(StringBuilder plainFormattedDiff, String line) {
        String property = extractProperty(line);
        String newValue = extractValue(line);
        plainFormattedDiff.append(String.format("Property '%s' was added with value: %s%n",
                property, getFormattedValue(newValue)));
    }

    private static String extractProperty(String line) {
        return line.substring(1, line.indexOf(':')).trim().replaceAll("[^a-zA-Z0-9]", "");
    }

    private static String extractValue(String line) {
        return line.substring(line.indexOf(':') + 1).replace("\"", "'").trim();
    }

    private static String getFormattedValue(String value) {
        return isComplexValue(value) ? "[complex value]" : value;
    }

    private static boolean isComplexValue(String value) {
        return value.startsWith("[") || value.startsWith("{");
    }
}
