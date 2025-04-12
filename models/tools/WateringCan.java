package models.tools;

import models.enums.Skill;
import models.enums.environment.Direction;


public class WateringCan extends Tool {
    public WateringCan(int energyNeeded, Skill relatedSkill) {
        super(energyNeeded, relatedSkill);
    }

    @Override
    public int calculateEnergyNeeded() {
        return super.calculateEnergyNeeded();
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


