package models.tools;

import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.TileType;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;
import models.farming.Tree;

import java.util.HashMap;


public class Axe extends Tool {
    public Axe() {
        super(ToolType.AXE);
    }

    public Axe(ToolMaterial material) {
        super(ToolType.AXE, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        SkillLevel skillLevel = playerSkills.get(Skill.FORAGING);
        return the54321EnergyPattern(tool, skillLevel);
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
        //todo: calc energy needed (successful vs. not)
        player.decreaseEnergyBy(energyNeeded);
        if (tile.getType() == TileType.TREE) {
            Tree tree = (Tree) tile.getItemPlacedOnTile();
            if (tree.isBurnt()) {
                // todo : coal (add to inventory)
                return;
            }
            boolean canHarvestWithAxe = switch (tree.getFruit()) {
                case OAK_RESIN, MAPLE_SYRUP, PINE_TAR,
                     SAP, COMMON_MUSHROOM, MYSTIC_SYRUP -> true;
                default -> false;
            };
            if (canHarvestWithAxe) {
                // todo: harvest and add to inventory
                return;
            } else {
                tile.setType(TileType.WOOD_LOG);
                return;
            }
        } else if (tile.getType() == TileType.WOOD_LOG) {
            // todo: add wood to inventory
            return;
        }
    }
}
