package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two JSON files and shows the differences.")

public class App implements Callable<Integer> {

    @CommandLine.Parameters(paramLabel = "file1.json", description = "path to the first JSON file")
    static File filepath1;

    @CommandLine.Parameters(paramLabel = "file2.json", description = "path to the second JSON file")
    static File filepath2;

    @CommandLine.Option(names = {"-f", "--format"}, description = "Format out [default: stylish]")
    static String format = "stylish";

    private final Parser yamlParser;

    public App() {
        this.yamlParser = new Parser();
    }

    @Override
    public Integer call() throws IOException {
        JsonNode json1 = yamlParser.parseYaml(filepath1).orElseThrow(() -> new RuntimeException("File cannot be parsed"));
        JsonNode json2 = yamlParser.parseYaml(filepath2).orElseThrow(() -> new RuntimeException("File cannot be parsed"));
        String diff = Differ.generate(json1, json2, format);
        System.out.println(diff);
        return 0;
    }
}
