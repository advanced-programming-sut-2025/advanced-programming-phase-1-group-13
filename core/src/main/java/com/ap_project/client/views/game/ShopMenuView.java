package com.ap_project.client.views.game;

import com.ap_project.common.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.Main.getBatch;
import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class ShopMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Image closeButton;
    private final GameView gameView;
    private final Label welcomeLabel;
    private final Image NPCPortraitImage;
    private final ArrayList<Image> productsImages;
    private final ArrayList<Good> products;
    private final SelectBox<String> filter;
    private final Image slider;
    private int firstRowIndex;

    public ShopMenuView(GameView gameView, Shop shop) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getShopMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

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

        this.productsImages = new ArrayList<>();
        this.products = shop.getShopInventory();
        for (Good product : products) {
            productsImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByGood(product)));
        }

        this.filter = new SelectBox<>(GameAssetManager.getGameAssetManager().getSkin());
        Array<String> options = new Array<>();
        options.add("Show all products");
        options.add("Show available products");
        filter.setItems(options);

        this.slider = new Image(GameAssetManager.getGameAssetManager().getSlider());
        slider.setX(1600);

        this.firstRowIndex = 0;

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);
        stage.addActor(closeButton);
        stage.addActor(NPCPortraitImage);
        stage.addActor(welcomeLabel);
        stage.addActor(filter);
        stage.addActor(slider);
        addBalanceLabel();
        addItemsToInventory();
        addShopProducts();
        addCloseButton();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        getBatch().begin();
        getBatch().end();

        System.out.println(firstRowIndex);

        slider.setY(900 - firstRowIndex * 50);

        addShopProducts();
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
        if (amountY >= 0) changeFirstRowIndex(1);

        if (amountY < 0) changeFirstRowIndex(-1);

        return false;
    }

    private void addShopProducts() {
        for (Image image : productsImages) {
            image.remove();
        }

        for (int i = 0; i < 4 && (firstRowIndex + i) < products.size(); i++) {
            Image image = productsImages.get(firstRowIndex + i);
            image.setScale(1.5f);
            // TODO
            image.setPosition(
                600,
                700 - 100 * i
            );
            stage.addActor(image);

            Label name = new Label(products.get(i).getName(), GameAssetManager.getGameAssetManager().getSkin());
            name.setColor(Color.BLACK);
            name.setPosition(
                image.getX() + 50,
                image.getY()
            );
            stage.addActor(name);

            Label price = new Label(products.get(i).getType().getPrice() + "", GameAssetManager.getGameAssetManager().getSkin());
            price.setColor(Color.BLACK);
            price.setPosition(
                image.getX() + 700,
                image.getY()
            );
            stage.addActor(price);
        }
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
            Image image = itemImages.get(i);
            image.setSize(50, 55);
            float x = startX + (i % columns) * spacingX - 20f;
            float y = startY - (i / columns) * spacingY + 50f;
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

    public void changeFirstRowIndex(int amount) {
        firstRowIndex += amount;
        if (firstRowIndex < 0) firstRowIndex = 0;
        if (firstRowIndex > products.size() - 4) firstRowIndex = products.size() - 4;
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
                    600  - 24 * i,
                    400
                );
                stage.addActor(digitLabel);
            }
        }
    }
}
