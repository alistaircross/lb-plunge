package my.application.service.ladbrokes;

import my.application.enums.RaceType;
import my.application.model.CannotFindElementException;
import my.application.model.CannotFindRaceException;
import my.application.model.PageNotOpenException;
import my.application.service.BaseBettingWebsite;
import my.application.utils.AuthHelper;
import my.application.view.ladbrokes.horse.LbHorseRaceBox;
import my.application.view.ladbrokes.horse.LbHorseRacePage;
import my.application.view.ladbrokes.nexttojump.LbNextToJumpPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static my.application.enums.DriverType.CHROME;
import static my.application.enums.RaceType.HORSE;
import static org.openqa.selenium.Keys.RETURN;

public class LbWebsite extends BaseBettingWebsite {
    public static final String LOCAL_RACE_HEADING = "AUSTRALIA & NEW ZEALAND HORSE RACING";
    public static final String INTERNATIONAL_RACE_HEADING = "INTERNATIONAL HORSE RACING";
    private static final String LADBROKES_URL = "https://www.ladbrokes.com.au/";
    private static final String LADBROKES_HORSES_URL = "https://www.ladbrokes.com.au/racing/horses/";
    private static final String LADBROKES_HARNESS_URL = "https://www.ladbrokes.com.au/racing/harness/";
    private static final String LADBROKES_GREYHOUNDS_URL = "https://www.ladbrokes.com.au/racing/greyhounds/";
    private static final Log LOG = LogFactory.getLog(LbWebsite.class);

    private static final int WAIT_TIME = 600;
    private AuthHelper authHelper;
    private HashMap<String, LbHorseRaceBox> horseRaces;


    public LbWebsite() {
        super(CHROME);
        authHelper = new AuthHelper();
    }

    public void login() {
        if (!isPageOpen()) {
            throw new PageNotOpenException("Ladbrokes Page not open");
        }
        WebElement loginForm = webDriver.findElement(By.id("form_users_login"));
        WebElement userNameInput = loginForm.findElement(By.id("userauth_username"));
        WebElement passwordInput = loginForm.findElement(By.id("userauth_password"));
        passwordInput.sendKeys(authHelper.getPassword());
        userNameInput.sendKeys(authHelper.getUserName());
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            LOG.error("There was a problem trying to wait for login");
        }
        userNameInput.sendKeys(RETURN);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
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

    public void placeHorseBet(String globalLocation, String location, Integer raceNumber, Integer horseNumber, BigDecimal amount) {
        checkLoggedIn();
        LbHorseRacePage racePage = navigateToHorseRacePage(globalLocation, location, raceNumber);
        if (racePage != null) {
            racePage.betOnHorse(horseNumber, amount);
        } else {
            LOG.error("Cannot find the race page.");
        }
    }

    private LbHorseRacePage navigateToHorseRacePage(String globalLocation, String location, Integer raceNumber) {
        LbHorseRacePage racePage = retrieveFromNextToJump(location, raceNumber, HORSE);
        if (racePage == null) {
            racePage = retrieveFromHorseRaces(globalLocation, location, raceNumber);
        }
        return racePage;
    }

    private LbHorseRacePage retrieveFromNextToJump(String location, Integer raceNumber, RaceType raceType) {
        super.openPage(LADBROKES_URL);
        WebElement nextToJumpWebElement = webDriver.findElement(By.className("next-to-go"));
        LbNextToJumpPage nextToJump = new LbNextToJumpPage(nextToJumpWebElement);
        try {
            nextToJump.navigateToRace(location, raceNumber, raceType);
            return extractHorseRacePage();
        } catch (CannotFindRaceException e) {
            LOG.info("Could not find race in next to jump: Location: " + location + " Race number: " + raceNumber + " RaceType: " + raceType);
            return null;
        }
    }

    private LbHorseRacePage retrieveFromHorseRaces(String globalLocation, String location, Integer raceNumber) {
        super.openPage(LADBROKES_HORSES_URL);
        horseRaces = new HashMap<String, LbHorseRaceBox>();
        WebElement racesDiv = webDriver.findElement(By.id("racesToday"));
        List<WebElement> raceBoxes = racesDiv.findElements(By.className("fullbox"));
        for (WebElement fullEventBox : raceBoxes) {
            LbHorseRaceBox box = new LbHorseRaceBox(fullEventBox);
            WebElement raceHeading = fullEventBox.findElement(By.className("fullbox-hdr"));
            if (raceHeading != null) {
                horseRaces.put(raceHeading.getText(), box);
            } else {
                throw new CannotFindElementException("Cannot find the race heading element fullbox-hdr");
            }
        }
        try {
            horseRaces.get(globalLocation).navigateToRace(location, raceNumber);
            return extractHorseRacePage();
        } catch (CannotFindRaceException e) {
            LOG.info("Could not find race in horse races page: Location: " + location + " Race number: " + raceNumber + " RaceType: " + HORSE);
            return null;
        }
    }

    private LbHorseRacePage extractHorseRacePage() {
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("competitor-view")));
        WebElement competitorView = webDriver.findElement(By.className("competitor-view"));
        if (competitorView == null) {
            throw new CannotFindElementException("Cannot find the competitor-view");
        }
        return new LbHorseRacePage(webDriver, competitorView);
    }

    private void checkLoggedIn() {
        if (!isPageOpen()) {
            openHomePage();
        }
        if (!getLoggedIn()) {
            login();
        }
    }
}
