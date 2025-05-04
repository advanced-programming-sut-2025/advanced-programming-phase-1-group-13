package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Command {
    EXIT_GAME("^\\s*exit\\s*game\\s*$"),
    NEXT_TURN("^\\s*next\\s*turn\\s*$"),
    TIME("^\\s*time\\s*$"),
    DATE("^\\s*date\\s*$"),
    DATETIME("^\\s*datetime\\s*$"),
    DAY_OF_THE_WEEK("^\\s*day\\s*of\\s*the\\s*week\\s*$"),
    CHEAT_ADV_TIME("^\\s*cheat\\s*advance\\s*time\\s*(?<hourIncrease>\\d+)h\\s*$"),
    CHEAT_ADV_DATE("^\\s*cheat\\s*advance\\s*date\\s*(?<dayIncrease>\\d+)d\\s*$"),
    SEASON("^\\s*season\\s*$"),
    CHEAT_THOR("^\\s*cheat\\s*Thor\\s*-l\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    WEATHER("^\\s*weather\\s*$"),
    WEATHER_FORECAST("^\\s*weather\\s*forecast\\s*$"),
    CHEAT_WEATHER_SET("^\\s*cheat\\s*weather\\s*set\\s*(?<type>.+)\\s*$"),
    GREENHOUSE_BUILD("^\\s*greenhouse\\s*build\\s*$"),
    WALK("^\\s*walk\\s*-l\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    WALK_CONFIRM("^\\s*walk\\s*confirm\\s*(?<y_or_n>\\w)\\s*$"),
    PRINT_MAP("^\\s*print\\s*map\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*-s\\s*size\\s*(?<size>\\d+)\\s*$"),
    PRINT_COLORED_MAP("^\\s*print\\s*colored\\s*map\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*-s\\s*size\\s*(?<size>\\d+)\\s*$"),
    HELP_READING_MAP("^\\s*help\\s*reading\\s*map\\s*$"),
    ENERGY_SHOW("^\\s*energy\\s*show\\s*$"),
    CHEAT_ENERGY_SET("^\\s*energy\\s*set\\s*-v\\s*(?<value>\\d+)\\s*$"),
    CHEAT_ENERGY_UNLIMITED("^\\s*energy\\s*unlimited\\s*$"),
    INVENTORY_SHOW("^\\s*energy\\s*show\\s*$"),
    // ... (continue applying the same pattern for all other commands)
    THROW_ITEM_TO_TRASH("^\\s*<itemName>\\s*<number>\\s*$") // number is optional
    ;

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
        return matcher.matches() ? matcher : null;
    }
}
