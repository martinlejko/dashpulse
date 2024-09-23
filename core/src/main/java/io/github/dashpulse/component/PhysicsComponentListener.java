package io.github.dashpulse.component;

import com.badlogic.ashley.core.Entity;

public class PhysicsComponentListener {

    public void onCollision(Entity entityA, Entity entityB) {
        // Handle collision logic between entityA and entityB
        System.out.println("Collision detected between: " + entityA + " and " + entityB);
    }

    public void onTrigger(Entity entity) {
        // Handle trigger logic when an entity enters a sensor
        System.out.println("Trigger activated for entity: " + entity);
    }
}
