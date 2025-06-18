package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.MainMenuController;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final TextButton gameMenuButton;
    private final TextButton profileMenuButton;
    private final TextButton logoutButton;
    private final Table table;
    private final MainMenuController controller;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.menuTitle = new Label("Main Menu", skin);
        menuTitle.setFontScale(2.0f);

        this.gameMenuButton = new TextButton("Game Menu", skin);
        this.profileMenuButton = new TextButton("Profile Menu", skin);
        this.logoutButton = new TextButton("Logout", skin);

        this.table = new Table();

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(gameMenuButton).padBottom(30).row();
        table.add(profileMenuButton).padBottom(30).row();
        table.add(logoutButton).padBottom(30).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleMainMenuButtons();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

    public TextButton getGameMenuButton() {
        return gameMenuButton;
    }

    public TextButton getProfileMenuButton() {
        return profileMenuButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }
}
