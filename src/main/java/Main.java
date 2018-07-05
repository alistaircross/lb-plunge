import my.application.service.ladbrokes.LbWebsite;

public class Main {
    public static void main(String[] args) {
        LbWebsite ldw = new LbWebsite();
        ldw.placeHorseBet(LbWebsite.LOCAL_RACE_HEADING,"Kawasaki", 4, 5);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ldw.closePage();
    }
}
