package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.LoginController;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label gameTitle;
    private final Label username;
    private final TextField usernameField;
    private final Label password;
    private final TextField passwordField;
    private final TextButton loginButton;
    private final TextButton forgotPasswordButton;
    private final TextButton backButton;
    private final Label errorMessageLabel;
    private final Table table;
    private final LoginController controller;

    public LoginMenuView(LoginController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.gameTitle = new Label("Login", skin);
        gameTitle.setFontScale(2.0f);

        this.username = new Label("Username", skin);
        username.setFontScale(2.0f);
        this.usernameField = new TextField("", skin);
        this.password = new Label("Password", skin);
        password.setFontScale(2.0f);
        this.passwordField = new TextField("", skin);

        this.loginButton = new TextButton("Login", skin);
        this.forgotPasswordButton = new TextButton("Forgot Password", skin);
        this.backButton = new TextButton("Back", skin);

        this.errorMessageLabel = new Label("", skin);
        errorMessageLabel.setColor(Color.RED);

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

        table.add(errorMessageLabel).colspan(2).left().padBottom(10).row();

        table.add(gameTitle).colspan(2).padBottom(60).center().row();

        table.add(username).align(Align.left).padRight(10);
        table.add(usernameField).width(750).padBottom(20).row();

        table.add(password).align(Align.left).padRight(10);
        table.add(passwordField).width(750).padBottom(30).row();

        table.add(backButton).padTop(50);
        table.add(forgotPasswordButton).padTop(50).padRight(100);
        table.add(loginButton).padTop(50);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleLoginMenuButtons();
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

    public Label getGameTitle() {
        return gameTitle;
    }

    public Label getUsername() {
        return username;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public Label getPassword() {
        return password;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }
}
