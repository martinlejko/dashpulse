package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Logger;

public class PositionComponent implements Component {
    public float x = 0;
    public float y = 0;
    private static final Logger logger = new Logger(PositionComponent.class.getName(), Logger.DEBUG);

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
        logger.debug("PositionComponent created at x: " + x + ", y: " + y);
    }
}
