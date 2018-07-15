package my.application.browser.model;

public class PageNotOpenException extends RuntimeException {
    public PageNotOpenException(String message) {
        super(message);
    }
}
