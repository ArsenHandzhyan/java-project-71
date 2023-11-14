package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());


    public Optional<JsonNode> parse(File file) {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        ObjectMapper jsonWriter = new ObjectMapper();
        String extension = getFileExtension(file).orElse("");
        try {
            if (extension.equals("json")) {
                return Optional.of(yamlReader.readTree(file));
            } else if (extension.equals("yaml") || extension.equals("yml")) {
                JsonNode jsonNodeTree = yamlReader.readTree(file);
                String jsonString = jsonWriter.writeValueAsString(jsonNodeTree);
                return Optional.of(jsonWriter.readTree(jsonString));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problem parsing the file", e);
        }
        return Optional.empty();
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
