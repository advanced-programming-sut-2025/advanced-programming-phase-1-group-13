package com.ap_project.common.models;

import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.environment.Weather;
import com.ap_project.common.models.enums.types.*;
import com.ap_project.common.models.farming.ForagingCrop;
import com.ap_project.common.models.farming.Tree;
import com.ap_project.common.models.tools.Scythe;
import com.ap_project.common.models.tools.Tool;
import com.ap_project.common.models.tools.WateringCan;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class GameAssetManager {
    private static final GameAssetManager gameAssetManager = new GameAssetManager();

    public static GameAssetManager getGameAssetManager() {
        return gameAssetManager;
    }

    public Skin getSkin() {
        return new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
    }

    public Texture getLogo() {
        return new Texture(Gdx.files.internal("Images/Logo.png"));
    }

    public Texture getMenuBackground() {
        return new Texture(Gdx.files.internal("Images/MenuBackground.png"));
    }

    public Texture getCookingMenu() {
        return new Texture(Gdx.files.internal("Images/Cooking/CookingMenu.png"));
    }

    public Texture getRefrigeratorMenu() {
        return new Texture(Gdx.files.internal("Images/Cooking/RefrigeratorMenu.png"));
    }

    public Texture getRefrigeratorMenuInventory() {
        return new Texture(Gdx.files.internal("Images/Cooking/Inventory.png"));
    }

    public Texture getOkButton() {
        return new Texture(Gdx.files.internal("Images/Cooking/OkButton.png"));
    }

    public Texture getAvatarBackground() {
        return new Texture(Gdx.files.internal("Images/Avatars/AvatarBackground.png"));
    }

    public Texture getAvatar(int number) {
        return new Texture(Gdx.files.internal("Images/Avatars/Avatar" + number + ".png"));
    }

    public Texture getSavedGame() {
        return new Texture(Gdx.files.internal("Images/PregameMenu/SavedGame.png"));
    }

    public Texture getCoin() {
        return new Texture(Gdx.files.internal("Images/PregameMenu/Coin.png"));
    }

    public Texture getMapPreview(int mapNumber, int playerIndex) {
        return new Texture(Gdx.files.internal("Images/Map/Farm/Preview/Map" + mapNumber + "Player" + playerIndex + ".png"));
    }

    public Texture getClock(Weather weather, Season season) {
        String path = "Images/Clock/" + weather.getName() + season.getName() + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getFood(FoodType foodType, boolean isLocked) {
        String path = "Images/Food/" + toPascalCase(foodType.getName()) + (isLocked ? "Locked" : "") + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getInventoryHotbar() {
        return new Texture(Gdx.files.internal("Images/Inventory/InventoryHotbar.png"));
    }

    public Texture getHotbarSelectedSlot() {
        return new Texture(Gdx.files.internal("Images/Inventory/SelectedSlot.png"));
    }

    public Texture getEnergyBar() {
        return new Texture(Gdx.files.internal("Images/Energy/EnergyBar.png"));
    }

    public Texture getGreenBar() {
        return new Texture(Gdx.files.internal("Images/Energy/GreenBar.png"));
    }

    public Texture getClockArrow() {
        return new Texture(Gdx.files.internal("Images/Clock/Arrow.png"));
    }

    public Texture getTextureByItem(Item item) {
        if (item instanceof AnimalProduct) {
            return getTextureByAnimalProduct((AnimalProduct) item);
        }

        if (item instanceof Fish) {
            return getTextureByFish((Fish) item);
        }

        if (item instanceof ForagingCrop) {
            return getTextureByForagingCrop((ForagingCrop) item);
        }

        if (item instanceof Good) {
            return getTextureByGood((Good) item);
        }

        if (item instanceof Mineral) {
            return getTextureByMineral((Mineral) item);
        }

        if (item instanceof Tool) {
            return getTextureByTool((Tool) item);
        }

        if (item instanceof Tree) {
            return getTextureByTree((Tree) item);
        }

        // TODO
        return null;
    }

    public Texture getTextureByAnimalProduct(AnimalProduct animalProduct) {
        return new Texture(Gdx.files.internal("Images/AnimalProduct/" + toPascalCase(animalProduct.getName())) + ".png");
    }

    public Texture getTextureByFish(Fish fish) {
        return new Texture(Gdx.files.internal("Images/Fish/" + toPascalCase(fish.getName()) + ".png"));
    }

    public Texture getTextureByForagingCrop(ForagingCrop foragingCrop) {
        return new Texture(Gdx.files.internal("Images/ForagingCrop/" + toPascalCase(foragingCrop.getForagingCropType().getName()) + ".png"));
    }

    public Texture getTextureByGood(Good good) {
        return new Texture(Gdx.files.internal("Images/Goods/" + good.getName().replaceAll(" ", "_") + ".png"));
    }

    public Texture getTextureByMineral(Mineral mineral) {
        return new Texture(Gdx.files.internal("Images/Mineral/" + toPascalCase(mineral.getMineralType().getName()) + ".png"));
    }

    public Texture getTextureByTool(Tool tool) {
        String name = toPascalCase(tool.getName());
        String material = toPascalCase(tool.getToolMaterial().getName());
        String path = "Images/Tool/" + name + "/" + material + name + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getTextureByTree(Tree tree) {
        if (tree.hasFruits()) {
            return new Texture(Gdx.files.internal("Images/Tree/" + toPascalCase(tree.getName()) + "/WithFruit.png"));
        }
        if (tree.isBurnt()) {
            return new Texture(Gdx.files.internal("Images/Tree/" + toPascalCase(tree.getType().getName() + "/Burnt.png")));
        }
        String seasonString = tree.getStage() == 5 ? App.getCurrentGame().getGameState().getTime().getSeason().getName() : "";
        return new Texture(Gdx.files.internal("Images/Tree/" + toPascalCase(tree.getName()) + "/Stage" + tree.getStage() + seasonString + ".png"));
    }

    public Texture getWood() {
        return new Texture(Gdx.files.internal("Images/Resource/Wood.png"));
    }

    public Texture getMenuWindowByType(GameMenuType tab) {
        String gender;
        if (App.getLoggedIn().getGender() == Gender.RATHER_NOT_SAY) {
            gender = "Man";
        } else {
            gender = App.getLoggedIn().getGender().getName();
        }
        String path = "Images/Menu/" + tab.getName() + "Menu" + gender + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getTrashCan(boolean isOpen) {
        return new Texture(Gdx.files.internal("Images/Menu/TrashCan" + (isOpen ? "Open" : "") + ".png"));
    }

    public Texture getBlackScreen() {
        return new Texture(Gdx.files.internal("Images/Menu/Black.png"));
    }

    public Texture getWhiteScreen() {
        return new Texture(Gdx.files.internal("Images/Menu/White.png"));
    }

    public Texture getNumber(int number) {
        return new Texture(Gdx.files.internal("Images/Menu/Number" + number + ".png"));
    }

    public Texture getSkillPoint() {
        return new Texture(Gdx.files.internal("Images/Menu/SkillPoint.png"));
    }

    public Texture getSkillMenuHover(String skillName) {
        return new Texture(Gdx.files.internal("Images/Menu/Hover" + skillName + ".png"));
    }

    public Texture getSocialMenuPage(int number) {
        return new Texture(Gdx.files.internal("Images/Menu/SocialMenuPage" + number + ".png"));
    }

    public Texture getHeart() {
        return new Texture(Gdx.files.internal("Images/Menu/Heart.png"));
    }

    public Texture getCheckedBox() {
        return new Texture(Gdx.files.internal("Images/Menu/CheckedBox.png"));
    }

    public Texture getPlayerFriendship(Gender gender) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }
        String path = "Images/Menu/PlayerFriendship" + genderStr + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getCloseButton() {
        return new Texture(Gdx.files.internal("Images/Menu/CloseButton.png"));
    }

    public Texture getJournal() {
        return new Texture(Gdx.files.internal("Images/Journal/Journal.png"));
    }

    public Texture getQuest(int id) {
        return new Texture(Gdx.files.internal("Images/Journal/Quest" + id + ".png"));
    }

    public Texture getRedCheckedBox() {
        return new Texture(Gdx.files.internal("Images/Journal/RedCheckedBox.png"));
    }

    public Texture getUncheckedBox() {
        return new Texture(Gdx.files.internal("Images/Journal/UncheckedBox.png"));
    }

    public Texture getLock() {
        return new Texture(Gdx.files.internal("Images/Journal/Lock.png"));
    }

    public Texture getSlider() {
        return new Texture(Gdx.files.internal("Images/Journal/Slider.png"));
    }

    public Texture getSliderTrack() {
        return new Texture(Gdx.files.internal("Images/Journal/SliderTrack.png"));
    }

    public Texture getIdlePlayer(Gender gender, Direction direction) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }

        String path = "Images/Player/" + genderStr + "/" + direction.toString() + "1.png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getPlayerUsingTool(Tool tool, Gender gender, Direction direction) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }

        String toolName;
        if (tool instanceof Scythe) {
            toolName = "Scythe";
        } else if (tool instanceof WateringCan) {
            toolName = "WateringCan";
        } else {
            toolName = "Axe";
        }

        return new Texture(Gdx.files.internal("Images/Player/" + genderStr + "/" + direction.toString() + toolName + ".png"));
    }

    public Animation<Texture> getPlayerAnimation(Gender gender, Direction direction) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }

        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 4; i++) {
            String path = "Images/Player/" + genderStr + "/" + direction.toString() + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.1f, frames);
    }

    public Animation<Texture> getFaintAnimation(Gender gender) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }

        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 12; i++) {
            String path = "Images/Player/" + genderStr + "/Faint" + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.5f, frames);
    }

    public Animation<Texture> loadAnimalAnimation(String animalName, String direction) {
        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 4; i++) {
            String path = "Images/Animal/" + animalName + "s" + "/" + animalName + "Sprite" + direction + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    public Texture getBuyAnimalsMenu() {
        return new Texture(Gdx.files.internal("Images/Animal/BuyAnimalsMenu.png"));
    }

    public Texture getFarm(Game game, User user) {
        int index = game.getPlayers().indexOf(user) + 1;
        String season = game.getGameState().getTime().getSeason().getName();
        return new Texture(Gdx.files.internal("Images/Map/Farm/Farm" + season + index + ".png"));
    }

    public Texture getCabin() {
        return new Texture(Gdx.files.internal("Images/Map/Farm/Cabin.png"));
    }

    public Texture getLake(int typeNumber) {
        return new Texture(Gdx.files.internal("Images/Map/Farm/Lake" + typeNumber + ".png"));
    }

    public Texture getGreenhouse(boolean isBuilt) {
        if (isBuilt) {
            return new Texture(Gdx.files.internal("Images/Map/Farm/Greenhouse.png"));
        }
        return new Texture(Gdx.files.internal("Images/Map/Farm/GreenhouseUnbuilt.png"));
    }

    public Texture getAnimal(AnimalType animalType) {
        return new Texture(Gdx.files.internal("Images/Animal/Idle/" + animalType.getName() + ".png"));
    }

    public Animation<Texture> getPettingAnimation() {
        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 10; i++) {
            String path = "Images/Animal/PettingAnimation/Frame" + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.1f, frames);
    }

    public Animation<Texture> getFeedingAnimation() {
        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 10; i++) {
            String path = "Images/Animal/FeedingAnimation/Frame" + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.1f, frames);
    }

    public Texture getAnimalMenu() {
        return new Texture(Gdx.files.internal("Images/Animal/AnimalMenu.png"));
    }

    public Texture getFarmBuilding(FarmBuildingType farmBuildingType) {
        return new Texture(Gdx.files.internal("Images/FarmBuilding/" + toPascalCase(farmBuildingType.getName()) + ".png"));
    }

    public Texture getAnimalLivingSpaceMenu() {
        return new Texture("Images/FarmBuilding/AnimalLivingSpaceMenu.png");
    }

    public Texture getCircle() {
        return new Texture(Gdx.files.internal("Images/Player/Circle.png"));
    }

    public Texture getRaindrop() {
        return new Texture(Gdx.files.internal("Images/Weather/Raindrop.png"));
    }

    public Texture getSnowflake() {
        return new Texture(Gdx.files.internal("Images/Weather/Snowflake.png"));
    }

    public Animation<Texture> getLightningAnimation() {
        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 4; i++) {
            String path = "Images/Weather/Lightning" + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.1f, frames);
    }

    public Texture getMap(Game game) {
        int numberOfPlayers = game.getPlayers().size();
        String season = game.getGameState().getTime().getSeason().getName();
        return new Texture(Gdx.files.internal("Images/Map/Map" + season + numberOfPlayers + ".png"));
    }

    public Texture getFarmhouse() {
        return new Texture(Gdx.files.internal("Images/Map/Farmhouse/Indoors.png"));
    }

    public Texture getRefrigerator() {
        return new Texture(Gdx.files.internal("Images/Map/Farmhouse/Refrigerator.png"));
    }

    public Texture getPlayerIcon(Gender gender) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }
        return new Texture(Gdx.files.internal("Images/Player/" + genderStr + "/Icon.png"));
    }

    public Texture getShop(ShopType shopType, Season season) {
        String shopName = toPascalCase(shopType.getName());
        String seasonStr = season.getName();
        return new Texture(Gdx.files.internal("Images/Map/Village/" + shopName + seasonStr + ".png"));
    }

    public Texture getVillage(Season season) {
        String seasonStr = season.getName();
        return new Texture(Gdx.files.internal("Images/Map/Village/Village" + seasonStr + ".png"));
    }

    public Texture getNPC(NPCType npcType) {
        return new Texture(Gdx.files.internal("Images/NPC/Idle/" + npcType.getName() + ".png"));
    }

    public Texture getNPCPortrait(NPCType npcType) {
        return new Texture(Gdx.files.internal("Images/NPC/Portrait/" + npcType.getName() + ".png"));
    }

    public Texture getNPCOptions() {
        return new Texture(Gdx.files.internal("Images/NPC/Options.png"));
    }

    public Texture getDialogIcon() {
        return new Texture(Gdx.files.internal("Images/NPC/Dialog.png"));
    }

    public Texture getDialogBox() {
        return new Texture(Gdx.files.internal("Images/NPC/DialogBox.png"));
    }

    public static String toPascalCase(String input) {
        if (input == null || input.isEmpty()) return "";

        input = input.replaceAll("'", "");

        StringBuilder pascal = new StringBuilder();
        for (String word : input.trim().split("\\s+")) {
            if (!word.isEmpty()) {
                pascal.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    pascal.append(word.substring(1).toLowerCase());
                }
            }
        }
        return pascal.toString();
    }

    public Texture getCraftingItemTexture(String itemName) {
        itemName = itemName.replaceAll(" ", "");
        String path = "Images/Craft/" + itemName + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getFishingMiniGameWindow() {
        return new Texture(Gdx.files.internal("Images/FishingMiniGame/FishingMiniGameWindow.png"));
    }

    public Texture getFishingGreenBar() {
        return new Texture(Gdx.files.internal("Images/FishingMiniGame/FishingGreenBar.png"));
    }

    public Texture getFishIcon() {
        return new Texture(Gdx.files.internal("Images/FishingMiniGame/FishIcon.png"));
    }

    public Texture getCrown() {
        return new Texture(Gdx.files.internal("Images/FishingMiniGame/Crown.png"));
    }
}
