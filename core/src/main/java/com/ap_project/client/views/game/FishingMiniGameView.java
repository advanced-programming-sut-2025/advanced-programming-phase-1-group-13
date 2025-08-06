package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.FishingController;
import com.ap_project.common.models.Fish;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.enums.Quality;
import com.ap_project.common.models.enums.types.FishType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FishingMiniGameView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowY;
    private final Image fish;
    private final Image greenBar;
    private float greenBarY;
    private final Image scoreBar;
    private float score;
    private final GameView gameView;
    private final FishingController controller;

    public FishingMiniGameView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getFishingMiniGameWindow());
        float windowX = Gdx.graphics.getWidth() / 2f + 50;
        this.windowY = Gdx.graphics.getHeight() / 2f - 300;
        window.setPosition(windowX, windowY);

        // TODO: get random fishing type based on season and skill and add sonar bobber
        // TODO: add orange crown if it's legendary
        this.fish = new Image(GameAssetManager.getGameAssetManager().getTextureByFish(new Fish(FishType.SUNFISH, Quality.NORMAL)));
        fish.setScale(0.65f);
        fish.setPosition(
            windowX + 80,
            windowY + (window.getHeight() / 2) - (fish.getHeight() / 2)
        );

        this.greenBar = new Image(GameAssetManager.getGameAssetManager().getFishingGreenBar());
        greenBarY = windowY + window.getHeight() / 2;
        greenBar.setPosition(
            windowX + 81,
            greenBarY
        );

        score = 0;
        this.scoreBar = new Image(GameAssetManager.getGameAssetManager().getGreenBar());
        scoreBar.setWidth(8f);
        scoreBar.setPosition(
            windowX + 128,
            windowY + 20
        );

        this.controller = new FishingController(this);
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);
        stage.addActor(greenBar);
        stage.addActor(fish);
        stage.addActor(scoreBar);
    }

    @Override
    public void render(float delta) {
        fish.setY(windowY + controller.getFishYOffset(delta));
        greenBar.setY(greenBarY);
        scoreBar.setHeight(score);

        score++; // TODO: change based on if fish is on green bar or not and finish mini game if its 0 or full
        // TODO: track if fishing was perfect and after winning, show on top of the screen if it is and increase quality and fishing skill

        stage.act(Math.min(delta, 1 / 30f));
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
        if (keycode == Input.Keys.Q) {
            Main.getMain().setScreen(gameView);
            return true;
        }

        // TODO: restrict greenBarY
        if (keycode == Input.Keys.UP) {
            greenBarY += 10;
        }

        if (keycode == Input.Keys.DOWN) {
            greenBarY -= 10;
        }

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

    public Image getWindow() {
        return window;
    }
}
