package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    private final ObjectMapper yamlMapper;
    private final ObjectMapper jsonMapper;

    public Parser() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        this.jsonMapper = new ObjectMapper();
    }

    public Optional<JsonNode> parse(File file) {
        String extension = getFileExtension(file).orElse("");
        try {
            if (extension.equals("json")) {
                return Optional.of(jsonMapper.readTree(file));
            } else if (extension.equals("yaml") || extension.equals("yml")) {
                return Optional.of(yamlMapper.readTree(file));
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