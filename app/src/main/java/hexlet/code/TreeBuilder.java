package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

public final class TreeBuilder {
    public static String buildTree(JsonNode json1, JsonNode json2, String format) throws JsonProcessingException {
        Map<String, String> diff = generateDifference(json1, json2);
        List<Map.Entry<String, String>> sortedDiffEntries = createSortedList(diff);
        return buildOutputString(sortedDiffEntries, format);
    }

    private static Map<String, String> generateDifference(JsonNode json1, JsonNode json2) {
        Map<String, String> diff = new HashMap<>();
        processFieldsInJson(json1, json2, diff, json1.fieldNames(), json2.fieldNames());
        return diff;
    }

    private static void processFieldsInJson(JsonNode json1, JsonNode json2, Map<String, String> diff,
                                            Iterator<String> fieldNames1, Iterator<String> fieldNames2) {
        String curPath = "";
        while (fieldNames1.hasNext()) {
            String fieldName = fieldNames1.next();
            String path = buildPath(curPath, fieldName);
            diff.putAll(processField(json1, json2, fieldName, path));
        }
        while (fieldNames2.hasNext()) {
            String fieldName = fieldNames2.next();
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

    private static Map<String, String> processField(JsonNode json1, JsonNode json2, String fieldName, String path) {
        Map<String, String> diff = new HashMap<>();
        if (!json2.has(fieldName)) {
            diff.put("- " + path, json1.get(fieldName).toString());
        } else {
            diff.putAll(getDifference(json1, json2, fieldName, path));
        }
        return diff;
    }

    private static Map<String, String> getDifference(JsonNode json1, JsonNode json2, String fieldName,
                                                     String path) {
        Map<String, String> diff = new HashMap<>();
        if (!json1.get(fieldName).equals(json2.get(fieldName))) {
            if (json1.get(fieldName).isObject() && json2.get(fieldName).isObject()) {
                diff.putAll(generateDifference(json1.get(fieldName), json2.get(fieldName)));
            } else {
                diff.put("- " + path, json1.get(fieldName).toString());
                diff.put("+ " + path, json2.get(fieldName).toString());
            }
        } else {
            diff.put("  " + path, json1.get(fieldName).toString());
        }
        return diff;
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

    private static String buildOutputString(List<Map.Entry<String, String>> sortedDiffEntries,
                                            String format) throws JsonProcessingException {
        StringBuilder output = new StringBuilder("{\n");
        for (Map.Entry<String, String> entry : sortedDiffEntries) {
            String key = entry.getKey();
            String value = entry.getValue();
            output.append(" ").append(key).append(": ").append(value).append("\n");
        }
        output.append("}");
        return Formatter.formatterSelection(format, output.toString());
    }
}
