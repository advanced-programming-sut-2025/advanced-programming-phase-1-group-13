package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.enums.types.AnimalType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class BuyAnimalsMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowX;
    private final float windowY;
    private final Label description;
    private final TextField nameField;
    private final TextButton enterButton;
    private final GameView gameView;

    public BuyAnimalsMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getBuyAnimalsMenu());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.description = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        this.nameField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        this.enterButton = new TextButton("", GameAssetManager.getGameAssetManager().getSkin());

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);

        description.setPosition(
            (Gdx.graphics.getWidth() - description.getWidth()) / 2,
            100
        );
        stage.addActor(description);

        nameField.setWidth(200);
        nameField.setPosition(
            (Gdx.graphics.getWidth() - nameField.getWidth()) / 2,
            50
        );
        stage.addActor(nameField);

        enterButton.setPosition(
            nameField.getX() + nameField.getWidth() + 20,
            50
        );
        stage.addActor(enterButton);
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

        Image closeButton = new Image(GameAssetManager.getGameAssetManager().getWhiteScreen());
        closeButton.setSize(60, 60);
        closeButton.setPosition(
            windowX + window.getWidth() - 63,
            windowY + window.getHeight() - 390
        );
        if (hoverOnImage(closeButton, screenX, convertedY)) {
            Main.getMain().setScreen(gameView);
            return true;
        }

        ArrayList<AnimalType> animals = new ArrayList<>(List.of(AnimalType.CHICKEN, AnimalType.COW, AnimalType.GOAT,
            AnimalType.DUCK, AnimalType.SHEEP, AnimalType.RABBIT, AnimalType.PIG, AnimalType.DINOSAUR));
        for (int i = 0; i < 8; i++) {
            Image animal = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            animal.setSize(100, 80);
            animal.setPosition(
                windowX + 30 + 120 * (i % 3),
                windowY + 220 - 95 * (i / 3)
            );
            stage.addActor(animal);
            if (hoverOnImage(animal, screenX, convertedY)) {
                String name = askAnimalName(animals.get(i));
                System.out.println(name);
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

    public String askAnimalName(AnimalType animal) {
        description.setText( "Enter a name for your " + animal.getName() + ":");
        return nameField.getText();
    }
}
