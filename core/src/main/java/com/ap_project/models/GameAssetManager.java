package com.ap_project.models;

import com.ap_project.models.enums.environment.Season;
import com.ap_project.models.enums.environment.Weather;
import com.ap_project.models.enums.types.GameMenuType;
import com.ap_project.models.tools.Tool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static final GameAssetManager gameAssetManager = new GameAssetManager();

    public static GameAssetManager getGameAssetManager() {
        return gameAssetManager;
    }

    public Skin getSkin() {
        return new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
    }

    public Texture getLogo() {
        return new Texture(Gdx.files.internal("Images/Logo.png"));
    }

    public Texture getMenuBackground() {
        return new Texture(Gdx.files.internal("Images/MenuBackground.png"));
    }

    public Texture getClock(Weather weather, Season season) {
        String path = "Images/Clock/" + weather.getName() + season.getName() + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getInventoryHotbar() {
        return new Texture(Gdx.files.internal("Images/Inventory/InventoryHotbar.png"));
    }

    public Texture getHotbarSelectedSlot() {
        return new Texture(Gdx.files.internal("Images/Inventory/SelectedSlot.png"));
    }

    public Texture getEnergyBar() {
        return new Texture(Gdx.files.internal("Images/Energy/EnergyBar.png"));
    }

    public Texture getGreenBar() {
        return new Texture(Gdx.files.internal("Images/Energy/GreenBar.png"));
    }

    public Texture getClockArrow() {
        return new Texture(Gdx.files.internal("Images/Clock/Arrow.png"));
    }

    public Texture getTextureByItem(Item item) {
        if (item instanceof Tool) {
            return getTextureByTool((Tool) item);
        }
        // TODO
        return null;
    }

    public Texture getTextureByTool(Tool tool) {
        String name = toPascalCase(tool.getName());
        String material = toPascalCase(tool.getToolMaterial().getName());
        String path = "Images/Tools/" + name + "/" + material + name + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getMenuWindowByType(GameMenuType tab) {
        String path = "Images/Menu/" + tab.getName() + "Menu.png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getCloseButton() {
        return new Texture(Gdx.files.internal("Images/Menu/CloseButton.png"));
    }

    public static String toPascalCase(String input) {
        if (input == null || input.isEmpty()) return "";

        StringBuilder pascal = new StringBuilder();
        for (String word : input.trim().split("\\s+")) {
            if (!word.isEmpty()) {
                pascal.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    pascal.append(word.substring(1).toLowerCase());
                }
            }
        }
        return pascal.toString();
    }
}
