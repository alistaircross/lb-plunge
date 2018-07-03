package my.application.model;

public class PageNotOpenException extends RuntimeException {
    public PageNotOpenException(String message) {
        super(message);
    }
}
