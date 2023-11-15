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
    @CommandLine.Parameters(index = "0", description = "Path to the first file.")
    static String filepath1;
    @CommandLine.Parameters(index = "1", description = "Path to the second file.")
    static String filepath2;
    @CommandLine.Option(names = {"-f", "--format"}, description = "Output format [default: stylish]")
    static String format = "stylish";


    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        String diff = Differ.generate(filepath1, filepath2, format);
        System.out.println(diff);
        return 0;
    }
}
