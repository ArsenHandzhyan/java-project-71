package hexlet.code;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class TreeBuilder {
    public static Map<String, Object> buildTree(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> difference = new TreeMap<>(new KeyComparator());

        compareAndBuildDifference(map1, map2, difference);
        addAdditionalEntries(map1, map2, difference);

        return difference;
    }

    private static void compareAndBuildDifference(Map<String, Object> map1, Map<String, Object> map2, Map<String, Object> difference) {
        for (Map.Entry<String, Object> entry : map1.entrySet()) {
            String key = entry.getKey();
            Object value1 = formatStringValues(entry.getValue());
            Object value2 = formatStringValues(map2.get(key));

            if (!map2.containsKey(key) || !Objects.equals(value2, value1)) {
                if (!map2.containsKey(key)) {
                    difference.put("- " + key, value1);
                } else {
                    difference.put("- " + key, value1);
                    difference.put("+ " + key, value2);
                }
            } else {
                difference.put("  " + key, value1);
            }
        }
    }

    private static void addAdditionalEntries(Map<String, Object> map1, Map<String, Object> map2, Map<String, Object> difference) {
        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();
            Object value2 = formatStringValues(entry.getValue());

            if (!map1.containsKey(key)) {
                difference.put("+ " + key, value2);
            }
        }
    }

    public static Object formatStringValues(Object value) {
        return (value instanceof String) ? "'" + value + "'" : value;
    }
}
