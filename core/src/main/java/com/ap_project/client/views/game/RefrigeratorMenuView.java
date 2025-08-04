package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class RefrigeratorMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image refrigeratorWindow;
    private final Image inventoryWindow;
    private final Image trashCan;
    private final Image okButton;
    private final GameView gameView;

    public RefrigeratorMenuView(GameView gameView) {
        this.refrigeratorWindow = new Image(GameAssetManager.getGameAssetManager().getRefrigeratorMenu());
        refrigeratorWindow.setPosition(
            (Gdx.graphics.getWidth() - refrigeratorWindow.getWidth()) / 2,
            Gdx.graphics.getHeight() / 2f + 25
        );

        this.inventoryWindow = new Image(GameAssetManager.getGameAssetManager().getRefrigeratorMenuInventory());
        inventoryWindow.setPosition(
            (Gdx.graphics.getWidth() - inventoryWindow.getWidth()) / 2,
            Gdx.graphics.getHeight() / 2f - inventoryWindow.getHeight() - 25
        );

        this.trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashCan());
        trashCan.setPosition(
            Gdx.graphics.getWidth() / 2f + 500,
            Gdx.graphics.getHeight() / 2f - 150
        );

        this.okButton = new Image(GameAssetManager.getGameAssetManager().getOkButton());
        okButton.setPosition(
            Gdx.graphics.getWidth() / 2f + 500,
            Gdx.graphics.getHeight() / 2f - 250
        );

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(refrigeratorWindow);
        stage.addActor(inventoryWindow);
        stage.addActor(trashCan);
        stage.addActor(okButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float convertedY = Gdx.graphics.getHeight() - screenY;

        if (hoverOnImage(okButton, screenX, convertedY)) {
            Main.getMain().setScreen(gameView);
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
