package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private final Image trashCan;
    private final Image okButton;
    private final GameView gameView;

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

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(refrigeratorWindow);
        addItemsToRefrigerator(10);

        stage.addActor(inventoryWindow);
        stage.addActor(trashCan);
        stage.addActor(okButton);
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
                    + column * (itemImage.getWidth() + 15.0f),
                initialY + 3 * Gdx.graphics.getHeight() / 4f - 90.0f - 75 * row
            );
            refrigeratorItemsImages.add(itemImage);
            refrigeratorItems.add(entry.getKey());
            stage.addActor(itemImage);
            count++;
        }
    }

}
