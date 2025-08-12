package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.common.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class AnimalMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Animal animal;
    private final Image closeButton;
    private final Label description;
    private final TextField nameField;
    private final TextButton enterButton;
    private final Label errorMessageLabel;
    private final FarmView farmView;
    private final GameController controller;

    public AnimalMenuView(FarmView farmView, Animal animal) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getAnimalMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.animal = animal;

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.description = new Label(
            "Enter the position you want " + animal.getName() + " to go to, in (x, y) format",
            GameAssetManager.getGameAssetManager().getSkin()
        );
        this.nameField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        this.enterButton = new TextButton("Enter", GameAssetManager.getGameAssetManager().getSkin());

        this.errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

        this.farmView = farmView;
        this.controller = new GameController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(window);

        stage.addActor(errorMessageLabel);

        description.setPosition(
            (Gdx.graphics.getWidth() - description.getWidth()) / 2f,
            150
        );

        nameField.setWidth(500);
        nameField.setPosition(
            (Gdx.graphics.getWidth() - nameField.getWidth()) / 2 - 30,
            50
        );

        enterButton.setPosition(
            nameField.getX() + nameField.getWidth() + 20,
            40
        );

        addCloseButton();

        addAnimalInfo();
    }

    @Override
    public void render(float delta) {
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (Gdx.input.getInputProcessor().equals(stage)) {
            if (enterButton.isChecked()) {
                Position previousPosition = animal.getPosition();

                String[] parts = nameField.getText().replaceAll("[()]", "").split(",\\s*");
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                Position newPosition = new Position(x, y);

                Result result = controller.shepherdAnimal(animal.getName(), newPosition);
                if (!result.success) {
                    errorMessageLabel.setText(result.message);
                } else {
                    farmView.setAnimalDestination(new Vector2(newPosition.getX(), newPosition.getY()), newPosition);
                    animal.setPosition(previousPosition);
                    errorMessageLabel.setText("");
                    farmView.startWalkingAnimation(animal, false);
                    farmView.updateTextures();
                    Main.getMain().setScreen(farmView);
                }

                description.remove();
                enterButton.setChecked(false);
                enterButton.remove();
                nameField.setText("");
                nameField.remove();
                Gdx.input.setInputProcessor(this);
            }
        }
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
            farmView.updateTextures();
            Main.getMain().setScreen(farmView);
            return true;
        }

        Result result = new Result(false, "");

        Image feedButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        feedButton.setScaleY(1.75f);
        feedButton.setScaleX(6.15f);
        feedButton.setPosition(
            window.getX() + 145,
            window.getY() + window.getHeight() - 273
        );
        if (hoverOnImage(feedButton, screenX, convertedY)) {
            result = controller.feedHayToAnimal(animal.getName());
            farmView.startFeedingAnimation(animal);
        }

        Image petButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        petButton.setScaleY(1.75f);
        petButton.setScaleX(6.15f);
        petButton.setPosition(
            window.getX() + 460,
            window.getY() + window.getHeight() - 273
        );
        if (hoverOnImage(petButton, screenX, convertedY)) {
            result = controller.pet(animal.getName());
            farmView.startPettingAnimation(animal);
        }

        Image shepherdButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        shepherdButton.setScaleY(1.75f);
        shepherdButton.setScaleX(6.15f);
        shepherdButton.setPosition(
            window.getX() + 145,
            window.getY() + window.getHeight() - 375
        );
        if (hoverOnImage(shepherdButton, screenX, convertedY)) {
            stage.addActor(description);
            stage.addActor(enterButton);
            stage.addActor(nameField);
            Gdx.input.setInputProcessor(stage);
        }

        Image collectProductsButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        collectProductsButton.setScaleY(1.75f);
        collectProductsButton.setScaleX(6.15f);
        collectProductsButton.setPosition(
            window.getX() + 460,
            window.getY() + window.getHeight() - 375
        );
        if (hoverOnImage(collectProductsButton, screenX, convertedY)) {
            result = controller.collectProducts(animal.getName()); // TODO: fix
        }

        Image goInsideButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        goInsideButton.setScaleY(1.75f);
        goInsideButton.setScaleX(6.15f);
        goInsideButton.setPosition(
            window.getX() + 145,
            window.getY() + window.getHeight() - 495
        );
        if (hoverOnImage(goInsideButton, screenX, convertedY)) {
            Position previousPosition = animal.getPosition();
            Position newPosition = new Position(animal.getAnimalLivingSpace().getPositionOfUpperLeftCorner());
            newPosition.setX(newPosition.getX() + animal.getAnimalLivingSpace().getFarmBuildingType().getDoorX());
            newPosition.setY(newPosition.getY() + animal.getAnimalLivingSpace().getLength());

            result = controller.shepherdAnimal(animal.getName(), newPosition);
            if (!result.success) {
                errorMessageLabel.setText(result.message);

            } else {
                farmView.setAnimalDestination(new Vector2(newPosition.getX(), newPosition.getY()), newPosition);
                animal.setPosition(previousPosition);
                farmView.startWalkingAnimation(animal, true);
                farmView.updateTextures();
                Main.getMain().setScreen(farmView);
            }
        }

        Image sellButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        sellButton.setScaleY(1.75f);
        sellButton.setScaleX(6.15f);
        sellButton.setPosition(
            window.getX() + 460,
            window.getY() + window.getHeight() - 495
        );
        if (hoverOnImage(sellButton, screenX, convertedY)) {
            int index = farmView.getFarm().getAllFarmAnimals().indexOf(animal);
            result = controller.sellAnimal(animal.getName());
            farmView.getAnimalsTextures().remove(index);
        }

        if (!result.success) {
            errorMessageLabel.setText(result.message);
        } else {
            farmView.updateTextures();
            Main.getMain().setScreen(farmView);
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

        if (animal.hasBeenFedToday()) {
            Image checkedBox = new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
            checkedBox.setPosition(
                window.getX() + 688,
                window.getY() + window.getHeight() - 137
            );
            stage.addActor(checkedBox);
        }

        if (animal.hasBeenPetToday()) {
            Image checkedBox = new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
            checkedBox.setPosition(
                window.getX() + 760,
                window.getY() + window.getHeight() - 137
            );
            stage.addActor(checkedBox);
        }

        showProducedProducts();
    }

    public void showProducedProducts() {
        if (animal.getProducedProducts().isEmpty()) {
            Label uncollectedProducts = new Label("No Uncollected Products.", GameAssetManager.getGameAssetManager().getSkin());
            uncollectedProducts.setPosition(
                window.getX() + 50,
                window.getY() + 30
            );
            uncollectedProducts.setColor(Color.BLACK);
            uncollectedProducts.setFontScale(0.8f);
            stage.addActor(uncollectedProducts);
            return;
        }

        Label uncollectedProducts = new Label("Uncollected Products:", GameAssetManager.getGameAssetManager().getSkin());
        uncollectedProducts.setPosition(
            window.getX() + 50,
            window.getY() + 30
        );
        uncollectedProducts.setColor(Color.BLACK);
        uncollectedProducts.setFontScale(0.8f);
        stage.addActor(uncollectedProducts);

        for (AnimalProduct product : animal.getProducedProducts()) {
            Image animalProduct = new Image(GameAssetManager.getGameAssetManager().getTextureByAnimalProduct(product));
            animalProduct.setPosition(
                uncollectedProducts.getX() + 270 + 60 * animal.getProducedProducts().indexOf(product),
                uncollectedProducts.getY()
            );
            stage.addActor(animalProduct);
        }
    }
}
