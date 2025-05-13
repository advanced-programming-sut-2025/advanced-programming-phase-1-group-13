package models.enums.commands;

import java.util.regex.*;

public interface Command {

    String getRegex();

    default Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(getRegex());
        return pattern.matcher(input);
    }

    default boolean matches(String input) {
        return getMatcher(input).matches();
    }
}