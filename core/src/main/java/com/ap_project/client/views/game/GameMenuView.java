package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllerss.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.types.GameMenuType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ap_project.Main.*;
import static com.ap_project.common.models.GameAssetManager.toPascalCase;

public class GameMenuView implements Screen, InputProcessor {
    private Stage stage;
    private GameMenuType currentTab;
    private Image window;
    private Image page;
    private final Image trashCan;
    private Image skillHoverImage;
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
        this.trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashCan());
        trashCan.setPosition(
            (Gdx.graphics.getWidth() + window.getWidth()) / 2f + 20,
            Gdx.graphics.getHeight() / 2f - 65
        );

        this.skillHoverImage = null;

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
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();

        if (currentTab != GameMenuType.INVENTORY && trashCan != null && trashCan.getStage() != null) {
            trashCan.remove();
        }

        if (currentTab != GameMenuType.SOCIAL && page != null && page.getStage() != null) {
            page.remove();
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
        if (hoverOnImage(inventoryButton, screenX, convertedY)) {
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
        if (hoverOnImage(skillsButton, screenX, convertedY)) {
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
        if (hoverOnImage(socialButton, screenX, convertedY)) {
            currentTab = GameMenuType.SOCIAL;
            updateWindow();
            showSocialMenu();
        }

        Image mapButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        mapButton.setScaleY(1.50f);
        mapButton.setScaleX(1.25f);
        mapButton.setPosition(
            Gdx.graphics.getWidth() / 2f - 175f,
            Gdx.graphics.getHeight() / 2f + 255f
        );
        if (hoverOnImage(mapButton, screenX, convertedY)) {
            goToMap(gameView);
        }

        Image craftingButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        craftingButton.setScaleY(1.50f);
        craftingButton.setScaleX(1.25f);
        craftingButton.setPosition(
            Gdx.graphics.getWidth() / 2f - 110f,
            Gdx.graphics.getHeight() / 2f + 255f
        );
        if (hoverOnImage(craftingButton, screenX, convertedY)) {
            currentTab = GameMenuType.CRAFTING;
            updateWindow();
            showCraftingMenu();
        }

        Image exitButton = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
        exitButton.setScaleY(1.50f);
        exitButton.setScaleX(1.25f);
        exitButton.setPosition(
            Gdx.graphics.getWidth() / 2f - 50f,
            Gdx.graphics.getHeight() / 2f + 255f
        );
        if (hoverOnImage(exitButton, screenX, convertedY)) {
            currentTab = GameMenuType.EXIT;
            updateWindow();
        }

        if (currentTab == GameMenuType.EXIT) {
            Image nextTurn = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            nextTurn.setScaleY(2.35f);
            nextTurn.setScaleX(6.45f);
            nextTurn.setPosition(
                Gdx.graphics.getWidth() / 2f - 120f,
                Gdx.graphics.getHeight() / 2f + 120f
            );
            if (hoverOnImage(nextTurn, screenX, convertedY)) {
                GameController.nextTurn();
                gameView.nextTurn();
                Main.getMain().setScreen(gameView);
            }

            Image forceTerminateGame = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            forceTerminateGame.setScaleY(2.35f);
            forceTerminateGame.setScaleX(10.85f);
            forceTerminateGame.setPosition(
                Gdx.graphics.getWidth() / 2f - 210f,
                Gdx.graphics.getHeight() / 2f
            );
            if (hoverOnImage(forceTerminateGame, screenX, convertedY)) {
                forceTerminateGame();
            }

            Image exitToTitle = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            exitToTitle.setScaleY(2.35f);
            exitToTitle.setScaleX(7.25f);
            exitToTitle.setPosition(
                Gdx.graphics.getWidth() / 2f - 140f,
                Gdx.graphics.getHeight() / 2f - 135f
            );
            if (hoverOnImage(exitToTitle, screenX, convertedY)) {
                if (App.getLoggedIn().equals(App.getCurrentGame().getPlayers().get(0))) {
                    App.setCurrentGame(null);
                    App.setLoggedIn(null);
                    goToTitleMenu();
                } else {
                    Label errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
                    errorMessageLabel.setColor(Color.RED);
                    errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);
                    errorMessageLabel.setText("Only the creator of the game can exit it.");
                    stage.addActor(errorMessageLabel);
                }
            }

            Image exitToDesktop = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            exitToDesktop.setScaleY(2.35f);
            exitToDesktop.setScaleX(8.50f);
            exitToDesktop.setPosition(
                Gdx.graphics.getWidth() / 2f - 165f,
                Gdx.graphics.getHeight() / 2f - 270f
            );
            if (hoverOnImage(exitToDesktop, screenX, convertedY)) {
                if (App.getLoggedIn().equals(App.getCurrentGame().getPlayers().get(0))) {

                    Gdx.app.exit();
                } else {
                    Label errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
                    errorMessageLabel.setColor(Color.RED);
                    errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);
                    errorMessageLabel.setText("Only the creator of the game can exit it.");
                    stage.addActor(errorMessageLabel);
                }
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
        if (currentTab == GameMenuType.SKILLS) {
            if (skillHoverImage != null && skillHoverImage.getStage() != null) {
                skillHoverImage.remove();
            }

            float xScale = 4.75f;
            float yScale = 1.50f;
            float x = Gdx.graphics.getWidth() / 2f - 220f;
            float initialY = Gdx.graphics.getHeight() / 2f + 160f;
            float ySpacing = 85f;

            Image farming = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            farming.setScaleY(yScale);
            farming.setScaleX(xScale);
            farming.setPosition(x, initialY);

            Image mining = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            mining.setScaleY(yScale);
            mining.setScaleX(xScale);
            mining.setPosition(x, initialY - ySpacing * 1);

            Image foraging = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            foraging.setScaleY(yScale);
            foraging.setScaleX(xScale);
            foraging.setPosition(x, initialY - ySpacing * 2);

            Image fishing = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            fishing.setScaleY(yScale);
            fishing.setScaleX(xScale);
            fishing.setPosition(x, initialY - ySpacing * 3);

            String skillHover;
            float convertedY = Gdx.graphics.getHeight() - screenY;

            if (hoverOnImage(farming, screenX, convertedY)) {
                skillHover = "Farming";
            } else if (hoverOnImage(mining, screenX, convertedY)) {
                skillHover = "Mining";
            } else if (hoverOnImage(foraging, screenX, convertedY)) {
                skillHover = "Foraging";
            } else if (hoverOnImage(fishing, screenX, convertedY)) {
                skillHover = "Fishing";
            } else {
                skillHover = "";
            }

            if (!skillHover.isEmpty()) {
                skillHoverImage = new Image(GameAssetManager.getGameAssetManager().getSkillMenuHover(skillHover));
                skillHoverImage.setPosition(
                    screenX,
                    Gdx.graphics.getHeight() - screenY - skillHoverImage.getHeight()
                );
                stage.addActor(skillHoverImage);
            }
        } else {
            if (skillHoverImage != null && skillHoverImage.getStage() != null) {
                skillHoverImage.remove();
                skillHoverImage = null;
            }
        }

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (currentTab == GameMenuType.SOCIAL) {
            if (amountY > 0) changeSocialMenuPageIndex(1);
            if (amountY < 0) changeSocialMenuPageIndex(-1);
            showSocialMenu();
        }
        return false;
    }

    public void updateWindow() {
        if (this.window != null) {
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
        updateWindow();
        User user = App.getLoggedIn();
        Label name = new Label(user.getUsername() + " Farm", GameAssetManager.getGameAssetManager().getSkin());
        name.setFontScale(1.5f);
        name.setColor(34f / 255, 17f / 255, 34f / 255, 1);
        Label currentFunds = new Label("Current Funds: " + getFundString((int) user.getBalance()),
            GameAssetManager.getGameAssetManager().getSkin());
        currentFunds.setFontScale(1.5f);
        currentFunds.setColor(34f / 255, 17f / 255, 34f / 255, 1);
        int total = (int) (user.getBalance() + user.getSpentMoney());
        Label totalEarnings = new Label("Total Earnings: " + getFundString(total),
            GameAssetManager.getGameAssetManager().getSkin());
        totalEarnings.setColor(34f / 255, 17f / 255, 34f / 255, 1);
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

        stage.addActor(trashCan);

        addItemsToInventory(-5);
    }

    public void showSkillsMenu() {
        User user = App.getLoggedIn();
        Label username = new Label(user.getUsername(), GameAssetManager.getGameAssetManager().getSkin());
        username.setFontScale(2.0f);
        username.setColor(34f / 255, 17f / 255, 34f / 255, 1);
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
        page = new Image(GameAssetManager.getGameAssetManager().getSocialMenuPage(socialMenuPageIndex));
        page.setPosition(windowX, windowY);
        stage.addActor(page);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        for (int i = 0; i < 5; i++) {
            int rowY = 682 - i * 112;
            int index = 5 * (socialMenuPageIndex - 1) + i;
            if (index >= App.getCurrentGame().getNpcs().size()) break;
            NPC npc = App.getCurrentGame().getNpcs().get(index);

            int friendshipPoints = App.getCurrentGame().getNpcFriendshipPoints(App.getLoggedIn(), npc);

            Label name;
            if (friendshipPoints > 0) {
                name = new Label(npc.getName(), skin);
            } else {
                name = new Label("???", skin);
            }
            name.setFontScale(1.25f);
            name.setColor(34f / 255, 17f / 255, 34f / 255, 1);
            name.setPosition(
                670 - name.getWidth() / 2,
                rowY
            );
            stage.addActor(name);

            Label friendshipPointsLabel = new Label(friendshipPoints + "", skin);
            friendshipPointsLabel.setFontScale(1.25f);
            friendshipPointsLabel.setColor(34f / 255, 17f / 255, 34f / 255, 1);
            friendshipPointsLabel.setPosition(
                850,
                rowY
            );
            stage.addActor(friendshipPointsLabel);

            int friendshipLevel = friendshipPoints / 200;
            for (int j = 0; j < friendshipLevel; j++) {
                Image heart = new Image(GameAssetManager.getGameAssetManager().getHeart());
                heart.setPosition(
                    963 + j * 36f,
                    rowY
                );
                stage.addActor(heart);
            }

            if (npc.hasReceivedGiftToday(App.getLoggedIn())) {
                Image giftCheckedBox = new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
                giftCheckedBox.setPosition(
                    1154,
                    rowY - 30
                );
                stage.addActor(giftCheckedBox);
            }

            if (npc.hasTalkedToToday(App.getLoggedIn())) {
                Image talkCheckedBox = new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
                talkCheckedBox.setPosition(
                    1271,
                    rowY - 30
                );
                stage.addActor(talkCheckedBox);
            }
        }

        if (socialMenuPageIndex == 3) {
            ArrayList<User> players = App.getCurrentGame().getPlayers();
            for (User player : players) {
                if (player.getUsername().equals(App.getLoggedIn().getUsername())) continue;
                Image image = new Image(
                    GameAssetManager.getGameAssetManager().getPlayerFriendship(player.getGender())
                );
                image.setPosition(
                    windowX + 30f,
                    windowY + 463f - 112 * players.indexOf(player)
                );
                stage.addActor(image);

                int rowY = 680 - App.getCurrentGame().getPlayers().indexOf(player) * 112;

                Friendship friendship = App.getCurrentGame().getUserFriendship(App.getLoggedIn(), player);
                int xp = friendship.getCurrentXP();
                int friendshipLevel = friendship.getLevel().getNumber();

                Label name;
                if (friendshipLevel == 0 && xp == 0) {
                    name = new Label("???", skin);
                } else {
                    name = new Label(player.getUsername(), skin);
                }
                name.setFontScale(1.25f);
                name.setColor(34f / 255, 17f / 255, 34f / 255, 1);
                name.setPosition(
                    670 - name.getWidth() / 2,
                    rowY
                );
                stage.addActor(name);

                Label xpLabel = new Label("XP: " + xp, skin);
                xpLabel.setFontScale(1.25f);
                xpLabel.setColor(34f / 255, 17f / 255, 34f / 255, 1);
                xpLabel.setPosition(
                    850 - xpLabel.getWidth() / 2,
                    rowY
                );
                stage.addActor(xpLabel);

                for (int j = 0; j < friendshipLevel; j++) {
                    Image heart = new Image(GameAssetManager.getGameAssetManager().getHeart());
                    heart.setPosition(
                        927 + j * 36f,
                        rowY
                    );
                    stage.addActor(heart);
                }

                if (player.exchangedGiftToday(App.getLoggedIn())) {
                    Image giftCheckedBox = new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
                    giftCheckedBox.setPosition(
                        1154,
                        rowY - 30
                    );
                    stage.addActor(giftCheckedBox);
                }

                if (player.hasTalkedToToday(App.getLoggedIn())) {
                    Image talkCheckedBox = new Image(GameAssetManager.getGameAssetManager().getCheckedBox());
                    talkCheckedBox.setPosition(
                        1271,
                        rowY - 30
                    );
                    stage.addActor(talkCheckedBox);
                }
            }
        }
    }

    public void addItemsToInventory(float initialY) {
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
                initialY + 3 * Gdx.graphics.getHeight() / 4f - 90.0f
            );
            stage.addActor(itemImage);

            count++;
        }
    }

    public static String getFundString(int number) {
        return String.format("%,d", number) + "g";
    }

    public static boolean hoverOnImage(Image image, float x, float y) {
        return hoverOn(
            image.getX() - 2f,
            image.getY() - 2f,
            image.getWidth() * image.getScaleX() + 2 * 2f,
            image.getHeight() * image.getScaleY() + 2 * 2f,
            x, y
        );
    }

    public static boolean hoverOn(float imageX, float imageY, float width, float height, float x, float y) {
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
        }
        if (socialMenuPageIndex > 3) {
            socialMenuPageIndex = 3;
        }
    }

    public void showCraftingMenu() {
        updateWindow();
        addCloseButton();
        String[] craftingItemNames = {
            "BeeHouse", "Bomb", "CharcoalKiln", "CheesePress", "CherryBomb",
            "FishSmoker", "Furnace", "GrassStarter", "Scarecrow",
            "DeluxeScarecrow", "Sprinkler", "QualitySprinkler", "IridiumSprinkler",
            "Keg", "Loom", "MayonnaiseMachine", "MegaBomb", "MysticTreeSeed", "OilMaker", "PreservesJar"
        };

        ArrayList<CraftRecipe> learntRecipes = App.getLoggedIn().getLearntCraftRecipes();
        ArrayList<String> learntRecipesNamesPascalCase = new ArrayList<>();
        for (CraftRecipe cr : learntRecipes) {
            learntRecipesNamesPascalCase.add(toPascalCase(cr.getName()));
        }

        for (int i = 0; i < craftingItemNames.length; i++) {
            if (!learntRecipesNamesPascalCase.contains(craftingItemNames[i])) {
                craftingItemNames[i] = craftingItemNames[i] + "Locked";
            }
        }

        float startX = windowX + 50;
        float startY = windowY + window.getHeight() - 190;

        float xSpacing = 30;
        float ySpacing = 50;

        int itemsPerRow = 10;
        float currentX = startX;
        float currentY = startY;

        for (int i = 0; i < craftingItemNames.length; i++) {
            String itemName = craftingItemNames[i];
            Image itemImage = new Image(GameAssetManager.getGameAssetManager().getCraftingItemTexture(itemName));

            itemImage.setPosition(currentX, currentY);
            stage.addActor(itemImage);

            currentX += itemImage.getWidth() + xSpacing;

            if ((i + 1) % itemsPerRow == 0) {
                currentX = startX;
                currentY -= itemImage.getHeight() + ySpacing;
            }
        }

        addItemsToInventory(-327);
    }

    public void forceTerminateGame() {
        for (User player : App.getCurrentGame().getPlayers()) {
            player.setActiveGame(null);
            if (player.getMostEarnedMoney() < player.getBalance()) {
                player.setMostEarnedMoney((int) player.getBalance());
            }
            player.resetPlayer();
        }
        goToPreGameMenu();
    }
}
