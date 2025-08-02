package com.ap_project.common.models.enums.commands;

import java.util.regex.*;

public enum LoginCommands implements Command {
    VALID_USERNAME("^[a-zA-Z0-9-]+$"),
    VALID_PASSWORD("^[a-zA-Z0-9?<>,\"';:\\\\/|\\[\\] {}+=)(*&^%\\$#!]+$"),
    VALID_EMAIL("^(?!.*\\.\\.)[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*" +
            "[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$"),
    REGISTER_USER_PASS("^\\s*register\\s+-u\\s+(?<username>.+)\\s+-p\\s+(?<password>.+)\\s+(?<repeatPassword>.+)\\s+" +
            "-n\\s+(?<nickname>.+)\\s+-e\\s+(?<email>.+)\\s+-g\\s+(?<gender>.+)\\s*$"),
    REGISTER_USER_RAND("^\\s*register\\s+-u\\s+(?<username>.+)\\s+" +
            "-n\\s+(?<nickname>.+)\\s+-e\\s+(?<email>.+)\\s+-g\\s+(?<gender>.+)\\s+-r\\s*$"),
    PICK_QUESTION_REGEX("^\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>\\d+)\\s+-a\\s+(?<answer>.+)\\s+-c\\s+(?<repeatAnswer>.+)\\s*$"),
    LOGIN("^\\s*login\\s+-u\\s+(?<username>.+)\\s+-p\\s+(?<password>.+)\\s*(?<stayLoggedIn>-stay-logged-in)?\\s*$"),
    FORGET_PASSWORD("^\\s*forget\\s+password\\s+-u\\s+(?<username>.+)\\s*$"),
    ANSWER_SECURITY_QUESTION("^\\s*answer\\s+-a\\s+(?<answer>.+)\\s*$"),
    SHOW_CURRENT_MENU("^\\s*show\\s+current\\s+menu\\s*$"),
    MENU_EXIT("^\\s*(menu\\s+)?exit\\s*$");

    private final String regex;
    private final Pattern pattern;

    LoginCommands(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(this.regex);
    }

    @Override
    public Boolean matches(String input) {
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


