package com.ap_project.client.controllers.pregame;

import com.ap_project.common.models.Farm;
import com.ap_project.client.views.pregame.ChooseMapMenuView;

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
        view.getPlayers().get(view.getCurrentPlayerIndex()).setFarm(new Farm(mapNumber));
    }
}
