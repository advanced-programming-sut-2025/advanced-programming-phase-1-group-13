package com.project.models.enums.environment;

import com.project.models.App;
import com.project.models.Artisan;
import com.project.models.Game;
import com.project.models.User;

public class Time {
    private int year;
    private Season season;
    private int dayInSeason;
    private Weekday weekday;
    private int hour;

    public Time() {
        this.year = 0;
        this.season = Season.SPRING;
        this.dayInSeason = 1;
        this.weekday = Weekday.MERCDAY;
        this.hour = 9;
    }

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

    public static void advanceOneHour(Game game) {
        Time time = game.getGameState().getTime();
        time.hour++;

        if (time.hour > 22) {
            time.hour = 9;
            time.dayInSeason++;
            time.weekday = time.weekday.next();

            if (time.dayInSeason > 28) {
                time.dayInSeason = 1;
                time.season = time.season.next();

                if (time.season == Season.SPRING) {
                    time.year++;
                }
            }

            System.out.println(game.changeDay().message());
        }

        App.getLoggedIn().decreaseHoursLeftTillBuffVanishes(1);

        for (User player : game.getPlayers()) {
            for (Artisan artisan : player.getFarm().getArtisans()) {
                if (artisan.getItemPending() != null) {
                    artisan.setTimeLeft(Math.max(artisan.getTimeLeft() - 1, 0));
                }
            }
        }
    }

    public static void cheatAdvanceTime(int hourIncrease, Game game) {
        for (int i = 0; i < hourIncrease; i++) {
            advanceOneHour(game);
        }
    }

    public static void cheatAdvanceDate(int dayIncrease, Game game) {
        for (int i = 0; i < dayIncrease * 14; i++) {
            advanceOneHour(game);
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
