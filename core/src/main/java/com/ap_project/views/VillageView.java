package com.ap_project.views;

import com.ap_project.controllers.GameController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.ap_project.Main.goToGame;

public class VillageView extends GameView {
    public VillageView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getVillage(App.getCurrentGame().getGameState().getTime().getSeason());
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.O) { // TODO
            App.getCurrentGame().setInNPCVillage(false);
            goToGame(new FarmView(controller, GameAssetManager.getGameAssetManager().getSkin()));
        }
        return false;
    }
}
