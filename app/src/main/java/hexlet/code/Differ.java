package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

public final class Differ {
    private final Parser parser;

    public Differ() {
        this.parser = new Parser();
    }

    Parser getParser() {
        return this.parser;
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        Differ differ = new Differ();
        JsonNode json1 = parseJson(filepath1, differ);
        JsonNode json2 = parseJson(filepath2, differ);
        Map<String, String> diff = generateDifference(json1, json2);

        List<Map.Entry<String, String>> sortedDiffEntries = createSortedList(diff);

        return buildOutputString(sortedDiffEntries, format);
    }

    private static JsonNode parseJson(String filepath, Differ differ) throws IOException {
        return differ.getParser().parse(new File(filepath)).orElseThrow(() ->
                new RuntimeException("File cannot be parsed: " + filepath));
    }

    private static List<Map.Entry<String, String>> createSortedList(Map<String, String> diff) {
        List<Map.Entry<String, String>> sortedDiffEntries = new ArrayList<>(diff.entrySet());
        sortedDiffEntries.sort((entry1, entry2) -> {
            String key1 = entry1.getKey().replaceAll("[^a-zA-Z0-9]", "").trim();
            String key2 = entry2.getKey().replaceAll("[^a-zA-Z0-9]", "").trim();
            int keyComparison = key1.compareToIgnoreCase(key2);
            if (keyComparison != 0) {
                return keyComparison;
            }
            char sign1 = entry1.getKey().charAt(0);
            char sign2 = entry2.getKey().charAt(0);
            return Character.compare(sign2, sign1);
        });
        return sortedDiffEntries;
    }

    private static String buildOutputString(List<Map.Entry<String, String>> sortedDiffEntries, String format) {
        StringBuilder output = new StringBuilder("{\n");
        for (Map.Entry<String, String> entry : sortedDiffEntries) {
            String key = entry.getKey();
            String value = entry.getValue();
            output.append("  ").append(key).append(": ").append(value).append("\n");
        }
        output.append("}");
        return Formatter.formatterSelection(format, output.toString());
    }

    private static Map<String, String> generateDifference(JsonNode json1, JsonNode json2) {
        String curPath = "";
        Map<String, String> diff = new HashMap<>();
        Iterator<String> fieldNames = json1.fieldNames();

        processFieldsInJson1(json1, json2, curPath, diff, fieldNames);
        processFieldsInJson2(json1, json2, curPath, diff, json2.fieldNames());

        return diff;
    }

    private static void processFieldsInJson1(JsonNode json1, JsonNode json2, String curPath, Map<String, String> diff, Iterator<String> fieldNames) {
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String path = buildPath(curPath, fieldName);
            if (!json2.has(fieldName)) {
                diff.put("- " + path, json1.get(fieldName).toString());
            } else if (!json1.get(fieldName).equals(json2.get(fieldName))) {
                if (json1.get(fieldName).isObject() && json2.get(fieldName).isObject()) {
                    diff.putAll(generateDifference(json1.get(fieldName), json2.get(fieldName)));
                } else {
                    diff.put("- " + path, json1.get(fieldName).toString());
                    diff.put("+ " + path, json2.get(fieldName).toString());
                }
            } else {
                diff.put("  " + path, json1.get(fieldName).toString());
            }
        }
    }

    private static void processFieldsInJson2(JsonNode json1, JsonNode json2, String curPath, Map<String, String> diff, Iterator<String> remainingFieldNames) {
        while (remainingFieldNames.hasNext()) {
            String fieldName = remainingFieldNames.next();
            String path = buildPath(curPath, fieldName);
            if (!json1.has(fieldName)) {
                diff.put("+ " + path, json2.get(fieldName).toString());
            }
        }
    }

    private static String buildPath(String curPath, String fieldName) {
        if (curPath.isEmpty()) {
            return fieldName;
        }
        return curPath + "." + fieldName;
    }
}
