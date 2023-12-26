package hexlet.code;

import java.util.Comparator;
import java.util.Map;

public final class KeyComparatorForJsonFormat implements Comparator<Map<String, Object>> {
    /**
     * Compares two strings for sorting, ignoring whitespace and hyphens.
     *
     * @param sb1 the first string to be compared
     * @param sb2 the second string to be compared
     * @return a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second
     */
    @Override
    public int compare(Map<String, Object> sb1, Map<String, Object> sb2) {


        String key1 = clearKeyName(sb1);
        String key2 = clearKeyName(sb2);
        if (key1.compareTo(key2) == 0) {
            return sb2.toString().compareTo(sb1.toString());
        }
        return key1.compareTo(key2);
    }

    public static String clearKeyName(Map<String, Object> s) {
        String changeType = s.keySet().stream()
                .filter(key -> key.matches("added|removed|updated|unchanged"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid change type"));

        return s.get(changeType).toString();
    }
}
