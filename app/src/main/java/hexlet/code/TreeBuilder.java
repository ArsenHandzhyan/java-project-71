package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.TreeMap;

public class TreeBuilder {
    public static String buildTree(Map<String, Object> map1, Map<String, Object> map2, String format) throws JsonProcessingException {
        Map<String, Object> difference = new TreeMap<>(new KeyComparator());
        for (Map.Entry<String, Object> entry : map1.entrySet()) {
            String key = entry.getKey();
            Object value1 = parseForNullValue(entry.getValue());
            Object value2 = parseForNullValue(map2.get(key));
            if (!map2.containsKey(key)) {
                difference.put("- " + key, value1);
            } else if (!value2.equals(value1)) {
                difference.put("- " + key, value1);
                difference.put("+ " + key, value2);
            } else {
                difference.put("  " + key, value1);
            }
        }

        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();
            Object value2 = entry.getValue();

            if (!map1.containsKey(key)) {
                difference.put("+ " + key, value2);
            }
        }
        return Formatter.formatterSelection(format, difference);
    }

    public static Object parseForNullValue(Object value) {
        if (value == null) {
            return "null";
        }
        return value;
    }
}

