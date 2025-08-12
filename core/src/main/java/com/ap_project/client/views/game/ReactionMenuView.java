package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.enums.types.ReactionMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

import static com.ap_project.Main.getMain;
import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class ReactionMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Image textButton;
    private final Image emojiButton;
    private final Image changeDefaultsButton;
    private boolean isEmoji;
    private boolean isChangingDefaults = false;
    private final Image closeButton;
    private ArrayList<Image> emojiImages;
    private ArrayList<Label> textLabels;
    private final TextField defaultEmojiField;
    private final TextField defaultTextsField;
    private final TextButton enterButton;
    private final GameView gameView;

    public ReactionMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getReactionMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.textButton = new Image(GameAssetManager.getGameAssetManager().getTextButton());
        this.emojiButton = new Image(GameAssetManager.getGameAssetManager().getEmojiButton());
        this.changeDefaultsButton = new Image(GameAssetManager.getGameAssetManager().getChangeDefaultReactionsButton());

        this.emojiImages = new ArrayList<>();
        this.textLabels = new ArrayList<>();

        this.isEmoji = true;

        StringBuilder defaultEmojis = new StringBuilder();
        for (Integer i : App.getLoggedIn().getDefaultEmojis()) {
            defaultEmojis.append(i).append(", ");
        }
        defaultEmojis.setLength(defaultEmojis.length() - 2);
        this.defaultEmojiField = new TextField(defaultEmojis.toString(), GameAssetManager.getGameAssetManager().getSkin());
        this.defaultEmojiField.setPosition(window.getX() + 50, window.getY() + 80);
        this.defaultEmojiField.setSize(500, 100);
        this.defaultEmojiField.setVisible(false);

        StringBuilder defaultTexts = new StringBuilder();
        for (ReactionMessage message : App.getLoggedIn().getDefaultReactions()) {
            defaultTexts.append(message.ordinal()).append(", ");
        }
        defaultTexts.setLength(defaultTexts.length() - 2);
        this.defaultTextsField = new TextField(defaultTexts.toString(), GameAssetManager.getGameAssetManager().getSkin());
        this.defaultTextsField.setPosition(window.getX() + 50, window.getY() + 80);
        this.defaultTextsField.setSize(500, 100);
        this.defaultTextsField.setVisible(false);

        this.enterButton = new TextButton("Enter", GameAssetManager.getGameAssetManager().getSkin());
        enterButton.setVisible(false);
        enterButton.setPosition(
            defaultEmojiField.getX() + 550,
            defaultEmojiField.getY()
        );

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(window);
        updateWindow();

        textButton.setPosition(
            850 - 200,
            Gdx.graphics.getHeight() / 2f + 180
        );
        stage.addActor(textButton);

        emojiButton.setPosition(
            850 + 200,
            Gdx.graphics.getHeight() / 2f + 180
        );
        stage.addActor(emojiButton);

        changeDefaultsButton.setPosition(
            850,
            Gdx.graphics.getHeight() / 2f - 200
        );
        stage.addActor(changeDefaultsButton);

        stage.addActor(defaultEmojiField);
        stage.addActor(defaultTextsField);
        stage.addActor(enterButton);

        addCloseButton();
    }

    @Override
    public void render(float delta) {
        if (isChangingDefaults) {
            Gdx.input.setInputProcessor(stage);
        } else {
            Gdx.input.setInputProcessor(this);
        }

        if (isChangingDefaults && enterButton.isChecked()) {
            if (isEmoji) {
                App.getLoggedIn().getDefaultEmojis().clear();

                String[] emojis = defaultEmojiField.getText().split(", ");
                for (String emoji : emojis) {
                    try {
                        int emojiId = Integer.parseInt(emoji.trim());
                        if (emojiId >= 1 && emojiId <= 20) {
                            App.getLoggedIn().getDefaultEmojis().add(emojiId);
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            } else {
                App.getLoggedIn().getDefaultReactions().clear();

                String[] messages = defaultTextsField.getText().split(", ");
                for (String message : messages) {
                    try {
                        int messageId = Integer.parseInt(message.trim());
                        if (messageId >= 0 && messageId < ReactionMessage.values().length) {
                            App.getLoggedIn().getDefaultReactions().add(ReactionMessage.values()[messageId]);
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            isChangingDefaults = false;
            defaultEmojiField.setVisible(false);
            defaultTextsField.setVisible(false);
            enterButton.setVisible(false);
            changeDefaultsButton.setVisible(true);

            updateWindow();
        }
        enterButton.setChecked(false);

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

        if (hoverOnImage(textButton, screenX, convertedY)) {
            isEmoji = false;
            if (isChangingDefaults) {
                defaultEmojiField.setVisible(false);
                defaultTextsField.setVisible(true);
            }
            updateWindow();
            return true;
        }

        if (hoverOnImage(emojiButton, screenX, convertedY)) {
            isEmoji = true;
            if (isChangingDefaults) {
                defaultEmojiField.setVisible(true);
                defaultTextsField.setVisible(false);
            }
            updateWindow();
            return true;
        }

        if (hoverOnImage(changeDefaultsButton, screenX, convertedY)) {
            isChangingDefaults = !isChangingDefaults;
            defaultEmojiField.setVisible(isChangingDefaults && isEmoji);
            defaultTextsField.setVisible(isChangingDefaults && !isEmoji);
            changeDefaultsButton.setVisible(false);
            enterButton.setVisible(true);
            updateWindow();
            return true;
        }

        for (Image image : emojiImages) {
            if (hoverOnImage(image, screenX, convertedY)) {
                // Create a NEW image instance for the reaction
                Image reactionImage = new Image(image.getDrawable());
                reactionImage.setScale(2); // Make it larger
                gameView.startReaction(reactionImage);
                Main.getMain().setScreen(gameView);
                return true;
            }
        }

        for (Label label : textLabels) {
            Image labelImage = new Image(GameAssetManager.getGameAssetManager().getBlackScreen());
            labelImage.setPosition(label.getX(), label.getY());
            labelImage.setSize(label.getWidth(), label.getHeight());
            if (hoverOnImage(labelImage, screenX, convertedY)) {
                // Create a NEW label instance for the reaction
                Label reactionLabel = new Label(label.getText(), label.getStyle());
                reactionLabel.setColor(label.getColor());
                gameView.startReaction(reactionLabel);
                Main.getMain().setScreen(gameView);
                return true;
            }
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

    private void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = window.getY() + window.getHeight() + 20f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }

    private void updateWindow() {
        for (Image image : emojiImages) {
            image.remove();
        }
        emojiImages = new ArrayList<>();
        for (Label label : textLabels) {
            label.remove();
        }
        textLabels = new ArrayList<>();

        if (isChangingDefaults) {
            if (isEmoji) {
                List<Integer> defaultEmojis = App.getLoggedIn().getDefaultEmojis();
                for (int i = 1; i <= 20; i++) {
                    Image emoji = new Image(GameAssetManager.getGameAssetManager().getEmoji(i));
                    emoji.setScale(5);
                    if (!defaultEmojis.contains(i)) {
                        emoji.setColor(1, 1, 1, 0.5f);
                    }
                    int col = (i - 1) % 10;
                    int row = 4 - (i - 1) / 10;
                    emoji.setPosition(
                        window.getX() + 40 + col * 80,
                        window.getY() + row * 80
                    );
                    stage.addActor(emoji);
                    emojiImages.add(emoji);
                }
            } else {
                List<ReactionMessage> defaultMessages = App.getLoggedIn().getDefaultReactions();
                for (int i = 0; i < ReactionMessage.values().length; i++) {
                    ReactionMessage message = ReactionMessage.values()[i];
                    Label messageLabel = new Label(message.getMessage(),
                        GameAssetManager.getGameAssetManager().getSkin());
                    messageLabel.setColor(Color.BLACK);
                    if (!defaultMessages.contains(message)) {
                        messageLabel.setColor(0, 0, 0, 0.5f);
                    }
                    int row = i / 5;
                    messageLabel.setPosition(
                        window.getX() + 80 + (i % 5) * 150,
                        window.getY() + 400 - row * 100
                    );
                    stage.addActor(messageLabel);
                    textLabels.add(messageLabel);
                }
            }
        } else {
            int count = 0;
            if (isEmoji) {
                for (Integer i : App.getLoggedIn().getDefaultEmojis()) {
                    Image emoji = new Image(GameAssetManager.getGameAssetManager().getEmoji(i));
                    emoji.setScale(5);
                    emoji.setPosition(
                        window.getX() + 40 + 80 * count,
                        window.getY() + 300
                    );
                    stage.addActor(emoji);
                    emojiImages.add(emoji);
                    count++;
                }
            } else {
                for (int i = 0; i < App.getLoggedIn().getDefaultReactions().size(); i++) {
                    int row = i / 5;
                    ReactionMessage message = App.getLoggedIn().getDefaultReactions().get(i);
                    Label messageLabel = new Label(message.getMessage(),
                        GameAssetManager.getGameAssetManager().getSkin());
                    messageLabel.setColor(Color.BLACK);
                    messageLabel.setPosition(
                        window.getX() + 80 + (i % 5) * 150,
                        window.getY() + 350 - row * 100
                    );
                    stage.addActor(messageLabel);
                    textLabels.add(messageLabel);
                }
            }
        }
    }
}
