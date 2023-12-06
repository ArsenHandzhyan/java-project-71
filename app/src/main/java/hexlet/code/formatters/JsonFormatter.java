package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.KeyComparatorForJsonFormat;

import java.util.Map;
import java.util.TreeMap;

public class JsonFormatter {

    public static String format(Map<String, Object> diff) throws JsonProcessingException {
        TreeMap<String, Object> sortedMap = new TreeMap<>(new KeyComparatorForJsonFormat());
        sortedMap.putAll(diff);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(sortedMap);
    }
}
