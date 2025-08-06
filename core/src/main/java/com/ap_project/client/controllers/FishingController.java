package com.ap_project.client.controllers;

import com.ap_project.client.views.game.FishingMiniGameView;
import com.ap_project.common.models.enums.types.FishMovementType;

import java.util.Random;

public class FishingController {
    private float fishY;
    private float fishSpeedY;
    private long lastUpdateTime;
    private final FishingMiniGameView view;
    private FishMovementType movementType;

    public FishingController(FishingMiniGameView view) {
        this.movementType = FishMovementType.values()[(new Random()).nextInt(FishMovementType.values().length)];
        this.view = view;
        this.fishY = view.getWindow().getHeight() / 2f;
    }

    public void updateMovement(float deltaTime) {
        movementType = FishMovementType.MIXED; // TODO: remove later
        if (movementType == FishMovementType.MIXED) {
            long now = System.currentTimeMillis();
            if (now - lastUpdateTime >= 500) {
                lastUpdateTime = now;

                int moveDirection = (int) (Math.random() * 3) - 1;
                fishSpeedY = moveDirection * 50;
            }
            fishY += fishSpeedY * deltaTime;
            fishY = Math.max(0, Math.min(fishY, view.getWindow().getHeight() + 20));
        } else if (movementType == FishMovementType.SINKER) {
            // TODO
        } else if (movementType == FishMovementType.SMOOTH) {
            // TODO
        } else if (movementType == FishMovementType.FLOATER) {
            // TODO
        } else if (movementType == FishMovementType.DART) {
            // TODO
        }
    }

    public float getFishYOffset(float deltaTime) {
        updateMovement(deltaTime);
        return fishY;
    }
}
