package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.types.CraftType;
import com.ap_project.common.models.enums.types.FarmBuildingType;
import com.ap_project.common.models.enums.types.ItemType;
import com.ap_project.common.models.enums.types.MineralType;
import com.ap_project.common.models.farming.ForagingCrop;
import com.ap_project.common.models.farming.Tree;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class FarmOverviewView implements Screen, InputProcessor {
    private Stage stage;
    protected static final float TILE_SIZE = 15.2f;
    protected Position originPosition = new Position(0, 0);
    protected Texture tileMarkerTexture;
    private final float scale;
    private final Image farmImage;
    private final Label descriptionLabel;
    private final Farm farm;
    private final Image itemImage;
    private final ItemType itemType;
    private final GameView gameView;
    private final Label positionLabel;
    private final Label errorMessageLabel;
    private final GameController controller;

    public FarmOverviewView(String description, ItemType itemType, GameView gameView) {
        this.tileMarkerTexture = GameAssetManager.getGameAssetManager().getWhiteScreen();

        this.farmImage = new Image(GameAssetManager.getGameAssetManager().getFarm(App.getCurrentGame(), App.getLoggedIn()));

        this.scale = 0.65f * Gdx.graphics.getWidth() / farmImage.getWidth();

        farmImage.setSize(farmImage.getWidth() * scale, farmImage.getHeight() * scale);
        farmImage.setPosition(
            (Gdx.graphics.getWidth() - farmImage.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - farmImage.getHeight()) / 2f - 40
        );

        this.descriptionLabel = new Label(description, GameAssetManager.getGameAssetManager().getSkin());
        descriptionLabel.setPosition(
            (Gdx.graphics.getWidth() - descriptionLabel.getWidth()) / 2f,
            Gdx.graphics.getHeight() - 50
        );

        this.farm = App.getLoggedIn().getFarm();

        this.itemType = itemType;
        if (itemType instanceof FarmBuildingType) {
            this.itemImage = new Image(GameAssetManager.getGameAssetManager().getFarmBuilding((FarmBuildingType) itemType));
        } else if (itemType instanceof CraftType) {
            this.itemImage = new Image(GameAssetManager.getGameAssetManager().getCraftingItemTexture(itemType.getName()));
        } else {
            this.itemImage = new Image(); // TODO
        }

        this.positionLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        positionLabel.setPosition(50, 50);

        this.errorMessageLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

        this.gameView = gameView;
        this.controller = new GameController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(farmImage);
        stage.addActor(descriptionLabel);

        Image cabinImage = new Image(GameAssetManager.getGameAssetManager().getCabin());
        addImage(cabinImage, farm.getCabin().getPosition(), scale);

        Image lakeImage = new Image(GameAssetManager.getGameAssetManager().getLake(farm.getMapNumber()));
        addImage(lakeImage, farm.getLake().getPosition(), scale);

        Image greenhouseImage = new Image(GameAssetManager.getGameAssetManager().getGreenhouse(farm.getGreenhouse().canEnter()));
        addImage(greenhouseImage, farm.getGreenhouse().getPosition(), scale);

        ArrayList<Image> foragingCropsImages = new ArrayList<>();
        for (ForagingCrop foragingCrop : farm.getForagingCrops()) {
            foragingCropsImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByForagingCrop(foragingCrop)));
        }
        for (int i = 0; i < farm.getForagingCrops().size(); i++) {
            Position position = farm.getForagingCrops().get(i).getPosition();
            addImage(foragingCropsImages.get(i), position, 0.3f);
        }

        for (int i = 0; i < farm.getStones().size(); i++) {
            Image stoneImage = new Image(GameAssetManager.getGameAssetManager().getTextureByMineral(new Mineral(MineralType.STONE)));
            Position position = farm.getStones().get(i).getPosition();
            addImage(stoneImage, position, 0.3f);
        }

        for (int i = 0; i < farm.getWoodLogs().size(); i++) {
            Image woodImage = new Image(GameAssetManager.getGameAssetManager().getWood());
            Position position = farm.getWoodLogs().get(i).getPosition();
            addImage(woodImage, position, 0.3f);
        }

        ArrayList<Image> treesImages = new ArrayList<>();
        for (Tree tree : farm.getTrees()) {
            treesImages.add(new Image(GameAssetManager.getGameAssetManager().getTextureByTree(tree)));
        }
        for (int i = 0; i < farm.getTrees().size(); i++) {
            Position position = farm.getTrees().get(i).getPosition();
            addImage(treesImages.get(i), position, 0.35f);
        }

        ArrayList<Image> farmBuildingsImages = new ArrayList<>();
        for (FarmBuilding farmBuilding : farm.getFarmBuildings()) {
            farmBuildingsImages.add(new Image(GameAssetManager.getGameAssetManager().getFarmBuilding(farmBuilding.getFarmBuildingType())));
        }
        for (int i = 0; i < farm.getFarmBuildings().size(); i++) {
            Position position = new Position(farm.getFarmBuildings().get(i).getPositionOfUpperLeftCorner());
            position.setY(position.getY() + farm.getFarmBuildings().get(i).getLength() * (int) TILE_SIZE);
            addImage(farmBuildingsImages.get(i), position, scale);
        }

        ArrayList<Image> animalsImages = new ArrayList<>();
        for (Animal animal : farm.getAllFarmAnimals()) {
            animalsImages.add(new Image(GameAssetManager.getGameAssetManager().getAnimal(animal.getAnimalType())));
        }
        for (int i = 0; i < farm.getAllFarmAnimals().size(); i++) {
            Position position = new Position(farm.getAllFarmAnimals().get(i).getPosition());
            addImage(animalsImages.get(i), position, 0.3f);
        }

        for (Craft craft : farm.getCrafts()) {
            CraftType craftType = craft.getCraftType();
            addImage(
                new Image(GameAssetManager.getGameAssetManager().getCraftingItemTexture(craftType.getName())),
                craft.getPosition(),
                0.3f);
        }

        stage.addActor(positionLabel);
        stage.addActor(errorMessageLabel);
    }

    @Override
    public void render(float delta) {
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
        if (keycode == Input.Keys.ESCAPE) {
            if (gameView instanceof FarmView) {
                FarmView farmView = (FarmView) gameView;
                farmView.updateTextures();
            }
            Main.getMain().setScreen(gameView);
            return true;
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
        Position position = new Position(
            (screenX - 355) / (int) TILE_SIZE,
            (screenY - 155) / (int) TILE_SIZE
        );

        Tile tile = farm.getTileByPosition(position);
        if (tile == null) {
            errorMessageLabel.setText("Choose a position inside the farm.");
        } else {
            errorMessageLabel.setText("");
        }

        Result result = new Result(false, "");
        if (itemType instanceof FarmBuildingType) {
            FarmBuildingType farmBuildingType = (FarmBuildingType) itemType;
            result = controller.build(farmBuildingType.getName(), position);
        } else if (itemType instanceof CraftType) {
            CraftType craftType = (CraftType) itemType;
//            result = controller.craft(craftType.getName()); todo
            result = new Result(true, "");

            farm.addCraftToFarm(craftType, position);

        }
        if (!result.success) {
            errorMessageLabel.setText(result.message);
        } else {
            if (gameView instanceof FarmView) {
                FarmView farmView = (FarmView) gameView;
                farmView.updateTextures();
            }
            Main.getMain().setScreen(gameView);
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
        if (itemType instanceof CraftType) itemImage.setScale(0.3f);
        itemImage.setPosition(
            screenX,
            Gdx.graphics.getHeight() - screenY - itemImage.getHeight() * itemImage.getScaleY()
        );
        stage.addActor(itemImage);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private void addImage(Image image, Position position, float scale) {
        image.setPosition(
            355 + position.getX() * TILE_SIZE,
            816 - position.getY() * TILE_SIZE
        );
        image.setSize(scale * image.getWidth(), scale * image.getHeight());
        stage.addActor(image);
    }
}
