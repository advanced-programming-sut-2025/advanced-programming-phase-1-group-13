package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Command {
    GAME_NEW("^\\s+game\\s+new\\s+(?<username_1>.+)\\s+(?<username_2>.+)\\s+(?<username_3>.+)\\s+$"),
    GAME_MAP("^\\s+game\\s+map\\s+(?<map_number>\\d+)$"),
    LOAD_GAME("^\\s+load\\s+game\\s+$"),
    EXIT_GAME("^\\s+exit\\s+game\\s+$"),
    NEXT_TURN("^\\s+next\\s+turn\\s+$"),
    TIME("^\\s+time\\s+$"),
    DATE("^\\s+date\\s+$"),
    DATETIME("^\\s+datetime\\s+$"),
    DAY_OF_THE_WEEK("^\\s+day\\s+of\\s+the\\s+week\\s+$"),
    CHEAT_ADV_TIME("^\\s+cheat\\s+advance\\s+time\\s+(?<hourIncrease>\\d+)h\\s+$"),
    CHEAT_ADV_DATE("^\\s+cheat\\s+advance\\s+date\\s+(?<dayIncrease>\\d+)d\\s+$"),
    SEASON("^\\s+season\\s+$"),
    CHEAT_THOR("^\\s+cheat\\s+Thor\\s+-l\\s+(?<X_Pos>\\d+)\\s+,\\s+(?<X_Pos>\\d+)\\s+$"),
    WEATHER("^\\s+weather\\s+$"),
    WEATHER_FORCAST("^\\s+weather\\s+forecast\\s+$"),
    CHEAT_WEATHER("^\\s+cheat\\s+weather\\s+set\\s+(?<type>.+)$"),
    GREENHOUSE_BUILD("^\\s+greenhouse\\s+build\\s+$"),
    WALK("^\\s+wals\\s+-l\\s+(?<x>\\d+)\\s+,\\s+(?<Y>\\d+)\\s+$"),
    PRINT_MAP("^\\s+print\\s+map\\s+(?<x>\\d+)\\s+,\\s+(?<Y>\\d+)\\s+-s\\s+size\\s+(?<size>\\d+)$"),
    HELP_READING_MAP("^\\s+help\\s+reading\\s+map\\s+$"),
    ENERGY_SHOW("^\\s+energy\\s+show\\s+$"),
    CHEAT_ENERGY_SET("^\\s+energy\\s+set\\s+-v\\s+(?<value>\\s+)$"),
    CHEAT_ENERGY_UNLIMITED("^\\s+energy\\s+unlimited\\s+$"),
    INVENTORY_SHOW("^$\\s+energy\\s+show\\s+");


    private final String regex;
    private final Pattern pattern;

    GameCommands(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(this.regex);
    }

    @Override
    public boolean matches(String input) {
        return Command.super.matches(input);
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
