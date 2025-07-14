package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.Item;
import com.ap_project.models.User;
import com.ap_project.models.enums.Skill;
import com.ap_project.models.enums.types.GameMenuType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

public class GameMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final GameMenuType currentTab;
    private Image window;
    private final Image closeButton;
    private GameView gameView;

    public GameMenuView(GameView gameView) {
        Image blackScreen = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        blackScreen.setColor(0, 0, 0, 0.2f);
        blackScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        this.currentTab = GameMenuType.INVENTORY;
        this.currentTab = GameMenuType.SKILLS;

        this.window = new Image(GameAssetManager.getGameAssetManager().getMenuWindowByType(currentTab));
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        updateWindow();
//        showInventoryMenu();
        showSkillsMenu();
        addCloseButton();
    }

    @Override
    public void render(float delta) {
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

    public void updateWindow() {
        this.window = new Image(GameAssetManager.getGameAssetManager().getMenuWindowByType(currentTab));
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);
        stage.addActor(window);
    }

    public void addCloseButton() {
        closeButton.setPosition(
            Gdx.graphics.getWidth() / 2f + 400f,
            Gdx.graphics.getHeight() / 2f + 300f
        );
        stage.addActor(closeButton);
    }

    public void showInventoryMenu() {
        User user = App.getLoggedIn();
        Label name = new Label(user.getUsername() + " Farm", GameAssetManager.getGameAssetManager().getSkin());
        name.setFontScale(1.5f);
        Label currentFunds = new Label("Current Funds: " + getFundString((int) user.getBalance()), GameAssetManager.getGameAssetManager().getSkin());
        currentFunds.setFontScale(1.5f);
        Label totalEarnings = new Label("Total Earnings: " + getFundString((int) user.getBalance()), GameAssetManager.getGameAssetManager().getSkin()); //Todo
        totalEarnings.setFontScale(1.5f);

        Table infoTable = new Table();
        infoTable.clear();
        infoTable.setFillParent(true);

        infoTable.add(name).row();
        infoTable.add(currentFunds).row();
        infoTable.add(totalEarnings).row();
        infoTable.setPosition(
            window.getImageX() + window.getWidth() / 6f,
            -window.getHeight() / 4f
        );
        stage.addActor(infoTable);

        addItemsToInventory();
    }

    public void showSkillsMenu() {
        User user = App.getLoggedIn();
        Label username = new Label(user.getUsername(), GameAssetManager.getGameAssetManager().getSkin());
        username.setFontScale(2.0f);
        username.setPosition(
            Gdx.graphics.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - window.getHeight() / 2.8f
        );
        stage.addActor(username);
        int count = 0;
        for (Skill skill : Skill.values()) {
            int level = user.getSkillLevels().get(skill).getNumber();

            Image number = new Image(GameAssetManager.getGameAssetManager().getNumber(level));
            number.setPosition(
                Gdx.graphics.getWidth() / 2f + window.getWidth() / 4f,
                Gdx.graphics.getHeight() / 2f + window.getHeight() / 3.75f - count * 85.0f
            );
            stage.addActor(number);

            for (int i = 0; i < level; i++) {
                Image skillPoint = new Image(GameAssetManager.getGameAssetManager().getSkillPoint());
                skillPoint.setPosition(
                    Gdx.graphics.getWidth() / 2f - 27.0f + i * 58.0f,
                    Gdx.graphics.getHeight() / 2f + window.getHeight() / 3.95f - count * 85.0f
                );
                stage.addActor(skillPoint);
            }

            count++;
        }
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
                3 * Gdx.graphics.getHeight() / 4f - 35f
            );
            stage.addActor(itemImage);

            count++;
        }
    }

    public String getFundString(int number) {
        return String.format("%,d", number) + "g";
    }
}
