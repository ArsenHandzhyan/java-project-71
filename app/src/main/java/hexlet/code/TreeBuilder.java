package hexlet.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TreeBuilder {
    public static Map<String, Object> buildTree(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> difference = new HashMap<>();

        compareAndBuildDifference(map1, map2, difference);
        addAdditionalEntries(map1, map2, difference);

        return difference;
    }

    private static void compareAndBuildDifference(Map<String, Object> map1,
                                                  Map<String, Object> map2,
                                                  Map<String, Object> difference) {
        for (Map.Entry<String, Object> entry : map1.entrySet()) {
            String key = entry.getKey();
            Object value1 = formatStringValues(entry.getValue());
            Object value2 = formatStringValues(map2.get(key));

            compareAndAddDifference(key, value1, value2, map2, difference);
        }
    }

    private static void compareAndAddDifference(String key,
                                                Object oldValue,
                                                Object newValue,
                                                Map<String, Object> map2,
                                                Map<String, Object> difference) {
        if (!map2.containsKey(key) || !Objects.equals(newValue, oldValue)) {
            if (!map2.containsKey(key)) {
                difference.put("removed " + key, oldValue);
            } else {
                difference.put("updated " + key, new OldAndNewValue(oldValue, newValue));
            }
        } else {
            difference.put("unchanged " + key, oldValue);
        }
    }

    private static void addAdditionalEntries(Map<String, Object> map1,
                                             Map<String, Object> map2,
                                             Map<String, Object> difference) {
        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();
            Object value2 = formatStringValues(entry.getValue());

            if (!map1.containsKey(key)) {
                difference.put("added " + key, value2);
            }
        }
    }

    public static Object formatStringValues(Object value) {
        return (value instanceof String) ? "'" + value + "'" : value;
    }
}
