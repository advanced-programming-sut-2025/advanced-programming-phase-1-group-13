package com.ap_project.views.game;

import com.ap_project.Main;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.views.game.GameMenuView.hoverOnImage;


public class MapView implements Screen, InputProcessor {
    private Stage stage;
    private final Image map;
    private final Image closeButton;
    private final GameView gameView;

    public MapView(GameView gameView) {
        this.map = new Image(GameAssetManager.getGameAssetManager().getMap(App.getCurrentGame()));
        map.setPosition(
            (Gdx.graphics.getWidth() - map.getWidth()) / 2,
            (Gdx.graphics.getHeight() - map.getHeight()) / 2
        );

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        closeButton.setPosition(
            Gdx.graphics.getWidth() / 2f + 400f,
            Gdx.graphics.getHeight() / 2f + 300f
        );

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(map);
        stage.addActor(closeButton);
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
        if (hoverOnImage(closeButton, screenX, Gdx.graphics.getHeight() - screenY)) {
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
