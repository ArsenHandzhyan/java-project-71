package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;

public class Differ {
    private final Parser parser;

    public Differ() {
        this.parser = new Parser();
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        Differ differ = new Differ();
        JsonNode json1 = differ.parser.parse(new File(filepath1)).orElseThrow(() ->
                new RuntimeException("File cannot be parsed: " + filepath1));
        JsonNode json2 = differ.parser.parse(new File(filepath2)).orElseThrow(() ->
                new RuntimeException("File cannot be parsed: " + filepath1));
        Map<String, String> diff = generateDifference(json1, json2);
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
        StringBuilder output = new StringBuilder("{\n");
        for (Map.Entry<String, String> entry : sortedDiffEntries) {
            String key = entry.getKey();
            String value = entry.getValue();
            output.append("  ").append(key).append(": ").append(value).append("\n");
        }
        output.append("}");

        String trimmedOutput = output.toString().trim();  // Removes any trailing newlines
        return Formatter.formatterSelection(format, trimmedOutput);  // Pass the trimmed output to the formatter
    }

    private static Map<String, String> generateDifference(JsonNode json1, JsonNode json2) {
        String curPath = "";
        Map<String, String> diff = new HashMap<>();
        Iterator<String> fieldNames = json1.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String path = buildPath(curPath, fieldName);
            if (!json2.has(fieldName)) {
                diff.put("- " + path, json1.get(fieldName).toString());
            } else if (!json1.get(fieldName).equals(json2.get(fieldName))) {
                if (json1.get(fieldName).isObject() && json2.get(fieldName).isObject()) {
                    Map<String, String> nestedDiff = generateDifference(
                            json1.get(fieldName),
                            json2.get(fieldName)
                    );
                    diff.putAll(nestedDiff);
                } else {
                    diff.put("- " + path, json1.get(fieldName).toString());
                    diff.put("+ " + path, json2.get(fieldName).toString());
                }
            } else {
                diff.put("  " + path, json1.get(fieldName).toString());
            }
        }
        Iterator<String> remainingFieldNames = json2.fieldNames();
        while (remainingFieldNames.hasNext()) {
            String fieldName = remainingFieldNames.next();
            String path = buildPath(curPath, fieldName);
            if (!json1.has(fieldName)) {
                diff.put("+ " + path, json2.get(fieldName).toString());
            }
        }
        return diff;
    }

    private static String buildPath(String curPath, String fieldName) {
        if (curPath.isEmpty()) {
            return fieldName;
        }
        return curPath + "." + fieldName;
    }
}
