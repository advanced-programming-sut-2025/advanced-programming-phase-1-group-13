package com.ap_project.client.views.game;

import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.Item;
import com.ap_project.common.models.Shop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.Main.getBatch;
import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public  class ShopMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Image closeButton;
    private final GameView gameView;
    private final Label welcomeLabel;
    private Image NPCPortraitImage;


    public ShopMenuView(GameView gameView, Shop shop) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getShopMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        Texture npcPortraitTexture = GameAssetManager.getGameAssetManager().getNPCPortrait(shop.getOwner().getType());
        NPCPortraitImage = new Image(new TextureRegionDrawable(new TextureRegion(npcPortraitTexture)));
        NPCPortraitImage.setSize(250,250);
        NPCPortraitImage.setPosition(windowX + 98, windowY + window.getHeight() - 281);
        this.gameView = gameView;
        Label welcomeLabel = new Label("Welcome to\n" + shop.getName() + "!\nLooking to buy\nsomething?",GameAssetManager.getGameAssetManager().getSkin());
        welcomeLabel.setFontScale(1.15f);
        welcomeLabel.setPosition(windowX + 31, windowY + window.getHeight() - 510);
        welcomeLabel.setColor(Color.BLACK);
        this.welcomeLabel = welcomeLabel;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);
        stage.addActor(closeButton);
        stage.addActor(NPCPortraitImage);
        stage.addActor(welcomeLabel);



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

        addCloseButton();
    }
    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 710f;
        float buttonY = window.getY() + window.getHeight() -10f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        getBatch().begin();
        getBatch().end();
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
        return false;
    }


}
