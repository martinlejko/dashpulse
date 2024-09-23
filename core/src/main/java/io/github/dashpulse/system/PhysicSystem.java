package io.github.dashpulse.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.component.PhysicsComponent;
import io.github.dashpulse.component.PositionComponent;

public class PhysicSystem extends IteratingSystem {
    private static final Logger logger = new Logger(PhysicSystem.class.getName(), Logger.DEBUG);
    private final World world;
    private float accumulator = 0f;
    private static final float TIME_STEP = 1 / 60f; // 1/60 seconds

    public PhysicSystem(World world) {
        super(Family.all(PhysicsComponent.class).get());
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        if(world.getAutoClearForces()) {
            world.setAutoClearForces(false);
        }
        accumulator += deltaTime;

        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        super.update(deltaTime);
    }

    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);

        if (physicsComponent != null && positionComponent != null) {
            physicsComponent.body.setTransform(positionComponent.x, positionComponent.y, physicsComponent.body.getAngle());
        }

    }
}
