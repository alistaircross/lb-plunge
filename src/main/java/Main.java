import my.application.service.ladbrokes.LadbrokesWebsite;

public class Main {
    public static void main(String[] args) {
        LadbrokesWebsite ldw = new LadbrokesWebsite();
        ldw.openPage();
        boolean loggedIn = ldw.getLoggedIn();
        if(!loggedIn) {
            System.out.println("Better log in");
            try {
                ldw.login();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loggedIn = ldw.getLoggedIn();
            System.out.println("Logged in: " + loggedIn);

        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello world");
        ldw.closePage();
    }
}
