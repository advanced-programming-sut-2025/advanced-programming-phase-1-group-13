package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.types.AnimalType;
import com.ap_project.common.models.enums.types.FarmBuildingType;
import com.ap_project.common.models.farming.ForagingCrop;
import com.ap_project.common.models.farming.Tree;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import static com.ap_project.Main.*;

public class FarmView extends GameView {
    private final Texture cabinTexture;
    private Texture lakeTexture;
    private Texture greenhouseTexture;
    private final ArrayList<Texture> foragingCropsTextures;
    private final Texture stoneTexture;
    private final Texture woodTexture;
    private final ArrayList<Texture> treesTextures;
    private final ArrayList<Texture> farmBuildingsTextures;
    private final ArrayList<Texture> animalsTextures;

    private final Animation<Texture> pettingAnimation;
    private boolean isPetting;
    private Animal animalBeingPet;
    private Texture pettingAnimationFrame;
    private float pettingAnimationTime;

    private final Animation<Texture> feedingAnimation;
    private boolean isFeeding;
    private Texture feedingAnimationFrame;
    private float feedingAnimationTime;

    private Animal walkingAnimal;
    private Animation<Texture> animalAnimation;
    private float animalAnimationTimer = 0f;
    private Texture currentWalkingAnimalFrame;
    private Vector2 walkingAnimalPosition;
    private Direction walkingAnimalDirection;
    private Vector2 animalDestination;
    private Position animalDestinationPosition;
    private boolean isAnimalWalking;
    private boolean setInsideWhenReachingDestination;

    private Farm farm;

    public FarmView(GameController controller, Skin skin) {
        super(controller, skin);

        this.background = GameAssetManager.getGameAssetManager().getFarm(App.getCurrentGame(), App.getLoggedIn());

        this.farm = App.getLoggedIn().getFarm();
        this.cabinTexture = GameAssetManager.getGameAssetManager().getCabin();
        this.lakeTexture = GameAssetManager.getGameAssetManager().getLake(farm.getMapNumber());
        this.greenhouseTexture = GameAssetManager.getGameAssetManager().getGreenhouse(farm.getGreenhouse().canEnter());

        this.foragingCropsTextures = new ArrayList<>();
        for (ForagingCrop foragingCrop : farm.getForagingCrops()) {
            foragingCropsTextures.add(GameAssetManager.getGameAssetManager().getTextureByForagingCrop(foragingCrop));
        }

        this.stoneTexture = GameAssetManager.getGameAssetManager().getTextureByMineral(new Mineral(null));

        this.woodTexture = GameAssetManager.getGameAssetManager().getWood();

        this.treesTextures = new ArrayList<>();
        for (Tree tree : farm.getTrees()) {
            treesTextures.add(GameAssetManager.getGameAssetManager().getTextureByTree(tree));
        }

        this.farmBuildingsTextures = new ArrayList<>();
        for (FarmBuilding farmBuilding : farm.getFarmBuildings()) {
            farmBuildingsTextures.add(GameAssetManager.getGameAssetManager().getFarmBuilding(farmBuilding.getFarmBuildingType()));
        }

        this.animalsTextures = new ArrayList<>();
        for (Animal animal : farm.getAllFarmAnimals()) {
            animalsTextures.add(GameAssetManager.getGameAssetManager().getAnimal(animal.getAnimalType()));
        }

        this.pettingAnimation = GameAssetManager.getGameAssetManager().getPettingAnimation();
        this.animalBeingPet = null;
        this.isPetting = false;
        this.pettingAnimationFrame = pettingAnimation.getKeyFrame(0);

        this.feedingAnimation = GameAssetManager.getGameAssetManager().getFeedingAnimation();
        this.animalBeingPet = null;
        this.isFeeding = false;
        this.feedingAnimationFrame = feedingAnimation.getKeyFrame(0);

        this.walkingAnimalPosition = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (isAnimalWalking) {
            animalAnimationTimer += delta;
            currentWalkingAnimalFrame = animalAnimation.getKeyFrame(animalAnimationTimer, true);
        }
    }

