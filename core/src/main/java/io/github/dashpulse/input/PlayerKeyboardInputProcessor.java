package io.github.dashpulse.input;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.component.MoveComponent;
import io.github.dashpulse.component.PlayerComponent;

public class PlayerKeyboardInputProcessor extends InputAdapter {
    private static final Logger logger = new Logger(PlayerKeyboardInputProcessor.class.getName(), Logger.DEBUG);
    private final Engine engine;
    private final ComponentMapper<MoveComponent> moveMapper;

    public PlayerKeyboardInputProcessor(Engine engine) {
        this.engine = engine;
        this.moveMapper = ComponentMapper.getFor(MoveComponent.class);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        updateMovement(keycode, 1f);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        updateMovement(keycode, 0f);
        return true;
    }

    private void updateMovement(int keycode, float value) {
        for (Entity entity : engine.getEntitiesFor(Family.all(PlayerComponent.class).get())) {
            MoveComponent moveComponent = moveMapper.get(entity);
            if (moveComponent != null) {
                switch (keycode) {
                    case Input.Keys.W:
                        moveComponent.sinAngle = value;
                        break;
                    case Input.Keys.S:
                        moveComponent.sinAngle = -value;
                        break;
                    case Input.Keys.D:
                        moveComponent.cosAngle = value;
                        break;
                    case Input.Keys.A:
                        moveComponent.cosAngle = -value;
                        break;
                }
                // Normalize the angles to allow diagonal movement
                if (moveComponent.cosAngle != 0 || moveComponent.sinAngle != 0) {
                    float length = (float) Math.sqrt(moveComponent.cosAngle * moveComponent.cosAngle + moveComponent.sinAngle * moveComponent.sinAngle);
                    moveComponent.cosAngle /= length;
                    moveComponent.sinAngle /= length;
                }
                logger.debug("cosAngle: " + moveComponent.cosAngle + ", sinAngle: " + moveComponent.sinAngle);
            }
        }
    }
}
