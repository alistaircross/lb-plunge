package my.application.view.ladbrokes.nexttojump;

import my.application.enums.RaceType;
import my.application.model.CannotFindRaceException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public class LbNextToJumpPage {
    private HashMap<String, LbNextToJumpRace> nextRaces;

    public LbNextToJumpPage(WebElement nextToJump) {
        WebElement raceListingContainer = nextToJump.findElement(By.className("race-listing-container"));
        processRaceListingContainer(raceListingContainer);
    }

    private void processRaceListingContainer(WebElement raceListingContainer) {
        List<WebElement> raceListings = raceListingContainer.findElements(By.className("race-listing"));
        nextRaces = new HashMap<String, LbNextToJumpRace>();
        for (WebElement raceListing : raceListings) {
            LbNextToJumpRace nextToJump = new LbNextToJumpRace(raceListing);
            String location = nextToJump.getLocation();
            if (location != null) {
                nextRaces.put(location, nextToJump);
            }
        }
    }

    public void navigateToRace(String location, Integer raceNumber, RaceType raceType) {
        LbNextToJumpRace race = nextRaces.get(location);
        if (race != null) {
            if(race.matchesData(raceNumber, raceType)) {
                race.clickRace();
                return;
            }
        }
        throw new CannotFindRaceException("Cannot find race: Location: " + location + " Race number: " + raceNumber + " Race type: " + raceType);
    }
}
