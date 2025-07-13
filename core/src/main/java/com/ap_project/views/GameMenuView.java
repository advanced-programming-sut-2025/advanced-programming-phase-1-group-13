package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.Item;
import com.ap_project.models.User;
import com.ap_project.models.enums.types.GameMenuType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

public class GameMenuView implements Screen, InputProcessor {
    private Stage stage;
    private GameMenuType currentTab;
    private Image window;
    private Image closeButton;
    private GameView gameView;

    public GameMenuView(GameView gameView) {
        this.currentTab = GameMenuType.INVENTORY;
//        this.currentTab = GameMenuType.SKILLS;
        this.window = new Image(GameAssetManager.getGameAssetManager().getMenuWindowByType(currentTab));
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        Main.getBatch().begin();
        Main.getBatch().end();

        if (currentTab == GameMenuType.INVENTORY) {
            showInventoryMenu();
        } else if (currentTab == GameMenuType.SKILLS) {
            showSkillsMenu();
        }

        this.window = new Image(GameAssetManager.getGameAssetManager().getMenuWindowByType(currentTab));

        closeButton.setPosition(
            Gdx.graphics.getWidth() / 2f + 400f,
            Gdx.graphics.getHeight() / 2f + 300f
        );
        stage.addActor(closeButton);

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

    public void showInventoryMenu() {
        window.setScale(1.25f);
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);
        stage.addActor(window);

        User user = App.getLoggedIn();
        Label name = new Label(user.getUsername() + " Farm", GameAssetManager.getGameAssetManager().getSkin());
        Label currentFunds = new Label("Current Funds: " + user.getBalance(), GameAssetManager.getGameAssetManager().getSkin());
        Label totalEarnings = new Label("Total Earnings: " + user.getBalance(), GameAssetManager.getGameAssetManager().getSkin()); //Todo

        Table infoTable = new Table();
        infoTable.clear();
        infoTable.setFillParent(true);

        infoTable.add(name).row();
        infoTable.add(currentFunds).row();
        infoTable.add(totalEarnings).row();
        infoTable.setPosition(
            window.getImageX() + window.getWidth() / 3F,
             -window.getHeight() / 4f
        );
        stage.addActor(infoTable);

        addItemsToInventory();
    }

    public void showSkillsMenu() {
        window.setScale(1.25f);
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);
        stage.addActor(window);
    }

    public void addItemsToInventory() {
        int count = 0;
        HashMap<Item, Integer> items = App.getLoggedIn().getBackpack().getItems();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (count > 11) {
                break;
            }

            Image itemImage = new Image(GameAssetManager.getGameAssetManager().getTextureByItem(entry.getKey()));
            itemImage.setPosition(
                ((Gdx.graphics.getWidth() - window.getWidth()) / 2 + 45.0f)
                    + count * (itemImage.getWidth() + 13.0f),
                3*Gdx.graphics.getHeight() / 4f - 35f
            );
            stage.addActor(itemImage);

            count++;
        }
    }
}
