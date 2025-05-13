package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.TileType;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

public class Hoe extends Tool {

    public Hoe() {
        super(ToolType.HOE);
    }

    public Hoe(ToolMaterial material) {
        super(ToolType.HOE, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        SkillLevel skillLevel = playerSkills.get(Skill.FARMING);
        ToolMaterial toolMaterial = tool.getToolMaterial();

        int energy = switch (toolMaterial) {
            case BASIC -> 5;
            case COPPER -> 4;
            case IRON -> 3;
            case GOLD -> 2;
            case IRIDIUM -> 1;
        };
        if (skillLevel == SkillLevel.LEVEL_THREE) {
            energy--;
        }
        return energy;
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
        tile.setType(TileType.PLOWED_SOIL);
        player.decreaseEnergyBy(energyNeeded);
    }
}

