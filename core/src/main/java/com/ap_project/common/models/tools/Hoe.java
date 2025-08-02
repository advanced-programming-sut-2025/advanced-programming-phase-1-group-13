package com.ap_project.common.models.tools;

import com.ap_project.common.models.Tile;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.types.TileType;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.enums.types.ToolType;

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
        tile.setType(TileType.PLOWED_SOIL);
        player.decreaseEnergyBy(energyNeeded);
    }
}

