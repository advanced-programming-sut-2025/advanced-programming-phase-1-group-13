package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.FishingController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.Fish;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.enums.Skill;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class FishingMiniGameView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowY;
    private final Fish fish;
    private final Image fishImage;
    private final Image greenBar;
    private final Image crown;
    private float greenBarY;
    private final Image scoreBar;
    private float score;
    private final Image sonarBobber;
    private boolean isPerfect;
    private boolean waitingForClick;
    private final GameView gameView;
    private final FishingController controller;

    public FishingMiniGameView(GameView gameView, Fish fish) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getFishingMiniGameWindow());
        float windowX = Gdx.graphics.getWidth() / 2f + 50;
        this.windowY = Gdx.graphics.getHeight() / 2f - 300;
        window.setPosition(windowX, windowY);

        this.fish = fish;
        this.fishImage = new Image(GameAssetManager.getGameAssetManager().getFishIcon());
        fishImage.setPosition(
            windowX + 80,
            windowY + (window.getHeight() / 2) - (fishImage.getHeight() / 2)
        );

        this.greenBar = new Image(GameAssetManager.getGameAssetManager().getFishingGreenBar());
        greenBarY = windowY + (window.getHeight() - greenBar.getHeight()) / 2;
        greenBar.setPosition(
            windowX + 81,
            greenBarY
        );

        this.crown = new Image(GameAssetManager.getGameAssetManager().getCrown());
        crown.setScale(0.65f);
        crown.setPosition(fishImage.getX(), fishImage.getY());

        score = 0;
        this.scoreBar = new Image(GameAssetManager.getGameAssetManager().getGreenBar());
        scoreBar.setWidth(8f);
        scoreBar.setPosition(
            windowX + 128,
            windowY + 20
        );

        this.sonarBobber = new Image(GameAssetManager.getGameAssetManager().getSonarBobber());
        sonarBobber.setPosition(
          windowX + 155,
          windowY + 35
        );

        this.isPerfect = true;

        this.waitingForClick = false;

        this.controller = new FishingController(this);
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);
        stage.addActor(greenBar);
        stage.addActor(fishImage);
        stage.addActor(scoreBar);
        stage.addActor(sonarBobber);
        if (fish.getType().isLegendary()) {
            stage.addActor(crown);
        }
    }

    @Override
    public void render(float delta) {
        fishImage.setY(windowY + controller.getFishYOffset(delta));
        crown.setY(fishImage.getY() + 20);

        greenBar.setY(greenBarY);
        scoreBar.setHeight(score);

        if (isFishOnGreenBar()) score++;
        else {
            score--;
            isPerfect = false;
        }

        if (score == 0) Main.getMain().setScreen(gameView);
        if (score > 430) {
            if (isPerfect) {
                Label perfect = new Label("Perfect!", GameAssetManager.getGameAssetManager().getSkin());
                perfect.setColor(Color.BLACK);
                perfect.setPosition(
                    Gdx.graphics.getWidth() / 2f + 80,
                    Gdx.graphics.getHeight() / 2f + 170
                );
                stage.addActor(perfect);

                fish.setQuality(fish.getQuality().getNextQuality());

                int previousSkillPoints = App.getLoggedIn().getSkillPoints().get(Skill.FISHING);
                App.getLoggedIn().updateSkillPoints(Skill.FISHING, (int) (1.4 * previousSkillPoints));
            }

            waitingForClick = true;
            score = 430;
            return;
        }

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

        if (keycode == Input.Keys.UP) {
            greenBarY += 10;
            if (greenBarY > 550) greenBarY = 550;
        }

        if (keycode == Input.Keys.DOWN) {
            greenBarY -= 10;
            if (greenBarY < 230) greenBarY = 230;
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
        if (waitingForClick) {
            App.getLoggedIn().getBackpack().addToInventory(fish, 1);
            Main.getMain().setScreen(gameView);
            return true;
        }

        if (hoverOnImage(sonarBobber, screenX, Gdx.graphics.getHeight() - screenY)) {
            Image sonarBobberWindow = new Image(GameAssetManager.getGameAssetManager().getSonarBobberWindow());
            sonarBobberWindow.setPosition(
                sonarBobber.getX(),
                windowY + 350
            );
            sonarBobberWindow.setScale(0.9f);
            stage.addActor(sonarBobberWindow);

            Image fishType = new Image(GameAssetManager.getGameAssetManager().getTextureByFish(fish));
            fishType.setPosition(
                sonarBobber.getX() + 35,
                sonarBobberWindow.getY() + 12
            );
            stage.addActor(fishType);
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

    public Image getWindow() {
        return window;
    }

    public boolean isFishOnGreenBar() {
        return (fishImage.getY() + fishImage.getHeight()) < (greenBarY + greenBar.getHeight())
            && fishImage.getY() > greenBarY;
    }
}
