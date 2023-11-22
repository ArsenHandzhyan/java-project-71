package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

public final class Differ {

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        JsonNode json1 = parseJsonOrYaml(filepath1);
        JsonNode json2 = parseJsonOrYaml(filepath2);
        return TreeBuilder.buildTree(json1, json2, format);
    }

    private static JsonNode parseJsonOrYaml(String filepath) throws IOException {
        return Parser.parse(new File(filepath)).orElseThrow(() ->
                new RuntimeException("File cannot be parsed: " + filepath));
    }
}
