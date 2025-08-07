package com.ap_project.client.views.game;

import com.ap_project.Main;
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

public class ArtisanMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final float windowX;
    private final float windowY;
    private final Image emptySlot;
    private final Image startButton;
    private Image itemInArtisan;
    private final Image closeButton;
    private final Artisan artisan;
    private ArrayList<Image> inventoryItemsImages;
    private ArrayList<Item> inventoryItems;
    private final Label errorMessageLabel;
    private final FarmView farmView;

    public ArtisanMenuView(FarmView farmView, Artisan artisan) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getArtisanMenu());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.emptySlot = new Image(GameAssetManager.getGameAssetManager().getEmptySlot());

        this.startButton = new Image(GameAssetManager.getGameAssetManager().getStartButton());

        this.itemInArtisan = null;

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

        this.artisan = artisan;
        this.farmView = farmView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);
        addItemsToInventory(-windowY - 70);

        Image artisanImage = new Image(GameAssetManager.getGameAssetManager().getArtisan(artisan));
        artisanImage.setScale(2);
        artisanImage.setPosition(
            windowX + 200,
            windowY + 300
        );
        stage.addActor(artisanImage);

        Label name = new Label(artisan.getType().getName(), GameAssetManager.getGameAssetManager().getSkin());
        name.setColor(Color.BLACK);
        name.setFontScale(1.25f);
        name.setPosition(
            windowX + 400,
            windowY + 500
        );
        stage.addActor(name);

        Label status = new Label(
            artisan.getItemPending() == null ? "Ready to use" : "Making " + artisan.getItemPending().getName(),
            GameAssetManager.getGameAssetManager().getSkin());
        status.setColor(Color.BLACK);
        status.setPosition(
            name.getX() + (name.getWidth() - status.getWidth()) / 2,
            name.getY() - 50
        );
        stage.addActor(status);

        emptySlot.setPosition(
            name.getX() + (name.getWidth() - emptySlot.getWidth()) / 2,
            status.getY() - 100
        );
        stage.addActor(emptySlot);


        startButton.setPosition(
            name.getX() + (name.getWidth() - startButton.getWidth()) / 2,
            status.getY() - 180
        );
        stage.addActor(startButton);

        stage.addActor(errorMessageLabel);

        addCloseButton();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();

        if (artisan.getItemPending() != null) {
            itemInArtisan = new Image(GameAssetManager.getGameAssetManager().getTextureByItem(artisan.getItemPending()));
            stage.addActor(itemInArtisan);
            itemInArtisan.setPosition(
                916,
                572
            );
        }

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

        for (Image image : inventoryItemsImages) {
            if (hoverOnImage(image, screenX, convertedY)) {
                if (artisan.getItemPending() != null) {
                    errorMessageLabel.setText("This " + artisan.getType().getName() + " is not empty.");
                    return false;
                }

                Item item = inventoryItems.get(inventoryItemsImages.indexOf(image));
                Result result = artisan.startProcessing(item.getName());
                if (result.success) {
                    show();
                    addItemsToInventory(-windowY - 70);
                    errorMessageLabel.setText("");
                } else {
                    errorMessageLabel.setText(result.message);
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

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = Gdx.graphics.getHeight() / 2f + 300f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }

    public void addItemsToInventory(float initialY) {
        inventoryItemsImages = new ArrayList<>();
        inventoryItems = new ArrayList<>();
        int count = 0;
        HashMap<Item, Integer> items = App.getLoggedIn().getBackpack().getItems();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            int row = count / 12;

            Image itemImage = new Image(GameAssetManager.getGameAssetManager().getTextureByItem(entry.getKey()));
            int column = count % 12;
            itemImage.setPosition(
                ((Gdx.graphics.getWidth() - window.getWidth()) / 2 + 53.0f)
                    + column * (itemImage.getWidth() + 15.0f),
                initialY + 3 * Gdx.graphics.getHeight() / 4f - 90.0f - 75 * row
            );
            inventoryItemsImages.add(itemImage);
            inventoryItems.add(entry.getKey());
            stage.addActor(itemImage);
            count++;
        }
    }
}
