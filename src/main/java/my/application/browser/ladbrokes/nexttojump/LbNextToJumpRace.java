package my.application.browser.ladbrokes.nexttojump;

import my.application.shared.enums.RaceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

public class LbNextToJumpRace {
    private static final Log LOG = LogFactory.getLog(LbNextToJumpRace.class);
    private RaceType raceType;
    private Integer raceNumber;
    private String raceLocation;
    private WebElement nextToJumpRace;

    public LbNextToJumpRace(WebElement nextToJumpRace) {
        this.nextToJumpRace = nextToJumpRace;
        processNextToJump(nextToJumpRace);
    }

    private void processNextToJump(WebElement nextToJumpRace) {
        if (!nextToJumpRace.getAttribute("class").contains("none")) {
            String[] raceDeets = nextToJumpRace.getText().trim().split("\n");
            raceLocation = raceDeets[0].toLowerCase().trim();
            raceNumber = new Integer(raceDeets[1].replace("R", ""));
            String raceTypeString = nextToJumpRace.getAttribute("data-racetype");
            raceType = RaceType.getRaceTypeForString(raceTypeString);
        }
    }

    public String getLocation() {
        return raceLocation;
    }

    public void clickRace() {
        nextToJumpRace.click();
    }

    public boolean matchesData(Integer raceNumber, RaceType raceType) {
        return this.raceNumber.equals(raceNumber) && this.raceType == raceType;
    }
}
