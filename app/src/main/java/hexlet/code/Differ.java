package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public final class Differ {

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        Map<String, Object> mapJson1 = parseJsonOrYaml(filepath1);
        Map<String, Object> mapJson2 = parseJsonOrYaml(filepath2);
        Map<String, Object> diff = TreeBuilder.buildTree(mapJson1, mapJson2);
        return Formatter.formatterSelection(format, diff);
    }

    private static Map<String, Object> parseJsonOrYaml(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Path file = resolvePath(path);
        String fileExtension = getFileExtension(file);
        return Parser.parse(fileExtension, file);
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
