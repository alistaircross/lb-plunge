package my.application.browser.ladbrokes.horse;

import my.application.browser.ladbrokes.all.LbCompetitorBettingPopUp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class LbHorseRacePage {
    private static final Log LOG = LogFactory.getLog(LbHorseRacePage.class);

    private WebDriver webDriver;
    private WebElement competitorTable;
    private HashMap<Integer, LbHorseCompetitor> competitors;

    public LbHorseRacePage(WebDriver webDriver, WebElement competitorTable) {
        this.webDriver = webDriver;
        this.competitorTable = competitorTable;
        setCompetitors();
    }

    private void setCompetitors() {
        List<WebElement> competitorList = competitorTable.findElements(By.className("competitor"));
        competitors = new HashMap<Integer, LbHorseCompetitor>();
        for (WebElement competitorElememt : competitorList) {
            LbHorseCompetitor competitor = new LbHorseCompetitor(competitorElememt);
            competitors.put(competitor.getHorseNumber(), competitor);
        }
    }

    public Boolean betOnHorse(Integer horseNumber, BigDecimal amount) {
        LbHorseCompetitor competitor = competitors.get(horseNumber);
        if (competitor != null && competitor.hasFixedOdds()) {
            competitor.clickWin();
            LbCompetitorBettingPopUp betPopUp = getBettingPopup();
            if (betPopUp != null) {
                betPopUp.selectEachWay();
                return betPopUp.placeBet(amount.toString());
            }
        } else {
            LOG.error("Tried to place bet on horse that either doesn't exist or have fixed odds");
        }
        return false;
    }

    private LbCompetitorBettingPopUp getBettingPopup() {
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pop_container")));
        WebElement bettingPopupElement = webDriver.findElement(By.className("pop_container"));
        return new LbCompetitorBettingPopUp(webDriver, bettingPopupElement);
    }
}
