package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0; // тут вы должны реализовать логику вашего приложения
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}