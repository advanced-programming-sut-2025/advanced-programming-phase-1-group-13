package models.tools;

import models.Item;
import models.enums.Skill;
import models.enums.environment.Direction;

public class Tool extends Item {
    private int energyNeeded;
    private Skill relatedSkill;

    public Tool(int energyNeeded, Skill relatedSkill) {
        this.energyNeeded = energyNeeded;
        this.relatedSkill = relatedSkill;
    }

    public int getEnergyNeeded() {
        return energyNeeded;
    }

    public Skill getRelatedSkill() {
        return relatedSkill;
    }

    public void equipTool() {

    }

    public void upgradeTool() {

    }

    public void useTool(Direction direction) {

    }


}
