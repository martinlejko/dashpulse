package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MoveComponent implements Component {
    public float cosAngle = 0f; // For x-direction
    public float sinAngle = 0f;  // For y-direction
    public float speed = 5f;      // Movement speed
    public Vector2 velocity = new Vector2(); // Current velocity
}

