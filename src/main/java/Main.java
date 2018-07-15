import my.application.browser.service.ladbrokes.LbWebsite;
import my.application.mobile.MobileIntegrationService;
import my.application.mobile.MobilePlungeBet;
import my.application.shared.enums.RaceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static final Log LOG = LogFactory.getLog(Main.class);

    public static void main(String[] args) {
        final MobileIntegrationService mis = new MobileIntegrationService();
        int delay = 10000;   // delay for 10 sec.
        int period = 60000;  // repeat every minutes.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                List<MobilePlungeBet> plungingBets = mis.getPlungeBets();
                if (plungingBets.size() != 0) {
                    for (MobilePlungeBet plungingBet : plungingBets) {
                        LOG.info("Plunging bet: " + plungingBet);
                        attemptToPlaceBet(plungingBet);
                    }
                } else {
                    LOG.info("Currently no plunging races.");
                }
            }
        }, delay, period);
    }

    private static void attemptToPlaceBet(MobilePlungeBet plungingBet) {
        if (plungingBet.getRaceType() == RaceType.HORSE) {
            LbWebsite ldw = new LbWebsite();
            ldw.placeHorseBet(LbWebsite.LOCAL_RACE_HEADING, plungingBet.getLocation(), plungingBet.getRaceNumber(), plungingBet.getRunnerNumber(), new BigDecimal(.50));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ldw.closePage();
        }
    }
}
