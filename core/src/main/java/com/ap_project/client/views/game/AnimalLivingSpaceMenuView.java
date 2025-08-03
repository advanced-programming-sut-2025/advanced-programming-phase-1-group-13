package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class AnimalLivingSpaceMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final AnimalLivingSpace animalLivingSpace;
    private final Image closeButton;
    private final ArrayList<Image> buttons;
    private final GameController controller;
    private final FarmView farmView;

    public AnimalLivingSpaceMenuView(FarmView farmView, AnimalLivingSpace animalLivingSpace) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getAnimalLivingSpaceMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.animalLivingSpace = animalLivingSpace;

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.buttons = new ArrayList<>();
        for (int i = 0; i < animalLivingSpace.getAnimals().size(); i++) {
            buttons.add(new Image(GameAssetManager.getGameAssetManager().getBlackScreen()));
            buttons.get(i).setWidth(330);
            buttons.get(i).setHeight(75);
        }

        this.controller = new GameController();

        this.farmView = farmView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);

        Label name = new Label(animalLivingSpace.getFarmBuildingType().getName(), GameAssetManager.getGameAssetManager().getSkin());
        name.setColor(Color.BLACK);
        name.setPosition(
            window.getX() + (window.getWidth() - name.getWidth()) / 2 - 200,
            window.getY() + window.getHeight() - 70
        );
        stage.addActor(name);

        Label capacity = new Label(
            "Capacity: " + (animalLivingSpace.getCapacity() - animalLivingSpace.getAnimals().size()) + "/" + animalLivingSpace.getCapacity(),
            GameAssetManager.getGameAssetManager().getSkin()
        );
        capacity.setColor(Color.BLACK);
        capacity.setPosition(
            window.getX() + (window.getWidth() - capacity.getWidth()) / 2 + 200,
            window.getY() + window.getHeight() - 70
        );
        stage.addActor(capacity);

        for (int i = 0; i < animalLivingSpace.getAnimals().size(); i++) {
            Animal animal = animalLivingSpace.getAnimals().get(i);
            Image animalImage = new Image(GameAssetManager.getGameAssetManager().getAnimal(animal.getAnimalType()));
            animalImage.setPosition(
                window.getX() + 55 + 400 * (i / 6),
                window.getY() + window.getHeight() - 150 - 80 * (i % 6)
            );
            stage.addActor(animalImage);

            Label animalName = new Label(animal.getName(), GameAssetManager.getGameAssetManager().getSkin());
            if (animal.isOutside()) animalName.setColor(Color.GRAY);
            else animalName.setColor(Color.BLACK);
            animalName.setPosition(
                animalImage.getX() + 150,
                animalImage.getY() + 20
            );
            stage.addActor(animalName);

            buttons.get(i).setPosition(
                animalImage.getX(),
                animalImage.getY() + 3
            );
        }

        addCloseButton();
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
            Main.getMain().setScreen(farmView);
            return true;
        }

        for (int i = 0; i < animalLivingSpace.getAnimals().size(); i++) {
            if (hoverOnImage(buttons.get(i), screenX, convertedY)) {
                Animal animal = animalLivingSpace.getAnimals().get(i);
                if (!animal.isOutside()) {
                    Position previousPosition = new Position(animal.getAnimalLivingSpace().getPositionOfUpperLeftCorner());
                    previousPosition.setX(previousPosition.getX() + animalLivingSpace.getFarmBuildingType().getDoorX());
                    previousPosition.setY(previousPosition.getY() + animal.getAnimalLivingSpace().getLength());

                    Position newPosition = new Position(previousPosition);
                    newPosition.setY(newPosition.getY() + 3);

                    Result result = controller.shepherdAnimal(animal.getName(), newPosition);
                    if (result.success) {
                        farmView.setAnimalDestination(new Vector2(newPosition.getX(), newPosition.getY()), newPosition);
                        animal.setPosition(previousPosition);
                        animal.setOutside(true);
                        farmView.startWalkingAnimation(animal, false);
                        Main.getMain().setScreen(farmView);
                    }
                }
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
        float buttonY = Gdx.graphics.getHeight() / 2f + 300f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }
}
