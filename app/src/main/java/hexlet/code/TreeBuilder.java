package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class TreeBuilder {
    public static List<Map<String, Object>> buildTree(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> difference = new ArrayList<>();

        Set<String> allKeys = new TreeSet<>(new KeyComparator());
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        for (String key : allKeys) {
            Object oldValue = formatStringValues(map1.get(key));
            Object newValue = formatStringValues(map2.get(key));

            compareAndAddDifference(key, oldValue, newValue, map1, map2, difference);
        }

        return difference;
    }

    private static void compareAndAddDifference(String key, Object oldValue, Object newValue,
                                                Map<String, Object> map1, Map<String, Object> map2,
                                                List<Map<String, Object>> difference) {
        Map<String, Object> change = new HashMap<>();

        if (!map2.containsKey(key)) {
            change.put("removed", key);
            change.put("value", oldValue);
        } else if (!map1.containsKey(key)) {
            change.put("added", key);
            change.put("value", newValue);
        } else if (!Objects.equals(newValue, oldValue)) {
            change.put("updated", key);
            change.put("newValue", newValue);
            change.put("oldValue", oldValue);
        } else {
            change.put("unchanged", key);
            change.put("value", oldValue);
        }

        difference.add(change);
    }

    public static Object formatStringValues(Object value) {
        return (value instanceof String) ? "'" + value + "'" : value;
    }
}
