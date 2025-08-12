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

import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class PurchaseMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Good product;
    private final Shop shop;
    private final Image window;
    private final Image closeButton;
    private final Label welcomeLabel;
    private final Image NPCPortraitImage;
    private final Image plus;
    private final Image minus;
    private int quantity;
    private final Label quantityLabel;
    private final Image buyButton;
    private final Label errorMessageLabel;
    private final GameView gameView;
    private final GameController controller;

    public PurchaseMenuView(GameView gameView, Shop shop, Good good) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getPurchaseMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.buyButton = new Image(GameAssetManager.getGameAssetManager().getBuyButton());

        NPCPortraitImage = new Image(GameAssetManager.getGameAssetManager().getNPCPortrait(shop.getOwner().getType()));
        NPCPortraitImage.setSize(250, 250);
        NPCPortraitImage.setPosition(windowX + 98, windowY + window.getHeight() - 281);

        this.welcomeLabel = new Label(
            "Welcome to\n" + shop.getName() + "!\nLooking to buy\nsomething?",
            GameAssetManager.getGameAssetManager().getSkin()
        );
        welcomeLabel.setFontScale(1.15f);
        welcomeLabel.setPosition(windowX + 31, windowY + window.getHeight() - 510);
        welcomeLabel.setColor(Color.BLACK);

        this.plus = new Image(GameAssetManager.getGameAssetManager().getPlus());
        this.minus = new Image(GameAssetManager.getGameAssetManager().getMinus());

        this.quantityLabel = new Label("0", GameAssetManager.getGameAssetManager().getSkin());

        this.product = good;
        this.shop = shop;

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

        stage.addActor(window);
        stage.addActor(closeButton);
        stage.addActor(NPCPortraitImage);
        stage.addActor(welcomeLabel);

        buyButton.setPosition(
            window.getX() + 1200,
            window.getY() + 438
        );
        stage.addActor(buyButton);

        quantityLabel.setColor(Color.BLACK);

        Image productImage = new Image(GameAssetManager.getGameAssetManager().getTextureByGood(product));
        productImage.setScale(2);
        productImage.setPosition(
            window.getX() + 450,
            window.getY() + 438
        );
        stage.addActor(productImage);

        Label name = new Label(product.getName(), GameAssetManager.getGameAssetManager().getSkin());
        name.setColor(Color.BLACK);
        name.setFontScale(1.15f);
        name.setPosition(
            productImage.getX() + 115,
            productImage.getY() + 20
        );
        stage.addActor(name);

        quantityLabel.setPosition(
            window.getX() + 930,
            window.getY() + 440
        );
        stage.addActor(quantityLabel);

        plus.setScale(1.5f);
        plus.setPosition(
            quantityLabel.getX() + 70,
            quantityLabel.getY()
        );
        stage.addActor(plus);

        minus.setScale(1.5f);
        minus.setPosition(
            quantityLabel.getX() - 110,
            quantityLabel.getY() + 10
        );
        stage.addActor(minus);

        stage.addActor(errorMessageLabel);

        addItemsToInventory();
        addBalanceLabel();
        addCloseButton();
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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

        if (hoverOnImage(buyButton, screenX, convertedY)) {
            Result result = controller.purchase(product, quantity, shop);
            if (result.success) {
                Main.getMain().setScreen(gameView);
                return true;
            } else {
                errorMessageLabel.setText(result.message);
                return false;
            }
        }

        if (hoverOnImage(plus, screenX, convertedY)) {
            quantity++;
        }

        if (hoverOnImage(minus, screenX, convertedY)) {
            quantity--;
            if (quantity < 0) quantity = 0;
        }

        quantityLabel.setText(String.valueOf(quantity));

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

    private void addItemsToInventory() {
        ArrayList<Item> items = new ArrayList<>(App.getLoggedIn().getBackpack().getItems().keySet());
        ArrayList<Image> itemImages = new ArrayList<>();
        for (Item item : items) {
            itemImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByItem(item)));
        }

        int columns = 12;
        int maxRows = 3;
        float spacingX = 64;
        float spacingY = 70;
        float startX = window.getX() + 730;
        float startY = window.getY() + window.getHeight() - 555;

        int maxItems = columns * maxRows;
        int displayCount = Math.min(itemImages.size(), maxItems);

        for (int i = 0; i < displayCount; i++) {
            int row = i / columns;
            Image image = itemImages.get(i);
            image.setSize(50, 55);
            float x = startX + (i % columns) * spacingX - 20f;
            float y = startY - row * spacingY + 50f;
            image.setPosition(x, y);

            stage.addActor(image);
        }
    }

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 710f;
        float buttonY = window.getY() + window.getHeight() - 10f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }


    public void addBalanceLabel() {
        int balance = (int) App.getLoggedIn().getBalance();
        boolean started = false;
        for (int i = 7; i >= 0; i--) {
            int digit = balance / ((int) Math.pow(10, i));
            balance %= ((int) Math.pow(10, i));
            if (digit != 0 || i == 0 || started) {
                started = true;
                String digitString = Integer.toString(digit);
                Label digitLabel = new Label(digitString, GameAssetManager.getGameAssetManager().getSkin());
                digitLabel.setColor(128 / 255f, 0, 0, 1);
                digitLabel.setPosition(
                    773 - 24 * i,
                    392
                );
                stage.addActor(digitLabel);
            }
        }
    }
}
