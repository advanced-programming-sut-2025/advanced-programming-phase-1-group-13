 package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.Item;
import com.ap_project.common.models.tools.Tool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.Main.getBatch;
import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public  class CropInfoView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final ArrayList<Tool> tools;
    private final ArrayList<Image> toolImages;
    private final Image closeButton;
    private final GameView gameView;

    public CropInfoView(GameView gameView) {
        this.gameView = gameView;
        this.window = new Image(GameAssetManager.getGameAssetManager().getToolMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.tools = new ArrayList<>();
        this.toolImages = new ArrayList<>();
        for (Item item : App.getLoggedIn().getBackpack().getItems().keySet()) {
            if (item instanceof Tool) {
                tools.add((Tool) item);
                toolImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByTool((Tool) item)));
            }
        }

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);

        for (int i = 0; i < toolImages.size(); i++) {
            toolImages.get(i).setPosition(
                window.getX()+ 45 + 120f * i,
                window.getY() - 140+ window.getHeight() + i % 5
            );
            toolImages.get(i).setScale(1.8f);
            stage.addActor(toolImages.get(i));
        }

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

        for (int i = 0; i < toolImages.size(); i++) {
            if (hoverOnImage(toolImages.get(i), screenX, convertedY)) {
                App.getLoggedIn().setCurrentTool(tools.get(i));
                gameView.show();
                gameView.setSelectedSlotIndex(i);
                Main.getMain().setScreen(gameView);
                return true;
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
        return false;
    }

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = window.getY() + window.getHeight() + 20f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }
}

