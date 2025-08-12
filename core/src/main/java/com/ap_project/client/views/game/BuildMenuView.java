package com.ap_project.client.views.game;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.enums.types.FarmBuildingType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.Main.*;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class BuildMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Image closeButton;
    private final GameView gameView;

    public BuildMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getBuildMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);
        addCloseButton();
    }

    @Override
    public void render(float delta) {
        getBatch().begin();
        getBatch().end();
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

        if (hoverOnImage(closeButton, screenX, convertedY)) {
            getMain().setScreen(gameView);
            return true;
        }

        FarmBuildingType farmBuildingType = null;

        Image barn = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        barn.setSize(520, 120);
        barn.setPosition(
            window.getX() + 30,
            window.getY() + window.getHeight() - 160
        );
        if (hoverOnImage(barn, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.BARN;
        }

        Image bigBarn = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        bigBarn.setSize(520, 120);
        bigBarn.setPosition(
            window.getX() + 30,
            window.getY() + window.getHeight() - 300
        );
        if (hoverOnImage(bigBarn, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.BIG_BARN;
        }

        Image deluxeBarn = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        deluxeBarn.setSize(520, 120);
        deluxeBarn.setPosition(
            window.getX() + 30,
            window.getY() + window.getHeight() - 440
        );
        if (hoverOnImage(deluxeBarn, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.DELUXE_BARN;
        }

        Image shippingBin = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        shippingBin.setSize(520, 120);
        shippingBin.setPosition(
            window.getX() + 30,
            window.getY() + 50
        );
        if (hoverOnImage(shippingBin, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.SHIPPING_BIN;
        }

        Image coop = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        coop.setSize(520, 120);
        coop.setPosition(
            window.getX() + 550,
            window.getY() + window.getHeight() - 160
        );
        if (hoverOnImage(coop, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.COOP;
        }

        Image bigCoop = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        bigCoop.setSize(520, 120);
        bigCoop.setPosition(
            window.getX() + 550,
            window.getY() + window.getHeight() - 300
        );
        if (hoverOnImage(bigCoop, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.BIG_COOP;
        }

        Image deluxeCoop = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        deluxeCoop.setSize(520, 120);
        deluxeCoop.setPosition(
            window.getX() + 550,
            window.getY() + window.getHeight() - 440
        );
        if (hoverOnImage(deluxeBarn, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.DELUXE_COOP;
        }

        Image well = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        well.setSize(520, 120);
        well.setPosition(
            window.getX() + 550,
            window.getY() + 50
        );
        if (hoverOnImage(well, screenX, convertedY)) {
            farmBuildingType = FarmBuildingType.WELL;
        }

        if (farmBuildingType != null) {
            goToFarmOverview("Choose the position of the " + farmBuildingType.getName(), farmBuildingType, gameView);
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
        float buttonX = Gdx.graphics.getWidth() / 2f + 600;
        float buttonY = window.getY() + window.getHeight() + 20f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }
}
