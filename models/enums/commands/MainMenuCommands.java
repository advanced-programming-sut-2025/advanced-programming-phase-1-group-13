package models.enums.commands;

public enum MainMenuCommands implements Command{
    TEMP("Regexes here");

    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
