package models.tools;

import models.Item;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

public class Tool extends Item {
    private ToolType toolType;
    private final Skill relatedSkill;
    private ToolMaterial toolMaterial;

    public Tool(ToolType toolType) {
        this.toolType = toolType;
        this.relatedSkill = toolType.getRelatedSkill();
    }

    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills) {
        // TODO: calculate energy needed based on related skill
        return 0;
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
