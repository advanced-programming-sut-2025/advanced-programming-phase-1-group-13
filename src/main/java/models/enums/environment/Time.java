package models.enums.environment;

public class Time {
    private int year;
    private Season season;
    private Month month;
    private int dayInMonth; // TODO: season, not month
    private Weekday weekday;
    private int hour;

    public int getYear() {
        return year;
    }

    public Season getSeason() {
        return season;
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

    public static void cheatAdvanceTime(int hourIncrease, Time currentTime) {
        if (hourIncrease < 0) {
            throw new IllegalArgumentException("Cannot advance time by negative hours");
        }

        Time newTime = new Time();
        newTime.year = currentTime.year;
        newTime.season = currentTime.season;
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
            cheatAdvanceDate(daysToAdvance, newTime);
        }

    }

    public static void cheatAdvanceDate(int dayIncrease, Time currentTime) {
        if (dayIncrease < 0) {
            throw new IllegalArgumentException("Cannot advance date by negative days");
        }

        int remainingDays = dayIncrease;

        while (remainingDays > 0) {
            currentTime.dayInMonth++;
            currentTime.weekday = currentTime.weekday.next();

            if (currentTime.dayInMonth > currentTime.month.getDaysInSeason()) {
                currentTime.dayInMonth = 1;
                currentTime.month = currentTime.month.next();

                if (currentTime.month == Month.getFirstMonthOfSeason(currentTime.season)) {
                    currentTime.season = currentTime.season.next();

                    if (currentTime.getSeason() == Season.SPRING) {
                        currentTime.year++;
                    }
                }
            }
            remainingDays--;
        }

    }

    public static boolean areInSameDay(Time time1, Time time2) {
        return time1.year == time2.year &&
                time1.season == time2.season &&
                time1.month == time2.month &&
                time1.dayInMonth == time2.dayInMonth;
    }

    public static int differenceInDays(Time time1, Time time2) {
        if (time1.year > time2.year ||
                (time1.year == time2.year && time1.season.ordinal() > time2.season.ordinal()) ||
                (time1.year == time2.year && time1.season == time2.season &&
                        time1.month.ordinal() > time2.month.ordinal()) ||
                (time1.year == time2.year && time1.season == time2.season && time1.month == time2.month &&
                        time1.dayInMonth > time2.dayInMonth)) {
            Time temp = time1;
            time1 = time2;
            time2 = temp;
        }

        int daysDifference = 0;

        while (!areInSameDay(time1, time2)) {
            time1.dayInMonth++;
            daysDifference++;

            if (time1.dayInMonth > time1.month.getDaysInSeason()) {
                time1.dayInMonth = 1;
                time1.month = time1.month.next();

                if (time1.month == Month.getFirstMonthOfSeason(time1.season)) {
                    time1.season = time1.season.next();

                    if (time1.season == Season.SPRING) {
                        time1.year++;
                    }
                }
            }
        }

        return daysDifference;
    }
}
