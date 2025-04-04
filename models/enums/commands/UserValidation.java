package models.enums.commands;

public enum User implements Command{
    PASSWORD_REGEX(),
    EMAIL_REGEX();

    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
