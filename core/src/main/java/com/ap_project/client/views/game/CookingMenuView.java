package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.types.FoodType;
import com.ap_project.common.models.enums.types.IngredientType;
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

public class CookingMenuView implements Screen, InputProcessor {
    private Stage stage;
    private Image window;
    private final float windowX;
    private final float windowY;
    private final ArrayList<Image> foodImages;
    private final Image foodHoverImage;
    private final Image closeButton;
    private final GameView gameView;

    public CookingMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getCookingMenu());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;

        this.foodImages = new ArrayList<>();

        this.foodHoverImage = new Image(GameAssetManager.getGameAssetManager().getCookingMenuHover());

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
        if (foodHoverImage != null && foodHoverImage.getStage() != null) {
            foodHoverImage.remove();
        }

        for (Image image : foodImages) {
            if (hoverOnImage(image, screenX, Gdx.graphics.getHeight() - screenY)) {
                FoodType foodType = FoodType.values()[foodImages.indexOf(image)];
                if (App.getLoggedIn().hasLearntCookingRecipe(foodType)) showFoodHover(foodType, screenX, screenY);
            }
        }
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

            foodImages.add(new Image(GameAssetManager.getGameAssetManager().getFood(food, !isLearnt)));
            foodImages.get(count).setPosition(
                count % 10 * 80f + 550,
                -count / 10 * 80f + 700
            );
            stage.addActor(foodImages.get(count));
            count++;
        }
    }

    public void showFoodHover(FoodType foodType, float screenX, float screenY) {
        foodHoverImage.setPosition(
            screenX,
            Gdx.graphics.getHeight() - screenY - foodHoverImage.getHeight()
        );
        stage.addActor(foodHoverImage);

        Label name = new Label(foodType.getName(), GameAssetManager.getGameAssetManager().getSkin());
        name.setColor(Color.BLACK);
        name.setPosition(
            foodHoverImage.getX(),
            foodHoverImage.getY()
        ); // TODO
        stage.addActor(name);

        int count = 0;
        for (IngredientType ingredient : foodType.getIngredients().keySet()) {
            Image ingredientImage = new Image(GameAssetManager.getGameAssetManager().getTextureByIngredient(new Ingredient(ingredient)));
            ingredientImage.setPosition( // TODO
              foodHoverImage.getX(),
              foodHoverImage.getY() + count * 20
            );
            stage.addActor(ingredientImage);

            Label quantity = new Label(foodType.getIngredients().get(ingredient) + "", GameAssetManager.getGameAssetManager().getSkin());
            quantity.setColor(Color.BLACK);
            quantity.setPosition( // TODO
                ingredientImage.getX() + 5,
                ingredientImage.getY()
            );

            Label energy = new Label(foodType.getEnergy() + "", GameAssetManager.getGameAssetManager().getSkin());
            energy.setColor(Color.BLACK);
            energy.setPosition( // TODO
                ingredientImage.getX(),
                ingredientImage.getY() + 50
            );
            stage.addActor(energy);

            count++;
        }
    }
}
