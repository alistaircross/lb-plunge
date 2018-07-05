package my.application.model;

public class CannotFindRaceException  extends RuntimeException{
    public CannotFindRaceException(String message) {
        super(message);
    }
}
