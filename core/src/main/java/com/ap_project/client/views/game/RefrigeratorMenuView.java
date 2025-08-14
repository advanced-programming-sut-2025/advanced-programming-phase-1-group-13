package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.common.models.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class RefrigeratorMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image refrigeratorWindow;
    private ArrayList<Image> refrigeratorItemsImages;
    private ArrayList<Item> refrigeratorItems;
    private final Image inventoryWindow;
    private ArrayList<Item> items;
    private ArrayList<Image> itemImages;
    private final Image trashCan;
    private final Image okButton;
    private final Label errorMessageLabel;
    private final GameView gameView;
    private final GameController controller;

    public RefrigeratorMenuView(GameView gameView) {
        this.refrigeratorWindow = new Image(GameAssetManager.getGameAssetManager().getRefrigeratorMenu());
        refrigeratorWindow.setPosition(
            (Gdx.graphics.getWidth() - refrigeratorWindow.getWidth()) / 2,
            Gdx.graphics.getHeight() / 2f + 25
        );
        this.refrigeratorItems = new ArrayList<>();
        this.refrigeratorItemsImages = new ArrayList<>();

        this.inventoryWindow = new Image(GameAssetManager.getGameAssetManager().getRefrigeratorMenuInventory());
        inventoryWindow.setPosition(
            (Gdx.graphics.getWidth() - inventoryWindow.getWidth()) / 2,
            Gdx.graphics.getHeight() / 2f - inventoryWindow.getHeight() - 25
        );

        this.trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashCan(false));
        trashCan.setPosition(
            Gdx.graphics.getWidth() / 2f + 500,
            Gdx.graphics.getHeight() / 2f - 150
        );

        this.okButton = new Image(GameAssetManager.getGameAssetManager().getOkButton());
        okButton.setPosition(
            Gdx.graphics.getWidth() / 2f + 500,
            Gdx.graphics.getHeight() / 2f - 250
        );

        this.errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

        this.gameView = gameView;

        this.controller = new GameController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(refrigeratorWindow);
        addItemsToRefrigerator(10);

        stage.addActor(inventoryWindow);
        addItemsToInventory();

        stage.addActor(trashCan);
        stage.addActor(okButton);
        stage.addActor(errorMessageLabel);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
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

        if (hoverOnImage(okButton, screenX, convertedY)) {
            Main.getMain().setScreen(gameView);
            return true;
        }

        for (int i = 0; i < refrigeratorItems.size(); i++) {
            if (hoverOnImage(refrigeratorItemsImages.get(i), screenX, convertedY)) {
                Result result = App.getLoggedIn().getBackpack().addToInventory(refrigeratorItems.get(i), 1);
                if (!result.success) {
                    errorMessageLabel.setText(result.message);
                    App.getLoggedIn().getFarm().getCabin().getRefrigerator().removeFromInventory(refrigeratorItems.get(i), 1);
                    return false;
                } else {
                    addItemsToRefrigerator(10);
                    addItemsToInventory();
                    return true;
                }
            }
        }

        for (int i = 0; i < itemImages.size(); i++) {
            if (hoverOnImage(itemImages.get(i), screenX, convertedY)) {
                try {
                    Result result = controller.putInRefrigerator(items.get(i));
                    if (!result.success) {
                        errorMessageLabel.setText(result.message);
                        return false;
                    }
                    for (Image image : itemImages) {
                        if (image.getStage() != null) image.remove();
                    }
                    addItemsToRefrigerator(10);
                    addItemsToInventory();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        errorMessageLabel.setText("");
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

    public void addItemsToRefrigerator(float initialY) {
        refrigeratorItemsImages = new ArrayList<>();
        refrigeratorItems = new ArrayList<>();
        int count = 0;
        HashMap<Item, Integer> items = App.getLoggedIn().getFarm().getCabin().getRefrigerator().getItems();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            int row = count / 12;

            System.out.println(entry.getKey());
            Image itemImage = new Image(GameAssetManager.getGameAssetManager().getTextureByItem(entry.getKey()));
            int column = count % 12;
            itemImage.setPosition(
                ((Gdx.graphics.getWidth() - refrigeratorWindow.getWidth()) / 2 + 53.0f)
                    + column * (itemImage.getWidth() + 15.0f) + 57,
                initialY + 3 * Gdx.graphics.getHeight() / 4f - 85 - 75 * row + 28
            );
            refrigeratorItemsImages.add(itemImage);
            refrigeratorItems.add(entry.getKey());
            stage.addActor(itemImage);
            count++;
        }
    }

    public void addItemsToInventory() {
        items = new ArrayList<>(App.getLoggedIn().getBackpack().getItems().keySet());
        itemImages = new ArrayList<>();
        for (Item item : items) {
            itemImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByItem(item)));
            if(itemImages.size() > 35){
                break;
            }
        }

        for (int i = 0; i < itemImages.size(); i++) {
            int row = i /12;
            Image image = itemImages.get(i);
            image.setSize(50, 55);
            image.setPosition(
                inventoryWindow.getX() + 110 + (i % 12) * 64,
                inventoryWindow.getY() + inventoryWindow.getHeight() - 85 - row * 70
            );

            stage.addActor(image);
        }
    }
}
