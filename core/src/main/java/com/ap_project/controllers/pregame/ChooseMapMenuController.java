package com.ap_project.controllers.pregame;

import com.ap_project.models.App;
import com.ap_project.models.GameMap;
import com.ap_project.models.User;
import com.ap_project.models.enums.Menu;
import com.ap_project.views.pregame.ChooseMapMenuView;

import static com.ap_project.models.App.setCurrentMenu;

public class ChooseMapMenuController {
    private ChooseMapMenuView view;

    public void setView(ChooseMapMenuView view) {
        this.view = view;
    }

    public void handleChooseMapMenuButtons() {
        if (view != null) {
            if (view.getChooseButton().isChecked()) {
                String mapNumber = view.getMapOptions().getSelected();
                chooseGameMap(mapNumber);
                view.nextTurn();
            }
            view.getChooseButton().setChecked(false);
        }
    }

    public void chooseGameMap(String mapNumberString) {
        int mapNumber = Integer.parseInt(mapNumberString);
        User player = App.getLoggedIn();
        player.getActiveGame().setGameMap(new GameMap(mapNumber));
        setCurrentMenu(Menu.GAME_MENU);
    }
}
