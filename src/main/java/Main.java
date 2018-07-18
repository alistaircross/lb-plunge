import my.application.browser.service.ladbrokes.LbWebsite;
import my.application.mobile.MobileIntegrationService;
import my.application.mobile.MobilePlungeBet;
import my.application.shared.PlacedBetsUtils;
import my.application.shared.enums.RaceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static final Log LOG = LogFactory.getLog(Main.class);
    private static final Log ERROR_LOG = LogFactory.getLog("ErrorLog");
    private static final Log BET_LOG = LogFactory.getLog("BetLogger");
    private static int count = 0;

    public static void main(String[] args) {
        ERROR_LOG.info("Starting cycle.");

        final MobileIntegrationService mis = new MobileIntegrationService();
        int delay = 10000;   // delay for 10 sec.
        int period = 60000;  // repeat every minutes.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    List<MobilePlungeBet> plungingBets = mis.getPlungeBets();
                    if (plungingBets.size() != 0) {
                        for (MobilePlungeBet plungingBet : plungingBets) {

                            if (!PlacedBetsUtils.betBeenPlaced(plungingBet.getId())) {
                                LOG.info("Plunging bet: " + plungingBet);
                                BET_LOG.info("Plunging bet start: " + plungingBet);
                                attemptToPlaceBet(plungingBet);
                            } else {
                                LOG.info("Plunging bet has already been seen: " + plungingBet);
                            }
                        }
                    } else {
                        LOG.info("Currently no plunging races.");
                    }
                } catch (Throwable e) {
                    ERROR_LOG.error("Error:", e);
                    if (count < 100) {
                        LOG.info("COUNT:" + count);
                        count++;
                    }
                    main(null);
                }
            }
        }, delay, period);
    }

    private static void attemptToPlaceBet(MobilePlungeBet plungingBet) {
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
