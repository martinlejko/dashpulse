package io.github.dashpulse.input;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.component.MoveComponent;
import io.github.dashpulse.component.PlayerComponent;

import java.util.HashSet;
import java.util.Set;

public class PlayerKeyboardInputProcessor extends InputAdapter {
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final Engine engine;
    private final ComponentMapper<MoveComponent> moveMapper;
    private float playerCos = 0f;
    private float playerSin = 0f;

    public PlayerKeyboardInputProcessor(Engine engine) {
        this.engine = engine;
        this.moveMapper = ComponentMapper.getFor(MoveComponent.class);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (isMovementKey(keycode)) {
            pressedKeys.add(keycode);
            updateMovement();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        pressedKeys.remove(keycode);
        updateMovement();
        return true;
    }

    private void updateMovement() {
        playerCos = 0f;
        playerSin = 0f;

        if (pressedKeys.contains(Input.Keys.W)) {
            playerSin = 1f;
        }
        if (pressedKeys.contains(Input.Keys.S)) {
            playerSin = -1f;
        }
        if (pressedKeys.contains(Input.Keys.D)) {
            playerCos = 1f;
        }
        if (pressedKeys.contains(Input.Keys.A)) {
            playerCos = -1f;
        }

        // Normalize angles to allow for diagonal movement
        if (playerCos != 0 || playerSin != 0) {
            float length = (float) Math.sqrt(playerCos * playerCos + playerSin * playerSin);
            playerCos /= length;
            playerSin /= length;
        }

        // Update movement component for the player entity (this part depends on your system)
        // For example:
        for (Entity entity : engine.getEntitiesFor(Family.all(PlayerComponent.class).get())) {
            MoveComponent moveComponent = moveMapper.get(entity);
            if (moveComponent != null) {
                moveComponent.cosAngle = playerCos;
                moveComponent.sinAngle = playerSin;
            }
        }
    }

    private boolean isMovementKey(int keycode) {
        return keycode == Input.Keys.W || keycode == Input.Keys.A || keycode == Input.Keys.S || keycode == Input.Keys.D;
    }
}

