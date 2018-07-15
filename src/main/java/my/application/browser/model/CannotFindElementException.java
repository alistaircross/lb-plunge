package my.application.browser.model;

public class CannotFindElementException extends RuntimeException{
    public CannotFindElementException(String message) {
        super(message);
    }
}
