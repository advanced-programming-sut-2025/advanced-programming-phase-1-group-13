package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.TileType;
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
        return the54321EnergyPattern(tool, skillLevel);
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        int energyNeeded = this.calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        player.decreaseEnergyBy(energyNeeded);
        if (
                tile.getType() == TileType.PLOWED_SOIL ||
                        tile.getType() == TileType.NOT_PLOWED_SOIL ||
                        tile.getType() == TileType.PLANTED_SEED ||
                        tile.getType() == TileType.GROWING_CROP ||
                        tile.getType() == TileType.UNDER_AN_ITEM) {
            tile.setType(TileType.NOT_PLOWED_SOIL);
        }
//        else if (tile.getType() == TileType.QUARRY ||
//                tile.getType() == TileType.STONE) {
//
//        }


    }
}
