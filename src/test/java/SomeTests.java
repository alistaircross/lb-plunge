import org.junit.Test;

public class SomeTests {
    @Test
    public void someTest() {
        String raceDetails = "AYR (UK)";
        String location;
        String countryOfOrigin = "AUS";
        String tmp = raceDetails.trim();
        int countryOfOriginStart = tmp.indexOf("(");
        if (countryOfOriginStart != -1) {
            location = tmp.substring(0, countryOfOriginStart).trim();
            countryOfOrigin = tmp.substring(countryOfOriginStart + 1, tmp.indexOf(")"));
        } else {
            location = tmp;
        }
        System.out.println("Location is:" + location + " Origin is: " + countryOfOrigin);
    }

}
