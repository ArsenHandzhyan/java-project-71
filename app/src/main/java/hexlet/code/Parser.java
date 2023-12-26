package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parseJson(String content) throws IOException {
        var objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, new TypeReference<>() {});
    }

    public static Map<String, Object> parseYaml(String content) {
        var yaml = new Yaml();
        return yaml.load(content);
    }
}
