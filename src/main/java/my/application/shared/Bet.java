package my.application.shared;

public class Bet {
    private final String location;
    private final Integer raceNumber;
    private final String entrantName;

    @Override
    public String toString() {
        return "Bet{" +
                "location='" + location + '\'' +
                ", raceNumber=" + raceNumber +
                ", entrantName='" + entrantName + '\'' +
                ", entrantNumber=" + entrantNumber +
                ", odds='" + odds + '\'' +
                '}';
    }

    private final Integer entrantNumber;
    private final String odds;

    public Bet(String location, Integer raceNumber, String entrantName, Integer entrantNumber, String odds) {
        this.location = location;
        this.raceNumber = raceNumber;
        this.entrantName = entrantName;
        this.entrantNumber = entrantNumber;
        this.odds = odds;
    }

    public String getId() {
        return location.toLowerCase() + raceNumber + entrantName.toLowerCase() + entrantNumber;
    }
}
