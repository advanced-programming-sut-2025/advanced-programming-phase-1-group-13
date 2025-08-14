package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
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
    private Label foodNameLabel;
    private Label errorLabel;
    private final ArrayList<Image> ingredientImages = new ArrayList<>();
    private final ArrayList<Label> ingredientQuantities = new ArrayList<>();
    private final ArrayList<Label> ingredientNameLabels = new ArrayList<>();
    private Label foodEnergyLabel;
    private final GameController gameController;

    public CookingMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getCookingMenu());
        this.windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        this.windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        this.foodImages = new ArrayList<>();
        this.foodHoverImage = new Image(GameAssetManager.getGameAssetManager().getCookingMenuHover());
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
        this.gameController = new GameController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        this.window = new Image(GameAssetManager.getGameAssetManager().getCookingMenu());
        window.setPosition(windowX, windowY);
        stage.addActor(window);

        addCloseButton();

        addFoodToMenu();

        addItemsToInventory(-windowY - 70);

        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorLabel.setColor(Color.RED);
        errorLabel.setFontScale(1.2f);
        errorLabel.setVisible(false);
        errorLabel.setPosition(10, Gdx.graphics.getHeight() - 20);
        stage.addActor(errorLabel);
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

        if (hoverOnImage(closeButton, screenX, convertedY)) {
            Main.getMain().setScreen(gameView);
            return true;
        }
        for (Image foodImage : foodImages) {
            if (hoverOnImage(foodImage, screenX, convertedY)) {
                FoodType foodType = FoodType.values()[foodImages.indexOf(foodImage)];
                if (App.getLoggedIn().hasLearntCookingRecipe(foodType)) {
                    try {
                        Result result = gameController.prepareCook(foodType.getName());
                        if (result.success) {
                            errorLabel.setVisible(false);
                            Main.getMain().setScreen(gameView);
                        } else {
                            errorLabel.setText(result.message);
                            errorLabel.setVisible(true);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage()); e.printStackTrace();
                        e.printStackTrace();
                    }
                }
                return true;
            }}

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
        boolean hovering = false;

        for (Image image : foodImages) {
            if (hoverOnImage(image, screenX, Gdx.graphics.getHeight() - screenY)) {
                FoodType foodType = FoodType.values()[foodImages.indexOf(image)];
                if (App.getLoggedIn().hasLearntCookingRecipe(foodType)) {
                    showFoodHover(foodType, screenX, screenY);
                    hovering = true;
                    break;
                }
            }
        }
        if (!hovering) {
            if (foodHoverImage.getStage() != null) foodHoverImage.remove();
            if (foodNameLabel != null) foodNameLabel.remove();
            if (foodEnergyLabel != null) foodEnergyLabel.remove();
            if(!ingredientNameLabels.isEmpty()) {
                for (Label label : ingredientNameLabels) {
                    label.remove();
                }
            }
            for (Image img : ingredientImages) img.remove();
            for (Label lbl : ingredientQuantities) lbl.remove();
            ingredientImages.clear();
            ingredientQuantities.clear();
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
            if(foodImages.size()>35){
                break;
            }
            foodImages.get(count).setPosition(
                count % 10 * 80f + 550,
                -count / 10 * 80f + 700
            );
            stage.addActor(foodImages.get(count));
            count++;
        }
    }

    public void showFoodHover(FoodType foodType, float screenX, float screenY) {
        float posY = Gdx.graphics.getHeight() - screenY - foodHoverImage.getHeight();

        foodHoverImage.setPosition(screenX, posY);
        if (foodHoverImage.getStage() == null) stage.addActor(foodHoverImage);

        if (foodNameLabel == null) {
            foodNameLabel = new Label(foodType.getName(), GameAssetManager.getGameAssetManager().getSkin());
            foodNameLabel.setColor(Color.BLACK);
            stage.addActor(foodNameLabel);
        } else {
            foodNameLabel.setText(foodType.getName());
        }
        stage.addActor(foodNameLabel);
        foodNameLabel.setPosition(screenX + 20, posY + foodHoverImage.getHeight() - 68);

        for (Image img : ingredientImages) img.remove();
        for (Label lbl : ingredientQuantities) lbl.remove();
        for (Label lbl : ingredientNameLabels) lbl.remove();
        ingredientImages.clear();
        ingredientQuantities.clear();
        ingredientNameLabels.clear();

        int count = 0;
        for (Map.Entry<IngredientType, Integer> entry : foodType.getIngredients().entrySet()) {
            Ingredient ingredient = new Ingredient(entry.getKey());

            Image ingredientImage = new Image(GameAssetManager.getGameAssetManager().getTextureByIngredient(ingredient));
            ingredientImage.setPosition(screenX + 10, posY + foodHoverImage.getHeight() - 200 - (count * 45));
            stage.addActor(ingredientImage);
            ingredientImages.add(ingredientImage);

            Label quantityLabel = new Label("x" + entry.getValue(), GameAssetManager.getGameAssetManager().getSkin());
            quantityLabel.setColor(Color.BLACK);
            quantityLabel.setPosition(ingredientImage.getX() + 40, ingredientImage.getY());
            stage.addActor(quantityLabel);
            ingredientQuantities.add(quantityLabel);

            Label nameLabel = new Label(ingredient.getName(), GameAssetManager.getGameAssetManager().getSkin());
            nameLabel.setColor(Color.BLACK);
            nameLabel.setPosition(quantityLabel.getX() + quantityLabel.getWidth() + 5, quantityLabel.getY());
            stage.addActor(nameLabel);
            ingredientNameLabels.add(nameLabel);
            nameLabel.setFontScale(0.89f);

            count++;
        }

        if (foodEnergyLabel == null) {
            foodEnergyLabel = new Label("" + foodType.getEnergy(), GameAssetManager.getGameAssetManager().getSkin());
            foodEnergyLabel.setColor(Color.BLACK);
        } else {
            foodEnergyLabel.setText("" + foodType.getEnergy());
        }
        stage.addActor(foodEnergyLabel);
        foodEnergyLabel.setPosition(screenX + 70, posY + 30);
    }}

