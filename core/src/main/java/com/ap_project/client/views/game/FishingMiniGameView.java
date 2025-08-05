package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.FishingController;
import com.ap_project.common.models.Fish;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.enums.Quality;
import com.ap_project.common.models.enums.types.FishType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class FishingMiniGameView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowX;
    private final float windowY;
    private final Image fish;
    private final Image greenBar;
    private final GameView gameView;
    private final FishingController controller;
    private final Image closeButton;

    public FishingMiniGameView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getFishingMiniGameWindow());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        this.fish = new Image(GameAssetManager.getGameAssetManager().getTextureByFish(new Fish(FishType.SALMON, Quality.NORMAL))); // TODO
        this.greenBar = new Image();
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.controller = new FishingController(this);
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        window.setPosition(windowX, windowY);
        stage.addActor(window);

        float fishX = windowX + (window.getWidth() - fish.getWidth()) / 2;
        float fishY = windowY + (window.getHeight() / 2) - (fish.getHeight() / 2);
        fish.setPosition(fishX, fishY);
        stage.addActor(fish);
        addCloseButton();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        fish.setPosition(
            windowX + (window.getWidth() / 2) - (fish.getWidth() / 2),
            windowY + controller.getFishYOffset(delta)
        );

        stage.act(Math.min(delta, 1/30f));
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

        if (hoverOnImage(closeButton, screenX, convertedY)) {
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
    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = Gdx.graphics.getHeight() / 2f + 300f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }

    public Image getWindow() {
        return window;
    }
}
