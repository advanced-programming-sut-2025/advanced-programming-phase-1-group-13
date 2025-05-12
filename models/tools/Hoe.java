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
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills) {
        SkillLevel skillLevel = playerSkills.get(Skill.FARMING);
        return switch (skillLevel) {
            case LEVEL_ZERO -> 5;
            case LEVEL_ONE -> 1;
            case LEVEL_TWO -> 2;
            case LEVEL_THREE -> 3;
            default -> 0;
        };
        // todo: ... take into account the toolMaterial

    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        int energyNeeded = calculateEnergyNeeded(player.getSkillLevels());
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        tile.setType(TileType.PLOWED_SOIL);
        player.decreaseEnergyBy(energyNeeded);
    }
}

