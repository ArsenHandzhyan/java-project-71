package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;

import static hexlet.code.StylishFormatter.format;


@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two JSON files and shows the differences.")

public class App implements Callable<Integer> {
    public static String filepath1;

    static {
        try {
            filepath1 = getFile("/home/arsen/IdeaProjects/java-project-71/app/src/test/resources/file1.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String filepath2;

    static {
        try {
            filepath2 = getFile("/home/arsen/IdeaProjects/java-project-71/app/src/test/resources/file2.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFile(String pathFile) throws Exception {
        Path path = Paths.get(pathFile).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' does not exist");
        }
        return Files.readString(path);
    }

    @CommandLine.Parameters(paramLabel = "file1.json", description = "path to the first JSON file")
    File filepath11;

    @CommandLine.Parameters(paramLabel = "file2.json", description = "path to the second JSON file")
    File filepath22;

    @CommandLine.Option(names = {"-f", "--format"}, description = "Формат вывода [по умолчанию: stylish]")
    String format = "stylish";

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json1 = mapper.readTree(filepath1);
        JsonNode json2 = mapper.readTree(filepath2);
        Map<String, String> diff = generateDifference(json1, json2, "");
        String outDiff = generateDiffOutput(diff);
        System.out.println(outDiff);
        return 0;
    }


    public static Map<String, String> generateDifference(JsonNode json1, JsonNode json2, String curPath) {
        Map<String, String> diff = new HashMap<>();
        Iterator<String> fieldNames = json1.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String path = buildPath(curPath, fieldName);
            if (!json2.has(fieldName)) {
                diff.put("- " + path, format(json1.get(fieldName)));
            } else if (!json1.get(fieldName).equals(json2.get(fieldName))) {
                if (json1.get(fieldName).isObject() && json2.get(fieldName).isObject()) {
                    Map<String, String> nestedDiff = generateDifference(
                            json1.get(fieldName),
                            json2.get(fieldName),
                            path
                    );
                    diff.putAll(nestedDiff);
                } else {
                    diff.put("- " + path, format(json1.get(fieldName)));
                    diff.put("+ " + path, format(json2.get(fieldName)));
                }
            } else {
                diff.put("  " + path, format(json1.get(fieldName)));
            }
        }
        Iterator<String> remainingFieldNames = json2.fieldNames();
        while (remainingFieldNames.hasNext()) {
            String fieldName = remainingFieldNames.next();
            String path = buildPath(curPath, fieldName);
            if (!json1.has(fieldName)) {
                diff.put("+ " + path, format(json2.get(fieldName)));
            }
        }
        return diff;
    }


    private static String buildPath(String curPath, String fieldName) {
        if (curPath.isEmpty()) {
            return fieldName;
        }
        return curPath + "." + fieldName;
    }

    public static String generateDiffOutput(Map<String, String> diff) {
        List<Map.Entry<String, String>> sortedDiffEntries = new ArrayList<>(diff.entrySet());
        sortedDiffEntries.sort((entry1, entry2) -> {
            String key1 = entry1.getKey().replaceAll("[^a-zA-Z0-9]", "").trim();
            String key2 = entry2.getKey().replaceAll("[^a-zA-Z0-9]", "").trim();
            int keyComparison = key1.compareToIgnoreCase(key2);
            if (keyComparison != 0) {
                return keyComparison;
            }
            char sign1 = entry1.getKey().charAt(0);
            char sign2 = entry2.getKey().charAt(0);
            return Character.compare(sign2, sign1);
        });
        StringBuilder output = new StringBuilder("{\n");
        for (Map.Entry<String, String> entry : sortedDiffEntries) {
            String key = entry.getKey();
            String value = entry.getValue();
            output.append(key).append(": ").append(value).append("\n");
        }
        output.append("}\n");
        return output.toString();
    }
}
