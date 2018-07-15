package my.application.browser.ladbrokes.horse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public class LbHorseRaceEvent {
    private static final Log LOG = LogFactory.getLog(LbHorseRaceEvent.class);
    private String localLocation ;
    private HashMap<Integer, LbHorseRace> races;

    public LbHorseRaceEvent(WebElement fullRaceMeet) {
        WebElement raceLocationCell = fullRaceMeet.findElement(By.className("racing-location"));
        if (raceLocationCell != null) {
            localLocation = raceLocationCell.getText();
            getRaces(fullRaceMeet.findElements(By.className("odds")));
        } else {
            LOG.error("Cannot find racing location. Not moving forward with creating the races.");
        }
    }

    private void getRaces(List<WebElement> fullRaceMeet) {
        Integer raceNumber = 1;
        races = new HashMap<Integer, LbHorseRace>();
        for (WebElement raceMeet : fullRaceMeet) {
            LbHorseRace race = new LbHorseRace(raceNumber, raceMeet);
            races.put(raceNumber, race);
            raceNumber++;
        }
    }

    public LbHorseRace getRaceNumber(Integer raceNumber) {
        return races.get(raceNumber);
    }
}
