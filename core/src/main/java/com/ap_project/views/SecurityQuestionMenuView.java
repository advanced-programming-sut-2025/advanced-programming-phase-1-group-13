package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.SecurityQuestionMenuController;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.enums.SecurityQuestion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SecurityQuestionMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final Label securityQuestion;
    private final SelectBox<String> securityQuestions;
    private final Label securityAnswerLabel;
    private final TextField securityAnswerField;
    private final TextButton enterButton;
    private final TextButton skipButton;
    private final Table table;
    private final SecurityQuestionMenuController controller;

    public SecurityQuestionMenuView(SecurityQuestionMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getTitleMenuBackground();

        this.menuTitle = new Label("Choose a security question:", skin);
        menuTitle.setFontScale(2.0f);

        this.securityQuestion = new Label("Security Question", skin);
        securityQuestion.setFontScale(2.0f);
        this.securityQuestions = new SelectBox<>(skin);

        this.securityAnswerLabel = new Label("Answer", skin);
        securityAnswerLabel.setFontScale(2.0f);
        this.securityAnswerField = new TextField("", skin);

        this.enterButton = new TextButton("Enter", skin);
        this.skipButton = new TextButton("Skip", skin);

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

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(securityQuestion).align(Align.right).padRight(10);
        table.add(securityQuestions).width(750).padBottom(20).row();

        table.add(securityAnswerLabel).align(Align.right).padRight(10);
        table.add(securityAnswerField).width(750).padBottom(30).row();

        table.add(skipButton).padTop(20).padBottom(30);
        table.add(enterButton).padTop(20).padBottom(30).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleSecurityQuestionMenuButtons();
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

    public TextButton getEnterButton() {
        return enterButton;
    }

    public TextButton getSkipButton() {
        return skipButton;
    }
}
