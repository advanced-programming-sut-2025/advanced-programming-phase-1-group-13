package models.enums.environment;

public class Time {
    private int year;
    private Season Season;
    private Month month;
    private int dayInMonth;
    private Weekday weekday;
    private int hour;

    public int getYear() {
        return year;
    }

    public Season getSeason() {
        return Season;
    }

    public Month getMonth() {
        return month;
    }

    public int getDayInMonth() {
        return dayInMonth;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public int getHour() {
        return hour;
    }

    public void increaseTime(Time increaseTimeAmount) {
        // TODO
    }

    public Time cheatAdvanceTime(int hourIncrease, Time currentTime) {
        // TODO
        return null;
    }

    public Time cheatAdvanceDate(int dayIncrease, Time currentTime) {
        // TODO
        return null;
    }

    public static boolean areInSameDay(Time time1, Time time2) {
        // TODO
        return true;
    }
}
