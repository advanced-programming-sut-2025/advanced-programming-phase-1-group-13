package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.common.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class GroupQuestView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowX;
    private final float windowY;
    private final Image slider;
    private Image sliderTrack;
    private int currentIndex;
    private final ArrayList<Image> groupquests;
    private final Label errorMessageLabel;
    private final GameView gameView;
    private final GameController controller;

    public GroupQuestView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getGroupQuest());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.slider = new Image(GameAssetManager.getGameAssetManager().getSlider());

        this.groupquests = new ArrayList<>();
        for (int i = 0; i < App.getCurrentGame().getGroupQuests().size(); i++) {
            groupquests.add(new Image(GameAssetManager.getGameAssetManager().getGroupQuest(i + 1)));
        }

        this.currentIndex = 0;

        this.errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

        this.gameView = gameView;
        this.controller = new GameController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);
        stage.addActor(errorMessageLabel);
        sliderTrack = new Image(GameAssetManager.getGameAssetManager().getSliderTrack());
        sliderTrack.setPosition(windowX + window.getWidth() + 20, windowY + 20);
        stage.addActor(sliderTrack);
        updateGroupQuests();
        updateScrollSlider();
        stage.addActor(slider);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        updateScrollSlider();
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
        stage.dispose();
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

        for (GroupQuest groupquest : App.getCurrentGame().getGroupQuests()) {
            if (hoverOnImage(groupquests.get(groupquest.getId() - 1), screenX, convertedY)) {
                Result result = controller.finishQuest(groupquest.getId() + "");
                if (result.success) Main.getMain().setScreen(gameView);
                else {
                    errorMessageLabel.setText(result.message);
                    return false;
                }
            }
        }
        errorMessageLabel.setText("");
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
        if (amountY > 0) changeIndex(1);
        else changeIndex(-1);
        updateGroupQuests();
        return false;
    }

    private void updateGroupQuests() {
        for (Image groupquest : groupquests) {
            groupquest.remove();
        }
        stage.addActor(window);
        for (int i = 0; i < 6; i++) {
            int questIndex = currentIndex + i;
            Image groupquest = groupquests.get(questIndex);
            groupquest.setPosition(
                windowX + 25,
                windowY + 474 - 90 * (i % 6)
            );
            stage.addActor(groupquest);

            Image status = getStatus(App.getCurrentGame().getGroupQuestById(currentIndex + i + 1));
            status.setPosition(
                windowX + 740,
                groupquest.getY() + 18
            );
            stage.addActor(status);
        }
    }

    public Image getStatus(GroupQuest groupquest) {
        if (!groupquest.isUnlocked(App.getLoggedIn())) {
            return new Image(GameAssetManager.getGameAssetManager().getLock());
        }

        if (!groupquest.isFinished()) {
            return new Image(GameAssetManager.getGameAssetManager().getUncheckedBox());
        }

        if (groupquest.getFinisher().equals(App.getLoggedIn())) {
            return new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
        }

        return new Image(GameAssetManager.getGameAssetManager().getRedCheckedBox());
    }

    private void updateScrollSlider() {
        if (sliderTrack == null) return;
        float tx = sliderTrack.getX();
        float ty = sliderTrack.getY();
        float th = sliderTrack.getHeight();

        if (groupquests.size() < 2) {
            slider.setPosition(tx, ty + th - slider.getHeight());
            return;
        }

        float step = (th - slider.getHeight()) / (groupquests.size() - 6);
        float knobY = ty + th - slider.getHeight() - (step * currentIndex);
        slider.setPosition(tx, knobY);
    }

    private void changeIndex(int amount) {
        int maxIndex = groupquests.size() - 6;
        currentIndex += amount;
        if (currentIndex < 0) currentIndex = 0;
        if (currentIndex > maxIndex) currentIndex = maxIndex;
    }
}
