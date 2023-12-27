package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TreeBuilder {
    public static List<Map<String, Object>> buildTree(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> difference = new ArrayList<>();

        compareAndBuildDifference(map1, map2, difference);
        addAdditionalEntries(map1, map2, difference);
        return difference;
    }

    private static void compareAndBuildDifference(Map<String, Object> map1,
                                                  Map<String, Object> map2,
                                                  List<Map<String, Object>> difference) {
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
                                                List<Map<String, Object>> difference) {
        Map<String, Object> change = new HashMap<>();
        if (!map2.containsKey(key)) {
            change.put("value", oldValue);
            change.put("removed", key);
            difference.add(change);
        } else if (!Objects.equals(newValue, oldValue)) {
            change.put("oldValue", oldValue);
            change.put("newValue", newValue);
            change.put("updated", key);
            difference.add(change);
        } else {
            change.put("value", oldValue);
            change.put("unchanged", key);
            difference.add(change);
        }
    }

    private static void addAdditionalEntries(Map<String, Object> map1,
                                             Map<String, Object> map2,
                                             List<Map<String, Object>> difference) {
        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();
            if (!map1.containsKey(key)) {
                Map<String, Object> added = new HashMap<>();
                added.put("value", formatStringValues(entry.getValue()));
                added.put("added", key);
                difference.add(added);
            }
        }
    }

    public static Object formatStringValues(Object value) {
        return (value instanceof String) ? "'" + value + "'" : value;
    }
}
