package com.ap_project.views.login;

import com.ap_project.Main;
import com.ap_project.controllers.login.ChangePasswordMenuController;
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

public class ChangePasswordMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final Label newPassword;
    private final TextField newPasswordField;
    private final TextButton enterButton;
    private final TextButton randomPasswordButton;
    private final Label errorMessageLabel;
    private final Table table;
    private final ChangePasswordMenuController controller;

    public ChangePasswordMenuView(ChangePasswordMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.menuTitle = new Label("Change Password", skin);
        menuTitle.setFontScale(1.5f);

        this.newPassword = new Label("New Password", skin);
        newPassword.setFontScale(1.5f);
        this.newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');

        this.enterButton = new TextButton("Enter", skin);
        this.randomPasswordButton = new TextButton("Random Password", skin);

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

        table.add(newPassword).align(Align.right).padBottom(40).padRight(20);
        table.add(newPasswordField).width(750).padBottom(40).row();

        table.add(randomPasswordButton).padTop(20);
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
        controller.handleChangePasswordMenuButtons();
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

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public TextButton getEnterButton() {
        return enterButton;
    }

    public TextButton getRandomPasswordButton() {
        return randomPasswordButton;
    }

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }
}
