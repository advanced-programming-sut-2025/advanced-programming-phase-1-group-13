package com.project.models.tools;

import com.project.models.Item;
import com.project.models.Tile;
import com.project.models.User;
import com.project.models.enums.Skill;
import com.project.models.enums.SkillLevel;
import com.project.models.enums.types.TileType;
import com.project.models.enums.types.ToolMaterial;
import com.project.models.enums.types.ToolType;
import com.project.models.farming.Crop;
import com.project.models.farming.Tree;

import java.util.HashMap;

public class WateringCan extends Tool {
    private int remainingWater;
    private int waterCapacity;

    public WateringCan() {
        super(ToolType.WATERING_CAN);
    }

    public WateringCan(ToolMaterial material) {
        super(ToolType.WATERING_CAN, material);
        this.waterCapacity = switch (material) {
            case BASIC -> 40;
            case COPPER -> 55;
            case IRON -> 70;
            case GOLD -> 85;
            case IRIDIUM -> 100;
        };
        this.remainingWater = this.waterCapacity;
    }


    public int getRemainingWater() {
        return this.remainingWater;
    }

    public int getWaterCapacity() {
        return this.waterCapacity;
    }


    public void fillTheWateringCan() {
        this.remainingWater = this.waterCapacity;
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        SkillLevel skillLevel = playerSkills.get(Skill.FARMING);
        return the54321EnergyPattern(tool, skillLevel);
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        int energyNeeded = calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (player.getBuffRelatedSkill() == Skill.FARMING && player.getHoursLeftTillBuffVanishes() > 0) {
            energyNeeded = Math.max(0, energyNeeded - 1);
        }
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        player.decreaseEnergyBy(energyNeeded);
        if (tile.getType() == TileType.WATER) {
            WateringCan wateringCan = (WateringCan) player.getCurrentTool();
            wateringCan.fillTheWateringCan();
        } else if (tile.getType() == TileType.TREE) {
            ((Tree) tile.getItemPlacedOnTile()).setHasBeenWateredToday(true);
        } else if (tile.getType() == TileType.PLANTED_SEED || tile.getType() == TileType.GROWING_CROP) {
            ((Crop) tile.getItemPlacedOnTile()).setHasBeenWateredToday(true);
        } else if (tile.getType() == TileType.PLOWED_SOIL) {
            tile.setType(TileType.WATERED_PLOWED_SOIL);
        } else if (tile.getType() == TileType.NOT_PLOWED_SOIL) {
            tile.setType(TileType.WATERED_NOT_PLOWED_SOIL);
        }
        if (tile.getType() != TileType.WATER) {
            this.remainingWater = Math.max(0, this.remainingWater - 1);
        }
    }
}


