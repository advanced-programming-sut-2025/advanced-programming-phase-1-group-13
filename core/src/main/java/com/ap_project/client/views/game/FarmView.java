package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.Quality;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.environment.Weather;
import com.ap_project.common.models.enums.types.*;
import com.ap_project.common.models.farming.Crop;
import com.ap_project.common.models.farming.ForagingCrop;
import com.ap_project.common.models.farming.Tree;
import com.ap_project.common.models.tools.FishingRod;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import static com.ap_project.Main.*;

public class FarmView extends GameView {
    private final Texture cabinTexture;
    private Texture lakeTexture;
    private Texture greenhouseTexture;
    private ArrayList<Texture> foragingCropsTextures;
    private Texture stoneTexture;
    private Texture woodTexture;
    private ArrayList<Texture> treesTextures;
    private ArrayList<Texture> farmBuildingsTextures;
    private ArrayList<Texture> shippingBinsTextures;
    private ArrayList<Texture> craftsTextures;
    private ArrayList<Texture> artisansTextures;
    private ArrayList<Texture> animalsTextures;
    private ArrayList<Texture> cropsTextures;
    private ArrayList<Texture> plantedTreesTextures;

    private Artisan artisanWithMenu;
    private Texture optionsMenu;
    private final Texture getItemButton;
    private Vector2 getItemButtonPosition;
    private final Texture informationButton;
    private Vector2 informationButtonPosition;
    private final Texture cancelButton;
    private Vector2 cancelButtonPosition;
    private final Texture cheatButton;
    private Vector2 cheatButtonPosition;

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
    private Image fishingRodImage;

    private Farm farm;

    public FarmView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getFarm(App.getCurrentGame(), App.getLoggedIn());

        this.farm = App.getLoggedIn().getFarm();
        this.cabinTexture = GameAssetManager.getGameAssetManager().getCabin();
        this.lakeTexture = GameAssetManager.getGameAssetManager().getLake(farm.getMapNumber());
        this.greenhouseTexture = GameAssetManager.getGameAssetManager().getGreenhouse(farm.getGreenhouse().canEnter());

        this.fishingRodImage = null;

        this.optionsMenu = null;

        updateTextures();

        this.getItemButton = GameAssetManager.getGameAssetManager().getGetItemButton();
        this.informationButton = GameAssetManager.getGameAssetManager().getInformationButton();
        this.cancelButton = GameAssetManager.getGameAssetManager().getCancelButton();
        this.cheatButton = GameAssetManager.getGameAssetManager().getCheatButton();

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

            for (int i = 0; i < farm.getPlantedCrops().size(); i++) {
                Crop crop = farm.getPlantedCrops().get(i);
                Position position = crop.getPosition();
                draw(cropsTextures.get(i), position);
            }

            scale = 1.4f;
            for (int i = 0; i < farm.getAllFarmAnimals().size(); i++) {
                Animal animal = farm.getAllFarmAnimals().get(i);
                Position position = animal.getPosition();
                if (animal.equals(walkingAnimal) && isAnimalWalking) continue;
                if (animal.isOutside()) draw(animalsTextures.get(i), position);
            }

            if (App.getCurrentGame().getGameState().getTime().getHour() == 9) {
                for (Position position : farm.getCrows()) {
                    draw(GameAssetManager.getGameAssetManager().getCrow(), position);
                }
            }

            scale = 4.400316f;
            if (isAnimalWalking) {
                draw(currentWalkingAnimalFrame, walkingAnimalPosition);
            }

            for (int i = 0; i < farm.getFarmBuildings().size(); i++) {
                Position position = new Position(farm.getFarmBuildings().get(i).getPositionOfUpperLeftCorner());
                position.setY(position.getY() + farm.getFarmBuildings().get(i).getLength());
                if (farm.getFarmBuildings().get(i).getFarmBuildingType() == FarmBuildingType.SHIPPING_BIN) scale = 1;
                else scale = 4.400316f;
                draw(farmBuildingsTextures.get(i), position);
            }

            scale = 1.5f;
            for (int i = 0; i < farm.getCrafts().size(); i++) {
                if (!farm.getCrafts().get(i).getCraftType().isArtisan()) {
                    Position position = farm.getCrafts().get(i).getPosition();
                    draw(craftsTextures.get(i), position);
                }
            }

            scale = 1;
            for (int i = 0; i < farm.getShippingBins().size(); i++) {
                Position position = farm.getShippingBins().get(i).getPositionOfUpperLeftCorner();
                draw(shippingBinsTextures.get(i), position);
            }

