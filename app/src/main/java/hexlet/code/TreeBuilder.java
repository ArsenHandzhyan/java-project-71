package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class TreeBuilder {
    private record DifferenceParams(String key, Object oldValue, Object newValue, Map<String, Object> map1,
                                    Map<String, Object> map2, List<Map<String, Object>> difference) {
    }

    public static List<Map<String, Object>> buildTree(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> difference = new ArrayList<>();

        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        for (String key : allKeys) {
            Object oldValue = formatStringValues(map1.get(key));
            Object newValue = formatStringValues(map2.get(key));

            compareAndAddDifference(new DifferenceParams(key, oldValue, newValue, map1, map2, difference));
        }

        return difference;
    }

    private static void compareAndAddDifference(DifferenceParams params) {
        Map<String, Object> change = new HashMap<>();

        if (!params.map2.containsKey(params.key)) {
            change.put("removed", params.key);
            change.put("value", params.oldValue);
        } else if (!params.map1.containsKey(params.key)) {
            change.put("added", params.key);
            change.put("value", params.newValue);
        } else if (!Objects.equals(params.newValue, params.oldValue)) {
            change.put("updated", params.key);
            change.put("newValue", params.newValue);
            change.put("oldValue", params.oldValue);
        } else {
            change.put("unchanged", params.key);
            change.put("value", params.oldValue);
        }

        params.difference.add(change);
    }

    public static Object formatStringValues(Object value) {
        return (value instanceof String) ? "'" + value + "'" : value;
    }
}
