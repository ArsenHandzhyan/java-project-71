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

    private static void compareAndAddDifference(String key, Object value1, Object value2,
                                                Map<String, Object> map2, Map<String, Object> difference) {
        if (!map2.containsKey(key) || !Objects.equals(value2, value1)) {
            if (!map2.containsKey(key)) {
                difference.put(DifferenceOperation.REMOVED + " " + key, value1);
            } else {
                difference.put(DifferenceOperation.REMOVED + " " + key, value1);
                difference.put(DifferenceOperation.ADDED + " " + key, value2);
            }
        } else {
            difference.put(DifferenceOperation.UNCHANGED + " " + key, value1);
        }
    }

    private static void addAdditionalEntries(Map<String, Object> map1,
                                             Map<String, Object> map2,
                                             Map<String, Object> difference) {
        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();
            Object value2 = formatStringValues(entry.getValue());

            if (!map1.containsKey(key)) {
                difference.put(DifferenceOperation.ADDED + " " + key, value2);
            }
        }
    }

    public static Object formatStringValues(Object value) {
        return (value instanceof String) ? "'" + value + "'" : value;
    }

    private enum DifferenceOperation {
        ADDED("+"),
        REMOVED("-"),
        UNCHANGED(" ");

        private final String symbol;

        DifferenceOperation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }
}
