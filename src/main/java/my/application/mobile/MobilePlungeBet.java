package my.application.mobile;

import my.application.shared.enums.RaceType;

import java.util.Objects;


public class MobilePlungeBet {
    private final RaceType raceType;
    private final String runnerName;
    private final Integer runnerNumber;
    private final String location;
    private final Integer raceNumber;
    private final String distance;
    private final String trackRating;
    private final String timeToJump;
    private String countryOfOrigin;

    public MobilePlungeBet(RaceType raceType, String runnerName, Integer runnerNumber, String location, Integer raceNumber, String distance, String trackRating, String timeToJump, String countryOfOrigin) {
        this.raceType = raceType;
        this.runnerName = runnerName;
        this.runnerNumber = runnerNumber;
        this.location = location;
        this.raceNumber = raceNumber;
        this.distance = distance;
        this.trackRating = trackRating;
        this.timeToJump = timeToJump;
        this.countryOfOrigin = countryOfOrigin;
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public String getRunnerName() {
        return runnerName;
    }

    @Override
    public String toString() {
        return "MobilePlungeBet{" +
                "raceType=" + raceType +
                ", runnerName='" + runnerName + '\'' +
                ", runnerNumber=" + runnerNumber +
                ", location='" + location + '\'' +
                ", raceNumber=" + raceNumber +
                ", distance='" + distance + '\'' +
                ", trackRating='" + trackRating + '\'' +
                ", timeToJump='" + timeToJump + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MobilePlungeBet plungeBet = (MobilePlungeBet) o;
        return raceType == plungeBet.raceType &&
                Objects.equals(runnerName, plungeBet.runnerName) &&
                Objects.equals(runnerNumber, plungeBet.runnerNumber) &&
                Objects.equals(location, plungeBet.location) &&
                Objects.equals(raceNumber, plungeBet.raceNumber) &&
                Objects.equals(distance, plungeBet.distance) &&
                Objects.equals(trackRating, plungeBet.trackRating) &&
                Objects.equals(timeToJump, plungeBet.timeToJump) &&
                Objects.equals(countryOfOrigin, plungeBet.countryOfOrigin);
    }

    @Override
    public int hashCode() {

        return Objects.hash(raceType, runnerName, runnerNumber, location, raceNumber, distance, trackRating, timeToJump, countryOfOrigin);
    }

    public String getCountryOfOrigin() {

        return countryOfOrigin;
    }

    public Integer getRunnerNumber() {
        return runnerNumber;
    }

    public String getLocation() {
        return location;
    }

    public Integer getRaceNumber() {
        return raceNumber;
    }

    public String getDistance() {
        return distance;
    }

    public String getTrackRating() {
        return trackRating;
    }

    public String getTimeToJump() {
        return timeToJump;
    }

    public String getId() {
        return location.toLowerCase() + raceNumber + runnerName.toLowerCase() + runnerNumber;
    }
}
