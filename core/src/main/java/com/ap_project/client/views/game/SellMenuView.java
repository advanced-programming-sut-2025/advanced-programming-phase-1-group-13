package com.ap_project.client.views.game;

import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.*;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class SellMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final ArrayList<Item> items;
    private final ArrayList<Image> itemImages;
    private final Image closeButton;
    private final FarmView farmView;
    private final GameController controller;
    private Item itemToSell;
    private Image itemToSellImage;
    private final SelectBox<Integer> quantityBox;
    private final Image emptySlot;
    private final TextButton sellButton;
    private final Label errorMessageLabel;

    public SellMenuView(FarmView farmView, FarmBuilding shippingBin) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getSellMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.items = new ArrayList<>(App.getLoggedIn().getBackpack().getItems().keySet());
        this.itemImages = new ArrayList<>();
        for (Item item : items) {
            itemImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByItem(item)));
        }

        this.quantityBox = new SelectBox<>(GameAssetManager.getGameAssetManager().getSkin());
        quantityBox.setWidth(200);

        this.sellButton = new TextButton("Sell", GameAssetManager.getGameAssetManager().getSkin());

        this.emptySlot = new Image(GameAssetManager.getGameAssetManager().getEmptySlot());;

        this.errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

        this.controller = new GameController();
        this.farmView = farmView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);

        emptySlot.setPosition(
            window.getX() + 200,
            window.getY() + window.getHeight() - 200
        );
        stage.addActor(emptySlot);

        Label quantityLabel = new Label("Quantity:", GameAssetManager.getGameAssetManager().getSkin());
        quantityLabel.setColor(Color.BLACK);
        quantityLabel.setPosition(
            window.getX() + 300,
            window.getY() + window.getHeight() - 200
        );
        stage.addActor(quantityLabel);

        quantityBox.setPosition(
            window.getX() + 700,
            window.getY() + window.getHeight() - 200
        );
        stage.addActor(quantityBox);

        addItemsToInventory();

        sellButton.setPosition(
            window.getX() + window.getWidth() / 2,
            window.getY() + window.getHeight() - 400
        );
        stage.addActor(sellButton);

        addCloseButton();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (itemToSell != null) {
            itemToSellImage.setPosition(
              emptySlot.getX(),
              emptySlot.getY()
            );
            stage.addActor(itemToSellImage);
            Gdx.input.setInputProcessor(stage);

            if (sellButton.isChecked()) {
                int quantity = quantityBox.getSelected();
                controller.sell(itemToSell, quantity);
            }
        } else {
            Gdx.input.setInputProcessor(this);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float convertedY = Gdx.graphics.getHeight() - screenY;

        if (hoverOnImage(closeButton, screenX, convertedY)) {
            getMain().setScreen(farmView);
            return true;
        }

        for (int i = 0; i < itemImages.size(); i++) {
            Image image = itemImages.get(i);
            if (hoverOnImage(image, screenX, convertedY)) {
                itemToSell = items.get(i);
                itemToSellImage = new Image(GameAssetManager.getGameAssetManager().getTextureByItem(itemToSell));
                Array<Integer> options = new Array<>();
                for (int j = 1; j < App.getLoggedIn().getBackpack().getItems().get(itemToSell); j++) {
                    options.add(j);
                }
                quantityBox.setItems(options);
            }
        }

        return false;
    }

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = window.getY() + window.getHeight();

        closeButton.setPosition(buttonX, buttonY);
        stage.addActor(closeButton);
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

    private void addItemsToInventory() {
        int columns = 12;
        float spacingX = 64;
        float spacingY = 70;
        float startX = window.getX() + 60;
        float startY = window.getY() + window.getHeight() - 400;

        for (int i = 0; i < itemImages.size(); i++) {
            Image image = itemImages.get(i);
            image.setSize(50, 55);
            float x = startX + (i % columns) * spacingX- 20f;
            float y = startY - (i / columns) * spacingY + 50f;
            image.setPosition(x, y);

            stage.addActor(image);
        }
    }
}
