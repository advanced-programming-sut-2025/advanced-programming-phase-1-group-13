package com.project.models.tools;

import com.project.models.Animal;
import com.project.models.App;
import com.project.models.Item;
import com.project.models.User;
import com.project.models.enums.Skill;
import com.project.models.enums.SkillLevel;
import com.project.models.enums.types.ToolMaterial;
import com.project.models.enums.types.ToolType;

import java.util.ArrayList;
import java.util.HashMap;

public class Shear extends Tool {
    public Shear() {
        super(ToolType.SHEARS);
    }

    public Shear(ToolMaterial material) {
        super(ToolType.SHEARS, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        return 4;
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    public void useTool(Animal animal) {
        User player = App.getLoggedIn();
        int energyNeeded = calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (!player.isEnergyUnlimited() && energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        HashMap<Item, Integer> items = player.getBackpack().getItems();
        for (Item item : animal.getProducedProducts()) {
            items.put(item, items.getOrDefault(item, 0) + 1);
        }
        animal.setProducedProducts(new ArrayList<>());
        player.decreaseEnergyBy(4);
    }
}


