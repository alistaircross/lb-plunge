package my.application.mobile;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import my.application.shared.enums.RaceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MobileIntegrationService {
    private static final Log LOG = LogFactory.getLog(MobileIntegrationService.class);

    AppiumDriver<MobileElement> driver;

    public MobileIntegrationService() {
        setup();
    }

    private void setup() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel_2_XL_API_24");
        caps.setCapability("udid", "emulator-5554"); //Give Device ID of your mobile phone
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "7.0");
        caps.setCapability("appPackage", "au.com.plungeapp.plunge");
        caps.setCapability("appActivity", "au.com.plungeapp.plunge.MainActivity");
        caps.setCapability("noReset", "true");
        //Instantiate Appium Driver
        try {
            driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
        } catch (Exception e) {
            LOG.error("Problem with creating the android driver.", e);
        }
    }

    public Boolean hasDriver() {
        return driver != null;
    }

    public List<MobilePlungeBet> getPlungeBets() {
        if (!hasDriver()) {
            LOG.info("No driver to get plunge bet's");
            setup();
        }
        LinkedList<MobilePlungeBet> returnRaces = new LinkedList<MobilePlungeBet>();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("au.com.plungeapp.plunge:id/bet_list")));
        MobileElement betList = driver.findElement(By.id("au.com.plungeapp.plunge:id/bet_list"));
        List<MobileElement> betListFrame = betList.findElements(By.className("android.widget.FrameLayout"));
        for (MobileElement mobileElement : betListFrame) {
            MobilePlungeBet bet = extractMobilePlunge(mobileElement);
            returnRaces.add(bet);
        }
        return returnRaces;
    }

    private MobilePlungeBet extractMobilePlunge(MobileElement raceItem) {
        MobileElement timeTillJump = raceItem.findElement(By.id("au.com.plungeapp.plunge:id/post_time"));
        MobileElement raceTypeE = raceItem.findElement(By.id("au.com.plungeapp.plunge:id/post_result"));
        MobileElement raceNumberAndRunnerName = raceItem.findElement(By.id("au.com.plungeapp.plunge:id/post_outcome"));
        MobileElement locationAndRaceInfo = raceItem.findElement(By.id("au.com.plungeapp.plunge:id/post_event"));
        String timeToJump = timeTillJump.getText();
        RaceType raceType = RaceType.getRaceTypeForString(raceTypeE.getText());
        String runnerInfo = raceNumberAndRunnerName.getText();
        int numberIndex = runnerInfo.indexOf(".");
        Integer runnerNumber = new Integer(runnerInfo.substring(0, numberIndex).trim());
        String runnerName = runnerInfo.substring(numberIndex + 1).trim();

        String[] raceDetails = locationAndRaceInfo.getText().split("-");
        String location = null;
        String countryOfOrigin = "AUS";
        Integer raceNumber = null;
        String distance = null;
        String trackRating = null;
        for (int i = 0; i < raceDetails.length; i++) {
            if (i == 0) {
                String tmp = raceDetails[i].trim();
                int countryOfOriginStart = tmp.indexOf("(");
                if (countryOfOriginStart != -1) {
                    location = tmp.substring(0, countryOfOriginStart).trim();
                    countryOfOrigin = tmp.substring(countryOfOriginStart + 1, tmp.indexOf(")"));
                } else {
                    location = tmp;
                }
            } else if (i == 1) {
                String temp = raceDetails[i].trim();
                temp = temp.trim();
                temp = temp.replace("Race ", "");
                String[] listItems = temp.split(" ");
                raceNumber = new Integer(listItems[0]);
            } else if (i == 2) {
                distance = raceDetails[i].trim();
            } else if (i == 3) {
                trackRating = raceDetails[i].trim();
            }
        }

        return new MobilePlungeBet(raceType, runnerName, runnerNumber, location, raceNumber, distance, trackRating, timeToJump, countryOfOrigin);
    }
}
