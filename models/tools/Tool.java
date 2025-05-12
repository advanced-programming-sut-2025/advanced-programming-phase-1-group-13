package models.tools;

import models.Item;
import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

public abstract class Tool extends Item {
    private final ToolType toolType;
    private final Skill relatedSkill;
    private ToolMaterial toolMaterial;

    public Tool(ToolType toolType) {
        this.toolType = toolType;
        this.relatedSkill = toolType.getRelatedSkill();
    }

    public Tool(ToolType toolType, ToolMaterial toolMaterial) {
        this.toolType = toolType;
        this.relatedSkill = toolType.getRelatedSkill();
        this.toolMaterial = toolMaterial;
    }

    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills) {
        // TODO: calculate energy needed based on related skill
        return 0;
    }

    public Skill getRelatedSkill() {
        return relatedSkill;
    }

    public void upgradeTool() {
        this.toolMaterial = switch (toolMaterial) {
            case BASIC:
                yield ToolMaterial.COPPER;
            case COPPER:
                yield ToolMaterial.IRON;
            case IRON:
                yield ToolMaterial.GOLD;
            case GOLD:
                yield ToolMaterial.IRIDIUM;
            case IRIDIUM:
                yield null;
        };
    }

    public void useTool(Tile tile, User player) {
        // TODO
    }

    @Override
    public String toString() {
        return this.toolType.getName();
    }

    public ToolType getToolType() {
        return this.toolType;
    }

    public ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }
}
