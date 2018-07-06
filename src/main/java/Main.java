import my.application.service.ladbrokes.LbWebsite;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        LbWebsite ldw = new LbWebsite();
        ldw.placeHorseBet(LbWebsite.LOCAL_RACE_HEADING,"Bendigo", 10, 2 , new BigDecimal(.50));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ldw.closePage();
    }
}
