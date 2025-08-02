package com.ap_project.common.models.tools;

import com.ap_project.common.models.Tile;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.types.FishingRodType;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.enums.types.ToolType;

import java.util.HashMap;

public class FishingRod extends Tool {
    private FishingRodType type;

    public FishingRod(ToolMaterial material) {
        super(ToolType.FISHING_ROD, material);
        // TODO
        this.type = FishingRodType.TRAINING;
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

        player.decreaseEnergyBy(energyNeeded);

        super.useTool(tile, player);
    }

    @Override
    public void upgradeTool() {
        switch (this.type) {
            case TRAINING:
                this.type = FishingRodType.BAMBOO;
                break;
            case BAMBOO:
                this.type = FishingRodType.FIBERGLASS;
                break;
            case FIBERGLASS:
            case IRIDIUM:
                this.type = FishingRodType.IRIDIUM;
                break;
            default:
                this.type = FishingRodType.TRAINING;
                break;
        }
    }
}
