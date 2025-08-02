package com.ap_project.client.views;

import com.ap_project.Main;
import com.ap_project.client.controllers.ProfileMenuController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.User;
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
    private Image avatar;
    private final Label avatarLabel;
    private final SelectBox<Integer> avatarBox;
    private final TextButton changeAvatarButton;
    private final TextButton backButton;
    private final Label errorMessageLabel;
    private final Table table;
    private final ProfileMenuController controller;

    public ProfileMenuView(ProfileMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.menuTitle = new Label("Profile Menu", skin);
        menuTitle.setFontScale(1.5f);

        User user = App.getLoggedIn();

        this.username = new Label("Username", skin);
        username.setFontScale(1.5f);
        this.usernameField = new TextField(user.getUsername(), skin);
        this.changeUsernameButton = new TextButton("Change Username", skin);

        this.password = new Label("Password", skin);
        password.setFontScale(1.5f);
        this.passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        this.changePasswordButton = new TextButton("Change Password", skin);

        this.nickname = new Label("Nickname", skin);
        nickname.setFontScale(1.5f);
        this.nicknameField = new TextField(user.getNickname(), skin);
        this.changeNicknameButton = new TextButton("Change Nickname", skin);

        this.email = new Label("Email", skin);
        email.setFontScale(1.5f);
        this.emailField = new TextField(user.getEmail(), skin);
        this.changeEmailButton = new TextButton("Change Email", skin);

        this.gender = new Label("Gender:   " + user.getGender().getName(), skin);
        gender.setFontScale(1.5f);

        this.mostEarnedMoney = new Label("Most Earned Money in a game: " + user.getMostEarnedMoney(), skin);
        mostEarnedMoney.setFontScale(1.5f);

        this.numberOfGames = new Label("Number of Games: " + user.getNumberOfGames(), skin);
        numberOfGames.setFontScale(1.5f);

        this.avatar = new Image(GameAssetManager.getGameAssetManager().getAvatar(App.getLoggedIn().getAvatarNumber()));

        this.avatarLabel = new Label("Avatar", skin);
        avatarLabel.setFontScale(1.5f);
        this.avatarBox = new SelectBox<>(skin);
        Array<Integer> options = new Array<>();
        for (int i = 1; i < 35; i++) {
            options.add(i);
        }
        avatarBox.setItems(options);
        avatarBox.setSelected(App.getLoggedIn().getAvatarNumber());
        this.changeAvatarButton = new TextButton("Change Avatar", skin);

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

        float buttonWidth = 500f;
        table.add(username).align(Align.right).padBottom(20).padRight(20);
        table.add(usernameField).width(750).padBottom(20);
        table.add(changeUsernameButton).width(buttonWidth).padLeft(20).padBottom(20).row();

        table.add(password).align(Align.right).padBottom(20).padRight(20);
        table.add(passwordField).width(750).padBottom(20);
        table.add(changePasswordButton).width(buttonWidth).padLeft(20).padBottom(20).row();

        table.add(nickname).align(Align.right).padBottom(20).padRight(20);
        table.add(nicknameField).width(750).padBottom(20);
        table.add(changeNicknameButton).width(buttonWidth).padLeft(20).padBottom(20).row();

        table.add(email).align(Align.right).padBottom(20).padRight(20);
        table.add(emailField).width(750).padBottom(20);
        table.add(changeEmailButton).width(buttonWidth).padLeft(20).padBottom(20).row();

        table.setPosition(table.getX(), table.getY() + 180);
        stage.addActor(table);

        Table avatarTable = new Table();
        avatarTable.add(avatarLabel).align(Align.center).padBottom(20);
        avatarTable.add(avatarBox).align(Align.center).padBottom(20).padLeft(20).row();
        avatarTable.setPosition(avatarLabel.getX() + 220, avatarLabel.getY() + 200);
        stage.addActor(avatarTable);
        changeAvatarButton.setPosition(avatarLabel.getX() + 100, avatarLabel.getY() + 30);
        stage.addActor(changeAvatarButton);

        Image avatarBackground = new Image(GameAssetManager.getGameAssetManager().getAvatarBackground());
        avatarBackground.setScale(5f);
        avatarBackground.setPosition(
            Gdx.graphics.getWidth() / 3f - 20,
            (avatarBackground.getHeight()) / 2f - 20
        );
        stage.addActor(avatarBackground);

        updateAvatar();

        Table infoTable = new Table();

        infoTable.add(gender).align(Align.center).padBottom(20).padLeft(100).row();
        infoTable.add(mostEarnedMoney).align(Align.center).padBottom(20).padLeft(100).row();
        infoTable.add(numberOfGames).align(Align.center).padBottom(20).padLeft(100).row();
        infoTable.add(backButton).padBottom(20);

        infoTable.setPosition(infoTable.getX() + 1300, infoTable.getY() + 200);
        stage.addActor(infoTable);

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

    public TextButton getChangeAvatarButton() {
        return changeAvatarButton;
    }

    public SelectBox<Integer> getAvatarBox() {
        return avatarBox;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }

    public void updateAvatar() {
        avatar.remove();
        avatar = new Image(GameAssetManager.getGameAssetManager().getAvatar(App.getLoggedIn().getAvatarNumber()));

        Image avatarBackground = new Image(GameAssetManager.getGameAssetManager().getAvatarBackground());
        avatar.setSize(4 * avatarBackground.getWidth(), 4 * avatarBackground.getHeight());
        avatar.setPosition(
            Gdx.graphics.getWidth() / 3f - 20 + (5 * avatarBackground.getWidth() - avatar.getWidth()) / 2,
            (avatarBackground.getHeight()) / 2f - 20 + (5 * avatarBackground.getHeight() - avatar.getHeight()) / 2 - 10
        );
        stage.addActor(avatar);
    }
}
