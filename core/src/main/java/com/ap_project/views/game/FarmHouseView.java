package com.ap_project.views.game;

import com.ap_project.controllers.GameController;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.ap_project.Main.goToGame;

public class FarmHouseView extends GameView {
    public FarmHouseView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getFarmHouse();
    }

    @Override
    public void renderGameWorld(float delta) {
        scale = 0.5f;
        super.renderGameWorld(delta);
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);

        if (keycode == Input.Keys.J) {
            goToGame(new FarmView(controller, GameAssetManager.getGameAssetManager().getSkin()));
        }
        return false;
    }
}
