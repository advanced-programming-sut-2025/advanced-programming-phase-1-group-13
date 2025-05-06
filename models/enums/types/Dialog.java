package models.enums.types;

import models.enums.environment.Season;
import models.enums.environment.Weather;

public enum Dialog {
    DIALOG_1(NPCType.CLINT, "Hello", 12, Season.SPRING, Weather.SUNNY, 0);
    // TODO: Add dialogs for all situations

    private final NPCType speaker;
    private final String message;
    private final int timeOfDay;
    private final Season season;
    private final Weather weather;
    private final int friendshipLevel;

    Dialog(NPCType speaker, String message, int timeOfDay, Season season, Weather weather, int friendshipLevel) {
        this.speaker = speaker;
        this.message = message;
        this.timeOfDay = timeOfDay;
        this.season = season;
        this.weather = weather;
        this.friendshipLevel = friendshipLevel;
    }

    public NPCType getSpeaker() {
        return speaker;
    }

    public String getMessage() {
        return message;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public Season getSeason() {
        return season;
    }

    public Weather getWeather() {
        return weather;
    }

    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public static Dialog getDialogBySituation(NPCType speaker, int timeOfDay, Season season,
                                              Weather weather, int friendshipLevel) {
        for (Dialog dialogType : Dialog.values()) {
            if (dialogType.getSpeaker().equals(speaker) &&
                    dialogType.getTimeOfDay() == timeOfDay &&
                    dialogType.getSeason().equals(season) &&
                    dialogType.getWeather().equals(weather) &&
                    dialogType.getFriendshipLevel() == friendshipLevel) {
                return dialogType;
            }
        }
        return null;
    }
}
