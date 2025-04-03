package models.enums.commands;

import java.util.regex.Matcher;

public interface Command {
    String getPattern();

    default Matcher getMatcher(String input) {
        
    }
}