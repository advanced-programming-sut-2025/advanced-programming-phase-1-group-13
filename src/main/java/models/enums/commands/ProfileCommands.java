package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileCommands implements Command {
    CHANGE_USERNAME("^\\s*change\\s+username\\s+-u\\s+(?<username>[a-zA-Z0-9-]+)$"),
    CHANGE_NICKNAME("^\\s*change\\s+nickname\\s+-u\\s+(?<nickname>[a-zA-Z0-9-]+)$"),
    CHANGE_EMAIL("^\\s*change\\s+email\\s+(?<email>(?!.*\\\\.\\\\.)[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\\\.)+[A-Za-z]{2,})$"),
    CHANGE_PASSWORD("^\\s*change\\s+password\\s+-p\\s+(?<oldPass>.+)\\s+-o\\s+(?<newPass>[a-zA-Z0-9?<>,\"';:\\\\/|\\[\\] {}+=)(*&^%\\$#!]+)$"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    USER_INFO("^\\s*user\\s+info\\s*$"),
    MENU_EXIT("^\\s*menu\\s+exit\\s*$");

    private final String regex;
    private final Pattern pattern;

    ProfileCommands(String regex) {
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
