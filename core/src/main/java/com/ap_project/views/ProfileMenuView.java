package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.ProfileMenuController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ProfileMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final Label username;
    private final TextField usernameField;
    private final TextButton changeUsernameButton;
    private final Label password;
    private final TextField passwordField;
    private final TextButton changePasswordButton;
    private final Label nickname;
    private final TextField nicknameField;
    private final TextButton changeNicknameButton;
    private final Label email;
    private final TextField emailField;
    private final TextButton changeEmailButton;
    private final Label gender;
    private final Label mostEarnedMoney;
    private final Label numberOfGames;
    private final TextButton backButton;
    private final Label errorMessageLabel;
    private final Table table;
    private final ProfileMenuController controller;

    public ProfileMenuView(ProfileMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.menuTitle = new Label("Profile Menu", skin);
        menuTitle.setFontScale(2.0f);

        User user = App.getLoggedIn();

        this.username = new Label("Username", skin);
        username.setFontScale(2.0f);
        this.usernameField = new TextField(user.getUsername(), skin);
        this.changeUsernameButton = new TextButton("Change Username", skin);

        this.password = new Label("Password", skin);
        password.setFontScale(2.0f);
        this.passwordField = new TextField("password", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        this.changePasswordButton = new TextButton("Change Password", skin);

        this.nickname = new Label("Nickname", skin);
        nickname.setFontScale(2.0f);
        this.nicknameField = new TextField(user.getNickname(), skin);
        this.changeNicknameButton = new TextButton("Change Nickname", skin);

        this.email = new Label("Email", skin);
        email.setFontScale(2.0f);
        this.emailField = new TextField(user.getEmail(), skin);
        this.changeEmailButton = new TextButton("Change Email", skin);

        this.gender = new Label("Gender:   " + user.getGender().getName(), skin);
        gender.setFontScale(2.0f);

        this.mostEarnedMoney = new Label("Most Earned Money in a game: " + user.getMostEarnedMoney(), skin);
        mostEarnedMoney.setFontScale(2.0f);

        this.numberOfGames = new Label("Number of Games: " + user.getNumberOfGames(), skin);
        numberOfGames.setFontScale(2.0f);

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

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(username).align(Align.right).padBottom(20).padRight(20);
        table.add(usernameField).width(750).padBottom(20);
        table.add(changeUsernameButton).padLeft(20).padBottom(20).row();

        table.add(password).align(Align.right).padBottom(20).padRight(20);
        table.add(passwordField).width(750).padBottom(20);
        table.add(changePasswordButton).padLeft(20).padBottom(20).row();

        table.add(nickname).align(Align.right).padBottom(20).padRight(20);
        table.add(nicknameField).width(750).padBottom(20);
        table.add(changeNicknameButton).padLeft(20).padBottom(20).row();

        table.add(email).align(Align.right).padBottom(20).padRight(20);
        table.add(emailField).width(750).padBottom(20);
        table.add(changeEmailButton).padBottom(20).row();

        Table infoTable = new Table();

        infoTable.add(gender).align(Align.center).padBottom(20).padLeft(100).row();

        infoTable.add(mostEarnedMoney).align(Align.center).padBottom(20).padLeft(100).row();

        infoTable.add(numberOfGames).align(Align.center).padBottom(20).padLeft(100).row();

        table.add(infoTable).width(750).padBottom(20).row();

        table.add(backButton).padBottom(20);

        stage.addActor(table);

        stage.addActor(errorMessageLabel);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleProfileMenuButtons();
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

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextButton getChangeUsernameButton() {
        return changeUsernameButton;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public TextButton getChangeNicknameButton() {
        return changeNicknameButton;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextButton getChangeEmailButton() {
        return changeEmailButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }
}
