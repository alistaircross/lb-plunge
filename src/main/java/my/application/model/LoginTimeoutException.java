package my.application.model;

public class LoginTimeoutException extends RuntimeException {
    public LoginTimeoutException() {
        super("There was a problem trying to Login.");
    }
}

