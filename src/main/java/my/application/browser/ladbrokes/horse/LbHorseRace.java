package my.application.browser.ladbrokes.horse;

import org.openqa.selenium.WebElement;

public class LbHorseRace {
    private Integer raceNumber;
    private WebElement raceMeet;

    public LbHorseRace(Integer raceNumber, WebElement raceMeet) {
        this.raceNumber = raceNumber;
        this.raceMeet = raceMeet;
    }

    public void clickRace() {
        raceMeet.click();
    }
}
