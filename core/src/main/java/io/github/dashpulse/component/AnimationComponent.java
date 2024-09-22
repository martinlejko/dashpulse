package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation; // Current animation
    public float stateTime = 0f; // Time for animation progress
    public String atlasKey = ""; // Key for current animation in TextureAtlas
    public Animation.PlayMode playMode = Animation.PlayMode.LOOP; // Play mode
    public String nextAnimation = ""; // Next animation to transition to

    public void setNextAnimation(String atlasKey, AnimationType animationType) {
        this.atlasKey = atlasKey;
        this.nextAnimation = atlasKey + "/" + animationType.name().toLowerCase();

    }
}
