package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.SignUpMenuController;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.enums.types.Gender;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final Label username;
    private final TextField usernameField;
    private final Label password;
    private final TextField passwordField;
    private final Label repeatPassword;
    private final TextField repeatPasswordField;
    private final Label nickname;
    private final TextField nicknameField;
    private final Label email;
    private final TextField emailField;
    private final Label gender;
    private final SelectBox<String> genders;
    private final TextButton signUpButton;
    private final TextButton randomPasswordButton;
    private final TextButton backButton;
    private final Label securityAnswerLabel;
    private final TextField securityAnswerField;
    private final Label errorMessageLabel;
    private final Table table;
    private final SignUpMenuController controller;

    public SignUpMenuView(SignUpMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.menuTitle = new Label("Signup", skin);
        menuTitle.setFontScale(2.0f);

        this.username = new Label("Username", skin);
        username.setFontScale(2.0f);
        this.usernameField = new TextField("", skin);

        this.password = new Label("Password", skin);
        password.setFontScale(2.0f);
        this.passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        this.repeatPassword = new Label("Repeat Password", skin);
        repeatPassword.setFontScale(2.0f);
        this.repeatPasswordField = new TextField("", skin);
        repeatPasswordField.setPasswordMode(true);
        repeatPasswordField.setPasswordCharacter('*');

        this.nickname = new Label("Nickname", skin);
        nickname.setFontScale(2.0f);
        this.nicknameField = new TextField("", skin);

        this.email = new Label("Email", skin);
        email.setFontScale(2.0f);
        this.emailField = new TextField("", skin);

        this.gender = new Label("Gender", skin);
        gender.setFontScale(2.0f);
        this.genders = new SelectBox<>(skin);

        this.securityAnswerLabel = new Label("Answer", skin);
        securityAnswerLabel.setFontScale(2.0f);
        this.securityAnswerField = new TextField("", skin);

        this.signUpButton = new TextButton("Signup", skin);
        this.randomPasswordButton = new TextButton("Random Password", skin);
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

        Array<String> genderOptions = new Array<>();
        for (int i = 0; i < 2 ; i++) {
            Gender genderOption = Gender.values()[i];
            genderOptions.add(genderOption.getName());
        }
        genders.setItems(genderOptions);

        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(errorMessageLabel).colspan(2).left().padBottom(10).row();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(username).align(Align.right).padRight(10);
        table.add(usernameField).width(750).padBottom(20).row();

        table.add(password).align(Align.right).padRight(10);
        table.add(passwordField).width(750).padBottom(20).row();

        table.add(repeatPassword).align(Align.right).padRight(10);
        table.add(repeatPasswordField).width(750).padBottom(20).row();

        table.add(nickname).align(Align.right).padRight(10);
        table.add(nicknameField).width(750).padBottom(20).row();

        table.add(email).align(Align.right).padRight(10);
        table.add(emailField).width(750).padBottom(20).row();

        table.add(gender).align(Align.right).padRight(10);
        table.add(genders).width(750).padBottom(20).row();

        table.add(backButton).padTop(20).padBottom(30);
        table.add(signUpButton).padTop(20).padBottom(30).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleSignUpMenuButtons();
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

    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public Table getTable() {
        return table;
    }

    public SignUpMenuController getController() {
        return controller;
    }

    public Label getSecurityAnswerLabel() {
        return securityAnswerLabel;
    }

    public TextField getSecurityAnswerField() {
        return securityAnswerField;
    }

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }
}
