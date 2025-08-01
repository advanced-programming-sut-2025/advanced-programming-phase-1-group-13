package com.ap_project.common.models.tools;

import com.ap_project.common.models.App;
import com.ap_project.common.models.Item;
import com.ap_project.common.models.Tile;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.types.TileType;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.enums.types.ToolType;
import com.ap_project.common.models.farming.Crop;
import com.ap_project.common.models.farming.Tree;

import java.util.HashMap;

public class Scythe extends Tool {
    public Scythe() {
        super(ToolType.SCYTHE);
    }

    public Scythe(ToolMaterial material) {
        super(ToolType.SCYTHE, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        return 2;
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        int energyNeeded = calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        player.decreaseEnergyBy(energyNeeded);
        player.updateSkillPoints(Skill.FARMING, 5);
        if (tile.getType() == TileType.GRASS) {
            tile.setType(TileType.NOT_PLOWED_SOIL);
        } else if (tile.getType() == TileType.TREE) {
            Tree tree = (Tree) tile.getItemPlacedOnTile();
            Season currentSeason = App.getCurrentGame().getGameState().getTime().getSeason();
            if (tree.isBurnt()) {
                System.out.println("The tree is burnt. Use axe to collect coal!");
                return;
            }
            if (!tree.getSeasons().contains(currentSeason)) {
                System.out.println("You cannot harvest " + tree.getFruitType().getName() +
                        " in the " + currentSeason.getName() + ".");
                return;
            }
            boolean lastStage = tree.getStage() == tree.getNumOfStages();
            if (lastStage) {
                Item fruit = getItemByItemType(tree.getFruitType());
                if (fruit != null) {
                    player.getBackpack().addToInventory(fruit, 20);
                    tree.setDaySinceLastHarvest(0);
                }
            }
        } else if (tile.getType() == TileType.GROWING_CROP ||
                tile.getType() == TileType.GREENHOUSE) {
            Crop crop = (Crop) tile.getItemPlacedOnTile();
            Season currentSeason = App.getCurrentGame().getGameState().getTime().getSeason();
            if (!crop.getSeasons().contains(currentSeason)) {
                System.out.println("You cannot harvest " + crop.getName() + " in the " + currentSeason.getName() + ".");
                return;
            }

            boolean lastStage = crop.getStage() == crop.getNumOfStages();
            if (lastStage) {

                if (crop.isGiant()) {
                    player.getBackpack().addToInventory(crop, 10);
                } else {
                    player.getBackpack().addToInventory(crop, 1);
                }

                if (crop.isOneTime()) {
                    if (tile.getType() != TileType.GREENHOUSE) {
                        tile.setType(TileType.NOT_PLOWED_SOIL);
                    } else {
                        tile.pLaceItemOnTile(null);
                    }
                } else {
                    crop.setDaySinceLastHarvest(0);
                }
            }
        }
    }
}
