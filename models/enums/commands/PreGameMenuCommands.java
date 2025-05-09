package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreGameMenuCommands implements Command {
    GAME_NEW("^\\s*game\\s+new\\s+(?<usernames>.+)*\\s*$"),
    GAME_MAP("^\\s*game\\s+map\\s+(?<map_number>\\d+)$"),
    LOAD_GAME("^\\s*load\\s+game\\s*$"),
    EXIT_GAME("^\\s*exit\\s+game\\s*$");

    private final String regex;
    private final Pattern pattern;

    PreGameMenuCommands(String regex) {
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
