package my.application.view.ladbrokes.horse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class LbHorseRacePage {
    private WebElement competitorTable;
    private HashMap<Integer, LbHorseCompetitor> competitors;

    public LbHorseRacePage(WebElement competitorTable) {
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
        return null;
    }
}
