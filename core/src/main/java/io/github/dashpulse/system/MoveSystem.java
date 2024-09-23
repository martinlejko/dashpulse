package io.github.dashpulse.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.component.MoveComponent;
import io.github.dashpulse.component.PhysicsComponent;

public class MoveSystem extends IteratingSystem {
    private static final Logger logger = new Logger(MoveSystem.class.getName(), Logger.DEBUG);
    private final ComponentMapper<MoveComponent> moveComponentMapper = ComponentMapper.getFor(MoveComponent.class);
    private final ComponentMapper<PhysicsComponent> physicsComponentMapper = ComponentMapper.getFor(PhysicsComponent.class);
    private static final float MAX_VELOCITY = 100f; // Set your maximum desired velocity

    public MoveSystem() {
        super(Family.all(MoveComponent.class, PhysicsComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MoveComponent move = moveComponentMapper.get(entity);
        PhysicsComponent physics = physicsComponentMapper.get(entity);

        if (move != null && physics != null) {
            // Update the cooldown timer
            if (move.blinkCooldownTimer > 0) {
                move.blinkCooldownTimer -= deltaTime;
            }

            if (move.isBlinking && move.blinkPosition != null && move.blinkCooldownTimer <= 0) {
                // Update the position to the blink position
                physics.body.setTransform(move.blinkPosition, physics.body.getAngle());
                move.isBlinking = false; // Reset the blinking flag
                move.blinkCooldownTimer = move.blinkCooldown; // Reset the cooldown timer
            } else {
                // Calculate the desired velocity based on movement direction
                float desiredVelocityX = move.cosAngle * move.speed;
                float desiredVelocityY = move.sinAngle * move.speed;

                // Set the linear velocity directly
                physics.body.setLinearVelocity(desiredVelocityX, desiredVelocityY);

                // Limit the maximum velocity to prevent sliding or excessive speeds
                if (physics.body.getLinearVelocity().len() > MAX_VELOCITY) {
                    physics.body.setLinearVelocity(physics.body.getLinearVelocity().nor().scl(MAX_VELOCITY));
                }
            }
        }
    }
}
