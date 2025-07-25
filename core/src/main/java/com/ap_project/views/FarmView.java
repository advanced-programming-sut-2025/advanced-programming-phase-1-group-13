package com.ap_project.views;

import com.ap_project.controllers.GameController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class FarmView extends GameView {
    public FarmView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getFarm(App.getCurrentGame(), App.getLoggedIn());
    }
}
