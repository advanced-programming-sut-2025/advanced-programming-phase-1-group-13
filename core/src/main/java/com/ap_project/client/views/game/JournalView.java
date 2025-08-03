package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.NPC;
import com.ap_project.common.models.enums.types.NPCType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class JournalView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowX;
    private final float windowY;
    private final Image slider;
    private Image sliderTrack;
    private int currentIndex;
    private final GameView gameView;
    private final List<Map.Entry<HashMap<?, Integer>, HashMap<?, Integer>>> quests;
    private final ArrayList<Label> questLabels = new ArrayList<>();
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public JournalView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getJournal());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.slider = new Image(GameAssetManager.getGameAssetManager().getSlider());
        // adjust slider height based on total number of quests
        HashMap<HashMap<?, Integer>, HashMap<?, Integer>> rawQuests =
            gameView.getNpc().getType().getQuests();
        this.quests = new ArrayList<>(rawQuests.entrySet());

        this.currentIndex = 0;

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);

        sliderTrack = new Image(GameAssetManager.getGameAssetManager().getSliderTrack());
        sliderTrack.setPosition(windowX + window.getWidth() + 20, windowY + 20);
        stage.addActor(sliderTrack);

        // adjust y based on currentIndex
        updateScrollSlider();
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
        stage.dispose();
        skin.dispose();
    }

    @Override
    public boolean keyDown(int keycode) { return false; }
    @Override
    public boolean keyUp(int keycode) { return false; }
    @Override
    public boolean keyTyped(char character) { return false; }

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
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0) changeIndex(1);
        else changeIndex(-1);
        return false;
    }

    private void updateQuestLabels() {
        // remove old labels
        for (Label lbl : questLabels) lbl.remove();
        questLabels.clear();

        if (quests.isEmpty()) return;

        // get current quest
        Map.Entry<HashMap<?, Integer>, HashMap<?, Integer>> entry = quests.get(currentIndex);
        HashMap<?, Integer> requests = entry.getKey();
        HashMap<?, Integer> rewards = entry.getValue();

        // title label
        Label title = new Label("Quest " + (currentIndex + 1), skin);
        title.setColor(Color.WHITE);
        title.setPosition(windowX + 20, windowY + window.getHeight() - 60);
        stage.addActor(title);
        questLabels.add(title);

        // display requests
        int offsetY = 100;
        for (Map.Entry<?, Integer> req : requests.entrySet()) {
            Label reqLabel = new Label(
                "Bring " + req.getValue() + "× " + req.getKey(), skin
            );
            reqLabel.setColor(Color.YELLOW);
            reqLabel.setPosition(windowX + 20, windowY + window.getHeight() - offsetY);
            stage.addActor(reqLabel);
            questLabels.add(reqLabel);
            offsetY += 30;
        }

        // display rewards
        offsetY += 20;
        for (Map.Entry<?, Integer> rew : rewards.entrySet()) {
            Label rewLabel = new Label(
                "Reward: " + rew.getValue() + "× " + rew.getKey(), skin
            );
            rewLabel.setColor(Color.GREEN);
            rewLabel.setPosition(windowX + 20, windowY + window.getHeight() - offsetY);
            stage.addActor(rewLabel);
            questLabels.add(rewLabel);
            offsetY += 30;
        }
    }

    private void updateScrollSlider() {
        if (sliderTrack == null) return;
        float tx = sliderTrack.getX();
        float ty = sliderTrack.getY();
        float th = sliderTrack.getHeight();

        if (quests.size() < 2) {
            slider.setPosition(tx, ty);
            return;
        }

        float step = (th - slider.getHeight()) / (quests.size() - 1);
        float knobY = ty + step * currentIndex;
        slider.setPosition(tx, knobY);
    }

    private void changeIndex(int amount) {
        int maxIndex = quests.size() - 1;
        currentIndex += amount;
        if (currentIndex < 0) currentIndex = 0;
        if (currentIndex > maxIndex) currentIndex = maxIndex;
    }
}