            scale = 1.5f;
            for (int i = 0; i < farm.getArtisans().size(); i++) {
                Position position = farm.getArtisans().get(i).getPosition();
                draw(artisansTextures.get(i), position);
                Artisan artisan = farm.getArtisans().get(i);
                if (artisan.getItemPending() != null) {
                    Texture progressBarWindow = GameAssetManager.getGameAssetManager().getProgressBarWindow();
                    Vector2 progressBarWindowPosition = new Vector2(
                        position.getX() * TILE_SIZE - 35,
                        (position.getY() - 2) * TILE_SIZE
                    );
                    scale = 0.75f;
                    draw(progressBarWindow, progressBarWindowPosition);

                    Texture progressBar = GameAssetManager.getGameAssetManager().getProgressBar();
                    Vector2 progressBarPosition = new Vector2(
                        position.getX() * TILE_SIZE - 30,
                        (position.getY() - 2) * TILE_SIZE
                    );
                    float width = (1 - (float) artisan.getTimeLeft() / artisan.getTotalTime()) * 140;
                    drawProgressBar(progressBar, progressBarPosition, width);

                    scale = 1.5f;

                }
            }

            scale = 2;
            for (int i = 0; i < farm.getTrees().size(); i++) {
                Position position = farm.getTrees().get(i).getPosition();
                draw(treesTextures.get(i), position);
            }

