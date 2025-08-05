package com.ap_project.client.controllers;

import com.ap_project.client.views.game.FishingMiniGameView;
import com.ap_project.common.models.enums.types.FishMovementType;

import java.util.Random;

public class FishingController {
    float fishY;
    float fishSpeedY;
    float fishMaxSpeed = 50f;
    float greenZoneMinY, greenZoneMaxY;
    float captureMeter;
    float captureSpeed = 0.1f;
    float captureDecaySpeed = 0.05f;
    long lastUpdateTime;
    private final FishingMiniGameView view;
    private final FishMovementType movementType;
    private float time;

    public FishingController(FishingMiniGameView view) {
        this.movementType = FishMovementType.values()[(new Random()).nextInt(FishMovementType.values().length)];
        this.view = view;
        this.time = 0;
        this.fishY = view.getWindow().getHeight() / 2f;
        this.greenZoneMinY = fishY - 30;
        this.greenZoneMaxY = fishY + 30;
    }

    public void updateMovement(float deltaTime) {
        long now = System.currentTimeMillis();
        if (now - lastUpdateTime >= 500) {
            lastUpdateTime = now;

            int moveDirection = (int)(Math.random() * 3) - 1;
            fishSpeedY = moveDirection * fishMaxSpeed;
        }

        fishY += fishSpeedY * deltaTime;

        fishY = Math.max(0, Math.min(fishY, view.getWindow().getHeight()));

        greenZoneMinY = fishY - 30;
        greenZoneMaxY = fishY + 30;

        if (fishY >= greenZoneMinY && fishY <= greenZoneMaxY) {
            captureMeter = Math.min(1f, captureMeter + captureSpeed * deltaTime);
        } else {
            captureMeter = Math.max(0f, captureMeter - captureDecaySpeed * deltaTime);
        }
    }

    public float getFishYOffset(float deltaTime) {
        updateMovement(deltaTime);
        return fishY;
    }

    public float getCaptureMeter() {
        return captureMeter;
    }
}
