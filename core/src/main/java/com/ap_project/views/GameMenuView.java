package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
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

public class GameMenuView implements Screen, InputProcessor {
    private Stage stage;
    private GameMenuType currentTab;
    private Image window;
    private GameView gameView;

    public GameMenuView(GameView gameView) {
        this.currentTab = GameMenuType.INVENTORY;
        this.window = new Image(GameAssetManager.getGameAssetManager().getMenuWindowByType(currentTab));
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
        Label totalEarnings = new Label("Total Earnings: " + user.getBalance(), GameAssetManager.getGameAssetManager().getSkin());//todo

        Table infoTable = new Table();
        infoTable.clear();
        infoTable.setFillParent(true);

        infoTable.add(name).row();
        infoTable.add(currentFunds).row();
        infoTable.add(totalEarnings).row();
        infoTable.setPosition(
            window.getImageX(),
            window.getImageY()
        );
        stage.addActor(infoTable);
    }
}
