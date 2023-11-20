package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public final class Parser {
    public Optional<JsonNode> parse(File file) throws IOException {
        String extension = getFileExtension(file).orElse("");
        return switch (extension) {
            case "json" -> Optional.of(parseJson(file));
            case "yaml", "yml" -> Optional.of(parseYaml(file));
            default -> throw new IllegalArgumentException("Unsupported file type.");
        };
    }

    private JsonNode parseJson(File file) throws IOException {
        ObjectMapper jsonReader = new ObjectMapper();
        return jsonReader.readTree(file);
    }

    private JsonNode parseYaml(File file) throws IOException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        ObjectMapper jsonWriter = new ObjectMapper();
        JsonNode jsonNodeTree = yamlReader.readTree(file);
        if (jsonNodeTree.isArray()) {
            ObjectNode newRoot = jsonWriter.createObjectNode();
            jsonNodeTree.forEach(node -> newRoot.setAll((ObjectNode) node));
            jsonNodeTree = newRoot;
        }
        return jsonNodeTree;
    }

    private Optional<String> getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return Optional.empty();
        }
        return Optional.of(name.substring(lastIndexOf + 1));
    }
}
