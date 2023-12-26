package hexlet.code.formatters;

public class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String format) {
        super("Unsupported format: " + format);
    }
}
