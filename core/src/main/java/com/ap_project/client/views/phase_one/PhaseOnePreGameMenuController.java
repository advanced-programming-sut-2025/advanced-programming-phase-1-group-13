package com.ap_project.client.views.phase_one;

import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.Menu;

import java.util.ArrayList;
import java.util.Arrays;

import static com.ap_project.common.models.App.*;

public class PhaseOnePreGameMenuController {
    public Result gameNew(String usernamesStr) {
        if (usernamesStr == null || usernamesStr.trim().isEmpty()) {
            return new Result(false, "Enter usernames.");
        }

        ArrayList<String> usernames = new ArrayList<>(Arrays.asList(usernamesStr.trim().split("\\s+")));
        if (usernames.isEmpty() || usernames.size() > 3) {
            return new Result(false, "Enter 1-3 usernames.");
        }

        User loggedInUser = App.getLoggedIn();
        ArrayList<User> players = new ArrayList<>();
        players.add(loggedInUser);

        for (String username : usernames) {
            User user = getUserByUsername(username);
            if (user == null) {
                return new Result(false, "Please enter valid usernames.");
            }
            if (username.equals(loggedInUser.getUsername())) {
                return new Result(false, "Don't enter your own username.");
            }
            players.add(user);
        }

        for (User player : players) {
            if (player.getActiveGame() != null) {
                return new Result(false, player.getUsername() + " is already in a game.");
            }
        }

        Game game = new Game(players);
        for (User user : players) {
            user.setActiveGame(game);
        }
        App.addGame(game);
        return new Result(true, "New game made.");
    }

    public Result chooseGameMap(String mapNumberString, User user) {
        int mapNumber = Integer.parseInt(mapNumberString);
        if (mapNumber != 1 & mapNumber != 2) {
            return new Result(false, "Map number out of bounds. Chose either 1 or 2.");
        }
        user.setFarm(new Farm(mapNumber));
        return new Result(true, "Game map chosen. " + user.getFarm().getTrees());
    }

    public Result loadGame() {
        User player = App.getLoggedIn();
        App.setCurrentGame(player.getActiveGame());
        App.setCurrentMenu(Menu.GAME_MENU);
        return new Result(true, "Starting game...");
    }

    public Result showCurrentMenu() {
        return new Result(true, "You are now in " + App.getCurrentMenu().toString() + ".");
    }
}
