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
    static String filepath1;

    @CommandLine.Parameters(index = "1", arity = "1", description = "Path to the second file.")
    static String filepath2;

    @CommandLine.Option(names = {"-f", "--format"}, defaultValue = "stylish",
            description = "Output format [default: stylish]")
    static String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        String diff = Differ.generate(filepath1, filepath2, format);
        System.out.println(diff);
        return 0;
    }
}
