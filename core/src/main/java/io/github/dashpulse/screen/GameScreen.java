package io.github.dashpulse.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.dashpulse.component.AnimationComponent;
import io.github.dashpulse.component.PositionComponent;
import io.github.dashpulse.component.TextureComponent;
import io.github.dashpulse.system.AnimationSystem;
import io.github.dashpulse.system.RenderSystem;

import java.util.logging.Logger;

public class GameScreen implements Screen {
    private static final Logger logger = Logger.getLogger(GameScreen.class.getName());

    private final Engine engine = new Engine();
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Entity player;

    @Override
    public void show() {
        // Initialize SpriteBatch and TextureAtlas
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("graphics/GameObjects.atlas");

        // Check if the atlas contains the "idle" animation region
        if (textureAtlas.findRegion("idle") == null) {
            throw new IllegalArgumentException("idle region not found in the atlas");
        }

        // Add systems to the engine
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new AnimationSystem(textureAtlas));

        // Create the player entity directly in the GameScreen
        player = new Entity();

        // Add PositionComponent to the player
        PositionComponent positionComponent = new PositionComponent(1, 1); // Set initial position
        player.add(positionComponent);

        // Add TextureComponent to the player (empty for now, will be updated by AnimationSystem)
        TextureComponent textureComponent = new TextureComponent();
        player.add(textureComponent);

        // Add AnimationComponent to the player with the "idle" animation
        AnimationComponent animationComponent = new AnimationComponent();
        Animation<TextureRegion> idleAnimation = new Animation<>(1 / 15f, textureAtlas.findRegions("idle"));
        animationComponent.animation = idleAnimation;
        player.add(animationComponent);

        // Add the player entity to the engine
        engine.addEntity(player);
    }

    @Override
    public void render(float delta) {
        // Clear screen and update engine
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        // Handle resizing if needed
    }

    @Override
    public void pause() {
        // Handle pause
    }

    @Override
    public void resume() {
        // Handle resume
    }

    @Override
    public void hide() {
        // Handle hide
    }

    @Override
    public void dispose() {
        // Clean up resources
        batch.dispose();
        textureAtlas.dispose();
    }
}
