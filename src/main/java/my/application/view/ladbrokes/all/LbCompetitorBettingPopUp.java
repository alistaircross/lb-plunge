package my.application.view.ladbrokes.all;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static my.application.enums.ShouldBet.SHOULD_BET;

public class LbCompetitorBettingPopUp {
    private static final Log LOG = LogFactory.getLog(LbCompetitorBettingPopUp.class);

    private WebElement eachWayElement;
    private WebDriver webDriver;
    private WebElement bettingPopupElement;
    private WebElement amountInput;
    private WebElement placeBetButton;
    private String location;
    private Integer raceNumber;
    private Integer entrantNumber;
    private String entrantName;
    private String odds;
    private Boolean isEachWay = false;

    public LbCompetitorBettingPopUp(WebDriver webDriver, WebElement bettingPopupElement) {
        this.webDriver = webDriver;
        this.bettingPopupElement = bettingPopupElement;
        this.eachWayElement = bettingPopupElement.findElement(By.id("each-way"));
        this.amountInput = bettingPopupElement.findElement(By.className("betamount"));
        this.placeBetButton = bettingPopupElement.findElement(By.className("betbutton"));
        processEntrant(bettingPopupElement.findElement(By.className("entrant")));
        processLocation(bettingPopupElement.findElement(By.className("location")));
    }

    private void processEntrant(WebElement entrant) {
        if (entrant != null) {
            String rawEntrant = entrant.getText();
            int locationOfEntrantNo = rawEntrant.indexOf(".");
            this.entrantNumber = new Integer(rawEntrant.substring(0, locationOfEntrantNo));
            this.entrantName = rawEntrant.substring(locationOfEntrantNo + 1).trim();
        }
    }

    private void processLocation(WebElement location) {
        if (location != null) {
            String rawLocation = location.getText();
            int locationOfRaceNo = rawLocation.lastIndexOf(" ");
            this.location = rawLocation.substring(0, locationOfRaceNo).trim();
            this.raceNumber = new Integer(rawLocation.substring(locationOfRaceNo).trim().replace("R", ""));
        }
    }

    public void selectEachWay() {
        if(!eachWayElement.isSelected()) {
            eachWayElement.click();
        }
    }

    public void deSelectEachWay() {
        if(eachWayElement.isSelected()) {
            eachWayElement.click();
        }
    }

    public Boolean placeBet(String amount) {
        extractOdds();
        amountInput.sendKeys(amount);
        placeBetButton.click();
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("betconfirm")));
        LOG.info("Placing bet on: Location: " + location + " Race" + raceNumber + " Runner: " + entrantName + " Runner number: " + entrantNumber + " Odds: " + odds);
        if(SHOULD_BET) {
            bettingPopupElement.findElement(By.className("betconfirm")).click();
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return wasBetPlaced();
        }
        return false;
    }

    private void extractOdds() {
        this.odds = bettingPopupElement.findElement(By.className("odds")).getText();
    }

    private Boolean wasBetPlaced() {
        try {
            WebElement errorElement = bettingPopupElement.findElement(By.className("betslip-error"));
            if (errorElement.isDisplayed()) {
                LOG.error("Not able to place bet: ");
            }
        } catch (NoSuchElementException e) {
            WebElement successElement = bettingPopupElement.findElement(By.className("success-inner"));
            webDriver.getCurrentUrl();
            return successElement.isDisplayed();
        }
        return false;
    }


}
