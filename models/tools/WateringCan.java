package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
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
        return super.calculateEnergyNeeded(playerSkills, tool);
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        super.useTool(tile, player);
    }
}


