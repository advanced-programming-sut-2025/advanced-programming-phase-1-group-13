package models.tools;

import models.Item;
import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.GoodsType;
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
        if (player.getBuffRelatedSkill() == Skill.FORAGING && player.getHoursLeftTillBuffVanishes() > 0) {
            energyNeeded = Math.max(0, energyNeeded - 1);
        }
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        // todo: energy needed BASED ON successful vs. not?
        player.decreaseEnergyBy(energyNeeded);
        if (tile.getType() == TileType.TREE) {
            Tree tree = (Tree) tile.getItemPlacedOnTile();
            if (tree.isBurnt()) {
                Item coal = getItemByItemType(GoodsType.COAL);
                player.getBackpack().addToInventory(coal, 1);
                return;
            }
            boolean canHarvestWithAxe = switch (tree.getFruit()) {
                case OAK_RESIN, MAPLE_SYRUP, PINE_TAR,
                     SAP, COMMON_MUSHROOM, MYSTIC_SYRUP -> true;
                default -> false;
            };
            if (canHarvestWithAxe) {
                // todo: harvest and add to inventory
            } else {
                tile.setType(TileType.WOOD_LOG);
            }
        } else if (tile.getType() == TileType.WOOD_LOG) {
            Item woodLog = getItemByItemType(GoodsType.WOOD);
            player.getBackpack().addToInventory(woodLog, 1);
        }
    }
}
