package io.github.dashpulse.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.dashpulse.component.AnimationComponent;
import io.github.dashpulse.component.TextureComponent;

import java.util.HashMap;

public class AnimationSystem extends IteratingSystem {
    private final TextureAtlas textureAtlas;
    private final HashMap<String, Animation<TextureRegion>> animationCache = new HashMap<>();

    public AnimationSystem(TextureAtlas textureAtlas) {
        super(Family.all(AnimationComponent.class, TextureComponent.class).get());
        this.textureAtlas = textureAtlas;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        TextureComponent textureComponent = entity.getComponent(TextureComponent.class);

        animationComponent.stateTime += deltaTime;

        // Check if we need to switch animations
        if (!animationComponent.nextAnimation.isEmpty()) {
            animationComponent.animation = getAnimation(animationComponent.nextAnimation);
            animationComponent.stateTime = 0f;
            animationComponent.nextAnimation = "";
        }

        TextureRegion currentFrame = animationComponent.animation.getKeyFrame(animationComponent.stateTime, true);
        textureComponent.texture = currentFrame;
    }

    // Method to fetch or create animations from the atlas, caching them for performance
    public Animation<TextureRegion> getAnimation(String animationKey) {
        if (animationCache.containsKey(animationKey)) {
            return animationCache.get(animationKey);
        }

        var regions = textureAtlas.findRegions(animationKey);
        if (regions.size == 0) {
            throw new IllegalArgumentException("No regions found for " + animationKey);
        }

        Animation<TextureRegion> animation = new Animation<>(1 / 15f, regions);
        animationCache.put(animationKey, animation);

        return animation;
    }
}
