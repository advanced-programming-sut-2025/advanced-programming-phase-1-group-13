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
        // TODO: Weather's effect
        return energy;
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        int energyNeeded = this.calculateEnergyNeeded(player.getSkillLevels(), null);
        if (player.getBuffRelatedSkill() == Skill.FISHING && player.getHoursLeftTillBuffVanishes() > 0) {
            energyNeeded = Math.max(0, energyNeeded - 1);
        }
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }

        player.updateSkillPoints(Skill.FISHING, 5);

        player.setEnergy(player.getEnergy() - energyNeeded);

        super.useTool(tile, player);
    }

    @Override
    public void upgradeTool() {
        this.type = switch (type) {
            case TRAINING -> FishingRodType.BAMBOO;
            case BAMBOO -> FishingRodType.FIBERGLASS;
            case FIBERGLASS, IRIDIUM -> FishingRodType.IRIDIUM;
            default -> FishingRodType.TRAINING;
        };
    }
}