package hexlet.code.formatters;

public class Formatter {
    public static String formatterSelection(String format, String diff) {
        return switch (format.toLowerCase()) {
            case "plain" -> PlainFormatter.format(diff);
            case "json" -> JsonFormatter.format(diff);
            default -> StylishFormatter.format(diff);
        };
    }
}
