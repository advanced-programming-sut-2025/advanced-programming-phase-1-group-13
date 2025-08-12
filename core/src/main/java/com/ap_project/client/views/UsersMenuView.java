package com.ap_project.client.views;

import com.ap_project.common.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

import static com.ap_project.Main.goToPreGameMenu;

public class UsersMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Table table;
    private final TextButton backButton;
    private final ScrollPane userListScroll;
    private final VerticalGroup userList;

    public UsersMenuView( Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image backgroundImage = new Image(GameAssetManager.getGameAssetManager().getMenuBackground());
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table = new Table();
        table.setFillParent(true);
        table.top().padTop(30);


        backButton = new TextButton("Back", skin);

        userList = new VerticalGroup();
        userList.top().left().columnLeft();
        userListScroll = new ScrollPane(userList, skin);
        userListScroll.setFadeScrollBars(false);


        layoutUI();
        addListeners();
    }



    private void layoutUI() {
        Label title = new Label("Users List", skin);
        title.setFontScale(1.6f);
        table.add(title).colspan(2).padBottom(10).row();


        table.add(backButton).colspan(1).padTop(600);

        stage.addActor(table);

        userListScroll.setSize(1500, 250);
        userListScroll.setPosition(
            Gdx.graphics.getWidth() / 2f - 700,
            Gdx.graphics.getHeight() / 2f - 350
        );
        stage.addActor(userListScroll);


    }

    private void addListeners() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToPreGameMenu();
            }
        });
    }

    public void updateUserList(List<String> users) {
        userList.clear();

        for (String rawLobbyInfo : users) {
            String[] parts = rawLobbyInfo.split(",");
            if (parts.length < 4) continue;

            String name = parts[1];
            String players = parts[2];

            HorizontalGroup row = new HorizontalGroup();
            row.space(20);
            row.space(20);
            row.left();
            row.left();


            userList.addActor(row);
        }
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

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
}
