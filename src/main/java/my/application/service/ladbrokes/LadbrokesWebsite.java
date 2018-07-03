package my.application.service.ladbrokes;

import my.application.model.PageNotOpenException;
import my.application.service.BaseBettingWebsite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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
        passwordInput.sendKeys("Reddog88");
        userNameInput.sendKeys("Alizkat");
        Thread.sleep(WAIT_TIME);
        userNameInput.sendKeys(RETURN);

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loggedin")));
    }

    public void openHomePage() {
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

    public void getAllHorseRaces(){
        super.openPage(LADBROKES_HORSES_URL);
        WebElement racesDiv = webDriver.findElement(By.id("racesToday"));
        List<WebElement> raceBoxes = racesDiv.findElements(By.className("fullbox"));
        for (WebElement fullEventBox : raceBoxes) {
            WebElement raceHeading = fullEventBox.findElement(By.className("fullbox-hdr"));
            System.out.println("Top level: " + raceHeading.getText());
            getRacesForLocation(fullEventBox);
        }
    }

    private void getRacesForLocation(WebElement fullEventBox) {
        List<WebElement> meetings = fullEventBox.findElements(By.className("meeting"));
        for (WebElement meeting : meetings) {
            WebElement raceLocationCell = meeting.findElement(By.className("racing-location"));
            System.out.println("Location: " + raceLocationCell.getText());
            List<WebElement> races = meeting.findElements(By.className("odds"));
            int raceCount = 1;
            for (WebElement race : races) {
                System.out.println("Race number : " +raceCount + "time or place:" + race.getText());
                raceCount++;
            }

        }
    }


}
