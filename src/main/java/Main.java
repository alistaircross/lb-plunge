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
        ldw.getAllHorseRaces();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ldw.closePage();
    }
}
