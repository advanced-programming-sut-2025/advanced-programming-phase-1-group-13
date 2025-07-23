package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.GameController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.Item;
import com.ap_project.models.Position;
import com.ap_project.models.enums.environment.Season;
import com.ap_project.models.enums.environment.Time;
import com.ap_project.models.enums.environment.Weather;
import com.ap_project.models.enums.environment.Direction;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ap_project.Main.*;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private final Label date;
    private final Label time;
    private final Texture clock;
    private final Texture clockArrow;
    private Image clockImage;
    private Image clockArrowImage;
    private boolean isTransitioning;
    private float transitionTimer;
    private boolean hasTriggeredTransition;
    private int lastHour;
    private final Image inventoryHotbarImage;
    private final Image selectedSlotImage;
    private int selectedSlotIndex;
    private final GameController controller;
    private final Sprite playerSprite;
    private Animation<Texture> playerUpAnimation;
    private Animation<Texture> playerDownAnimation;
    private Animation<Texture> playerLeftAnimation;
    private Animation<Texture> playerRightAnimation;
    private Animation<Texture> playerFaintAnimation;
    private Animation<Texture> currentAnimation;
    private float stateTime;
    private boolean isMoving;
    private boolean isFainting;
    private final Texture background;
    private final OrthographicCamera camera;
    private Image energyBar;
    private Image greenBar;
    private final ArrayList<Image> raindrops;
    private boolean isRaining;
    private final ArrayList<Image> snowflakes;
    private boolean isSnowing;
    private final Animation<Texture> lightningAnimation;
    private float lightningStateTime;
    private boolean isLightningActive;
    private Image lightningImage;
    private int lightningX;
    private int lightningY;
    private boolean hasTriggeredLightningTransition;
    private final Image lightCircle;

    public GameView(GameController controller, Skin skin) {
        this.date = new Label("", skin);
        date.setFontScale(0.90f);
        date.setColor(34f / 255, 17f / 255, 34f / 255, 1);
        updateDateLabel();

        this.time = new Label("", skin);
        time.setFontScale(0.90f);
        time.setColor(34f / 255, 17f / 255, 34f / 255, 1);
        updateTimeLabel();

        Weather weather = App.getCurrentGame().getGameState().getCurrentWeather();
        Season season = App.getCurrentGame().getGameState().getTime().getSeason();
        this.clock = GameAssetManager.getGameAssetManager().getClock(weather, season);
        this.clockArrow = GameAssetManager.getGameAssetManager().getClockArrow();

        this.isTransitioning = false;
        this.transitionTimer = 0f;
        this.hasTriggeredTransition = false;
        this.lastHour = -1;

        this.inventoryHotbarImage = new Image(GameAssetManager.getGameAssetManager().getInventoryHotbar());
        this.selectedSlotImage = new Image(GameAssetManager.getGameAssetManager().getHotbarSelectedSlot());
        this.selectedSlotIndex = 0;

        this.controller = controller;
        controller.setView(this);

        currentAnimation = playerDownAnimation;
        App.getLoggedIn().setDirection(Direction.DOWN);
        stateTime = 0f;
        isMoving = false;

        playerSprite = new Sprite(GameAssetManager.getGameAssetManager().getIdlePlayer(App.getLoggedIn().getGender(), Direction.DOWN));
        playerSprite.setPosition(
            Gdx.graphics.getWidth() - 40f,
            Gdx.graphics.getHeight()
        );

        this.background = GameAssetManager.getGameAssetManager().getFarm(App.getCurrentGame(), App.getLoggedIn());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.raindrops = new ArrayList<>();
        this.isRaining = false;

        this.snowflakes = new ArrayList<>();
        this.isSnowing = false;

        this.lightningAnimation = GameAssetManager.getGameAssetManager().getLightningAnimation();
        this.lightningStateTime = 0f;
        this.isLightningActive = false;
        this.lightningImage = new Image();
        this.lightningImage.setVisible(false);

        this.lightCircle = new Image(GameAssetManager.getGameAssetManager().getCircle());
        this.lightCircle.setColor(1, 1, 1, 0.1f);
        this.lightCircle.setVisible(false);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        stage.addActor(lightCircle);
        lightCircle.setVisible(false);

        addClock();
        updateClockInfo();
        addInventoryHotbar();
        updateGreenBar();

        int count = 0;
        HashMap<Item, Integer> items = App.getLoggedIn().getBackpack().getItems();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (count > 11) {
                break;
            }

            Image itemImage = new Image(GameAssetManager.getGameAssetManager().getTextureByItem(entry.getKey()));
            itemImage.setPosition(
                ((stage.getWidth() - inventoryHotbarImage.getWidth()) / 2 + 25.0f)
                    + count * (itemImage.getWidth() + 15.0f),
                30.0f
            );
            stage.addActor(itemImage);
            count++;
        }
    }

    @Override
    public void render(float delta) {
        playerUpAnimation = GameAssetManager.getGameAssetManager().getPlayerAnimation(App.getLoggedIn().getGender(), Direction.UP);
        playerDownAnimation = GameAssetManager.getGameAssetManager().getPlayerAnimation(App.getLoggedIn().getGender(), Direction.DOWN);
        playerLeftAnimation = GameAssetManager.getGameAssetManager().getPlayerAnimation(App.getLoggedIn().getGender(), Direction.LEFT);
        playerRightAnimation = GameAssetManager.getGameAssetManager().getPlayerAnimation(App.getLoggedIn().getGender(), Direction.RIGHT);
        playerFaintAnimation = GameAssetManager.getGameAssetManager().getFaintAnimation(App.getLoggedIn().getGender());

        int currentHour = App.getCurrentGame().getGameState().getTime().getHour();
        if (currentHour == 9 && lastHour != 9 && !hasTriggeredTransition) {
            isTransitioning = true;
            transitionTimer = 1f;
            hasTriggeredTransition = true;
        }

        if (currentHour != 9) {
            hasTriggeredTransition = false;
        }
        lastHour = currentHour;

        if (isTransitioning) {
            transitionTimer -= delta;
            if (transitionTimer <= 0) {
                isTransitioning = false;
                transitionTimer = 0;
            }
        }

        float fadeAlpha = 1f;
        if (isTransitioning) {
            float progress = 1f - transitionTimer;
            fadeAlpha = (progress < 0.5f) ? (1f - 2f * progress) : (2f * progress - 1f);
        }

        updateGameLogic(delta);
        ScreenUtils.clear(0, 0, 0, 1f);

        renderGameWorld(fadeAlpha);

        Weather weather = App.getCurrentGame().getGameState().getCurrentWeather();
        if ((weather != Weather.RAINY && weather != Weather.STORM) && isRaining) {
            clearRaindrops();
            isRaining = false;
        }
        if (weather != Weather.SNOW && isSnowing) {
            clearSnowflakes();
            isSnowing = false;
        }

        if (weather == Weather.RAINY || weather == Weather.STORM) {
            if (!isRaining) {
                isRaining = true;
            }
            updateRain();
        }

        if (weather == Weather.SNOW) {
            if (!isSnowing) {
                isSnowing = true;
            }
            updateSnow();
        }

        if (isLightningActive) {
            lightningStateTime += delta;
            Texture frame = lightningAnimation.getKeyFrame(lightningStateTime, false);
            lightningImage.setDrawable(new Image(frame).getDrawable());

            lightningImage.setPosition(
                lightningX - frame.getWidth() / 2f,
                lightningY - frame.getHeight() / 2f
            );

            if (!hasTriggeredLightningTransition) {
                hasTriggeredLightningTransition = true;
            }

            if (lightningAnimation.isAnimationFinished(lightningStateTime)) {
                isLightningActive = false;
                lightningImage.setVisible(false);
                hasTriggeredLightningTransition = false;
            }

            if (lightningImage.getStage() == null) {
                stage.addActor(lightningImage);
            }
        }

        if (currentHour > 16) {
            Main.getBatch().begin();
            Main.getBatch().setColor(0, 0, 0, 0.4f * fadeAlpha);
            Main.getBatch().draw(GameAssetManager.getGameAssetManager().getBlackScreen(),
                0, 0, Gdx.graphics.getWidth() * 5, Gdx.graphics.getHeight() * 5);
            Main.getBatch().end();

            lightCircle.setVisible(true);
            float circleX = playerSprite.getX() + playerSprite.getWidth() / 2 - lightCircle.getWidth() / 2;
            float circleY = playerSprite.getY() + playerSprite.getHeight() / 2 - lightCircle.getHeight() / 2;

            circleX -= camera.position.x - Gdx.graphics.getWidth() / 2f;
            circleY -= camera.position.y - Gdx.graphics.getHeight() / 2f;

            lightCircle.setPosition(circleX, circleY);
        } else {
            lightCircle.setVisible(false);
        }

        renderUI();
    }

    private void updateGameLogic(float delta) {
        isFainting = App.getLoggedIn().getEnergy() == 0;

        if (isFainting) {
            currentAnimation = playerFaintAnimation;
            stateTime += delta;
            if (playerFaintAnimation.isAnimationFinished(stateTime)) {
                App.getLoggedIn().faint();
                return;
            }
            return;
        }

        float displacement = 200f * delta;
        isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerSprite.setY(playerSprite.getY() + displacement);
            currentAnimation = playerUpAnimation;
            App.getLoggedIn().setDirection(Direction.UP);
            walk();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerSprite.setY(playerSprite.getY() - displacement);
            currentAnimation = playerDownAnimation;
            App.getLoggedIn().setDirection(Direction.DOWN);
            walk();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerSprite.setX(playerSprite.getX() - displacement);
            currentAnimation = playerLeftAnimation;
            App.getLoggedIn().setDirection(Direction.LEFT);
            walk();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerSprite.setX(playerSprite.getX() + displacement);
            currentAnimation = playerRightAnimation;
            App.getLoggedIn().setDirection(Direction.RIGHT);
            walk();
        }

        camera.position.set(
            playerSprite.getX() + playerSprite.getWidth() / 2,
            playerSprite.getY() + playerSprite.getHeight() / 2,
            0
        );
        camera.update();
        stateTime += delta;
    }

    private void renderGameWorld(float alpha) {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();
        Main.getBatch().setColor(1, 1, 1, alpha);

        float backgroundWidth = 3 * Gdx.graphics.getWidth();
        float backgroundHeight = backgroundWidth * background.getHeight() / background.getWidth();
        float cameraX = camera.position.x;
        float cameraY = camera.position.y;
        float viewportWidth = camera.viewportWidth;
        float viewportHeight = camera.viewportHeight;

        float startX = cameraX + viewportWidth / 2;
        float startY = cameraY + viewportHeight / 2;

        int tilesX = (int) Math.ceil(viewportWidth / backgroundWidth) + 1;
        int tilesY = (int) Math.ceil(viewportHeight / backgroundHeight) + 1;
        float offsetX = startX - (startX % backgroundWidth);
        float offsetY = startY - (startY % backgroundHeight);

        for (int x = -1; x <= tilesX; x++) {
            for (int y = -1; y <= tilesY; y++) {
                Main.getBatch().draw(
                    background,
                    offsetX + x * backgroundWidth,
                    offsetY + y * backgroundHeight,
                    backgroundWidth,
                    backgroundHeight
                );
            }
        }

        int currentHour = App.getCurrentGame().getGameState().getTime().getHour();
        if (currentHour > 16) {
            Texture circleTexture = GameAssetManager.getGameAssetManager().getCircle();
            float circleX = playerSprite.getX() + playerSprite.getWidth() / 2 - circleTexture.getWidth() / 2f;
            float circleY = playerSprite.getY() + playerSprite.getHeight() / 2 - circleTexture.getHeight() / 2f;

            Main.getBatch().draw(
                circleTexture,
                circleX,
                circleY
            );
        }

        if (isFainting) {
            Texture frame = currentAnimation.getKeyFrame(stateTime, true);
            playerSprite.setRegion(new TextureRegion(frame));
        } else if (isMoving) {
            Texture frame = currentAnimation.getKeyFrame(stateTime, true);
            playerSprite.setRegion(new TextureRegion(frame));
        } else {
            playerSprite.setRegion(new TextureRegion(
                GameAssetManager.getGameAssetManager().getIdlePlayer(App.getLoggedIn().getGender(), App.getLoggedIn().getDirection())
            ));
        }
        playerSprite.draw(Main.getBatch());

        Main.getBatch().end();
        Main.getBatch().setColor(1, 1, 1, 1);
    }

    private void renderUI() {
        if (clockImage != null) clockImage.getColor().a = 1f;
        if (clockArrowImage != null) clockArrowImage.getColor().a = 1f;
        date.getColor().a = 1f;
        time.getColor().a = 1f;
        inventoryHotbarImage.getColor().a = 1f;
        selectedSlotImage.getColor().a = 1f;
        if (energyBar != null) energyBar.getColor().a = 1f;
        if (greenBar != null) greenBar.getColor().a = 1f;

        selectedSlotImage.setPosition(
            ((stage.getWidth() - inventoryHotbarImage.getWidth()) / 2 + 16.0f)
                + selectedSlotIndex * (selectedSlotImage.getWidth() + 1.0f),
            25.0f
        );

        updateGreenBar();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            selectedSlotIndex = keycode - Input.Keys.NUM_1;
        }
        if (keycode >= Input.Keys.NUMPAD_1 && keycode <= Input.Keys.NUMPAD_9) {
            selectedSlotIndex = keycode - Input.Keys.NUMPAD_1;
        }

        if (keycode == Input.Keys.NUM_0 || keycode == Input.Keys.NUMPAD_0) {
            selectedSlotIndex = 9;
        }

        if (keycode == Input.Keys.MINUS) {
            selectedSlotIndex = 10;
        }

        if (keycode == Input.Keys.EQUALS) {
            selectedSlotIndex = 11;
        }

        if (keycode == Input.Keys.E || keycode == Input.Keys.ESCAPE) {
            goToGameMenu(this);
        }

        if (keycode == Input.Keys.Q) {
            goToCheatWindow(this, controller);
        }

        if (keycode == Input.Keys.M) {
            goToMap(this);
        }

        if (keycode == Input.Keys.R) {
            controller.cheatAdvanceTime("1");
            updateClockInfo();
        }

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
        if (amountY >= 0) {
            changeSelectedInventorySlot(1);
        }
        if (amountY <= 0) {
            changeSelectedInventorySlot(-1);
        }
        return false;
    }

    private void addClock() {
        clockImage = new Image(clock);
        float scale = 0.90f;
        clockImage.setScale(scale);
        float xPosition = Gdx.graphics.getWidth() - clockImage.getWidth() * scale - 10;
        float yPosition = Gdx.graphics.getHeight() - clockImage.getHeight() * scale - 10;
        clockImage.setPosition(xPosition, yPosition);
        stage.addActor(clockImage);
    }


    private void updateClockInfo() {
        Weather weather = App.getCurrentGame().getGameState().getCurrentWeather();
        Season season = App.getCurrentGame().getGameState().getTime().getSeason();
        Texture newClock = GameAssetManager.getGameAssetManager().getClock(weather, season);
        clockImage.setDrawable(new Image(newClock).getDrawable());

        float scale = 0.90f;
        float xPosition = Gdx.graphics.getWidth() - clockImage.getWidth() * scale - 10;
        float yPosition = Gdx.graphics.getHeight() - clockImage.getHeight() * scale - 10;

        if (clockArrowImage != null) {
            clockArrowImage.remove();
        }

        clockArrowImage = new Image(clockArrow);
        clockArrowImage.setScale(scale);
        clockArrowImage.setOrigin(
            clockArrowImage.getWidth() / 2,
            clockArrowImage.getHeight()
        );
        clockArrowImage.setPosition(
            xPosition + (0.23f * clockImage.getWidth()),
            yPosition + (0.31f * clockImage.getHeight())
        );
        clockArrowImage.setRotation(getClockArrowDegree());
        stage.addActor(clockArrowImage);

        date.setPosition(
            xPosition + (0.5f * clockImage.getWidth()) - 10,
            yPosition + (0.77f * clockImage.getHeight())
        );
        stage.addActor(date);

        time.setPosition(
            xPosition + (0.5f * clockImage.getWidth()) - 20,
            yPosition + (0.41f * clockImage.getHeight())
        );
        stage.addActor(time);

        updateTimeLabel();
        updateDateLabel();
        updateBalanceLabel(clockImage, xPosition, yPosition, scale);
    }

    private void updateDateLabel() {
        Time currentTime = App.getCurrentGame().getGameState().getTime();
        String weekday = currentTime.getWeekday().getName().substring(0, 3);
        int dayInSeason = currentTime.getDayInSeason();
        date.setText(weekday + ". " + dayInSeason);
    }

    private void updateTimeLabel() {
        int hour = App.getCurrentGame().getGameState().getTime().getHour();
        String text = hour % 12 + ":00 " + (hour < 12 ? "am" : "pm");
        if (text.equals("0:00 pm")) {
            text = "12:00 pm";
        }
        time.setText(text);
    }

    private void updateBalanceLabel(Image clockImage, float xPosition, float yPosition, float scale) {
        int balance = (int) App.getLoggedIn().getBalance();
        boolean started = false;
        for (int i = 7; i >= 0; i--) {
            int digit = balance / ((int) Math.pow(10, i));
            balance %= ((int) Math.pow(10, i));
            if (digit != 0 || i == 0 || started) {
                started = true;
                String digitString = Integer.toString(digit);
                Label digitLabel = new Label(digitString, GameAssetManager.getGameAssetManager().getSkin());
                digitLabel.setColor(128 / 255f, 0, 0, 1);
                digitLabel.setPosition(
                    xPosition + (0.75f * clockImage.getWidth()) - 24 * scale * i,
                    yPosition + (0.025f * clockImage.getHeight())
                );
                stage.addActor(digitLabel);
            }
        }
    }

    private float getClockArrowDegree() {
        float hour = (float) App.getCurrentGame().getGameState().getTime().getHour();
        return -180 * (hour - 9f) / (22f - 9f);
    }

    public void updateGreenBar() {
        if (energyBar != null) {
            energyBar.remove();
        }
        if (greenBar != null) {
            greenBar.remove();
        }

        energyBar = new Image(GameAssetManager.getGameAssetManager().getEnergyBar());
        energyBar.setPosition(
            Gdx.graphics.getWidth() - energyBar.getWidth() - 10.0f,
            10.0f
        );
        stage.addActor(energyBar);

        greenBar = new Image(GameAssetManager.getGameAssetManager().getGreenBar());
        float energyPercentage = App.getLoggedIn().getEnergy() / 200f;
        greenBar.setHeight(energyPercentage * 0.72f * energyBar.getHeight());
        greenBar.setPosition(
            Gdx.graphics.getWidth() - energyBar.getWidth() + 1.0f,
            20.0f
        );
        stage.addActor(greenBar);
    }

    public void addInventoryHotbar() {
        inventoryHotbarImage.setPosition((stage.getWidth() - inventoryHotbarImage.getWidth()) / 2, 10);
        stage.addActor(inventoryHotbarImage);
        stage.addActor(selectedSlotImage);
    }

    private void changeSelectedInventorySlot(int amount) {
        selectedSlotIndex += amount;
        if (selectedSlotIndex > 11) {
            selectedSlotIndex = 0;
        }
        if (selectedSlotIndex < 0) {
            selectedSlotIndex = 11;
        }
    }

    public void walk() {
        isMoving = true;
        App.getLoggedIn().decreaseEnergyBy((int) (0.05f * App.getLoggedIn().getEnergy()));
        System.out.println(App.getLoggedIn().getEnergy());
    }

    private void clearRaindrops() {
        for (Image raindrop : raindrops) {
            raindrop.remove();
        }
        raindrops.clear();
    }

    private void updateRain() {
        clearRaindrops();
        int raindropCount = randomIntBetween(100, 150);
        for (int i = 0; i < raindropCount; i++) {
            Image raindrop = new Image(GameAssetManager.getGameAssetManager().getRaindrop());
            raindrop.setPosition(
                randomIntBetween(0, Gdx.graphics.getWidth()),
                randomIntBetween(0, Gdx.graphics.getHeight())
            );
            stage.addActor(raindrop);
            raindrops.add(raindrop);
        }
    }

    private void clearSnowflakes() {
        for (Image snowflake : snowflakes) {
            snowflake.remove();
        }
        snowflakes.clear();
    }

    private void updateSnow() {
        clearSnowflakes();
        int snowflakeCount = randomIntBetween(100, 150);
        for (int i = 0; i < snowflakeCount; i++) {
            Image snowflake = new Image(GameAssetManager.getGameAssetManager().getSnowflake());
            snowflake.setPosition(
                randomIntBetween(0, Gdx.graphics.getWidth()),
                randomIntBetween(0, Gdx.graphics.getHeight())
            );
            stage.addActor(snowflake);
            snowflakes.add(snowflake);
        }
    }

    public void setLightningPosition(Position position) {
        this.lightningX = position.getX();
        this.lightningY = position.getY() + (int) lightningImage.getHeight() / 2;
        this.isLightningActive = true;
        this.lightningStateTime = 0f;

        if (lightningImage.getStage() != null) {
            lightningImage.remove();
        }

        Texture firstFrame = lightningAnimation.getKeyFrame(0);
        lightningImage = new Image(firstFrame);
        lightningImage.setVisible(true);

        lightningImage.setPosition(
            lightningX - firstFrame.getWidth() / 2f,
            Gdx.graphics.getHeight() - lightningY - firstFrame.getHeight() / 2f
        );

        stage.addActor(lightningImage);
    }

    public int randomIntBetween(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
