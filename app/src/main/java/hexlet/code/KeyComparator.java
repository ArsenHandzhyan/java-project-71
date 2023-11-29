package hexlet.code;

import java.util.Comparator;

public class KeyComparator implements Comparator<String> {
    @Override
    public int compare(String key1, String key2) {
        // Убираем знаки +, -, и пробелы при сравнении ключей
        String cleanKey1 = key1.replaceAll("[\\s\\-+]", "");
        String cleanKey2 = key2.replaceAll("[\\s\\-+]", "");
        if (cleanKey1.compareTo(cleanKey2) == 0) {
            return key2.compareTo(key1);
        }
        // Сравниваем чистые ключи
        return cleanKey1.compareTo(cleanKey2);
    }
}