    @Override
    public void renderMap(float delta) {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        moveAnimal();

        if (isPetting) {
            float tempScale = scale;
            scale = 1;
            pettingAnimationTime += delta;

            if (pettingAnimation.isAnimationFinished(pettingAnimationTime)) {
                isPetting = false;
                animalBeingPet = null;
            } else {
                pettingAnimationFrame = pettingAnimation.getKeyFrame(pettingAnimationTime, false);

                if (animalBeingPet != null) {
                    Position position = new Position(
                        animalBeingPet.getPosition().getX(),
                        animalBeingPet.getPosition().getY() - 1
                    );
                    draw(pettingAnimationFrame, position);
                }
            }
            scale = tempScale;
        }

        if (isFeeding) {
            float tempScale = scale;
            scale = 1;
            feedingAnimationTime += delta;

            if (feedingAnimation.isAnimationFinished(feedingAnimationTime)) {
                isFeeding = false;
                animalBeingPet = null;
            } else {
                feedingAnimationFrame = feedingAnimation.getKeyFrame(feedingAnimationTime, false);

                if (animalBeingPet != null) {
                    Position position = new Position(
                        animalBeingPet.getPosition().getX(),
                        animalBeingPet.getPosition().getY() - 1
                    );
                    draw(feedingAnimationFrame, position);
                }
            }
            scale = tempScale;
        }

        draw(cabinTexture, farm.getCabin().getPosition());
        draw(lakeTexture, farm.getLake().getPosition());
        draw(greenhouseTexture, farm.getGreenhouse().getPosition());

        try {
            scale = 1f;
            for (int i = 0; i < foragingCropsTextures.size(); i++) {
                Position position = farm.getForagingCrops().get(i).getPosition();
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

            scale = 1.4f;
            for (int i = 0; i < farm.getAllFarmAnimals().size(); i++) {
                Animal animal = farm.getAllFarmAnimals().get(i);
                Position position = animal.getPosition();
                if (animal.equals(walkingAnimal) && isAnimalWalking) continue;
                if (animal.isOutside()) draw(animalsTextures.get(i), position);
            }

            scale = 4.400316f;
            if (isAnimalWalking) {
                draw(currentWalkingAnimalFrame, walkingAnimalPosition);
            }

            for (int i = 0; i < farm.getFarmBuildings().size(); i++) {
                Position position = new Position(farm.getFarmBuildings().get(i).getPositionOfUpperLeftCorner());
                position.setY(position.getY() + farm.getFarmBuildings().get(i).getLength());
                draw(farmBuildingsTextures.get(i), position);
            }

            scale = 2;
            for (int i = 0; i < farm.getTrees().size(); i++) {
                Position position = farm.getTrees().get(i).getPosition();
                draw(treesTextures.get(i), position);
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

        if (keycode == Input.Keys.P) { // TODO: move to carpenter's shop
            FarmBuildingType farmBuildingType = FarmBuildingType.COOP;
            goToFarmOverview("Choose the position of the " + farmBuildingType.getName(), farmBuildingType, this); // TODO
        }

        if (keycode == Input.Keys.I) { // TODO: move to Marnie's ranch
            goToBuyAnimalsMenu(this);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        if (button == Input.Buttons.RIGHT) {
            for (int i = 0; i < farm.getAllFarmAnimals().size(); i++) {
                Animal animal = farm.getAllFarmAnimals().get(i);
                float scale = (animal.getAnimalType() == AnimalType.PIG) ? 3f : 1.25f;
                if (clickedOnTexture(screenX, screenY, animalsTextures.get(i), animal.getPosition(), scale)) {
                    goToAnimalMenu(this, animal);
                    return true;
                }
            }
        }

        if (clickedOnTexture(screenX, screenY, cabinTexture, farm.getCabin().getPosition(), 1f)) {
            goToFarmHouse(this);
            return true;
        }

        if (clickedOnTexture(screenX, screenY, greenhouseTexture, farm.getGreenhouse().getPosition(), 1f)) {
            Result result = controller.buildGreenhouse();
            if (result.success) greenhouseTexture = GameAssetManager.getGameAssetManager().getGreenhouse(farm.getGreenhouse().canEnter());
            return true;
        }


        for (int i = 0; i < farm.getFarmBuildings().size(); i++) {
            FarmBuilding farmBuilding = farm.getFarmBuildings().get(i);
            Position farmBuildingPosition = new Position(farmBuilding.getPositionOfUpperLeftCorner());
            if (clickedOnTexture(screenX, screenY, farmBuildingsTextures.get(i), farmBuildingPosition, scale)) {
                if (farmBuilding.getFarmBuildingType().getCapacity() != 0) {
                    AnimalLivingSpace animalLivingSpace = (AnimalLivingSpace) farmBuilding;
                    goToAnimalLivingSpaceMenu(this, animalLivingSpace);
                }
                return true;
            }
        }
        return false;
    }

    public void startPettingAnimation(Animal animal) {
        isPetting = true;
        animalBeingPet = animal;
        pettingAnimationTime = 0;
    }

    public void startFeedingAnimation(Animal animal) {
        isFeeding = true;
        animalBeingPet = animal;
        feedingAnimationTime = 0;
    }

    public void startWalkingAnimation(Animal animal, boolean goingInside) {
        isAnimalWalking = true;
        setInsideWhenReachingDestination = goingInside;
        animalAnimationTimer = 0;
        walkingAnimal = animal;
        if (animalDestination.x >= walkingAnimal.getPosition().getX() * TILE_SIZE)
            walkingAnimalDirection = Direction.RIGHT;
        else walkingAnimalDirection = Direction.LEFT;
        this.animalAnimation = GameAssetManager.getGameAssetManager().loadAnimalAnimation(animal.getAnimalType().getName(), walkingAnimalDirection.toString());
        walkingAnimalPosition = new Vector2(
            walkingAnimal.getPosition().getX() * TILE_SIZE,
            walkingAnimal.getPosition().getY() * TILE_SIZE
        );
        currentWalkingAnimalFrame = animalAnimation.getKeyFrame(animalAnimationTimer, true);
    }

    public Farm getFarm() {
        return farm;
    }

    public ArrayList<Texture> getAnimalsTextures() {
        return animalsTextures;
    }

    public void moveAnimal() {
        float displacement = 1f;
        if (walkingAnimalDirection == Direction.RIGHT) {
            walkingAnimalPosition.x += displacement;
            if (animalDestination.x <= walkingAnimalPosition.x) {
                if (animalDestination.y > walkingAnimalPosition.y) walkingAnimalDirection = Direction.DOWN;
                else walkingAnimalDirection = Direction.UP;
                animalAnimation = GameAssetManager.getGameAssetManager().loadAnimalAnimation(walkingAnimal.getAnimalType().getName(), walkingAnimalDirection.toString());
            }
        }
        if (walkingAnimalDirection == Direction.LEFT) {
            walkingAnimalPosition.x -= displacement;
            if (animalDestination.x >= walkingAnimalPosition.x) {
                if (animalDestination.y > walkingAnimalPosition.y) walkingAnimalDirection = Direction.DOWN;
                else walkingAnimalDirection = Direction.UP;
                animalAnimation = GameAssetManager.getGameAssetManager().loadAnimalAnimation(walkingAnimal.getAnimalType().getName(), walkingAnimalDirection.toString());
            }
        }
        if (walkingAnimalDirection == Direction.UP) {
            walkingAnimalPosition.y -= displacement;
            if (animalDestination.y >= walkingAnimalPosition.y) {
                walkingAnimal.setPosition(animalDestinationPosition);
                isAnimalWalking = false;
                if (setInsideWhenReachingDestination) walkingAnimal.setOutside(false);
            }
        }
        if (walkingAnimalDirection == Direction.DOWN) {
            walkingAnimalPosition.y += displacement;
            if (animalDestination.y <= walkingAnimalPosition.y) {
                walkingAnimal.setPosition(animalDestinationPosition);
                isAnimalWalking = false;
                if (setInsideWhenReachingDestination) walkingAnimal.setOutside(false);
            }
        }
    }

    public void setAnimalDestination(Vector2 animalDestination, Position position) {
        this.animalDestination = animalDestination;
        animalDestination.x *= TILE_SIZE;
        animalDestination.y *= TILE_SIZE;
        this.animalDestinationPosition = position;
    }
}
