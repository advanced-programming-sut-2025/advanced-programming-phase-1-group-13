package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.FishingRodType;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

public class FishingRod extends Tool {
    private FishingRodType type;

    public FishingRod(ToolMaterial material) {
        super(ToolType.FISHING_ROD, material);
    }

    public FishingRodType getRodType() {
        return type;
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        int energy = this.type.getEnergy();
        if (playerSkills.get(Skill.FISHING) == SkillLevel.LEVEL_THREE) {
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
        int energyNeeded = this.calculateEnergyNeeded(player.getSkillLevels(), null);
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }

        player.setEnergy(player.getEnergy() - energyNeeded);

        super.useTool(tile, player);
    }
}