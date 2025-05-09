package controllers;

import models.App;
import models.Game;
import models.Result;
import models.User;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.Arrays;

import static models.App.getUserByUsername;


public class PreGameMenuController {
    public Result gameNew(String usernamesStr) {
        if (usernamesStr == null) {
            return new Result(false, "");
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

        if (!players.get(0).equals(App.getLoggedIn())) {
            return new Result(false, "Enter your username as the first player.");
        }

        //TODO: return new Result(true, "You are already in a game.");

        Game game = new Game(players);
        for (User user : players) {
            user.getGames().add(game);
        }
        App.addGame(game);
        return new Result(true, "New game made with you, " + usernames.get(1) +
                " and " + usernames.get(2));
    }

    public Result chooseGameMap(String mapNumberString) {
        int mapNumber = Integer.parseInt(mapNumberString); // TODO : ERRORS
        // TODO
        return new Result(true, "");
    }

    public Result loadGame() {
        // TODO
        return new Result(true, "");
    }

    public Result exitGame() {
        App.setCurrentMenu(Menu.PRE_GAME_MENU);
        return new Result(true, "Exiting Game ... Heading to Pre-Game Menu");
    }
}
