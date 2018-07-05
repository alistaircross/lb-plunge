package my.application.view.ladbrokes.horse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;

public class LbHorseCompetitor {
    private static final Log LOG = LogFactory.getLog(LbHorseCompetitor.class);

    private Integer horseNumber;
    private String horseName;
    private String horseWeight;
    private Integer gateNumber;
    private String jockey;
    private Boolean scratched;
    private BigDecimal winOdds;
    private BigDecimal placeOdds;
    private WebElement winOddsElement;
    private WebElement placeOddsElement;

    public LbHorseCompetitor(WebElement competitor) {
        setOdds(competitor);
        setHorseDetails(competitor);
    }

    private void setHorseDetails(WebElement competitor) {
        WebElement entrantData = competitor.findElement(By.className("entrant"));
        String allData = entrantData.getText();
        int horseNumberEnd = allData.indexOf('.');
        int gateNumberStart = allData.indexOf('(');
        int gateNumberEnd = allData.indexOf(')');
        int jockeyStart = allData.indexOf("J:");
        int weightStart = allData.indexOf("W:");

        this.horseNumber = new Integer(allData.substring(0, horseNumberEnd));
        this.horseName = allData.substring(horseNumberEnd + 1, gateNumberStart).trim();
        this.gateNumber = new Integer(allData.substring(gateNumberStart + 1, gateNumberEnd));
        this.jockey = allData.substring(jockeyStart + 2, weightStart).trim();
        this.horseWeight = allData.substring(weightStart + 2).trim();
    }

    private void setOdds(WebElement competitor) {
        String competitorClass = competitor.getAttribute("class");
        if(competitorClass.contains("not-scratched")) {
            winOddsElement = competitor.findElement(By.className("win"));
            if (winOddsElement != null && winOddsElement.getText() != null) {
                String winOdds = winOddsElement.getText().trim();
                this.winOdds = new BigDecimal(winOdds);
            }
            placeOddsElement = competitor.findElement(By.className("place"));
            if (placeOddsElement != null && placeOddsElement.getText() != null) {
                String placeOdds = placeOddsElement.getText().trim();
                this.placeOdds = new BigDecimal(placeOdds);
            }
        } else {
            scratched = true;
            return;
        }
    }

    @Override
    public String toString() {
        return "LbHorseCompetitor{" +
                "horseNumber=" + horseNumber +
                ", horseName='" + horseName + '\'' +
                ", gateNumber=" + gateNumber +
                ", jockey='" + jockey + '\'' +
                ", winOdds=" + winOdds +
                ", placeOdds=" + placeOdds +
                '}';
    }

    public Integer getHorseNumber() {
        return horseNumber;
    }
}
