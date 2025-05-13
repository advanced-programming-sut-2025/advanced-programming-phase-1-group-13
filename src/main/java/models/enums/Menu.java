package models.enums;

import views.*;

import java.util.Scanner;

public enum Menu {
    LOGIN_MENU(new LoginMenu(), "Login Menu"),
    MAIN_MENU(new MainMenu(), "Main Menu"),
    GAME_MENU(new GameMenu(), "Game Menu"),
    PRE_GAME_MENU(new PreGameMenu(), "Pre-Game Menu"),
    PROFILE_MENU(new ProfileMenu(), "Profile Menu"),
    TARDE_MENU(new TradeMenu(), "Trade Menu"),
    EXIT(new ExitMenu(), "Exit");

    private final AppMenu menu;
    private final String displayName;

    Menu(AppMenu menu, String displayName) {
        this.menu = menu;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }

    public static Menu getMenuFromDisplayName(String displayName) {
        return switch (displayName) {
            case "Login Menu" -> LOGIN_MENU;
            case "Main Menu" -> MAIN_MENU;
            case "Profile Menu" -> PROFILE_MENU;
            case "Game Menu" -> GAME_MENU;
            case "Pre-Game Menu" -> PRE_GAME_MENU;
            case "Trade Menu" -> TARDE_MENU;
            case "Exit" -> EXIT;
            default -> null;
        };
    }

}
