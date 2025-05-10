package models.tools;

import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

//
public class WateringCan extends Tool {
    public WateringCan() {
        super(ToolType.WATERING_CAN);
    }

    public WateringCan(ToolMaterial material) {
        super(ToolType.WATERING_CAN, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills) {
        return super.calculateEnergyNeeded(playerSkills);
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Direction direction) {
        super.useTool(direction);
    }
}


