package hexlet.code;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two JSON or YAML files and shows the differences."
)
public class App implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", arity = "1", description = "Path to the first file.")
    private static String filepath1;

    @CommandLine.Parameters(index = "1", arity = "1", description = "Path to the second file.")
    private static String filepath2;

    @CommandLine.Option(names = {"-f", "--format"}, defaultValue = "stylish",
            description = "Output format [default: stylish]")
    private static String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    public static String getFilepath1() {
        return filepath1;
    }

    public static String getFilepath2() {
        return filepath2;
    }

    public static String getFormat() {
        return format;
    }

    @Override
    public Integer call() throws IOException {
        String diff = Differ.generate(getFilepath1(), getFilepath2(), getFormat());
        System.out.println(diff);
        return null;
    }
}
