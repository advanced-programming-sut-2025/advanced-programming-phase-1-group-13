package com.ap_project.common.models.farming;

import com.ap_project.common.models.Item;
import com.ap_project.common.models.Position;
import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.types.CropType;
import com.ap_project.common.models.enums.types.SeedType;

import java.util.ArrayList;

public class Crop extends Item implements Harvestable {
    private final CropType type;
    private final SeedType source;
    private final Integer numOfStages;
    private final ArrayList<Integer> stagesTimes;
    private final Integer totalHarvestTime;
    private final Integer baseSellPrice;
    private final boolean isEdible;
    private final Integer energy;
    private final ArrayList<Season> seasons;
    private final boolean canBecomeGiant;
    private boolean isGiant;
    private final boolean oneTime;
    private final Integer regrowthTime;
    private Integer dayInStage;
    private Integer stage;
    private Integer daySinceLastHarvest;
    private boolean hasBeenWateredToday;
    private boolean hasBeenFertilizedToday;
    private final String name;
    private Position position;

    public Crop(CropType type) {
        this.type = type;
        this.name = type.getName();
        this.source = type.getSource();
        this.numOfStages = type.getNumberOfStages();
        this.stagesTimes = type.getStages();
        this.totalHarvestTime = type.getTotalHarvestTime();
        this.baseSellPrice = type.getSellPrice();
        this.isEdible = type.isEdible();
        this.energy = type.getEnergy();
        this.seasons = type.getSeasons();
        this.canBecomeGiant = type.canBecomeGiant();
        this.oneTime = type.isOneTime();
        this.regrowthTime = type.getRegrowthTime();
        this.daySinceLastHarvest = null;
        this.isGiant = false;
        this.dayInStage = 0;
        this.stage = 1;
        this.hasBeenWateredToday = false;
        this.hasBeenFertilizedToday = false;
        this.position = null;
    }

    public Crop(CropType type, Position position) {
        this(type);
        this.position = position;
    }

    public Crop(SeedType source) {
        this.source = source;
        this.type = CropType.getCropTypeBySeedType(source);
        assert this.type != null;
        this.name = (this.type).getName();
        this.numOfStages = (this.type).getNumberOfStages();
        this.stagesTimes = (this.type).getStages();
        this.totalHarvestTime = (this.type).getTotalHarvestTime();
        this.baseSellPrice = (this.type).getSellPrice();
        this.isEdible = (this.type).isEdible();
        this.energy = (this.type).getEnergy();
        this.seasons = (this.type).getSeasons();
        this.canBecomeGiant = (this.type).canBecomeGiant();
        this.oneTime = (this.type).isOneTime();
        this.regrowthTime = (this.type).getRegrowthTime();
        this.daySinceLastHarvest = null;
        this.isGiant = false;
        this.dayInStage = 0;
        this.stage = 1;
        this.hasBeenWateredToday = false;
        this.hasBeenFertilizedToday = false;
    }

    public CropType getType() {
        return this.type;
    }

    public SeedType getSource() {
        return source;
    }

    public Integer getNumOfStages() {
        return numOfStages;
    }

    public ArrayList<Integer> getStagesTimes() {
        return stagesTimes;
    }

    public Integer getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public Integer getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public Integer getEnergy() {
        return energy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public Integer getRegrowthTime() {
        return regrowthTime;
    }

    public Integer getDayInStage() {
        return dayInStage;
    }

    public void setDayInStage(Integer dayInStage) {
        this.dayInStage = dayInStage;
    }

    public Integer getStage() {
        return stage;
    }

    public void showInfo() {

    }

    public Integer getDaySinceLastHarvest() {
        return this.daySinceLastHarvest;
    }

    public void incrementDayInStage() {
        this.dayInStage++;
    }

    public void setDaySinceLastHarvest(Integer daySinceLastHarvest) {
        this.daySinceLastHarvest = daySinceLastHarvest;
    }

    public boolean hasBeenWateredToday() {
        return this.hasBeenWateredToday;
    }

    public boolean hasBeenFertilizedToday() {
        return this.hasBeenFertilizedToday;
    }

    public void setHasBeenFertilizedToday(boolean hasBeenFertilizedToday) {
        this.hasBeenFertilizedToday = hasBeenFertilizedToday;
    }

    public void setHasBeenWateredToday(boolean hasBeenWateredToday) {
        this.hasBeenWateredToday = hasBeenWateredToday;
    }

    public void incrementStage() {
        this.stage++;
        if (this.stage >= this.numOfStages) {
            this.stage = this.numOfStages;
            if (!this.oneTime) {
                this.daySinceLastHarvest = 0;
            }
        }
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public void incrementDaySinceLastHarvest() {
        if (this.daySinceLastHarvest == null) {
            this.daySinceLastHarvest = 0;
        }
        this.daySinceLastHarvest++;
    }

    public Integer getDaysLeftToHarvest() {
        if (this.daySinceLastHarvest == null) {
            return this.totalHarvestTime - this.dayInStage;
        }
        int remainingTime = this.totalHarvestTime - this.daySinceLastHarvest;
        if (!this.oneTime && remainingTime <= 0) {
            return this.regrowthTime;
        }
        return remainingTime;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

/*

tools equip Hoe
tools use -d r
cheat advance date 29d
cheat add item -n Blueberry Seeds
plant -s Blueberry Seeds -d r
pm15
showplant -l 6,8
fertilize -f Basic Fertilizer -d r
showplant -l 6,8
tools show current
tools equip Watering can
tools show current
tools use -d r
showplant -l 6,8
cheat advance date 10d
showplant -l 6,8
cheat advance date 13d

 */
