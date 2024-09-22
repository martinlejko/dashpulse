package io.github.dashpulse.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.dashpulse.component.PositionComponent;
import io.github.dashpulse.component.TextureComponent;


public class RenderSystem extends IteratingSystem {
    private final Batch batch;

    public RenderSystem(Batch batch) {
        super(Family.all(PositionComponent.class, TextureComponent.class).get());
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
        PositionComponent position = entity.getComponent(PositionComponent.class);
        TextureComponent texture = entity.getComponent(TextureComponent.class);


        if (texture.texture == null) {
            return;
        }

        batch.draw(texture.texture, position.x, position.y);
    }

}

