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

    public MobilePlungeBet(RaceType raceType, String runnerName, Integer runnerNumber, String location, Integer raceNumber, String distance, String trackRating, String timeToJump) {
        this.raceType = raceType;
        this.runnerName = runnerName;
        this.runnerNumber = runnerNumber;
        this.location = location;
        this.raceNumber = raceNumber;
        this.distance = distance;
        this.trackRating = trackRating;
        this.timeToJump = timeToJump;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MobilePlungeBet that = (MobilePlungeBet) o;
        return raceType == that.raceType &&
                Objects.equals(runnerName, that.runnerName) &&
                Objects.equals(runnerNumber, that.runnerNumber) &&
                Objects.equals(location, that.location) &&
                Objects.equals(raceNumber, that.raceNumber) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(trackRating, that.trackRating) &&
                Objects.equals(timeToJump, that.timeToJump);
    }

    @Override
    public int hashCode() {

        return Objects.hash(raceType, runnerName, runnerNumber, location, raceNumber, distance, trackRating, timeToJump);
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
                '}';
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public String getRunnerName() {
        return runnerName;
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
}