            for (int i = 0; i < farm.getPlantedTrees().size(); i++) {
                Tree tree = farm.getPlantedTrees().get(i);
                Position position = tree.getPosition();
                draw(plantedTreesTextures.get(i), position);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        if (optionsMenu != null) {
            scale = 1f;
            Position position = new Position(artisanWithMenu.getPosition());
            position.setY(position.getY() + 3);
            position.setX(position.getX() + 1);
            draw(optionsMenu, position);

            getItemButtonPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 3.4f));
            informationButtonPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 2.4f));
            cancelButtonPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 1.4f));
            cheatButtonPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 0.4f));

            draw(informationButton, informationButtonPosition);
            draw(cancelButton, cancelButtonPosition);
            draw(cheatButton, cheatButtonPosition);
            if (artisanWithMenu.getTimeLeft() == 0) draw(getItemButton, getItemButtonPosition);
        }

        scale = 4.400316f;

        Main.getBatch().end();
    }

    @Override
    public void nextTurn() {
        updateTextures();
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
            goToFarmhouse();
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

        if (clickedOnTexture(screenX, screenY, lakeTexture, farm.getLake().getPosition(), scale)) {
            FishingRod fishingRod = null;

            for (Item item : App.getLoggedIn().getBackpack().getItems().keySet()) {
                if (item instanceof FishingRod) fishingRod = (FishingRod) item;
            }


            if (fishingRod == null) return false;

            double M;
            Weather currentWeather = App.getCurrentGame().getGameState().getCurrentWeather();
            if (currentWeather.equals(Weather.SUNNY)) M = 1.5;
            else if (currentWeather.equals(Weather.RAINY)) M = 1.2;
            else M = 0.5;

            Season currentSeason = App.getCurrentGame().getGameState().getTime().getSeason();
            int fishingSkillLevel = App.getLoggedIn().getSkillLevels().get(Skill.FISHING).getNumber();
            double poleNumber = fishingRod.getRodType().getQualityNumber();
            double qualityNumber = (Math.random() * (fishingSkillLevel + 2) * poleNumber) / (7 - M);
            boolean canCatchLegendary = fishingSkillLevel == 4;

            FishType fishType = FishType.getRandomFishType(currentSeason, canCatchLegendary, fishingRod.getRodType());
            Quality quality = Quality.getQualityByNumber(qualityNumber);

            Fish fish = new Fish(fishType, quality);

            fishingRodImage = new Image(GameAssetManager.getGameAssetManager().getTextureByTool(fishingRod));
            fishingRodImage.setPosition(screenX, screenY);
            stage.addActor(fishingRodImage);
            goToFishingMiniGameMenu(this, fish);
            fishingRod.useTool(null, App.getLoggedIn());

            return true;
        }

        if (clickedOnTexture(screenX, screenY, cabinTexture, farm.getCabin().getPosition(), scale)) {
            goToFarmhouse();
            return true;
        }

        if (clickedOnTexture(screenX, screenY, greenhouseTexture, farm.getGreenhouse().getPosition(), scale)) {
            Result result = controller.buildGreenhouse();
            if (result.success)
                greenhouseTexture = GameAssetManager.getGameAssetManager().getGreenhouse(farm.getGreenhouse().canEnter());
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

        for (int i = 0; i < shippingBinsTextures.size(); i++) {
            ShippingBin shippingBin = farm.getShippingBins().get(i);
            if (clickedOnTexture(screenX, screenY, shippingBinsTextures.get(i), shippingBin.getPositionOfUpperLeftCorner(), 1)) {
                goToSellMenu(this, shippingBin);
            }
        }

        if (optionsMenu != null) {
            Result result;

            if (artisanWithMenu.getTimeLeft() == 0) {
                if (clickedOnTexture(screenX, screenY, getItemButton, getItemButtonPosition)) {
                    result = artisanWithMenu.collectItem();
                    updateTextures();
                    show();
                    if (!result.success) errorMessageLabel.setText(result.message);
                    else {
                        optionsMenu = null;
                        errorMessageLabel.setText("");
                    }
                    return true;
                }
            }

            if (clickedOnTexture(screenX, screenY, informationButton, informationButtonPosition)) {
                goToArtisanInfo(this, artisanWithMenu);
                return true;
            }

            if (clickedOnTexture(screenX, screenY, cancelButton, cancelButtonPosition)) {
                result = artisanWithMenu.cancel();
                if (!result.success) errorMessageLabel.setText(result.message);
                else {
                    updateTextures();
                    optionsMenu = null;
                    errorMessageLabel.setText("");
                }
                return true;
            }

            if (clickedOnTexture(screenX, screenY, cheatButton, cheatButtonPosition)) {
                result = artisanWithMenu.collectItem();
                updateTextures();
                show();
                if (!result.success) errorMessageLabel.setText(result.message);
                else {
                    optionsMenu = null;
                    errorMessageLabel.setText("");
                }
                return true;
            }
        }
        optionsMenu = null;
        errorMessageLabel.setText("");

        for (int i = 0; i < cropsTextures.size(); i++) {
            if (clickedOnTexture(screenX, screenY, cropsTextures.get(i), farm.getPlantedCrops().get(i).getPosition(), 1.5f)) {
                goToCropInfoMenu(this, farm.getPlantedCrops().get(i).getName());
                return true;
            }
        }

        for (int i = 0; i < treesTextures.size(); i++) {
            if (clickedOnTexture(screenX, screenY, treesTextures.get(i), farm.getTrees().get(i).getPosition(), 2)) {
                goToCropInfoMenu(this, farm.getTrees().get(i).getName());
                return true;
            }
        }

        for (int i = 0; i < plantedTreesTextures.size(); i++) {
            if (clickedOnTexture(screenX, screenY, plantedTreesTextures.get(i), farm.getPlantedTrees().get(i).getPosition(), 2)) {
                goToCropInfoMenu(this, farm.getPlantedTrees().get(i).getName());
                return true;
            }
        }

        for (int i = 0; i < artisansTextures.size(); i++) {
            Artisan artisan = farm.getArtisans().get(i);
            Position position = artisan.getPosition();

            if (clickedOnTexture(screenX, screenY, artisansTextures.get(i), position, scale)) {
                if (button == Input.Buttons.RIGHT) {
                    if (optionsMenu == null) {
                        if (artisan.getTimeLeft() == 0) {
                            optionsMenu = GameAssetManager.getGameAssetManager().getFourOptions();
                        } else {
                            optionsMenu = GameAssetManager.getGameAssetManager().getThreeOptions();
                        }
                        artisanWithMenu = artisan;
                    }
                } else {
                    goToArtisanMenu(this, artisan);
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

    public void updateTextures() {
        this.foragingCropsTextures = new ArrayList<>();
        for (ForagingCrop foragingCrop : farm.getForagingCrops()) {
            foragingCropsTextures.add(GameAssetManager.getGameAssetManager().getTextureByForagingCrop(foragingCrop));
        }

        this.stoneTexture = GameAssetManager.getGameAssetManager().getTextureByMineral(new Mineral(MineralType.STONE));

        this.woodTexture = GameAssetManager.getGameAssetManager().getWood();

        this.treesTextures = new ArrayList<>();
        for (Tree tree : farm.getTrees()) {
            treesTextures.add(GameAssetManager.getGameAssetManager().getTextureByTree(tree));
        }

        this.farmBuildingsTextures = new ArrayList<>();
        for (FarmBuilding farmBuilding : farm.getFarmBuildings()) {
            farmBuildingsTextures.add(GameAssetManager.getGameAssetManager().getFarmBuilding(farmBuilding.getFarmBuildingType()));
        }

        this.shippingBinsTextures = new ArrayList<>();
        for (int i = 0; i < farm.getShippingBins().size(); i++) {
            shippingBinsTextures.add(GameAssetManager.getGameAssetManager().getFarmBuilding(FarmBuildingType.SHIPPING_BIN));
        }

        this.craftsTextures = new ArrayList<>();
        for (Craft craft : farm.getCrafts()) {
            craftsTextures.add(GameAssetManager.getGameAssetManager().getCraftingItemTexture(craft.getCraftType().getName()));
        }

        this.artisansTextures = new ArrayList<>();
        for (Artisan artisan : farm.getArtisans()) {
            artisansTextures.add(GameAssetManager.getGameAssetManager().getArtisan(artisan));
        }

        this.animalsTextures = new ArrayList<>();
        for (Animal animal : farm.getAllFarmAnimals()) {
            animalsTextures.add(GameAssetManager.getGameAssetManager().getAnimal(animal.getAnimalType()));
        }

        this.cropsTextures = new ArrayList<>();
        for (Crop crop : farm.getPlantedCrops()) {
            cropsTextures.add(GameAssetManager.getGameAssetManager().getTextureByCrop(crop));
        }

        this.plantedTreesTextures = new ArrayList<>();
        for (Tree tree : farm.getPlantedTrees()) {
            plantedTreesTextures.add(GameAssetManager.getGameAssetManager().getTextureByTree(tree));
        }
    }
}
