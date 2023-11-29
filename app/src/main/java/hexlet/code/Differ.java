package hexlet.code;

import java.io.IOException;
import java.util.Map;

public final class Differ {

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        Map<String, Object> mapJson1 = parseJsonOrYaml(filepath1);
        Map<String, Object> mapJson2 = parseJsonOrYaml(filepath2);
        return TreeBuilder.buildTree(mapJson1, mapJson2, format);
    }

    private static Map<String, Object> parseJsonOrYaml(String filepath) throws IOException {
        return Parser.parse(filepath);
    }
}
