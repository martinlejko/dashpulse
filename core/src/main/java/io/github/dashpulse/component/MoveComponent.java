package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;

public class MoveComponent implements Component {
    public float speed = 2000f; // Speed of movement
    public float cosAngle = 0f; // For horizontal movement
    public float sinAngle = 0f; // For vertical movement
}
