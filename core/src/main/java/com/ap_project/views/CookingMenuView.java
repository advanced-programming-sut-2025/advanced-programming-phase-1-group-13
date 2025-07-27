package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.models.*;
import com.ap_project.models.enums.types.FoodType;
import com.ap_project.views.game.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

import static com.ap_project.views.game.GameMenuView.hoverOnImage;

public class CookingMenuView implements Screen, InputProcessor {
    private Stage stage;
    private Image window;
    private final float windowX;
    private final float windowY;
    private final Image closeButton;
    private final GameView gameView;

    public CookingMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getCookingMenu());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        updateWindow();
        addCloseButton();

        this.window = new Image(GameAssetManager.getGameAssetManager().getCookingMenu());
        window.setPosition(windowX, windowY);
        stage.addActor(window);

        addItemsToInventory(-windowY - 70);
        addFoodToMenu();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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

        if (hoverOnImage(closeButton, screenX, convertedY)) {
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

    public void updateWindow() {
        if (this.window != null) {
            this.window.remove();
        }

    }

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = Gdx.graphics.getHeight() / 2f + 300f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
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

    public void addFoodToMenu() {
        int count = 0;
        for (FoodType food : FoodType.values()) {
            boolean isLearnt = App.getLoggedIn().hasLearntCookingRecipe(food);

            Image foodImage = new Image(GameAssetManager.getGameAssetManager().getFood(food, !isLearnt));
            foodImage.setPosition(
                count % 10 * 80f + 550,
                -count / 10 * 80f + 700
            );
            stage.addActor(foodImage);
            count++;
        }
    }
}
