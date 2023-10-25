package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two JSON files and shows the differences.")
public class App implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "file1.json", description = "path to the first JSON file")
    File filepath1;

    @CommandLine.Parameters(paramLabel = "file2.json", description = "path to the second JSON file")
    File filepath2;
    @CommandLine.Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

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
        String diffOutput = generateDiffOutput(diff);
        System.out.println(diffOutput);
        return 0;
    }

    private Map<String, String> generateDifference(JsonNode json1, JsonNode json2, String curPath) {
        Map<String, String> diff = new TreeMap<>();
        Iterator<String> fieldNames = json1.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String path = buildPath(curPath, fieldName);

            if (!json2.has(fieldName)) {
                diff.put("- " + path, json1.get(fieldName).asText());
            } else if (!json1.get(fieldName).equals(json2.get(fieldName))) {
                if (json1.get(fieldName).isObject() && json2.get(fieldName).isObject()) {
                    Map<String, String> nestedDiff = generateDifference(
                            json1.get(fieldName),
                            json2.get(fieldName),
                            path
                    );
                    diff.putAll(nestedDiff);
                } else {
                    diff.put("- " + path, json1.get(fieldName).asText());
                    diff.put("+ " + path, json2.get(fieldName).asText());
                }
            } else {
                diff.put("  " + path, json1.get(fieldName).asText());
            }
        }

        Iterator<String> remainingFieldNames = json2.fieldNames();
        while (remainingFieldNames.hasNext()) {
            String fieldName = remainingFieldNames.next();
            String path = buildPath(curPath, fieldName);

            if (!json1.has(fieldName)) {
                diff.put("+ " + path, json2.get(fieldName).asText());
            }
        }

        return diff;
    }

    private String buildPath(String curPath, String fieldName) {
        if (curPath.isEmpty()) {
            return fieldName;
        }
        return curPath + "." + fieldName;
    }

    private String generateDiffOutput(Map<String, String> diff) {
        StringBuilder output = new StringBuilder("{\n");

        for (Map.Entry<String, String> entry : diff.entrySet()) {
            output.append("  ").append(entry.getKey());
            output.append(": ");
            output.append(entry.getValue());
            output.append("\n");
        }
        output.append("}\n");
        return output.toString();
    }
}
