package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.enums.Menu;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (users == null) {
            users = new ArrayList<>();
        }

        try (FileReader reader = new FileReader("users.json")) {
            Gson gson = new Gson();
            ArrayList<User> loadedUsers = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
            if (loadedUsers != null) {
                users = loadedUsers;
            }
        } catch (IOException e) {
            users = new ArrayList<>();
        }
        return users;
    }


    public static Shop getCurrentShop() {
        return currentShop;
    }

    public static void setCurrentShop(Shop currentShop) {
        App.currentShop = currentShop;
    }

    public static void addUser(User user) {
        App.users.add(user);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        try (FileWriter writer = new FileWriter("users.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace(); // Handle errors
        }
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void addGame(Game game) {
        games.add(game);
    }

    public static User getUserByUsername(String username) {
        ArrayList<User> usersList = getUsers();
        if (usersList.isEmpty()) {
            return null;
        }
        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    public static User getUserByEmail(String email) {
        ArrayList<User> usersList = getUsers();

        if (usersList.isEmpty()) {
            return null;
        }
        for (User user : usersList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}