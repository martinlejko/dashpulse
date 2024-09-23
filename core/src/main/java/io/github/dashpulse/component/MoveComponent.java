package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MoveComponent implements Component {
    public float speed = 2000f; // Speed of movement
    public float cosAngle = 0f; // For horizontal movement
    public float sinAngle = 0f; // For vertical movement
    public boolean isBlinking = false;
    public Vector2 blinkPosition;
    public float blinkCooldown = 2.0f; // Cooldown duration in seconds
    public float blinkCooldownTimer = 0f; // Timer to track cooldown
}
