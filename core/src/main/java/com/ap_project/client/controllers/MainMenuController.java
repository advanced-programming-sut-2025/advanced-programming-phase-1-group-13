package com.ap_project.client.controllers;

import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.Menu;
import com.ap_project.client.views.MainMenuView;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;

import java.util.HashMap;

import static com.ap_project.Main.*;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getGameMenuButton().isChecked()) {
                goToPreGameMenu();
            } else if (view.getProfileMenuButton().isChecked()) {
                goToProfileMenu();
            } else if (view.getUsersMenuButton().isChecked()) {
                Message message = new Message(new HashMap<>(), MessageType.REQUESTS_USERS_INFO);
                getClient().sendMessage(JSONUtils.toJson(message));
            } else if (view.getLogoutButton().isChecked()) {
                goToTitleMenu();
            }
            view.getGameMenuButton().setChecked(false);
            view.getProfileMenuButton().setChecked(false);
            view.getLogoutButton().setChecked(false);
        }
    }

    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenuFromDisplayName(menuName);
        if (newMenu == null) {
            return new Result(false, "Invalid menu name!");
        }
        if (!canSwitchMenu(newMenu)) {
            return new Result(false, "You can't switch to " + menuName + " from here!");
        }
        App.setCurrentMenu(newMenu);
        return new Result(true, "Menu switched to " + menuName + ".");
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.LOGIN_MENU);
        return new Result(true, "You are now in Login Menu.");
    }

    public Result showCurrentMenu() {
        return new Result(true, "You are now in " + App.getCurrentMenu().toString() + ".");
    }

    public Result logout() {
        App.setLoggedIn(null);
        App.setCurrentMenu(Menu.LOGIN_MENU);
        return new Result(true, "Logged out. You are now in Login Menu.");
    }

    private boolean canSwitchMenu(Menu newMenu) {
        return !newMenu.equals(Menu.TRADE_MENU) && !newMenu.equals(Menu.GAME_MENU);
    }

}
