package io.github.dashpulse.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.component.PhysicsComponent;
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
//            logger.debug("Texture or physics component is null for entity");
            return;
        }

        // Get the current position of the physics body
        float x = physics.body.getPosition().x;
        float y = physics.body.getPosition().y;

        float width = texture.texture.getRegionWidth() / 2f;
        float height = texture.texture.getRegionHeight() / 2f;

        logger.debug("Renderiddng entity at x: " + x + ", y: " + y);

        // Render the entity at the new position
        batch.draw(texture.texture, x - width, y - height);
    }
}

