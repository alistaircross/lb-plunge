package my.application.model;

public class CannotFindElementException extends RuntimeException{
    public CannotFindElementException(String message) {
        super(message);
    }
}
