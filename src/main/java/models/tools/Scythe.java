package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.TileType;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;
import models.farming.Crop;
import models.farming.Tree;

import java.util.HashMap;

public class Scythe extends Tool {
    public Scythe() {
        super(ToolType.SCYTHE);
    }

    public Scythe(ToolMaterial material) {
        super(ToolType.SCYTHE, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        return 2;
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        int energyNeeded = calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        player.decreaseEnergyBy(energyNeeded);
        if (tile.getType() == TileType.GRASS) {
            tile.setType(TileType.NOT_PLOWED_SOIL);
        } else if (tile.getType() == TileType.TREE) {
            Tree tree = (Tree) tile.getItemPlacedOnTile();
            // todo: harvest
        } else if (tile.getType() == TileType.GROWING_CROP) {
            Crop crop = (Crop) tile.getItemPlacedOnTile();
            // todo: harvest
        } else if (tile.getType() == TileType.GREENHOUSE) {
            // todo
            return;
        }
    }
}
