package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.Main.getBatch;
import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public  class ChatMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Image closeButton;
    private final Image privateButton;
    private final Image publicButton;
    private final TextArea publicChat;
    private final TextField chatTextField;
    private final TextButton sendButton;
    private final GameView gameView;

    public ChatMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getChatMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.privateButton = new Image(GameAssetManager.getGameAssetManager().getPrivateButton());
        this.publicButton = new Image(GameAssetManager.getGameAssetManager().getPublicButton());

        this.publicChat = new TextArea(App.getCurrentGame().getPublicChat(), GameAssetManager.getGameAssetManager().getSkin());
        publicChat.setDisabled(true);
        publicChat.setPosition(
            windowX + (window.getWidth() - publicChat.getWidth()) / 2,
            windowY + (window.getHeight() - publicChat.getHeight()) / 2 + 100
        );
        publicChat.setSize(
            window.getWidth() * 0.75f,
            window.getHeight() * 0.75f
        );

        this.chatTextField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        chatTextField.setMessageText("Enter your message");
        chatTextField.setWidth(window.getWidth() * 0.75f);
        chatTextField.setPosition(
            windowX + (window.getWidth() - chatTextField.getWidth()) / 2,
            windowY + 50
        );

        this.sendButton = new TextButton("Send", GameAssetManager.getGameAssetManager().getSkin());
        sendButton.setPosition(
            chatTextField.getX() + chatTextField.getWidth() + 20,
            chatTextField.getY()
        );

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);

        Label title = new Label("Chat", GameAssetManager.getGameAssetManager().getSkin());
        title.setFontScale(1.5f);
        title.setColor(Color.BLACK);
        title.setPosition(
            window.getX() + (window.getWidth() - title.getWidth()) / 2,
            window.getY() + window.getHeight() - 30
        );
        stage.addActor(title);

        privateButton.setPosition(
            window.getX() + (window.getWidth() - privateButton.getWidth()) / 2,
            window.getY() + window.getHeight() / 2 + 50
        );
        stage.addActor(privateButton);
        publicButton.setPosition(
            window.getX() + (window.getWidth() - privateButton.getWidth()) / 2,
            window.getY() + window.getHeight() / 2 - 50
        );
        stage.addActor(publicButton);

        addCloseButton();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.getInputProcessor().equals(stage)) {
            if (sendButton.isChecked()) {
                App.getCurrentGame().addMessage(App.getLoggedIn().getUsername(), chatTextField.getText());
                System.out.println("Sent message: " + chatTextField.getText());
                publicChat.setText(App.getCurrentGame().getPublicChat());
            }

            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                Main.getMain().setScreen(gameView);
            }

            sendButton.setChecked(false);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float convertedY = Gdx.graphics.getHeight() - screenY;

        if (hoverOnImage(closeButton, screenX, convertedY)) {
            getMain().setScreen(gameView);
            return true;
        }

        if (hoverOnImage(publicButton, screenX, convertedY)) {
            Gdx.input.setInputProcessor(stage);
            stage.addActor(publicChat);
            stage.addActor(chatTextField);
            stage.addActor(sendButton);
            return true;
        }

        if (hoverOnImage(privateButton, screenX, convertedY)) {
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = window.getY() + window.getHeight() + 20f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }
}
