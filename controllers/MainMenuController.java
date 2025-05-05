package controllers;

import models.*;
import models.enums.Menu;

public class MainMenuController {

    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenuFromDisplayName(menuName);
        if (newMenu == null) {
            return new Result(false, "Invalid menu name!");
        }
        if (!canSwitchMenu(newMenu)) {
            return new Result(false, "You can't switch to " + menuName + " from here!");
        }
        App.setCurrentMenu(newMenu);
        return new Result(true, "Menu switched to " + menuName);
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.LOGIN_MENU);
        return new Result(true, "You are now in Login Menu");
    }

    public Result showCurrentMenu() {
        return new Result(true, "You are now in " + App.getCurrentMenu().toString());
    }

    public Result logout() {
        App.setLoggedIn(null);
        App.setCurrentMenu(Menu.LOGIN_MENU);
        return new Result(true, "Logged out. You are now in Login Menu");
    }

    private boolean canSwitchMenu(Menu newMenu) {
        return !newMenu.equals(Menu.TARDE_MENU) && !newMenu.equals(Menu.GAME_MENU);
    }

}
