package my.application.browser.service;

public interface BettingWebsite {
    void openPage(String url);
    public void login() throws InterruptedException;
    public boolean getLoggedIn();
}
