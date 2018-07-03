package my.application.service.ladbrokes;

import my.application.model.PageNotOpenException;
import my.application.service.BaseBettingWebsite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static my.application.enums.DriverType.CHROME;
import static org.openqa.selenium.Keys.RETURN;

public class LadbrokesWebsite extends BaseBettingWebsite {

    private static final String LADBROKES_URL = "https://www.ladbrokes.com.au/";
    private static final String LADBROKES_HORSES_URL = "https://www.ladbrokes.com.au/racing/horses/";
    private static final String LADBROKES_HARNESS_URL = "https://www.ladbrokes.com.au/racing/harness/";
    private static final String LADBROKES_GREYHOUNDS_URL = "https://www.ladbrokes.com.au/racing/greyhounds/";

    private static final int WAIT_TIME = 1000;

    public LadbrokesWebsite() {
        super(CHROME);
    }

    public void login() throws InterruptedException {
        if (!isPageOpen()) {
            throw new PageNotOpenException("Ladbrokes Page not open");
        }
        WebElement loginForm = webDriver.findElement(By.id("form_users_login"));
        WebElement userNameInput = loginForm.findElement(By.id("userauth_username"));
        WebElement passwordInput = loginForm.findElement(By.id("userauth_password"));
        passwordInput.sendKeys("Password");
        userNameInput.sendKeys("Alizkat");
        Thread.sleep(WAIT_TIME);
        userNameInput.sendKeys(RETURN);

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loggedin")));
    }

    public void openPage() {
        super.openPage(LADBROKES_URL);
    }

    public boolean getLoggedIn() {
        if (!isPageOpen()) {
            throw new PageNotOpenException("Ladbrokes Page not open");
        }
        WebElement loginDiv = webDriver.findElement(By.id("loggedin"));
        if (loginDiv != null) {
            return loginDiv.isDisplayed();
        }
        return false;
    }


}
