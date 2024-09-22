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
    private final TextureAtlas textureAtlas; // TextureAtlas to fetch animations
    private final HashMap<String, Animation<TextureRegion>> animationCache = new HashMap<>(); // Animation cache

    public AnimationSystem(TextureAtlas textureAtlas) {
        super(Family.all(AnimationComponent.class, TextureComponent.class).get());
        this.textureAtlas = textureAtlas;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        TextureComponent textureComponent = entity.getComponent(TextureComponent.class);

        // Update the current stateTime for the animation
        animationComponent.stateTime += deltaTime;

        // Check if we need to switch animations
        if (!animationComponent.nextAnimation.isEmpty()) {
            animationComponent.animation = getAnimation(animationComponent.nextAnimation);
            animationComponent.stateTime = 0f; // Reset animation time on switch
            animationComponent.nextAnimation = ""; // Clear next animation after switching
        }

        // Get the current frame of the animation and update the texture component
        TextureRegion currentFrame = animationComponent.animation.getKeyFrame(animationComponent.stateTime, true);
        textureComponent.texture = currentFrame.getTexture();

        // Log to see if the animation frame is being updated
        System.out.println("Updating texture for entity with animation frame: " + currentFrame);
    }

    // Method to fetch or create animations from the atlas, caching them for performance
    public Animation<TextureRegion> getAnimation(String animationKey) {
        // Check if the animation is already cached
        if (animationCache.containsKey(animationKey)) {
            return animationCache.get(animationKey);
        }

        // Load animation from TextureAtlas
        var regions = textureAtlas.findRegions(animationKey);
        if (regions.size == 0) {
            throw new IllegalArgumentException("No regions found for " + animationKey);
        }

        // Create new animation with the regions, set to play at 15 FPS
        Animation<TextureRegion> animation = new Animation<>(1 / 15f, regions);
        animationCache.put(animationKey, animation); // Cache the animation

        return animation;
    }
}
