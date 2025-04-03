package models.enums.environment;

public class Time {
    private int year;
    private Season Season;
    private Month month;
    private Weekday weekday;
    private int hour;
    private int minute;
    private int second;
    public static Time currentTime;

    public void increaseTime(Time increaseTimeAmount) {
        // TODO
    }

    public Time cheatAdvanceTime(int hourIncrease, Time currentTime) {
        // TODO
    }

    public Time cheatAdvanceDate(int dayIncrease, Time currentTime) {
        // TODO
    }
}
