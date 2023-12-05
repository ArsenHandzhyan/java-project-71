package hexlet.code;

import java.util.Comparator;

public final class KeyComparator implements Comparator<String> {
    /**
     * Compares two strings for sorting, ignoring whitespace and hyphens.
     *
     * @param sb1 the first string to be compared
     * @param sb2 the second string to be compared
     * @return a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second
     */
    @Override
    public int compare(String sb1, String sb2) {
        String cleanKey1 = clearKeyName(sb1);
        String cleanKey2 = clearKeyName(sb2);
        if (cleanKey1.compareTo(cleanKey2) == 0) {
            return sb2.compareTo(sb1);
        }
        return cleanKey1.compareTo(cleanKey2);
    }

    public static String clearKeyName(String s) {
        String[] parts = s.split(":");
        String key = parts[0];
        return key.replaceAll("[\\s\\-+]", "");
    }
}
