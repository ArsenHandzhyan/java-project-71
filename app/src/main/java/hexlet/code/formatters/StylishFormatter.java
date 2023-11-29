package hexlet.code.formatters;

import java.util.Map;
import java.util.Set;

public class StylishFormatter {

    public static String format(Map<String, Object> diff) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        Set<Map.Entry<String, Object>> entrySet = diff.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            result.append("  ")
                    .append(entry.getKey())
                    .append(": ")
                    .append(formatValue(entry.getValue()))
                    .append("\n");
        }

        result.append("}");
        return result.toString();
    }

    private static String formatValue(Object value) {
        if (value instanceof Map) {
            // Если значение - это объект, просто вызываем toString()
            return value.toString();
        } else if (value instanceof Iterable) {
            // Если значение - это массив, также вызываем toString()
            return value.toString();
        } else {
            // В остальных случаях просто возвращаем значение как строку
            return String.valueOf(value);
        }
    }
}