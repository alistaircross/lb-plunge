import my.application.service.ladbrokes.LadbrokesWebsite;

public class Main {
    public static void main(String[] args) {
        LadbrokesWebsite ldw = new LadbrokesWebsite();
        ldw.openHomePage();
        boolean loggedIn = ldw.getLoggedIn();
        if(!loggedIn) {
            try {
                ldw.login();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ldw.placeHorseBet(LadbrokesWebsite.LOCAL_RACE_HEADING,"Belmont", 3, 5);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ldw.closePage();
    }
}
