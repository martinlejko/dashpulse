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
            // Calculate the desired velocity based on movement direction
            float desiredVelocityX = move.cosAngle * move.speed;
            float desiredVelocityY = move.sinAngle * move.speed;

            // Set the linear velocity directly
            physics.body.setLinearVelocity(desiredVelocityX, desiredVelocityY);

            // Limit the maximum velocity to prevent sliding or excessive speeds
            if (physics.body.getLinearVelocity().len() > MAX_VELOCITY) {
                physics.body.setLinearVelocity(physics.body.getLinearVelocity().nor().scl(MAX_VELOCITY));
            }

            logger.debug("Moving entity with velocity: " + desiredVelocityX + ", " + desiredVelocityY);
        }
    }
}
