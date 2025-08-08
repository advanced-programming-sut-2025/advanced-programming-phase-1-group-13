package com.ap_project.client.controllers;

import com.ap_project.client.views.game.FishingMiniGameView;
import com.ap_project.common.models.enums.types.FishMovementType;

import java.util.Random;

public class FishingController {
    private float fishY;
    private float fishSpeedY;
    private long lastUpdateTime;
    private final FishingMiniGameView view;
    private final FishMovementType movementType;

    public FishingController(FishingMiniGameView view) {
        this.movementType = FishMovementType.values()[(new Random()).nextInt(FishMovementType.values().length)];
        this.view = view;
        this.fishY = view.getWindow().getHeight() / 2f;
    }

    public void updateMovement(float deltaTime) {
        long now = System.currentTimeMillis();
        if (movementType == FishMovementType.MIXED) {
            if (now - lastUpdateTime >= 500) {
                lastUpdateTime = now;
                int moveDirection = (int) (Math.random() * 3) - 1;
                fishSpeedY = moveDirection * 50;
            }
        } else if (movementType == FishMovementType.SMOOTH) {
            if (now - lastUpdateTime >= 200) {
                lastUpdateTime = now;
                float change = ((float) Math.random() - 0.5f) * 30;
                fishSpeedY += change;
                fishSpeedY = Math.max(-80, Math.min(fishSpeedY, 80));
            }
        } else if (movementType == FishMovementType.SINKER) {
            float sinkerAccel = -30f;
            if (now - lastUpdateTime >= 500) {
                lastUpdateTime = now;
                fishSpeedY += (float) (Math.random() * 20 - 10);
                fishSpeedY = Math.min(fishSpeedY, 0);
            }
            fishSpeedY += sinkerAccel * deltaTime;
        } else if (movementType == FishMovementType.FLOATER) {
            float floaterAccel = 30f;
            if (now - lastUpdateTime >= 500) {
                lastUpdateTime = now;
                fishSpeedY += (float) (Math.random() * 20 - 10);
                fishSpeedY = Math.max(fishSpeedY, 0);
            }
            fishSpeedY += floaterAccel * deltaTime;
        } else if (movementType == FishMovementType.DART) {
            if (now - lastUpdateTime >= 150) {
                lastUpdateTime = now;
                int moveDirection = (int) (Math.random() * 3) - 1;
                fishSpeedY = moveDirection * 70;
            }
        }
        fishY += fishSpeedY * deltaTime;
        fishY = Math.max(25, Math.min(fishY, view.getWindow().getHeight() - 50));
    }

    public float getFishYOffset(float deltaTime) {
        updateMovement(deltaTime);
        return fishY;
    }
}
