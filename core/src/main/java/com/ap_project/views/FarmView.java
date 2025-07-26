package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.GameController;
import com.ap_project.models.App;
import com.ap_project.models.Farm;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class FarmView extends GameView {
    private final Texture cabinTexture;
    private Texture lakeTexture;
    private Farm farm;

    public FarmView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getFarm(App.getCurrentGame(), App.getLoggedIn());
        this.farm = App.getLoggedIn().getFarm();
        this.cabinTexture = GameAssetManager.getGameAssetManager().getCabin();
        this.lakeTexture = GameAssetManager.getGameAssetManager().getLake(farm.getMapNumber());
    }

    @Override
    public void renderMap() {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        draw(cabinTexture, farm.getCabin().getPosition());
        draw(lakeTexture, farm.getLake().getPosition());

        Main.getBatch().end();
    }

    @Override
    public void nextTurn() {
        playerSprite.setPosition(
            (App.getLoggedIn().getPosition().getX()) * TILE_SIZE,
            (-App.getLoggedIn().getPosition().getY() + originPosition.getY()) * TILE_SIZE
        );
        this.farm = App.getLoggedIn().getFarm();
        lakeTexture = GameAssetManager.getGameAssetManager().getLake(farm.getMapNumber());
    }
}
