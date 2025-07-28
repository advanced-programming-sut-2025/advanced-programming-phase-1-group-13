package com.ap_project.views.game;

import com.ap_project.Main;
import com.ap_project.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.views.game.GameMenuView.hoverOnImage;

public class JournalView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowX;
    private final float windowY;
    private final Image slider;
    private int currentIndex;
    private final GameView gameView;

    public JournalView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getJournal());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.slider = new Image(GameAssetManager.getGameAssetManager().getSlider());
        // TODO: adjust slider height based on total number of quests

        this.currentIndex = 0;

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);

        Image sliderTrack = new Image(GameAssetManager.getGameAssetManager().getSliderTrack());
        sliderTrack.setPosition(windowX + window.getWidth() + 20, windowY + 20);
        stage.addActor(sliderTrack);

        slider.setPosition(sliderTrack.getX(), sliderTrack.getY()); // TODO: adjust y based on currentIndex
        stage.addActor(slider);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        updateScrollSlider();
        updateQuestLabels();
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

        Image closeButton = new Image(GameAssetManager.getGameAssetManager().getWhiteScreen());
        closeButton.setSize(40, 40);
        closeButton.setPosition(
            windowX + window.getWidth() - 40,
            windowY + window.getHeight() - 110
        );
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
        if (amountY > 0) {
            changeIndex(1);
        } else {
            changeIndex(-1);
        }
        return false;
    }

    private void updateQuestLabels() {
        // TODO: add Labels for quests and their info based on the current page index
    }

    private void updateScrollSlider() {
        // TODO: move slider based on value of currentIndex
    }

    private void changeIndex(int amount) {
        int numberOfQuests = 10; // TODO: get from game
        currentIndex += amount;
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        if (currentIndex > numberOfQuests) {
            currentIndex = numberOfQuests;
        }
    }
}
