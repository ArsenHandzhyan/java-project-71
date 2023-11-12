package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.code.formatters.Formatter;
import picocli.CommandLine;
import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two JSON or YAML files and shows the differences."
)
public class App implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Path to the first file.")
    static File filepath1;

    @CommandLine.Parameters(index = "1", description = "Path to the second file.")
    static File filepath2;

    @CommandLine.Option(names = {"-f", "--format"}, description = "Output format [default: stylish]")
    static String format = "stylish";

    private final Parser parser;

    public App() {
        this.parser = new Parser();
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        JsonNode json1 = parser.parse(filepath1).orElseThrow(() -> new RuntimeException("File cannot be parsed: " + filepath1));
        JsonNode json2 = parser.parse(filepath2).orElseThrow(() -> new RuntimeException("File cannot be parsed: " + filepath2));
        String diff = Differ.generate(json1, json2, format);
        String formattedDiff = Formatter.formatterSelection(format, diff);
        System.out.println(formattedDiff);
        return 0;
    }
}