package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.FishingRodType;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.HashMap;

public class FishingRod extends Tool {
    private FishingRodType type;

    public FishingRod() {
        super(ToolType.FISHING_ROD);
    }

    public FishingRod(ToolMaterial material) {
        super(ToolType.FISHING_ROD, material);
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
    public void useTool(Tile tile, User player) {
        super.useTool(tile, player);
    }
}

