package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.TileType;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

public class WateringCan extends Tool {
    public WateringCan() {
        super(ToolType.WATERING_CAN);
    }

    public WateringCan(ToolMaterial material) {
        super(ToolType.WATERING_CAN, material);
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
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        player.decreaseEnergyBy(energyNeeded);
        if (tile.getType() == TileType.WATER) {
            // todo: filling up the Watering Can with water

            return;
        } else if (tile.getType() == TileType.TREE ||
                tile.getType() == TileType.PLANTED_SEED ||
                tile.getType() == TileType.GROWING_CROP) {
            // todo: abyari mishavand
            return;
        } else if (tile.getType() == TileType.PLOWED_SOIL) {
            tile.setType(TileType.WATERED_PLOWED_SOIL);
        } else if (tile.getType() == TileType.NOT_PLOWED_SOIL) {
            tile.setType(TileType.WATERED_NOT_PLOWED_SOIL);
        }
    }
}


