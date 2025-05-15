package controllers;

import models.*;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.Arrays;

import static models.App.getUserByUsername;

public class PreGameMenuController {
    public Result gameNew(String usernamesStr) {
        if (usernamesStr == null) {
            return new Result(false, "Enter usernames.");
        }

        ArrayList<String> usernames = new ArrayList<>(Arrays.asList(usernamesStr.split("\\s+")));
        if (usernames.size() != 3) {
            return new Result(false, "Enter three valid usernames");
        }

        ArrayList<User> players = new ArrayList<>(Arrays.asList(getUserByUsername(usernames.get(0)),
                getUserByUsername(usernames.get(1)),
                getUserByUsername(usernames.get(2))));

        if (players.get(0) == null || players.get(1) == null || players.get(2) == null) {
            return new Result(false, "Please enter valid usernames.");
        }

        if (!players.get(0).getUsername().equals(App.getLoggedIn().getUsername())) {
            return new Result(false, "Enter your username as the first player.");
        }

        for (User player : players) {
            if (player.getActiveGame() != null) {
                return new Result(false, player.getUsername() + " is already in a game.");
            }
        }

        Game game = new Game(players, 0); // TODO: Choose map
        for (User user : players) {
            user.setActiveGame(game);
        }
        App.addGame(game);
        return new Result(true, "New game made with you, " + players.get(1).getUsername() +
                " and " + players.get(2).getUsername() + " as players.");
    }

    public Result chooseGameMap(String mapNumberString) {
        int mapNumber = Integer.parseInt(mapNumberString);
        if (mapNumber != 1 & mapNumber != 2) {
            return new Result(false, "Map number out of bounds. Chose either 1 or 2.");
        }
        User player = App.getLoggedIn();
        player.getActiveGame().setGameMap(new GameMap(mapNumber));
        return new Result(true, "Game");
    }

    public Result loadGame() {
        // TODO
        User player = App.getLoggedIn();
        App.setCurrentGame(player.getActiveGame());
        App.setCurrentMenu(Menu.GAME_MENU);
        return new Result(true, "Starting game...");
    }
}
