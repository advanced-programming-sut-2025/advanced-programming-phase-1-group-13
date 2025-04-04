package models;

import java.util.ArrayList;

public class App {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Game> games = new ArrayList<>();
    private static User loggedIn = null;

    public static User getLoggedIn() {
        return loggedIn ;
    }

    public static void setLoggedIn(User user) {
        App.loggedIn = user;
    }
}
