package models.enums.commands;

import java.util.regex.*;

public enum LoginCommands implements Command {
    USERNAME_REGEX("^[a-zA-Z0-9-]+$"),
    PASSWORD_REGEX("^[a-zA-Z0-9?<>,\"';:\\\\/|\\[\\] {}+=)(*&^%\\$#!]+$"),
    SHOW_CURRENT_MENU("\\s+show\\s+current\\s+menu\\s+"),
    EMAIL_REGEX("^(?!.*\\.\\.)[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$"),
    REGISTER_REGEX("^\\s+register\\s+-u\\s+(?<username>.+)\\s+-p\\s+(?<password>.+)\\s+(?<password_confirm>.+)\\s+-n\\s+(?<nickname>.+)\\s+-e\\s+(?<email>.+)\\s+-g\\s+(?<gender>.+)\\s+$"),
    PICK_QUESTION_REGEX("^\\s+pick\\s+question\\s+-q\\s+(?<question_number>\\d+)\\s+-a\\s+(?<answer>.+)\\s+-c\\s+(?<answer_confirm>.+)\\s+$"),
    LOGIN_REGEX("^\\s+login\\s+-u\\s+(?<username>.+)\\s+(?<password>.+)\\s+(?<stayLoggedIn>-stay-logged-in)?$"),
    FORGET_PASSWORD_REGEX("^\\s+forget\\s+password\\s+-u\\s+(?<username>.+)\\s+$"),
    ANSWER_REGEX("^\\s+answer\\s+-a\\s+(?<answer>.+)$");


    private final String regex;
    private final Pattern pattern;

    LoginCommands(String regex) {
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


