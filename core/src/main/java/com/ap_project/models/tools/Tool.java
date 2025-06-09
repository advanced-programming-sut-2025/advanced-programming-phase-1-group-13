package com.ap_project.models.tools;

import com.ap_project.models.Item;
import com.ap_project.models.Tile;
import com.ap_project.models.User;
import com.ap_project.models.enums.Skill;
import com.ap_project.models.enums.SkillLevel;
import com.ap_project.models.enums.types.ToolMaterial;
import com.ap_project.models.enums.types.ToolType;

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
        int energy;
        switch (toolMaterial) {
            case BASIC:
                energy = 5;
                break;
            case COPPER:
                energy = 4;
                break;
            case IRON:
                energy = 3;
                break;
            case GOLD:
                energy = 2;
                break;
            case IRIDIUM:
                energy = 1;
                break;
            default:
                energy = 0;
                break;
        }

        if (skillLevel == SkillLevel.LEVEL_THREE) {
            energy--;
        }
        return energy;
    }

    public Skill getRelatedSkill() {
        return relatedSkill;
    }

    public void upgradeTool() {
        switch (this.toolMaterial) {
            case BASIC:
                this.toolMaterial = ToolMaterial.COPPER;
                break;
            case COPPER:
                this.toolMaterial = ToolMaterial.IRON;
                break;
            case IRON:
                this.toolMaterial = ToolMaterial.GOLD;
                break;
            case GOLD:
                this.toolMaterial = ToolMaterial.IRIDIUM;
                break;
            case IRIDIUM:
                this.toolMaterial = null;
                break;
        }
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
