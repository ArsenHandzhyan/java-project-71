package hexlet.code;

import java.util.Comparator;

public final class KeyComparator implements Comparator<String> {
    /**
     * Compares two strings for sorting, ignoring whitespace and hyphens.
     *
     * @param key1 the first string to be compared
     * @param key2 the second string to be compared
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second
     */
    @Override
    public int compare(String key1, String key2) {
        String cleanKey1 = key1.replaceAll("[\\s\\-+]", "");
        String cleanKey2 = key2.replaceAll("[\\s\\-+]", "");
        if (cleanKey1.compareTo(cleanKey2) == 0) {
            return key2.compareTo(key1);
        }
        return cleanKey1.compareTo(cleanKey2);
    }
}
