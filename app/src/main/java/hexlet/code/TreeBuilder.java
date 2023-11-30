package hexlet.code;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class TreeBuilder {
    public static Map<String, Object> buildTree(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> difference = new TreeMap<>(new KeyComparator());
        for (Map.Entry<String, Object> entry : map1.entrySet()) {
            String key = entry.getKey();
            Object value1 = formatStringValues(entry.getValue());
            Object value2 = formatStringValues(map2.get(key));
            if (!map2.containsKey(key)) {
                difference.put("- " + key, value1);
            } else if (!(Objects.equals(value2, value1))) {
                difference.put("- " + key, value1);
                difference.put("+ " + key, value2);
            } else {
                difference.put("  " + key, value1);
            }
        }

        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();
            Object value2 = formatStringValues(entry.getValue());

            if (!map1.containsKey(key)) {
                difference.put("+ " + key, value2);
            }
        }
        return difference;
    }

    public static Object formatStringValues(Object value) {
        if (value instanceof String) {
            return ("'" + value + "'");
        } else {
            return value;
        }
    }
}

