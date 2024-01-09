package hexlet.code;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two JSON or YAML files and shows the differences."
)
public class App implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", arity = "1", description = "Path to the first file.")
    private String filepath1;

    @CommandLine.Parameters(index = "1", arity = "1", description = "Path to the second file.")
    private String filepath2;

    @CommandLine.Option(names = {"-f", "--format"}, defaultValue = "stylish",
            description = "Output format [default: stylish]")
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public final Integer call() throws Exception {
        System.out.println(Differ.generate(filepath1, filepath2, format));
        return null;
    }
}
