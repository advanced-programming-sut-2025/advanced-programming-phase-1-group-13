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
    private final Label menuTitle;
    private final Label username;
    private final TextField usernameField;
    private final Label password;
    private final TextField passwordField;
    private final CheckBox stayLoggedIn;
    private final TextButton loginButton;
    private final TextButton forgotPasswordButton;
    private final TextButton backButton;
    private final Label errorMessageLabel;
    private final Table table;
    private final LoginController controller;

    public LoginMenuView(LoginController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.menuTitle = new Label("Login", skin);
        menuTitle.setFontScale(2.0f);

        this.username = new Label("Username", skin);
        username.setFontScale(2.0f);
        this.usernameField = new TextField("", skin);

        this.password = new Label("Password", skin);
        password.setFontScale(2.0f);
        this.passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);

        this.stayLoggedIn = new CheckBox("Stay LoggedIn", skin);

        this.loginButton = new TextButton("Login", skin);
        this.forgotPasswordButton = new TextButton("Forgot Password", skin);
        this.backButton = new TextButton("Back", skin);

        this.errorMessageLabel = new Label("", skin);
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

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

        stage.addActor(errorMessageLabel);

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(40).center().row();

        table.add(username).align(Align.right).padBottom(40).padRight(20);
        table.add(usernameField).width(750).padBottom(40).row();

        table.add(password).align(Align.right).padBottom(40).padRight(20);
        table.add(passwordField).width(750).padBottom(40).row();

        table.add(stayLoggedIn).right().padRight(10).row();

        table.add(backButton).padTop(20);
        table.add(loginButton).padTop(20).row();

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
        return menuTitle;
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
