package me.anxuiz.settings;

public class TypeParseException extends Exception {
    private static final long serialVersionUID = 1L;

    public TypeParseException() {
        super();
    }

    public TypeParseException(String message) {
        super(message);
    }

    public TypeParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeParseException(Throwable cause) {
        super(cause);
    }
}
