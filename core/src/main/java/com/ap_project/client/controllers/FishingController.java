package com.ap_project.client.controllers;

import com.ap_project.common.models.enums.types.FishMovementType;

import java.util.Random;

public class FishingController {
    private final FishMovementType movementType;
    private float time;

    public FishingController() {
        this.movementType = FishMovementType.values()[(new Random()).nextInt(FishMovementType.values().length)];
        this.time = 0;
    }

    public float getFishDisplacement(float delta) {
        time += delta;

        if (movementType == FishMovementType.MIXED) {}
        else if (movementType == FishMovementType.SINKER) {}
        else if(movementType == FishMovementType.SMOOTH) {}
        else if(movementType == FishMovementType.FLOATER){}
        else if(movementType == FishMovementType.DART){}
        return time;
    }


}
