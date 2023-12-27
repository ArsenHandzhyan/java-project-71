package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.KeyComparatorForJsonFormat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter {

    public static String format(List<Map<String, Object>> diff) throws JsonProcessingException {
        Map<String, Object> combinedMap = new LinkedHashMap<>();

        diff.sort(new KeyComparatorForJsonFormat());
        for (int i = 0; i < diff.size(); i++) {
            combinedMap.put("change" + i, diff.get(i));
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(combinedMap);
    }
}
