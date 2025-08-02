package com.ap_project.common.models.tools;

import com.ap_project.common.models.Mineral;
import com.ap_project.common.models.Tile;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.types.MineralType;
import com.ap_project.common.models.enums.types.TileType;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.enums.types.ToolType;
import com.ap_project.common.models.inventory.Backpack;

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
            tile.pLaceItemOnTile(null);
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

                    tile.setType(TileType.NOT_PLOWED_SOIL);
                    System.out.println("One " + mineral.getName() + " has been added to the inventory.");
                }
            } else {
                System.out.println("You can't mine " + mineral.getName() + " with your current pickaxe.");
            }
        }
    }

    private boolean canMineMineral(Mineral mineral, Tool tool) {
        MineralType mineralType = mineral.getMineralType();
        boolean result;

        switch (tool.getToolMaterial()) {
            case BASIC:
                switch (mineralType) {
                    case STONE:
                    case COPPER:
                        result = true;
                        break;
                    default:
                        result = false;
                }
                break;
            case COPPER:
                switch (mineralType) {
                    case STONE:
                    case COPPER:
                    case IRON:
                        result = true;
                        break;
                    default:
                        result = false;
                }
                break;
            case IRON:
                result = mineralType != MineralType.IRIDIUM;
                break;
            case GOLD:
            case IRIDIUM:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }
}
