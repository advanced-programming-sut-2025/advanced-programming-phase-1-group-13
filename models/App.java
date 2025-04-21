package models;

import models.enums.Menu;

import java.util.ArrayList;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();
    private static User loggedIn = null;
    private static Menu currentMenu = Menu.LOGIN_MENU;
    private static Game currentGame;

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static User getLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(User user) {
        App.loggedIn = user;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }
}