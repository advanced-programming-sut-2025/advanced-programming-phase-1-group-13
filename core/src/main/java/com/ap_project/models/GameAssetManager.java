package com.ap_project.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static final GameAssetManager gameAssetManager = new GameAssetManager();

    private final Skin skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));

    private final Texture logo = new Texture(Gdx.files.internal("Images/Logo.png"));
    private final Texture titleMenuBackground = new Texture(Gdx.files.internal("Images/Logo.png")); // TODO

    public static GameAssetManager getGameAssetManager() {
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getLogo() {
        return logo;
    }

    public Texture getTitleMenuBackground() {
        return titleMenuBackground;
    }
}
