package com.ap_project.controllers.pregame;

import com.ap_project.controllers.GameController;
import com.ap_project.models.*;
import com.ap_project.models.enums.Menu;
import com.ap_project.views.FarmView;
import com.ap_project.views.GameView;
import com.ap_project.views.VillageView;
import com.ap_project.views.pregame.PreGameMenuView;

import static com.ap_project.Main.*;

public class PreGameMenuController {
    private PreGameMenuView view;

    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void handlePreGameMenuButtons() {
        if (view != null) {
            if (view.getNewGameButton().isChecked()) {
                goToNewGameMenu();
            } else if (view.getLoadGameButton().isChecked()) {
                loadGame();
            } else if (view.getBackButton().isChecked()) {
                goToMainMenu();
            }
            view.getNewGameButton().setChecked(false);
            view.getLoadGameButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

    public Result loadGame() {
        User player = App.getLoggedIn();
        Game game = player.getActiveGame();
        App.setCurrentGame(game);
        GameView gameView;
        if (game.isInNPCVillage()) {
            gameView = new VillageView(new GameController(), GameAssetManager.getGameAssetManager().getSkin());
        } else {
            gameView = new FarmView(new GameController(), GameAssetManager.getGameAssetManager().getSkin());
        }
        goToGame(gameView);
        App.setCurrentMenu(Menu.GAME_MENU);
        return new Result(true, "Starting game...");
    }

    public Result showCurrentMenu() {
        return new Result(true, "You are now in " + App.getCurrentMenu().toString() + ".");
    }
}
