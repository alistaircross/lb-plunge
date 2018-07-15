package my.application.browser.ladbrokes.nexttojump;

import my.application.shared.enums.RaceType;
import my.application.browser.model.CannotFindRaceException;
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
                nextRaces.put(location.toLowerCase(), nextToJump);
            }
        }
    }

    public void navigateToRace(String location, Integer raceNumber, RaceType raceType) {
        LbNextToJumpRace race = nextRaces.get(location.toLowerCase());
        if (race != null) {
            if(race.matchesData(raceNumber, raceType)) {
                race.clickRace();
                return;
            }
        }
        throw new CannotFindRaceException("Cannot find race: Location: " + location + " Race number: " + raceNumber + " Race type: " + raceType);
    }
}
