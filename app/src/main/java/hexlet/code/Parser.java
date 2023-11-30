package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String fileExtension, Path file) throws IOException {
        if ("json".equalsIgnoreCase(fileExtension)) {
            return parseJson(file);
        } else if ("yml".equalsIgnoreCase(fileExtension) || "yaml".equalsIgnoreCase(fileExtension)) {
            return parseYml(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + fileExtension);
        }
    }

    public static Map<String, Object> parseJson(Path filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(Map.class, String.class, Object.class);
        return objectMapper.readValue(filePath.toFile(), mapType);

    }

    public static Map<String, Object> parseYml(Path filePath) throws IOException {
        Yaml yaml = new Yaml();
        try (var inputStream = Files.newInputStream(filePath)) {
            return yaml.load(inputStream);
        }
    }
}

