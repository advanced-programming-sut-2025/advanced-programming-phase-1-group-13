package models.tools;

import controllers.GameController;
import models.Mineral;
import models.Tile;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.MineralType;
import models.enums.types.TileType;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;
import models.inventory.Backpack;

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
        boolean successfulUsage = tile.getType() != TileType.QUARRY_GROUND && tile.getType() != TileType.NOT_PLOWED_SOIL;
        int energyNeeded = this.calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (player.getBuffRelatedSkill() == Skill.MINING && player.getHoursLeftTillBuffVanishes() > 0) {
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
        player.updateSkillPoints(Skill.MINING, 10);

        if (tile.getType() == TileType.PLOWED_SOIL ||
                tile.getType() == TileType.NOT_PLOWED_SOIL ||
                tile.getType() == TileType.PLANTED_SEED ||
                tile.getType() == TileType.GROWING_CROP ||
                tile.getType() == TileType.UNDER_AN_ITEM) {
            tile.setType(TileType.NOT_PLOWED_SOIL);
        } else if (tile.getType() == TileType.STONE) {
            Mineral mineral = (Mineral) tile.getItemPlacedOnTile();
            if (canMineMineral(mineral, player.getCurrentTool())) {
                System.out.println("Mineral " + mineral.getName() + " has been mined.");
                tile.setType(TileType.NOT_PLOWED_SOIL);
                tile.pLaceItemOnTile(null);
                Backpack backpack = player.getBackpack();
                if (backpack == null ||
                        (backpack.getCapacity() <= backpack.getItems().size() && !backpack.isCapacityUnlimited())) {
                    tile.setType(TileType.UNDER_AN_ITEM);
                    tile.pLaceItemOnTile(mineral);
                    System.out.println("Not able to add mineral to inventory.");
                } else {

                    if (player.getSkillLevels().get(Skill.MINING) != SkillLevel.LEVEL_ONE) {
                        backpack.addToInventory(mineral, 2);
                    } else {
                        backpack.addToInventory(mineral, 1);
                    }

                    System.out.println("One " + mineral.getName() + " has been added to the inventory.");
                }
            } else {
                System.out.println("You can't mine " + mineral.getName() + " with your current pickaxe.");
            }
        }
    }

    private boolean canMineMineral(Mineral mineral, Tool tool) {
        MineralType mineralType = mineral.getMineralType();
        return switch (tool.getToolMaterial()) {
            case BASIC -> switch (mineralType) {
                case STONE, COPPER -> true;
                default -> false;
            };
            case COPPER -> switch (mineralType) {
                case STONE, COPPER, IRON -> true;
                default -> false;
            };
            case IRON -> mineralType != MineralType.IRIDIUM;
            case GOLD, IRIDIUM -> true;
        };
    }
}
