package my.application.view.ladbrokes.horse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public class LbHorseRaceBox {
    private String raceGlobalLocation;
    private HashMap<String, LbHorseRaceEvent> raceEvents;
    private static final Log LOG = LogFactory.getLog(LbHorseRaceBox.class);

    public LbHorseRaceBox(WebElement raceBox) {
        WebElement raceHeading = raceBox.findElement(By.className("fullbox-hdr"));
        if (raceHeading != null) {
            raceGlobalLocation = raceHeading.getText();
        }
        setRaceEventsForLocation(raceBox);
    }

    private void setRaceEventsForLocation(WebElement raceBox) {
        List<WebElement> meetings = raceBox.findElements(By.className("meeting"));
        raceEvents = new HashMap<String, LbHorseRaceEvent>();
        for (WebElement meeting : meetings) {
            WebElement raceLocationCell = meeting.findElement(By.className("racing-location"));
            if (raceLocationCell != null) {
                String localLocation = raceLocationCell.getText();
                LbHorseRaceEvent event = new LbHorseRaceEvent(meeting);
                raceEvents.put(localLocation, event);
            } else {
                LOG.error("Cannot find racing location. Not moving forward with creating the races.");
            }
        }
    }

    public LbHorseRaceEvent getHorseRaceEvent(String location) {
        return raceEvents.get(location);
    }
}
