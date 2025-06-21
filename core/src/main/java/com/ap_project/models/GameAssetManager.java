package com.ap_project.models;

import com.ap_project.models.enums.environment.Season;
import com.ap_project.models.enums.environment.Weather;
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

    public Texture getClockArrow() {
        return new Texture(Gdx.files.internal("Images/Clock/Arrow.png"));
    }
}
