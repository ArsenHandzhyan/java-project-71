package hexlet.code;

import java.util.Comparator;

public final class KeyComparator implements Comparator<String> {
    /**
     * Compares two strings for sorting, ignoring whitespace and hyphens.
     *
     * @param s1 the first string to be compared
     * @param s2 the second string to be compared
     * @return a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second
     */
    @Override
    public int compare(String s1, String s2) {
        String key1 = cleaningString(s1);
        String key2 = cleaningString(s2);
        if (key1.compareTo(key2) == 0) {
            return key2.compareTo(key1);
        }
        return key1.compareTo(key2);
    }

    public static String cleaningString(String s) {
        String[] parts1 = s.split(":");
        String s1 = parts1[0];
        String[] parts2 = s1.split(" ");
        if (parts2.length > 1) {
            s1 = parts2[1];
        } else {
            s1 = parts2[0];
        }
        return s1;
    }
}
