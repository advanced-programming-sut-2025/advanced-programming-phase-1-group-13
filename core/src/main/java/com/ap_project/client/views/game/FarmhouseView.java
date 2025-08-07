package com.ap_project.client.views.game;

import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.Artisan;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.Position;
import com.ap_project.common.models.enums.environment.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.Main.*;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class FarmhouseView extends GameView {
    private final Texture refrigeratorTexture;

    public FarmhouseView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getFarmhouse();

        this.refrigeratorTexture = GameAssetManager.getGameAssetManager().getRefrigerator();

        Position doorPosition;
        if (App.getLoggedIn().getFarm().getMapNumber() == 1) doorPosition = new Position(56, 9);
        else doorPosition = new Position(10, 9);
        playerSprite.setPosition(
            doorPosition.getX() * TILE_SIZE,
            (-doorPosition.getY() + originPosition.getY()) * TILE_SIZE
        );
        App.getLoggedIn().setDirection(Direction.UP);
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);

        if (keycode == Input.Keys.J) {
            goToGame(new FarmView(controller, GameAssetManager.getGameAssetManager().getSkin()));
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Position refrigeratorPosition;
        if (App.getLoggedIn().getFarm().getMapNumber() == 1) refrigeratorPosition = new Position(54, 2);
        else refrigeratorPosition = new Position(8, 2);
        if (clickedOnTexture(screenX, screenY, refrigeratorTexture, refrigeratorPosition, scale)) {
            goToRefrigeratorMenu(this);
            return true;
        }
        return false;
    }
}
