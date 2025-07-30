package com.ap_project.views.game;

import com.ap_project.Main;
import com.ap_project.controllers.GameController;
import com.ap_project.models.*;
import com.ap_project.models.farming.ForagingCrop;
import com.ap_project.models.farming.Tree;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import static com.ap_project.Main.goToFarmHouse;

public class FarmView extends GameView {
    private final Texture cabinTexture;
    private Texture lakeTexture;
    private Texture greenhouseTexture;
    private final ArrayList<Texture> foragingCropsTextures;
    private final Texture stoneTexture;
    private final Texture woodTexture;
    private final ArrayList<Texture> treesTextures;
    private final ArrayList<Texture> farmbuildingsTextures;
    private final ArrayList<Texture> animalsTextures;
    private Farm farm;

    public FarmView(GameController controller, Skin skin) {
        super(controller, skin);

        this.background = GameAssetManager.getGameAssetManager().getFarm(App.getCurrentGame(), App.getLoggedIn());

        this.farm = App.getLoggedIn().getFarm();
        this.cabinTexture = GameAssetManager.getGameAssetManager().getCabin();
        this.lakeTexture = GameAssetManager.getGameAssetManager().getLake(farm.getMapNumber());
        this.greenhouseTexture = GameAssetManager.getGameAssetManager().getGreenhouse(farm.getGreenhouse().canEnter());

        this.foragingCropsTextures = new ArrayList<>();
        for (ForagingCrop foragingCrop : farm.getforagingCrops()) {
            foragingCropsTextures.add(GameAssetManager.getGameAssetManager().getTextureByForagingCrop(foragingCrop));
        }

        this.stoneTexture =GameAssetManager.getGameAssetManager().getTextureByMineral(new Mineral(null));

        this.woodTexture = GameAssetManager.getGameAssetManager().getWood();

        this.treesTextures = new ArrayList<>();
        for (Tree tree : farm.getTrees()) {
            treesTextures.add(GameAssetManager.getGameAssetManager().getTextureByTree(tree));
        }

        this.farmbuildingsTextures = new ArrayList<>();
        for (FarmBuilding farmBuilding : farm.getFarmBuildings()) {
            farmbuildingsTextures.add(GameAssetManager.getGameAssetManager().getFarmBuilding(farmBuilding.getFarmBuildingType()));
        }

        this.animalsTextures = new ArrayList<>();
        for (Animal animal : farm.getAllFarmAnimals()) {
            animalsTextures.add(GameAssetManager.getGameAssetManager().getAnimal(animal.getAnimalType()));
        }
    }

    @Override
    public void renderMap() {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        draw(cabinTexture, farm.getCabin().getPosition());
        draw(lakeTexture, farm.getLake().getPosition());
        draw(greenhouseTexture, farm.getGreenhouse().getPosition());

        try{
            scale = 1f;
            for (int i = 0; i < foragingCropsTextures.size(); i++) {
                Position position = farm.getforagingCrops().get(i).getPosition();
                draw(foragingCropsTextures.get(i), position);
            }

            for (Mineral mineral : farm.getStones()) {
                Position position = mineral.getPosition();
                draw(stoneTexture, position);
            }

            scale = 1.5f;
            for (Tile tile : farm.getWoodLogs()) {
                Position position = tile.getPosition();
                draw(woodTexture, position);
            }

            scale = 2;
            for (int i = 0; i < farm.getTrees().size(); i++) {
                Position position = farm.getTrees().get(i).getPosition();
                draw(treesTextures.get(i), position);
            }

            for (int i = 0; i < farm.getFarmBuildings().size(); i++) {
                Position position = farm.getFarmBuildings().get(i).getPositionOfUpperLeftCorner();
                position.setY(position.getY() + farm.getFarmBuildings().get(i).getWidth());
                draw(farmbuildingsTextures.get(i), position);
            }

            scale = 1.25f;
            for (int i = 0; i < farm.getAllFarmAnimals().size(); i++) {
                Position position = farm.getAllFarmAnimals().get(i).getPosition();
                draw(animalsTextures.get(i), position);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        scale = 4.400316f;

        Main.getBatch().end();
    }

    @Override
    public void nextTurn() {
        playerSprite.setPosition(
            (App.getLoggedIn().getPosition().getX()) * TILE_SIZE,
            (-App.getLoggedIn().getPosition().getY() + originPosition.getY()) * TILE_SIZE
        );
        this.farm = App.getLoggedIn().getFarm();
        lakeTexture = GameAssetManager.getGameAssetManager().getLake(farm.getMapNumber());
        greenhouseTexture = GameAssetManager.getGameAssetManager().getGreenhouse(farm.getGreenhouse().canEnter());
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);

        if (keycode == Input.Keys.H) {
            goToFarmHouse(this);
        }
        return false;
    }
}
