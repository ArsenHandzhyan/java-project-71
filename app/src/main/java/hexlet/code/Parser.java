package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Parser {

    private final ObjectMapper objectMapper;

    public Parser() {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    public Optional<JsonNode> parseYaml(File file) {
        try {
            return Optional.of(objectMapper.readTree(file));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
