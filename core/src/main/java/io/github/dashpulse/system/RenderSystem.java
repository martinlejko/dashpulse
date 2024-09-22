package io.github.dashpulse.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.dashpulse.component.PositionComponent;
import io.github.dashpulse.component.TextureComponent;


public class RenderSystem extends IteratingSystem {
    private final Batch batch; // SpriteBatch for rendering

    public RenderSystem(Batch batch) {
        // Family requires PositionComponent and TextureComponent
        super(Family.all(PositionComponent.class, TextureComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        TextureComponent texture = entity.getComponent(TextureComponent.class);

        // Log to see if entities are being processed
        System.out.println("Rendering entity at position: " + position.x + ", " + position.y);

        if (texture.texture == null) {
            System.out.println("Texture is null for entity at position: " + position.x + ", " + position.y);
            return;
        }

        batch.begin();
        batch.draw(
            texture.texture,
            position.x, position.y,  // Position
            1f, 1f                   // Width and height (can adjust based on your game)
        );
        batch.end();
    }

}

