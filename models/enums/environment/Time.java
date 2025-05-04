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

    }

    public Time cheatAdvanceTime(int hourIncrease, Time currentTime) {
        if (hourIncrease < 0) {
            throw new IllegalArgumentException("Cannot advance time by negative hours");
        }

        Time newTime = new Time();
        newTime.year = currentTime.year;
        newTime.Season = currentTime.Season;
        newTime.month = currentTime.month;
        newTime.dayInMonth = currentTime.dayInMonth;
        newTime.weekday = currentTime.weekday;

        int totalHours = currentTime.hour + hourIncrease;
        int daysToAdvance = 0;

        while (totalHours > 22) {  // 10 PM is max hour
            totalHours -= 14;      // 14 hours in a game day (9AM-10PM)
            daysToAdvance++;
        }

        newTime.hour = Math.max(9, totalHours);  // Ensure minimum 9 AM

        if (daysToAdvance > 0) {
            return cheatAdvanceDate(daysToAdvance, newTime);
        }

        return newTime;
    }

    public Time cheatAdvanceDate(int dayIncrease, Time currentTime) {
        if (dayIncrease < 0) {
            throw new IllegalArgumentException("Cannot advance date by negative days");
        }

        Time newTime = new Time();
        // Copy all fields from current time
        newTime.year = currentTime.year;
        newTime.Season = currentTime.Season;
        newTime.month = currentTime.month;
        newTime.dayInMonth = currentTime.dayInMonth;
        newTime.weekday = currentTime.weekday;
        newTime.hour = currentTime.hour;  // Keep the same hour

        int remainingDays = dayIncrease;

        while (remainingDays > 0) {
            newTime.dayInMonth++;
            newTime.weekday = newTime.weekday.next();

            if (newTime.dayInMonth > newTime.month.getDaysInSeason()) {
                newTime.dayInMonth = 1;
                newTime.month = newTime.month.next();

                if (newTime.month == Month.getFirstMonthOfSeason(currentTime.Season)) {
                    newTime.Season = newTime.Season.next();

                    if (newTime.Season == Season.SPRING) {
                        newTime.year++;
                    }
                }
            }
            remainingDays--;
        }

        return newTime;
    }

    public static boolean areInSameDay(Time time1, Time time2) {
        return time1.year == time2.year &&
                time1.Season == time2.Season &&
                time1.month == time2.month &&
                time1.dayInMonth == time2.dayInMonth;
    }
}
