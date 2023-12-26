package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.UnsupportedFormatException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public final class Differ {

    public static String generate(String filepath1, String filepath2) {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) {
        String content1;
        String content2;
        try {
            content1 = readFileContent(filepath1);
            content2 = readFileContent(filepath2);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        Map<String, Object> map1;
        try {
            map1 = parse(content1, getFileExtension(Path.of(filepath1)));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        Map<String, Object> map2;
        try {
            map2 = parse(content2, getFileExtension(Path.of(filepath2)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Map<String, Object>> diff = TreeBuilder.buildTree(map1, map2);
        try {
            return Formatter.formatterSelection(format, diff); // This will also need refactoring to match new Formatter requirements.
        } catch (JsonProcessingException | UnsupportedFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFileContent(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Path resolvedPath = resolvePath(path);
        return Files.readString(resolvedPath);
    }

    private static Map<String, Object> parse(String content, String fileType) throws IOException {
        if ("json".equalsIgnoreCase(fileType)) {
            return Parser.parseJson(content);
        } else if ("yml".equalsIgnoreCase(fileType) || "yaml".equalsIgnoreCase(fileType)) {
            return Parser.parseYaml(content);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }

    private static Path resolvePath(Path path) {
        if (!path.isAbsolute()) {
            // Если путь относительный, преобразовываем его в абсолютный
            Path currentPath = Paths.get("");
            path = currentPath.resolve(path).normalize();
        }
        return path;
    }

    private static String getFileExtension(Path file) {
        String fileName = file.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
