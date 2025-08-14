package com.ap_project.client.views.game;

import com.ap_project.client.controllers.game.GameController;
import com.ap_project.common.models.*;


import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ap_project.Main.*;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class GiftPlayerMenuView implements Screen, InputProcessor {
    private final Image window;
    private final ArrayList<Item> items;
    private final ArrayList<Image> itemImages;
    private final Image closeButton;
    private final VillageView villageView;
    private final GameController controller;
    private Stage stage;
    private final SelectBox<String> playerSelectBox;
    private final TextButton submitButton;
    private String username;

    public GiftPlayerMenuView(VillageView villageView) {
        this.villageView = villageView;
        this.window = new Image(GameAssetManager.getGameAssetManager().getCookingMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.items = new ArrayList<>(App.getLoggedIn().getBackpack().getItems().keySet());
        this.itemImages = new ArrayList<>();
        for (Item item : items) {
            itemImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByItem(item)));
            if (itemImages.size() > 35) {
                break;
            }
        }
        this.controller = new GameController();
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.submitButton = new TextButton("Submit", GameAssetManager.getGameAssetManager().getSkin());
        this.playerSelectBox = new SelectBox<>(GameAssetManager.getGameAssetManager().getSkin());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);

        int columns = 12;
        float spacingX = 64;
        float spacingY = 70;
        float startX = window.getX() + 60;
        float startY = window.getY() + window.getHeight() - 130;

        for (int i = 0; i < itemImages.size(); i++) {
            int row = i / columns;
            Image image = itemImages.get(i);
            image.setSize(50, 55);
            float x = startX + (i % columns) * spacingX - 20f;
            float y = startY - row * spacingY - 282f;
            image.setPosition(x, y);

            stage.addActor(image);
        }

        Array<String> options = new Array<>();
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.equals(App.getLoggedIn())) continue;
            options.add(player.getUsername());
        }
        username = options.get(0);
        playerSelectBox.setItems(options);
        playerSelectBox.setPosition(610, 600);
        playerSelectBox.setWidth(400f);
        submitButton.setPosition(1028, 580);

        stage.addActor(playerSelectBox);
        stage.addActor(submitButton);
        submitButton.setChecked(false);
        addCloseButton();
    }

    @Override
    public void render(float delta) {
        getBatch().begin();
        getBatch().end();

        if (Gdx.input.getInputProcessor().equals(stage)) {
            if (submitButton.isChecked()) {
                username = playerSelectBox.getSelected();
                Gdx.input.setInputProcessor(this);
            }
            submitButton.setChecked(false);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float convertedY = Gdx.graphics.getHeight() - screenY;

        if (hoverOnImage(closeButton, screenX, convertedY)) {
            getMain().setScreen(villageView);
            return true;
        }
        for (int i = 0; i < itemImages.size(); i++) {
            Image image = itemImages.get(i);
            if (hoverOnImage(image, screenX, convertedY)) {
                Item selectedItem = items.get(i);
                User player = App.getUserByUsername(username);
                if (player == null) return false;

                Result result = controller.giveGift(player.getUsername(), selectedItem.getName(), "1");
                if (result.success) {
                    villageView.setPlayerWithGift(player);
                    getMain().setScreen(villageView);

                    HashMap<String, Object> body = new HashMap<>();
                    body.put("sender", App.getLoggedIn().getUsername());
                    body.put("receiver", username);
                    body.put("item", selectedItem.getName());
                    getClient().sendMessage(JSONUtils.toJson(new Message(body, MessageType.SEND_GIFT)));

                    return true;
                } else {
                    System.out.println(result.message);
                }
            }
        }

        Image selectBox = new Image(GameAssetManager.getGameAssetManager().getWhiteScreen());
        selectBox.setPosition(playerSelectBox.getX(), playerSelectBox.getY());
        selectBox.setSize(playerSelectBox.getWidth(), playerSelectBox.getHeight());
        if (hoverOnImage(selectBox, screenX, convertedY)) {
            Gdx.input.setInputProcessor(stage);
            System.out.println("stage input processor set");
            return true;
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

    public TextButton getSubmitButton() {
        return submitButton;
    }

    public VillageView getVillageView() {
        return villageView;
    }
}
