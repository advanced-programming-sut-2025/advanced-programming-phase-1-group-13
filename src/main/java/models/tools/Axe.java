package models.tools;

import models.*;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.*;
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
        boolean successfulUsage = tile.getType() != TileType.PLOWED_SOIL && tile.getType() != TileType.NOT_PLOWED_SOIL;

        int energyNeeded = calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (player.getBuffRelatedSkill() == Skill.FORAGING && player.getHoursLeftTillBuffVanishes() > 0) {
            energyNeeded = Math.max(0, energyNeeded - 1);
        }
        if (!successfulUsage) {
            energyNeeded = Math.max(0, energyNeeded - 1);
        }
        if (energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        player.decreaseEnergyBy(energyNeeded);
        player.updateSkillPoints(Skill.FORAGING, 10);
        if (tile.getType() == TileType.TREE) {
            Tree tree = (Tree) tile.getItemPlacedOnTile();
            if (tree.isBurnt()) {
                Item coal = getItemByItemType(GoodsType.COAL);
                player.getBackpack().addToInventory(coal, 1);
                tile.setType(TileType.NOT_PLOWED_SOIL);
                return;
            }
            boolean canHarvestWithAxe = switch (tree.getFruitType()) {
                case OAK_RESIN, MAPLE_SYRUP, PINE_TAR,
                     SAP, COMMON_MUSHROOM, MYSTIC_SYRUP -> true;
                default -> false;
            };
            if (canHarvestWithAxe) {
                Fruit fruit = new Fruit(tree.getFruitType());
                player.getBackpack().addToInventory(fruit, 20);
                System.out.println(20 + " of " + fruit + " added to backpack.");
                tree.setDaySinceLastHarvest(0);
            } else {
                tile.pLaceItemOnTile(new Good(GoodsType.WOOD));
                tile.setType(TileType.WOOD_LOG);
                System.out.println("You chopped down the tree.");
            }
        } else if (tile.getType() == TileType.WOOD_LOG) {
            Good woodLog = new Good(GoodsType.WOOD);
            player.getBackpack().addToInventory(woodLog, 5);
            System.out.println(5 + " wood logs added to backpack.");
            tile.setType(TileType.NOT_PLOWED_SOIL);
        }
    }
}
