package com.project.models.tools;

import com.project.models.Item;
import com.project.models.Tile;
import com.project.models.User;
import com.project.models.enums.Skill;
import com.project.models.enums.SkillLevel;
import com.project.models.enums.types.ToolMaterial;
import com.project.models.enums.types.ToolType;

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

    @Override
    public String getName() {
        return this.toolType.getName();
    }

    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        return 0;
    }

    static int the54321EnergyPattern(Tool tool, SkillLevel skillLevel) {
        ToolMaterial toolMaterial = tool.getToolMaterial();
        int energy = switch (toolMaterial) {
            case BASIC -> 5;
            case COPPER -> 4;
            case IRON -> 3;
            case GOLD -> 2;
            case IRIDIUM -> 1;
        };
        if (skillLevel == SkillLevel.LEVEL_THREE) {
            energy--;
        }
        return energy;
    }

    public Skill getRelatedSkill() {
        return relatedSkill;
    }

    public void upgradeTool() {
        this.toolMaterial = switch (toolMaterial) {
            case BASIC -> ToolMaterial.COPPER;
            case COPPER -> ToolMaterial.IRON;
            case IRON -> ToolMaterial.GOLD;
            case GOLD -> ToolMaterial.IRIDIUM;
            case IRIDIUM -> null;
        };
    }

    public void useTool(Tile tile, User player) {
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
