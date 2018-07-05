import my.application.service.ladbrokes.LbWebsite;

public class Main {
    public static void main(String[] args) {
        LbWebsite ldw = new LbWebsite();
        ldw.placeHorseBet(LbWebsite.LOCAL_RACE_HEADING,"Grafton", 6, 5);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ldw.closePage();
    }
}
