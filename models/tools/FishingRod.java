package models.tools;

import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.FishingRodType;
import models.enums.types.ToolType;

import java.util.HashMap;

public class FishingRod extends Tool {
    private FishingRodType type;

    public FishingRod(int energyNeeded, Skill relatedSkill) {
        super(ToolType.FISHING_ROD);
    }

    public FishingRodType getRodType() {
        return type;
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

