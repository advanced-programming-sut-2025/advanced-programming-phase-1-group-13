package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.Quest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class JournalView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowX;
    private final float windowY;
    private final Image slider;
    private Image sliderTrack;
    private int currentIndex;
    private final ArrayList<Image> quests;
    private final GameView gameView;
    private final GameController controller;

    public JournalView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getJournal());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.slider = new Image(GameAssetManager.getGameAssetManager().getSlider());

        this.quests = new ArrayList<>();
        for (int i = 0; i < App.getCurrentGame().getQuests().size(); i++) {
            quests.add(new Image(GameAssetManager.getGameAssetManager().getQuest(i + 1)));
        }

        this.currentIndex = 0;

        this.gameView = gameView;
        this.controller = new GameController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);

        sliderTrack = new Image(GameAssetManager.getGameAssetManager().getSliderTrack());
        sliderTrack.setPosition(windowX + window.getWidth() + 20, windowY + 20);
        stage.addActor(sliderTrack);
        updateQuests();
        updateScrollSlider();
        stage.addActor(slider);
    }

    @Override
    public void render(float delta) {
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

        for (Quest quest : App.getCurrentGame().getQuests()) {
            if (hoverOnImage(quests.get(quest.getId() - 1), screenX, convertedY)) {
                controller.finishQuest(quest.getId() + "");
            }
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
        if (amountY > 0) changeIndex(1);
        else changeIndex(-1);
        updateQuests();
        return false;
    }

    private void updateQuests() {
        for (Image quest : quests) {
            quest.remove();
        }
        stage.addActor(window);
        for (int i = 0; i < 6; i++) {
            int questIndex = currentIndex + i;
            Image quest = quests.get(questIndex);
            quest.setPosition(
                windowX + 25,
                windowY + 474 - 90 * (i % 6)
            );
            stage.addActor(quest);

            Image status = getStatus(App.getCurrentGame().getQuestById(currentIndex + i + 1));
            status.setPosition(
                windowX + 740,
                quest.getY() + 18
            );
            stage.addActor(status);
        }
    }

    public Image getStatus(Quest quest) {
        if (!quest.isUnlocked(App.getLoggedIn())) {
            return new Image(GameAssetManager.getGameAssetManager().getLock());
        }

        if (!quest.isFinished()) {
            return new Image(GameAssetManager.getGameAssetManager().getUncheckedBox());
        }

        if (quest.getFinisher().equals(App.getLoggedIn())) {
            return new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
        }

        return new Image(GameAssetManager.getGameAssetManager().getRedCheckedBox());
    }

    private void updateScrollSlider() {
        if (sliderTrack == null) return;
        float tx = sliderTrack.getX();
        float ty = sliderTrack.getY();
        float th = sliderTrack.getHeight();

        if (quests.size() < 2) {
            slider.setPosition(tx, ty + th - slider.getHeight());
            return;
        }

        float step = (th - slider.getHeight()) / (quests.size() - 6);
        float knobY = ty + th - slider.getHeight() - (step * currentIndex);
        slider.setPosition(tx, knobY);
    }

    private void changeIndex(int amount) {
        int maxIndex = quests.size() - 6;
        currentIndex += amount;
        if (currentIndex < 0) currentIndex = 0;
        if (currentIndex > maxIndex) currentIndex = maxIndex;
    }
}
