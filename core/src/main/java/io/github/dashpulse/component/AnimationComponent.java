package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.system.MoveSystem;

public class AnimationComponent implements Component {
    private static final Logger logger = new Logger(AnimationComponent.class.getName(), Logger.DEBUG);
    public Animation<TextureRegion> animation;
    public float stateTime = 0f;
    public String atlasKey = "";
    public Animation.PlayMode playMode = Animation.PlayMode.LOOP;
    public String nextAnimation = "";

    public void setNextAnimation(String atlasKey, AnimationType animationType) {
        logger.debug("Setting next animation to " + atlasKey + "/" + animationType.name().toLowerCase());
        this.atlasKey = atlasKey;
        this.nextAnimation = atlasKey + "/" + animationType.name().toLowerCase();

    }
}
