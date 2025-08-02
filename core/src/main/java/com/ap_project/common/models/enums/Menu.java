package com.ap_project.models.enums;

import com.ap_project.views.phase_one.*;

import java.util.Scanner;

public enum Menu {
    LOGIN_MENU(new LoginMenu(), "Login Menu"),
    MAIN_MENU(new MainMenu(), "Main Menu"),
    GAME_MENU(new GameMenu(), "Game Menu"),
    PRE_GAME_MENU(new PreGameMenu(), "Pre-Game Menu"),
    PROFILE_MENU(new ProfileMenu(), "Profile Menu"),
    TRADE_MENU(new TradeMenu(), "Trade Menu"),
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
        Menu result;
        switch (displayName) {
            case "Login Menu":
                result = LOGIN_MENU;
                break;
            case "Main Menu":
                result = MAIN_MENU;
                break;
            case "Profile Menu":
                result = PROFILE_MENU;
                break;
            case "Game Menu":
                result = GAME_MENU;
                break;
            case "Pre-Game Menu":
                result = PRE_GAME_MENU;
                break;
            case "Trade Menu":
                result = TRADE_MENU;
                break;
            case "Exit":
                result = EXIT;
                break;
            default:
                result = null;
        }
        return result;
    }

}
