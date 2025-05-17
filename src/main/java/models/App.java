package models;

import models.enums.Menu;

import java.util.ArrayList;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();
    private static User loggedIn = null;
    private static Menu currentMenu = Menu.LOGIN_MENU;
    private static Game currentGame = null;
    private static Shop currentShop = null;

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

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static Shop getCurrentShop() {
        return currentShop;
    }

    public static void setCurrentShop(Shop currentShop) {
        App.currentShop = currentShop;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void addGame(Game game) {
        games.add(game);
    }

    public static User getUserByUsername(String username) {
        if (users.isEmpty()) {
            return null;
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        if (users.isEmpty()) {
            return null;
        }
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
