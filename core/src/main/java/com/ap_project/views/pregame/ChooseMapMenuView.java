package com.ap_project.views.pregame;

import com.ap_project.Main;
import com.ap_project.controllers.GameController;
import com.ap_project.controllers.pregame.ChooseMapMenuController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.User;
import com.ap_project.views.FarmView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.Main.goToGame;

public class ChooseMapMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final Label description;
    private final Label mapNumber;
    private final SelectBox<String> mapOptions;
    private final TextButton chooseButton;
    private final Table table;
    private final ChooseMapMenuController controller;
    private int currentPlayerIndex;
    private final ArrayList<User> players;

    public ChooseMapMenuView(ChooseMapMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.menuTitle = new Label("Choose Map", skin);
        menuTitle.setFontScale(1.5f);

        this.players = App.getCurrentGame().getPlayers();
        this.description = new Label(players.get(currentPlayerIndex).getUsername() + ", please choose a map for your farm", skin);
        description.setFontScale(1.5f);

        this.mapNumber = new Label("Map Number", skin);
        this.mapNumber.setFontScale(1.5f);
        this.mapOptions = new SelectBox<>(skin);

        this.chooseButton = new TextButton("Choose", skin);

        this.table = new Table();

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        Array<String> numbers = new Array<>();
        numbers.add("1");
        numbers.add("2");
        mapOptions.setItems(numbers);

        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(description).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(mapNumber).align(Align.right).padBottom(20).padRight(20);
        table.add(mapOptions).width(750).padBottom(20).row();

        table.setPosition(table.getX(), table.getY() + 50);
        stage.addActor(table);

        chooseButton.setPosition(800, 320);
        stage.addActor(chooseButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleChooseMapMenuButtons();
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

    public SelectBox<String> getMapOptions() {
        return mapOptions;
    }

    public TextButton getChooseButton() {
        return chooseButton;
    }

    public void nextTurn() {
        if (currentPlayerIndex != players.size() - 1) {
            currentPlayerIndex++;
            description.setText(players.get(currentPlayerIndex).getUsername() + ", please choose a map for your farm");
            mapOptions.setSelectedIndex(0);
            return;
        }
        goToGame(new FarmView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
