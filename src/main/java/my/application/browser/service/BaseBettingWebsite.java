package my.application.browser.service;

import my.application.browser.enums.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseBettingWebsite implements BettingWebsite {
    protected WebDriver webDriver;
    private boolean pageOpen;
    private WebDriverWait wait = null;
    public BaseBettingWebsite(DriverType driverType) {
        setWebDriver(driverType);
    }

    private void setWebDriver(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                setChromeWebDriver();
                break;
            case FIREFOX:
                break;
        }
    }

    private void setChromeWebDriver() {
        System.setProperty("webdriver.chrome.driver", ".\\src\\resources\\chromedriver.exe");
        webDriver  = new ChromeDriver();
    }

    public void openPage(String url){
        webDriver.get(url);
        pageOpen = true;
    }

    public void closePage(){
        webDriver.close();
        pageOpen = false;
    }

    public boolean isPageOpen(){
        return pageOpen;
    }

    public WebDriverWait getWebDriverWait(){
        if(wait == null) {
            wait = new WebDriverWait(webDriver, 1);
        }
        return wait;
    }
}