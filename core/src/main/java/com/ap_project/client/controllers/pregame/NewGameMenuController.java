package com.ap_project.client.controllers.pregame;

import com.ap_project.common.models.*;
import com.ap_project.client.views.pregame.NewGameMenuView;

import java.util.ArrayList;
import java.util.Arrays;

import static com.ap_project.Main.goToChooseMapMenu;
import static com.ap_project.Main.goToPreGameMenu;
import static com.ap_project.common.models.App.getUserByUsername;

public class NewGameMenuController {
    private NewGameMenuView view;

    public void setView(NewGameMenuView view) {
        this.view = view;
    }

    public void handleNewGameMenuButtons() {
        if (view != null) {
            if (view.getStartButton().isChecked()) {
                String usernames =
                    view.getPlayer2Field().getText() + " " +
                    view.getPlayer3Field().getText() + " " +
                    view.getPlayer4Field().getText();
                Result result = gameNew(usernames);
                if (result.success) {
                    goToChooseMapMenu();
                } else {
                    view.setErrorMessage(result.message);
                }
            } else if (view.getBackButton().isChecked()) {
                goToPreGameMenu();
            }
            view.getStartButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

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

        Game game = new Game(players, "", false);
        for (User user : players) {
            user.setActiveGame(game);
        }
        App.addGame(game);
        return new Result(true, "New game made.");
    }
}
