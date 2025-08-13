package com.ap_project.client.controllers.pregame;

import com.ap_project.Main;
import com.ap_project.client.views.pregame.ScoreboardView;
import com.ap_project.common.models.App;
import com.ap_project.common.models.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreboardController {
    private ScoreboardView view;

    public void handleButtons() {
        if (view != null) {
            if (view.getBackButton().isChecked()) {
                Main.getMain().setScreen(view.getScreen());
            }
            view.getBackButton().setChecked(false);
        }
    }

    public ArrayList<User> getSortedUsers(String sortBy) {
        ArrayList<User> users = App.getLoggedIn().getActiveGame().getPlayers();

        if (sortBy.equals("Money")) {
            users.sort(Comparator.comparingInt(User::getBalance).reversed());
        }

        try {
            if (sortBy.equals("Number of Quests")) {
                users.sort(Comparator.comparingInt(User::getNumberOfQuests).reversed());
            }

            if (sortBy.equals("Total Skills")) {
                users.sort(Comparator.comparingInt(User::getTotalSkills).reversed());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return users;
    }
    public void setView(ScoreboardView view) {
        this.view = view;
    }
}
