package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.SignUpMenuController;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.enums.SecurityQuestion;
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
    private final Label gameTitle;
    private final Label username;
    private final TextField usernameField;
    private final Label password;
    private final TextField passwordField;
    private final TextButton signUpButton;
    private final TextButton backButton;
    private final Label securityQuestion;
    private final SelectBox<String> securityQuestions;
    private final Label securityAnswerLabel;
    private final TextField securityAnswerField;
    private final Label errorMessageLabel;
    private final Table table;
    private final SignUpMenuController controller;

    public SignUpMenuView(SignUpMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.gameTitle = new Label("Signup", skin);
        gameTitle.setFontScale(2.0f);

        this.username = new Label("Username", skin);
        username.setFontScale(2.0f);
        this.usernameField = new TextField("", skin);
        this.password = new Label("Password", skin);
        password.setFontScale(2.0f);
        this.passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        this.securityQuestion = new Label("Security Question", skin);
        securityQuestion.setFontScale(2.0f);
        this.securityQuestions = new SelectBox<>(skin);
        this.securityAnswerLabel = new Label("Answer", skin);
        securityAnswerLabel.setFontScale(2.0f);
        this.securityAnswerField = new TextField("", skin);

        this.signUpButton = new TextButton("Signup", skin);
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

        Array<String> questions = new Array<>();
        questions.add("None");
        for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
            questions.add(securityQuestion.getQuestion());
        }

        securityQuestions.setItems(questions);

        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(errorMessageLabel).colspan(2).left().padBottom(10).row();

        table.add(gameTitle).align(Align.center).colspan(2).padBottom(60).center().row();

        table.add(username).align(Align.right).padRight(10);
        table.add(usernameField).width(750).padBottom(20).row();

        table.add(password).align(Align.right).padRight(10);
        table.add(passwordField).width(750).padBottom(20).row();

        table.add(securityQuestion).align(Align.right).padRight(10);
        table.add(securityQuestions).width(750).padBottom(20).row();

        table.add(securityAnswerLabel).align(Align.right).padRight(10);
        table.add(securityAnswerField).width(750).padBottom(30).row();

        table.add(backButton).padTop(50);
        table.add(signUpButton).padTop(50);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(39f / 255f, 33f / 255f, 48f / 255f, 1f);
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

    public Label getSecurityQuestion() {
        return securityQuestion;
    }

    public SelectBox<String> getSecurityQuestions() {
        return securityQuestions;
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
