package com.ap_project.models.tools;

import com.ap_project.models.Animal;
import com.ap_project.models.App;
import com.ap_project.models.Item;
import com.ap_project.models.User;
import com.ap_project.models.enums.Skill;
import com.ap_project.models.enums.SkillLevel;
import com.ap_project.models.enums.types.ToolMaterial;
import com.ap_project.models.enums.types.ToolType;

import java.util.ArrayList;
import java.util.HashMap;

public class MilkPail extends Tool {

    public MilkPail() {
        super(ToolType.MILK_PAIL);
    }

    public MilkPail(ToolMaterial material) {
        super(ToolType.MILK_PAIL, ToolMaterial.BASIC);
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
        HashMap<Item, Integer> items = player.getBackpack().getItems();
        for (Item item : animal.getProducedProducts()) {
            items.put(item, items.getOrDefault(item, 0) + 1);
        }
        animal.setProducedProducts(new ArrayList<>());
        player.setEnergy(player.getEnergy() - 4);
    }
}


