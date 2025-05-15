package models.enums.environment;

public class Time {
    private int year;
    private Season season;
    private int dayInSeason;
    private Weekday weekday;
    private int hour;

    public int getYear() {
        return year;
    }

    public Season getSeason() {
        return season;
    }

    public int getDayInSeason() {
        return dayInSeason;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public int getHour() {
        return hour;
    }

    public static void cheatAdvanceTime(int hourIncrease, Time currentTime) {
        if (hourIncrease < 0) {
            throw new IllegalArgumentException("Cannot advance time by negative hours");
        }

        int totalHours = currentTime.hour + hourIncrease;
        int daysToAdvance = 0;

        while (totalHours > 22) {  // 10 PM is max hour
            totalHours -= 14;      // 14 hours in a game day (9AM-10PM)
            daysToAdvance++;
        }

        currentTime.hour = Math.max(9, totalHours);  // Ensure minimum 9 AM

        if (daysToAdvance > 0) {
            cheatAdvanceDate(daysToAdvance, currentTime); // Handles season transitions
        }
    }

    public static void cheatAdvanceDate(int dayIncrease, Time currentTime) {
        if (dayIncrease < 0) {
            throw new IllegalArgumentException("Cannot advance date by negative days");
        }

        int remainingDays = dayIncrease;

        while (remainingDays > 0) {
            currentTime.dayInSeason++;
            currentTime.weekday = currentTime.weekday.next();

            if (currentTime.dayInSeason > 28) { // Each season has 28 days
                currentTime.dayInSeason = 1;
                currentTime.season = currentTime.season.next();

                if (currentTime.season == Season.SPRING) {
                    currentTime.year++;
                }
            }
            remainingDays--;
        }
    }

    public static int differenceInDays(Time time1, Time time2) {
        if (time1.year > time2.year ||
                (time1.year == time2.year && time1.season.ordinal() > time2.season.ordinal()) ||
                (time1.year == time2.year && time1.season == time2.season &&
                        time1.dayInSeason > time2.dayInSeason)) {
            Time temp = time1;
            time1 = time2;
            time2 = temp;
        }

        int daysDifference = 0;

        while (!areInSameDay(time1, time2)) {
            time1.dayInSeason++;
            daysDifference++;

            if (time1.dayInSeason > 28) { // Each season has 28 days
                time1.dayInSeason = 1;
                time1.season = time1.season.next();

                if (time1.season == Season.SPRING) {
                    time1.year++;
                }
            }
        }

        return daysDifference;
    }

    public static boolean areInSameDay(Time time1, Time time2) {
        return time1.year == time2.year &&
                time1.season == time2.season &&
                time1.dayInSeason == time2.dayInSeason;
    }

}
