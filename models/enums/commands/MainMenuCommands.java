package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Command{
    USER_LOGOUT("^\\s*(user\\s+)?logout\\s*$"),
    MENU_EXIT("^\\s*menu\\s+exit\\s*$"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    MENU_ENTER("^\\s*menu\\s+enter\\s+(?<newMenu>.+)\\s*$");

    private final String regex;
    private final Pattern pattern;

    MainMenuCommands(String regex) {
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
