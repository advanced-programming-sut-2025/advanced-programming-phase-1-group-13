package com.ap_project.views.login;

import com.ap_project.Main;
import com.ap_project.controllers.login.ForgetPasswordMenuController;
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

public class ForgetPasswordMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final Label username;
    private final TextField usernameField;
    private final Label securityQuestion;
    private final Label answer;
    private final TextField answerField;
    private final TextButton enterButton;
    private final TextButton backButton;
    private final Label errorMessageLabel;
    private final Table table;
    private final ForgetPasswordMenuController controller;

    public ForgetPasswordMenuView(ForgetPasswordMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.menuTitle = new Label("Change Password", skin);
        menuTitle.setFontScale(2.0f);

        this.username = new Label("Username", skin);
        username.setFontScale(2.0f);
        this.usernameField = new TextField("", skin);

        this.securityQuestion = new Label("Security Question: ", skin);
        securityQuestion.setFontScale(2.0f);

        this.answer = new Label("Answer", skin);
        username.setFontScale(2.0f);
        this.answerField = new TextField("", skin);

        this.enterButton = new TextButton("Enter", skin);
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

        table.add(securityQuestion).align(Align.right).padBottom(40).row();

        table.add(answer).align(Align.right).padBottom(40).row();
        table.add(answerField).align(Align.right).padBottom(40).row();

        table.add(backButton).padTop(20);
        table.add(enterButton).padTop(20).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleForgetPasswordMenuButtons();
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

    public TextField getAnswerField() {
        return answerField;
    }

    public void setQuestion(String message) {
        securityQuestion.setText("Security Question: " + message);
    }

    public TextButton getEnterButton() {
        return enterButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }
}
