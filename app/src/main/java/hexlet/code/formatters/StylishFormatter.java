package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StylishFormatter {

    public static String format(List<Map<String, Object>> diff) {
        List<String> lines = buildFormattedLines(diff);
        return buildFormattedOutput(lines);
    }

    private static List<String> buildFormattedLines(List<Map<String, Object>> diff) {
        List<String> lines = new ArrayList<>();
        diff.forEach(change -> lines.addAll(formatChange(change)));
        return lines;
    }

    private static List<String> formatChange(Map<String, Object> change) {
        List<String> formattedLines = new ArrayList<>();

        String changeType = (String) change.get("type");
        String key = getFormattedValue(change.get("key"));
        String formattedValue = getFormattedValue(change.get("value"));
        String formattedOldValue = getFormattedValue(change.get("oldValue"));
        String formattedNewValue = getFormattedValue(change.get("newValue"));

        switch (changeType) {
            case "added":
                formattedLines.add(formatLine("  + ", key, formattedValue));
                break;
            case "removed":
                formattedLines.add(formatLine("  - ", key, formattedValue));
                break;
            case "updated":
                formattedLines.add(formatLine("  - ", key, formattedOldValue));
                formattedLines.add(formatLine("  + ", key, formattedNewValue));
                break;
            case "unchanged":
                formattedLines.add(formatLine("    ", key, formattedValue));
                break;
            default:
                throw new IllegalArgumentException("Unknown change type: " + changeType);
        }
        return formattedLines;
    }

    private static String formatLine(String prefix, String key, String value) {
        return prefix + key + ": " + value;
    }

    private static String buildFormattedOutput(List<String> lines) {
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
