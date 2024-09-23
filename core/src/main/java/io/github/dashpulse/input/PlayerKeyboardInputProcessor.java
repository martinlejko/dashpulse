package io.github.dashpulse.input;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import io.github.dashpulse.component.MoveComponent;
import io.github.dashpulse.component.PhysicsComponent;
import io.github.dashpulse.component.PlayerComponent;
import io.github.dashpulse.system.MoveSystem;

import java.util.HashSet;
import java.util.Set;

public class PlayerKeyboardInputProcessor extends InputAdapter {
    private static final Logger logger = new Logger(PlayerKeyboardInputProcessor.class.getName(), Logger.DEBUG);
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final Engine engine;
    private final ComponentMapper<MoveComponent> moveMapper;
    private final ComponentMapper<PhysicsComponent> physicsComponentMapper;
    private float playerCos = 0f;
    private float playerSin = 0f;

    public PlayerKeyboardInputProcessor(Engine engine) {
        this.engine = engine;
        this.moveMapper = ComponentMapper.getFor(MoveComponent.class);
        this.physicsComponentMapper = ComponentMapper.getFor(PhysicsComponent.class);
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
        if (pressedKeys.contains(Input.Keys.C)) {
            triggerBlink();
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
        return keycode == Input.Keys.W || keycode == Input.Keys.A || keycode == Input.Keys.S || keycode == Input.Keys.D || keycode == Input.Keys.C;
    }

    private void triggerBlink() {
        logger.debug("Blinking");
        float blinkDistance = 100f;
        for (Entity entity : engine.getEntitiesFor(Family.all(PlayerComponent.class).get())) {
            MoveComponent moveComponent = moveMapper.get(entity);
            PhysicsComponent physics = physicsComponentMapper.get(entity);

            if (moveComponent != null && physics != null) {
                moveComponent.isBlinking = true;
                Vector2 currentPosition = physics.body.getPosition();
                logger.debug("Current position: x: " + currentPosition.x + " y: " + currentPosition.y);// Get current position from PhysicsComponent
                moveComponent.blinkPosition = new Vector2(currentPosition)
                    .add(moveComponent.cosAngle * blinkDistance, moveComponent.sinAngle * blinkDistance);
                logger.debug("Blink position: x: " + moveComponent.blinkPosition.x + " y: " + moveComponent.blinkPosition.y);
            }
        }
    }
}

