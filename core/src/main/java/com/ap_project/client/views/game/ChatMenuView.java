package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class ChatMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Image closeButton;
    private final Image privateButton;
    private final Image publicButton;
    private final TextArea publicChatTextArea;
    private final TextField chatTextField;
    private final TextButton sendButton;
    private final TextButton backButton;
    private final GameView gameView;
    private boolean isPublicChatActive = false;
    private boolean isPrivateChatActive = false;
    private String currentPrivateChatUser = null;
    private Table privateChatListTable;
    private ScrollPane privateChatScrollPane;
    private TextArea privateChatTextArea;
    private Dialog privateChatDialog;

    public ChatMenuView(GameView gameView) {
        App.getLoggedIn().setTaggedInPublicChat(null);

        this.window = new Image(GameAssetManager.getGameAssetManager().getChatMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.privateButton = new Image(GameAssetManager.getGameAssetManager().getPrivateButton());
        this.publicButton = new Image(GameAssetManager.getGameAssetManager().getPublicButton());

        this.publicChatTextArea = new TextArea(App.getCurrentGame().getPublicChat(), GameAssetManager.getGameAssetManager().getSkin());
        publicChatTextArea.setDisabled(true);
        publicChatTextArea.setPosition(
            windowX - 240 + (window.getWidth() - publicChatTextArea.getWidth()) / 2,
            windowY + (window.getHeight() - publicChatTextArea.getHeight()) / 2 - 104
        );
        publicChatTextArea.setSize(
            window.getWidth() * 0.75f,
            window.getHeight() * 0.68f
        );

        this.chatTextField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        chatTextField.setMessageText("Enter your message");
        chatTextField.setWidth(window.getWidth() * 0.75f);
        chatTextField.setPosition(
            windowX + (window.getWidth() - chatTextField.getWidth()) / 2,
            windowY + 48
        );

        this.sendButton = new TextButton("Send", GameAssetManager.getGameAssetManager().getSkin());
        sendButton.setPosition(
            chatTextField.getX() + chatTextField.getWidth() + 20,
            chatTextField.getY()
        );
        this.backButton = new TextButton("Back", GameAssetManager.getGameAssetManager().getSkin());
        backButton.setPosition(
            sendButton.getX(),
            sendButton.getY() + sendButton.getHeight() + 10
        );

        this.gameView = gameView;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);

        Label title = new Label("Chat", GameAssetManager.getGameAssetManager().getSkin());
        title.setFontScale(2f);
        title.setColor(Color.BLACK);
        title.setPosition(
            window.getX() - 35 + (window.getWidth() - title.getWidth()) / 2,
            window.getY() + window.getHeight() - 95
        );
        stage.addActor(title);

        privateButton.setScale(2f);

        privateButton.setPosition(
            window.getX() - 68 + (window.getWidth() - privateButton.getWidth()) / 2,
            window.getY() + window.getHeight() / 2 + 5
        );
        stage.addActor(privateButton);

        publicButton.setScale(2f);

        publicButton.setPosition(
            window.getX() - 68 + (window.getWidth() - privateButton.getWidth()) / 2,
            window.getY() + window.getHeight() / 2 - 145
        );
        stage.addActor(publicButton);

        addCloseButton();
    }


    @Override
    public void render(float delta) {
        if (Gdx.input.getInputProcessor().equals(stage)) {
            if (sendButton.isChecked()) {
                String message = chatTextField.getText();

                if (isPublicChatActive) {
                    App.getCurrentGame().addMessage(App.getLoggedIn().getUsername(), message);

                    for (User user : App.getCurrentGame().getPlayers()) {
                        if (message.contains("@" + user.getUsername())) {
                            user.setTaggedInPublicChat(message);
                        }
                    }
                    publicChatTextArea.setText(App.getCurrentGame().getPublicChat());

                } else if (isPrivateChatActive && currentPrivateChatUser != null) {
                    User recipient = App.getUserByUsername(currentPrivateChatUser);

                    if (recipient != null) {
                        App.getLoggedIn().sendPrivateMessage(recipient.getUsername(), message);

                        recipient.receivePrivateMessage(App.getLoggedIn().getUsername(), message);

                        privateChatTextArea.setText(App.getLoggedIn().getPrivateChat(currentPrivateChatUser));
                    }
                }

                chatTextField.setText("");
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
            isPrivateChatActive = false;
            isPublicChatActive = true;
            if (privateChatDialog != null) {
                privateChatDialog.hide();
            }
            if (privateChatTextArea != null) privateChatTextArea.remove();

            privateButton.remove();
            publicButton.remove();

            backButton.setPosition(
                sendButton.getX(),
                sendButton.getY() + sendButton.getHeight() + 10
            );

            Gdx.input.setInputProcessor(stage);

            chatTextField.setTextFieldListener(null);
            chatTextField.setTextFieldListener(new TextField.TextFieldListener() {
                @Override
                public void keyTyped(TextField textField, char c) {
                    if (c == '\n') {
                        sendButton.setChecked(true);
                    }
                }
            });

            stage.addActor(publicChatTextArea);
            stage.addActor(chatTextField);
            stage.addActor(sendButton);

            backButton.clearListeners();
            backButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    isPublicChatActive = false;
                    publicChatTextArea.remove();
                    chatTextField.remove();
                    sendButton.remove();
                    backButton.remove();
                    // Re-add the public and private chat buttons
                    stage.addActor(privateButton);
                    stage.addActor(publicButton);
                    Gdx.input.setInputProcessor(ChatMenuView.this);
                }
            });
            stage.addActor(backButton);
            return true;
        }

        if (hoverOnImage(privateButton, screenX, convertedY)) {
            isPublicChatActive = false;
            publicChatTextArea.remove();
            chatTextField.remove();
            sendButton.remove();

            backButton.remove();

            isPrivateChatActive = true;
            Gdx.input.setInputProcessor(stage);
            showPrivateChatDialog();
            return true;
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

    private void showPrivateChatDialog() {
        privateChatDialog = new Dialog("Private Chats", GameAssetManager.getGameAssetManager().getSkin()) {
            @Override
            protected void result(Object object) {
                String username = (String) object;
                if (username != null) {
                    openPrivateChatWith(username);
                }
            }
        };

        Table privateChatListTable = new Table(GameAssetManager.getGameAssetManager().getSkin());

        for (User player : App.getCurrentGame().getPlayers()) {
            if (!player.getUsername().equals(App.getLoggedIn().getUsername())) {
                String buttonText = player.getUsername();
                if (App.getLoggedIn().getUnreadPrivateMessagesMap().getOrDefault(player.getUsername(), false)) {
                    buttonText = "* " + buttonText;
                }

                TextButton chatButton = new TextButton(buttonText, GameAssetManager.getGameAssetManager().getSkin());
                privateChatListTable.add(chatButton).center().pad(10).row();

                chatButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
                    @Override
                    public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                        privateChatDialog.hide();
                        openPrivateChatWith(player.getUsername());
                    }
                });
            }
        }

        ScrollPane scrollPane = new ScrollPane(privateChatListTable, GameAssetManager.getGameAssetManager().getSkin());
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        TextButton closeButton = new TextButton("Close", GameAssetManager.getGameAssetManager().getSkin());

        closeButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                privateChatDialog.hide();
                Gdx.input.setInputProcessor(ChatMenuView.this);
            }
        });

        privateChatDialog.getContentTable().add(scrollPane).expand().fill();
        privateChatDialog.getButtonTable().add(closeButton).pad(10);

        privateChatDialog.show(stage);
    }

    private void openPrivateChatWith(String username) {
        currentPrivateChatUser = username;
        if (publicChatTextArea != null) publicChatTextArea.remove();
        if (chatTextField != null) chatTextField.remove();
        if (sendButton != null) sendButton.remove();

        privateButton.remove();
        publicButton.remove();

        privateChatTextArea = new TextArea("", GameAssetManager.getGameAssetManager().getSkin());
        privateChatTextArea.setDisabled(true);
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        privateChatTextArea.setPosition(
            windowX - 240 + (window.getWidth() - privateChatTextArea.getWidth()) / 2,
            windowY + (window.getHeight() - privateChatTextArea.getHeight()) / 2 - 104
        );
        privateChatTextArea.setSize(window.getWidth() * 0.75f, window.getHeight() * 0.68f);
        privateChatTextArea.setText(App.getLoggedIn().getPrivateChat(username));

        chatTextField.setWidth(window.getWidth() * 0.75f);
        chatTextField.setPosition(windowX + (window.getWidth() - chatTextField.getWidth()) / 2, windowY + 48);
        sendButton.setPosition(chatTextField.getX() + chatTextField.getWidth() + 20, chatTextField.getY());

        backButton.setPosition(
            sendButton.getX(),
            sendButton.getY() + sendButton.getHeight() + 10
        );

        chatTextField.setTextFieldListener(null);
        chatTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\n') {
                    sendButton.setChecked(true);
                }
            }
        });

        stage.addActor(privateChatTextArea);
        stage.addActor(chatTextField);
        stage.addActor(sendButton);

        backButton.clearListeners();
        backButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                isPrivateChatActive = false;
                privateChatTextArea.remove();
                chatTextField.remove();
                sendButton.remove();
                backButton.remove();
                stage.addActor(privateButton);
                stage.addActor(publicButton);
                Gdx.input.setInputProcessor(ChatMenuView.this);
            }
        });
        stage.addActor(backButton);

        App.getLoggedIn().readPrivateMessage(username);
    }
}
