package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

public class Pickaxe extends Tool {

    public Pickaxe() {
        super(ToolType.PICKAXE);
    }

    public Pickaxe(ToolMaterial material) {
        super(ToolType.PICKAXE, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        SkillLevel skillLevel = playerSkills.get(Skill.MINING);
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

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        super.useTool(tile, player);
    }
}
