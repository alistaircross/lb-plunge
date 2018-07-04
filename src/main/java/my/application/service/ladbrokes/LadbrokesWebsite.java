package my.application.service.ladbrokes;

import my.application.model.PageNotOpenException;
import my.application.service.BaseBettingWebsite;
import my.application.utils.AuthHelper;
import my.application.view.ladbrokes.horse.LbHorseRace;
import my.application.view.ladbrokes.horse.LbHorseRaceBox;
import my.application.view.ladbrokes.horse.LbHorseRaceEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static my.application.enums.DriverType.CHROME;
import static org.openqa.selenium.Keys.RETURN;

public class LadbrokesWebsite extends BaseBettingWebsite {
    public static final String LOCAL_RACE_HEADING = "AUSTRALIA & NEW ZEALAND HORSE RACING";
    public static final String INTERNATIONAL_RACE_HEADING = "INTERNATIONAL HORSE RACING";
    private static final String LADBROKES_URL = "https://www.ladbrokes.com.au/";
    private static final String LADBROKES_HORSES_URL = "https://www.ladbrokes.com.au/racing/horses/";
    private static final String LADBROKES_HARNESS_URL = "https://www.ladbrokes.com.au/racing/harness/";
    private static final String LADBROKES_GREYHOUNDS_URL = "https://www.ladbrokes.com.au/racing/greyhounds/";
    private static final int WAIT_TIME = 1000;
    private AuthHelper authHelper;
    private HashMap<String, LbHorseRaceBox> horseRaces;


    public LadbrokesWebsite() {
        super(CHROME);
        authHelper = new AuthHelper();
    }

    public void login() throws InterruptedException {
        if (!isPageOpen()) {
            throw new PageNotOpenException("Ladbrokes Page not open");
        }
        WebElement loginForm = webDriver.findElement(By.id("form_users_login"));
        WebElement userNameInput = loginForm.findElement(By.id("userauth_username"));
        WebElement passwordInput = loginForm.findElement(By.id("userauth_password"));
        passwordInput.sendKeys(authHelper.getPassword());
        userNameInput.sendKeys(authHelper.getUserName());
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

    public void placeHorseBet(String globalLocation, String location, Integer raceNumber, Integer horseNumber) {
        getAllHorseRaces();
        LbHorseRaceBox horseRaceBox = horseRaces.get(globalLocation);
        LbHorseRaceEvent event = horseRaceBox.getHorseRaceEvent(location);
        LbHorseRace race = event.getRaceNumber(raceNumber);
        race.clickRace();
    }

    private void getAllHorseRaces() {
        super.openPage(LADBROKES_HORSES_URL);
        horseRaces = new HashMap<String, LbHorseRaceBox>();
        WebElement racesDiv = webDriver.findElement(By.id("racesToday"));
        List<WebElement> raceBoxes = racesDiv.findElements(By.className("fullbox"));
        for (WebElement fullEventBox : raceBoxes) {
            LbHorseRaceBox box = new LbHorseRaceBox(fullEventBox);
            WebElement raceHeading = fullEventBox.findElement(By.className("fullbox-hdr"));
            if (raceHeading != null) {
                horseRaces.put(raceHeading.getText(), box);
            }
        }
    }
}
