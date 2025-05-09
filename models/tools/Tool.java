package models.tools;

import models.Item;
import models.enums.Skill;
import models.enums.environment.Direction;
import models.enums.types.ToolTypes;

public class Tool extends Item {
    private int energyNeeded;
    private Skill relatedSkill;
    private ToolTypes toolType;

    public Tool(int energyNeeded, Skill relatedSkill) {
        this.energyNeeded = energyNeeded;
        this.relatedSkill = relatedSkill;
    }

    public int calculateEnergyNeeded() {
        // TODO: calculate energy needed based on related skill
        return energyNeeded;
    }

    public Skill getRelatedSkill() {
        return relatedSkill;
    }


    public void upgradeTool() {
        // TODO
    }

    public void useTool(Direction direction) {
        // TODO
    }

    @Override
    public String toString() {
        return this.toolType.getName();
    }
}
