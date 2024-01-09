package hexlet.code;

import java.util.Comparator;

/**
 * A comparator for sorting strings based on extracted keys.
 * It is designed to be used for special formatting such as "stylish" or "plain" formats,
 * where the keys in the strings may be prefixed with whitespace, plus, or minus characters
 * as part of the formatting.
 * /
 * The comparator ignores these formatting characters and sorts based on the key name.
 */
public final class KeyComparator implements Comparator<String> {

    /**
     * Compares two strings using their extracted keys.
     *
     * @param s1 the first string to be compared.
     * @param s2 the second string to be compared.
     * @return a negative integer, zero, or a positive integer as the
     *         extracted key of the first string is less than, equal to,
     *         or greater than the extracted key of the second string.
     */
    @Override
    public int compare(String s1, String s2) {
        String cleanKey1 = extractKey(s1);
        String cleanKey2 = extractKey(s2);
        return cleanKey1.compareTo(cleanKey2);
    }

    /**
     * Extracts the key from a formatted line by removing any leading whitespace or
     * special formatting characters and then splitting the line at the colon.
     *
     * @param line the string line from which to extract the key.
     * @return the extracted key as a String.
     */
    private static String extractKey(String line) {
        // Strip off leading spaces and formatting characters (-, +), then split by colon and return the key part [0].
        return line.replaceAll("^[\\s\\-+]*", "").split(":")[0];
    }
}
