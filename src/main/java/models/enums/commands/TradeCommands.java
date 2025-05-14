package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeCommands implements Command {
    TRADE("^\\s*trade\\s+-u\\s+(?<username>.+)\\s+-t\\s+(?<type>.+)\\s+-i\\s+(?<item>.+)\\s+-a\\s+(?<amount>.+)" +
            "\\s*((-p\\s+(?<price>\\d+))|-ti\\s+(?<targetItem>.+)\\s+-ta\\s+(?<targetAmount>.+))\\s*$"),
    TRADE_LIST("^\\s*trade\\s+list\\s*$"),
    TRADE_RESPONSE("^\\s*trade\\s+response\\s+(?<response>accept|reject)\\s+-i\\s+(?<id>\\d+)\\s*$"),
    TRADE_HISTORY("^\\s*trade\\s+history\\s*$"),
    EXIT_TRADE_MENU("^\\s*exit\\s+trade\\s+menu\\s*$"),
    ;
    private final String regex;
    private final Pattern pattern;

    TradeCommands(String regex) {
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