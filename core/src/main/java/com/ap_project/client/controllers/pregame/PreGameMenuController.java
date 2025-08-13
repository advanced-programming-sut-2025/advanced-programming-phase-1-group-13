package com.ap_project.client.controllers.pregame;

import com.ap_project.client.controllers.game.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.Menu;
import com.ap_project.client.views.game.FarmView;
import com.ap_project.client.views.game.GameView;
import com.ap_project.client.views.game.VillageView;
import com.ap_project.client.views.pregame.PreGameMenuView;

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
                Result result = loadGame();
                if (!result.success) {
                    view.getErrorMessageLabel().setText(result.message);
                }
            } else if (view.getScoreboardButton().isChecked()) {
                if (App.getLoggedIn().getActiveGame() == null) {
                    view.getErrorMessageLabel().setText("You have no active games.");
                } else {
                    goToScoreboard(view);
                }
            } else if (view.getLobbyButton().isChecked()) {
                goToLobbyMenu();
            } else if (view.getBackButton().isChecked()) {
                goToMainMenu();
            }
            view.getNewGameButton().setChecked(false);
            view.getLoadGameButton().setChecked(false);
            view.getLobbyButton().setChecked(false);
            view.getScoreboardButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

    public Result loadGame() {
        User player = App.getLoggedIn();
        Game game = player.getActiveGame();
        if (game == null) {
            return new Result(false, "You have no active game to load.");
        }
        App.setCurrentGame(game);
        GameView gameView;
        if (player.isInVillage()) {
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
