package com.ap_project.client.views;

import com.ap_project.Main;
import com.ap_project.client.controllers.MainMenuController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
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
    private final TextButton preGameMenuButton;
    private final TextButton profileMenuButton;
    private final TextButton logoutButton;
    private final TextButton UsersMenuButton;
    private final Table table;
    private final MainMenuController controller;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.menuTitle = new Label("Main Menu", skin);
        menuTitle.setFontScale(1.5f);

        this.preGameMenuButton = new TextButton("Pre Game Menu", skin);
        this.profileMenuButton = new TextButton("Profile Menu", skin);
        this.logoutButton = new TextButton("Logout", skin);
        this.UsersMenuButton = new TextButton("Users Menu", skin);

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

        Image avatarBackground = new Image(GameAssetManager.getGameAssetManager().getAvatarBackground());
        avatarBackground.setScale(5f);
        avatarBackground.setPosition(
            Gdx.graphics.getWidth() / 1.75f,
            (Gdx.graphics.getHeight() - 5 * avatarBackground.getHeight()) / 2f
        );
        stage.addActor(avatarBackground);

        float bgX = avatarBackground.getX();
        float bgY = avatarBackground.getY();
        float bgWidth = avatarBackground.getWidth() * avatarBackground.getScaleX();
        float bgHeight = avatarBackground.getHeight() * avatarBackground.getScaleY();

        Image avatar = new Image(GameAssetManager.getGameAssetManager().getAvatar(App.getLoggedIn().getAvatarNumber()));
        avatar.setSize(4 * avatarBackground.getWidth(), 4 * avatarBackground.getHeight());
        avatar.setPosition(
            bgX + (bgWidth - avatar.getWidth()) / 2,
            bgY + (bgHeight - avatar.getHeight()) / 2 - 10
        );
        stage.addActor(avatar);

        Label nickname = new Label(App.getLoggedIn().getNickname(), GameAssetManager.getGameAssetManager().getSkin());
        nickname.setFontScale(1.5f);
        nickname.setPosition(
            bgX + (bgWidth - nickname.getPrefWidth()) / 2,
            bgY - nickname.getPrefHeight() - 10f
        );
        stage.addActor(nickname);

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        float buttonWidth = 425f;
        table.add(preGameMenuButton).width(buttonWidth).padBottom(30).row();
        table.add(profileMenuButton).width(buttonWidth).padBottom(30).row();
        table.add(UsersMenuButton).width(buttonWidth).padBottom(30).row();
        table.add(logoutButton).width(buttonWidth).padBottom(30).row();

        table.padRight(500f);
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
        return preGameMenuButton;
    }

    public TextButton getProfileMenuButton() {
        return profileMenuButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public TextButton getUsersMenuButton() {
        return UsersMenuButton;
    }
}
