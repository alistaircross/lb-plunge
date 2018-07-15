package my.application.browser.model;

public class LoginTimeoutException extends RuntimeException {
    public LoginTimeoutException() {
        super("There was a problem trying to Login.");
    }
}

