package hexlet.code;

import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.UnsupportedFormatException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public final class Differ {
    public static String generate(String filepath1, String filepath2) {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) {
        try {
            var content1 = readFileContent(filepath1);
            var content2 = readFileContent(filepath2);
            var map1 = parse(content1, getFileExtension(filepath1));
            var map2 = parse(content2, getFileExtension(filepath2));

            var diff = TreeBuilder.buildTree(map1, map2);
            return Formatter.formatterSelection(format, diff);
        } catch (IOException e) {
            throw new RuntimeException("Error processing input file", e);
        } catch (UnsupportedFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> parse(String content, String fileType) throws IOException {
        return switch (fileType) {
            case "json" -> Parser.parseJson(content);
            case "yml", "yaml" -> Parser.parseYaml(content);
            default -> throw new IllegalArgumentException("Unsupported file type: " + fileType);
        };
    }

    private static String readFileContent(String filepath) throws IOException {
        var path = Paths.get(filepath);
        return Files.readString(resolvePath(path));
    }

    private static String getFileExtension(String filename) {
        var file = Paths.get(filename);
        var name = file.toString();
        var lastIndexOfDot = name.lastIndexOf('.');
        return lastIndexOfDot != -1 ? name.substring(lastIndexOfDot + 1) : "";
    }

    private static java.nio.file.Path resolvePath(java.nio.file.Path path) {
        return path.toAbsolutePath().normalize();
    }
}
