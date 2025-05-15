package models.enums.environment;

import models.Artisan;
import models.Game;
import models.User;

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

    public static void advanceOneHour(Game game) {
        // TODO: change game time
        Time time = game.getGameState().getTime();

        // =========================================================

        // TODO: call game.changeDay() here if day has changed
        for (User player : game.getPlayers()) {
            // TODO: show unread messages when starting new turn
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
