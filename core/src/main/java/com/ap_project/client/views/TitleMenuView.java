package com.ap_project.client.views;

import com.ap_project.Main;
import com.ap_project.client.controllers.TitleMenuController;
import com.ap_project.common.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TitleMenuView implements Screen {
    private Stage stage;
    private final Texture logo;
    private final Texture background;
    private final TextButton signUpButton;
    private final TextButton loginButton;
    private final TextButton exitButton;
    private final Table table;
    private final TitleMenuController controller;

    public TitleMenuView(TitleMenuController controller, Skin skin) {
        this.logo = GameAssetManager.getGameAssetManager().getLogo();
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.signUpButton = new TextButton("Signup", skin);
        this.loginButton = new TextButton("Login", skin);
        this.exitButton = new TextButton("Exit", skin);

        this.table = new Table();

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("Images/Cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        Image logoImage = new Image(logo);
        float originalWidth = logo.getWidth();
        float originalHeight = logo.getHeight();
        logoImage.setSize(originalWidth * 2, originalHeight * 2);

        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.add(logoImage).width(originalWidth * 2).height(originalHeight * 2).padBottom(40);
        table.row();

        float buttonWidth = 300f;
        table.add(signUpButton).width(buttonWidth).padBottom(20);
        table.row();
        table.add(loginButton).width(buttonWidth).padBottom(20);
        table.row();
        table.add(exitButton).width(buttonWidth);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.136f, 0.508f, 0.884f, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleButtons();
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

    public Stage getStage() {
        return stage;
    }

    public Table getTable() {
        return table;
    }

    public TitleMenuController getController() {
        return controller;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }
}
