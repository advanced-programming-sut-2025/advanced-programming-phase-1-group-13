package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.models.*;
import com.ap_project.models.enums.Skill;
import com.ap_project.models.enums.types.GameMenuType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ap_project.Main.goToTitleMenu;

public class GameMenuView implements Screen, InputProcessor {
    private Stage stage;
    private GameMenuType currentTab;
    private Image window;
    private final float windowX;
    private final float windowY;
    private int socialMenuPageIndex;
    private final Image closeButton;
    private final GameView gameView;

    public GameMenuView(GameView gameView) {
        Image blackScreen = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        blackScreen.setColor(0, 0, 0, 0.2f);
        blackScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.currentTab = GameMenuType.INVENTORY;

        this.window = new Image(GameAssetManager.getGameAssetManager().getMenuWindowByType(currentTab));

        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;

        this.socialMenuPageIndex = 1;

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        updateWindow();
        showInventoryMenu();
        addCloseButton();
    }

    @Override
    public void render(float delta) {
        Main.getBatch().begin();
        if (currentTab == GameMenuType.SOCIAL) showSocialMenu();
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

        if (clickedOnImage(closeButton, screenX, convertedY)) {
            Main.getMain().setScreen(gameView);
            return true;
        }

        Image inventoryButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        inventoryButton.setScaleY(1.50f);
        inventoryButton.setScaleX(1.25f);
        inventoryButton.setPosition(
            Gdx.graphics.getWidth() / 2f - 370f,
            Gdx.graphics.getHeight() / 2f + 255f
        );

        if (clickedOnImage(inventoryButton, screenX, convertedY)) {
            currentTab = GameMenuType.INVENTORY;
            updateWindow();
            showInventoryMenu();
        }

        Image skillsButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        skillsButton.setScaleY(1.50f);
        skillsButton.setScaleX(1.25f);
        skillsButton.setPosition(
            Gdx.graphics.getWidth() / 2f - 305f,
            Gdx.graphics.getHeight() / 2f + 255f
        );
        if (clickedOnImage(skillsButton, screenX, convertedY)) {
            currentTab = GameMenuType.SKILLS;
            updateWindow();
            showSkillsMenu();
        }

        Image socialButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        socialButton.setScaleY(1.50f);
        socialButton.setScaleX(1.25f);
        socialButton.setPosition(
            Gdx.graphics.getWidth() / 2f - 240f,
            Gdx.graphics.getHeight() / 2f + 255f
        );
        if (clickedOnImage(socialButton, screenX, convertedY)) {
            currentTab = GameMenuType.SOCIAL;
            updateWindow();
            showSocialMenu();
        }

        Image exitButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        exitButton.setScaleY(1.50f);
        exitButton.setScaleX(1.25f);
        exitButton.setPosition(
            Gdx.graphics.getWidth() / 2f - 50f,
            Gdx.graphics.getHeight() / 2f + 255f
        );
        if (clickedOnImage(exitButton, screenX, convertedY)) {
            currentTab = GameMenuType.EXIT;
            updateWindow();
        }

        if (currentTab == GameMenuType.EXIT) {
            Image exitToTitle = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            exitToTitle.setScaleY(2.35f);
            exitToTitle.setScaleX(7.25f);
            exitToTitle.setPosition(
                Gdx.graphics.getWidth() / 2f - 140f,
                Gdx.graphics.getHeight() / 2f + 19f
            );
            if (clickedOnImage(exitToTitle, screenX, convertedY)) {
                App.setCurrentGame(null);
                App.setLoggedIn(null);
                goToTitleMenu();
            }

            Image exitToDesktop = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            exitToDesktop.setScaleY(2.35f);
            exitToDesktop.setScaleX(8.50f);
            exitToDesktop.setPosition(
                Gdx.graphics.getWidth() / 2f - 165f,
                Gdx.graphics.getHeight() / 2f - 120f
            );
            if (clickedOnImage(exitToDesktop, screenX, convertedY)) {
                Gdx.app.exit();
            }
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
        if (currentTab == GameMenuType.SOCIAL) {
            if (amountY > 0) changeSocialMenuPageIndex(1);
            if (amountY < 0) changeSocialMenuPageIndex(-1);
        }
        return false;
    }

    public void updateWindow() {
        if (this.window != null) { // TODO: doesn't work
            this.window.remove();
        }

        this.window = new Image(GameAssetManager.getGameAssetManager().getMenuWindowByType(currentTab));
        window.setPosition(windowX, windowY);

        stage.addActor(window);
    }

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = Gdx.graphics.getHeight() / 2f + 300f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }

    public void showInventoryMenu() {
        User user = App.getLoggedIn();
        Label name = new Label(user.getUsername() + " Farm", GameAssetManager.getGameAssetManager().getSkin());
        name.setFontScale(1.5f);
        Label currentFunds = new Label("Current Funds: " + getFundString((int) user.getBalance()),
            GameAssetManager.getGameAssetManager().getSkin());
        currentFunds.setFontScale(1.5f);
        int total = (int) (user.getBalance() + user.getSpentMoney());
        Label totalEarnings = new Label("Total Earnings: " + getFundString(total),
            GameAssetManager.getGameAssetManager().getSkin());
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

    public void showSocialMenu() {
        Image page = new Image(GameAssetManager.getGameAssetManager().getSocialMenuPage(socialMenuPageIndex));
        page.setPosition(windowX, windowY);
        stage.addActor(page);

        if (socialMenuPageIndex == 3) {
            ArrayList<User> players = App.getCurrentGame().getPlayers();
            for(User player : players) {
                if (player.getUsername().equals(App.getLoggedIn().getUsername())) continue;
                Image image = new Image(
                    GameAssetManager.getGameAssetManager().getPlayerFriendship(player.getGender())
                );
                image.setPosition(
                    windowX + 30f,
                    windowY + 463f - 112 * players.indexOf(player)
                );
                stage.addActor(image);
            }
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
                ((Gdx.graphics.getWidth() - window.getWidth()) / 2 + 53.0f)
                    + count * (itemImage.getWidth() + 15.0f),
                3 * Gdx.graphics.getHeight() / 4f - 90.0f
            );
            stage.addActor(itemImage);

            count++;
        }
    }

    public String getFundString(int number) {
        return String.format("%,d", number) + "g";
    }

    public boolean clickedOnImage(Image image, float x, float y) {
        return clickedOn(
            image.getX() - 2f,
            image.getY() - 2f,
            image.getWidth() + 2 * 2f,
            image.getHeight() + 2 * 2f,
            x, y
        );
    }

    public boolean clickedOn(float imageX, float imageY, float width, float height, float x, float y) {
        return
            x >= imageX &&
            x <= imageX + width &&
            y >= imageY &&
            y <= imageY + height;
    }

    public void changeSocialMenuPageIndex(int amount) {
        socialMenuPageIndex += amount;
        if (socialMenuPageIndex < 1) {
            socialMenuPageIndex = 1;
        } if (socialMenuPageIndex > 3) {
            socialMenuPageIndex = 3;
        }
    }
}
