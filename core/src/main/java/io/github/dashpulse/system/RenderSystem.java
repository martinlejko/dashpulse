package io.github.dashpulse.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.component.PhysicsComponent;
import io.github.dashpulse.component.PositionComponent;
import io.github.dashpulse.component.TextureComponent;



public class RenderSystem extends IteratingSystem {
    private final Batch batch;
    private static final Logger logger = new Logger(RenderSystem.class.getName(), Logger.DEBUG);

    public RenderSystem(Batch batch) {
        super(Family.all(TextureComponent.class, PhysicsComponent.class).get());
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureComponent texture = entity.getComponent(TextureComponent.class);
        PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);

        if (texture.texture == null || physics == null) {
            return;
        }

        // Get the position from the physics body
        float x = physics.body.getPosition().x;
        float y = physics.body.getPosition().y;

        logger.debug("Rendering entity at x: " + x + ", y: " + y);
        batch.draw(texture.texture, x, y);
    }
}

