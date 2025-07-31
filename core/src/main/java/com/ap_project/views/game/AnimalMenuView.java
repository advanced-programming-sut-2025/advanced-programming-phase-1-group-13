package com.ap_project.views.game;

import com.ap_project.Main;
import com.ap_project.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.views.game.GameMenuView.hoverOnImage;

public class AnimalMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Animal animal;
    private final Image closeButton;
    private final GameView gameView;

    public AnimalMenuView(GameView gameView, Animal animal) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getAnimalMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);
        this.animal = animal;
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);

        addCloseButton();

        addAnimalInfo();
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

    public void addAnimalInfo() {
        Image animalImage = new Image(GameAssetManager.getGameAssetManager().getAnimal(animal.getAnimalType()));
        animalImage.setPosition(
            window.getX() + 55,
            window.getY() + window.getHeight() - 140
        );
        stage.addActor(animalImage);

        Label name = new Label(animal.getName(), GameAssetManager.getGameAssetManager().getSkin());
        name.setPosition(
            window.getX() + 275,
            window.getY() + window.getHeight() - 85
        );
        name.setColor(Color.BLACK);
        name.setFontScale(0.8f);
        stage.addActor(name);

        Label friendshipLevel = new Label("" + animal.getFriendshipLevel(), GameAssetManager.getGameAssetManager().getSkin());
        friendshipLevel.setPosition(
            name.getX() + 125,
            name.getY() - 60
        );
        friendshipLevel.setColor(Color.BLACK);
        friendshipLevel.setFontScale(0.8f);
        stage.addActor(friendshipLevel);
    }
}
