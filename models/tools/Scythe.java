package models.tools;

import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.ToolTypes;

import java.util.HashMap;


public class Scythe extends Tool {
    public Scythe() {
        super(ToolTypes.SCYTHE);
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
