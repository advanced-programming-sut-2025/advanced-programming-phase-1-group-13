package com.ap_project.common.models.tools;

import com.ap_project.common.models.Item;
import com.ap_project.common.models.Tile;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.types.TileType;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.enums.types.ToolType;
import com.ap_project.common.models.farming.Crop;
import com.ap_project.common.models.farming.Tree;

import java.util.HashMap;

public class WateringCan extends Tool {
    private int remainingWater;
    private int waterCapacity;

    public WateringCan() {
        super(ToolType.WATERING_CAN);
    }

    public WateringCan(ToolMaterial material) {
        super(ToolType.WATERING_CAN, material);

        switch (material) {
            case BASIC:
                this.waterCapacity = 40;
                break;
            case COPPER:
                this.waterCapacity = 55;
                break;
            case IRON_ORE:
                this.waterCapacity = 70;
                break;
            case GOLD:
                this.waterCapacity = 85;
                break;
            case IRIDIUM:
                this.waterCapacity = 100;
                break;
            default:
                this.waterCapacity = 0;
                break;
        }

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


