package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation;
    public float stateTime = 0f;
    public String atlasKey = "";
    public Animation.PlayMode playMode = Animation.PlayMode.LOOP;
    public String nextAnimation = "";

    public void setNextAnimation(String atlasKey, AnimationType animationType) {
        this.atlasKey = atlasKey;
        this.nextAnimation = atlasKey + "/" + animationType.name().toLowerCase();

    }
}
