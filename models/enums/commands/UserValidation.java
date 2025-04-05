package models.enums.commands;

public enum UserValidation implements Command{
    USERNAME_REGEX("^[a-zA-Z0-9-]+$"),
    PASSWORD_REGEX("^[a-zA-Z0-9?<>,\"';:\\\\/|\\[\\] {}+=)(*&^%\\$#!]+$"),
    EMAIL_REGEX("^(?!.*\\.\\.)[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$");

    private String pattern;

    void MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    UserValidation(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
