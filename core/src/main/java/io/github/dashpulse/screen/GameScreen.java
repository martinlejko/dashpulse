package io.github.dashpulse.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.dashpulse.component.AnimationComponent;
import io.github.dashpulse.component.PositionComponent;
import io.github.dashpulse.component.TextureComponent;
import io.github.dashpulse.event.MapChangeEvent;
import io.github.dashpulse.system.AnimationSystem;
import io.github.dashpulse.system.RenderSystem;

import java.util.logging.Logger;

public class GameScreen implements Screen {
    private static final Logger logger = Logger.getLogger(GameScreen.class.getName());

    private final Engine engine = new Engine();
    private Stage stage = new Stage();

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Entity player;

    @Override
    public void show() {
        logger.info("Game screen shown");
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("graphics/GameObjects.atlas");

        //load map
        TiledMap map = new TmxMapLoader().load("/Users/martinlejko/Repos/github.com/martinlejko/dashpulse/assets/map/map.tmx");
        stage.getRoot().fire(new MapChangeEvent(map));
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new AnimationSystem(textureAtlas));

        player = new Entity();

        PositionComponent positionComponent = new PositionComponent(1, 1); // Set initial position
        player.add(positionComponent);

        TextureComponent textureComponent = new TextureComponent();
        player.add(textureComponent);

        AnimationComponent animationComponent = new AnimationComponent();
        Animation<TextureRegion> idleAnimation = new Animation<>(1 / 8f, textureAtlas.findRegions("death"));
        animationComponent.animation = idleAnimation;
        player.add(animationComponent);

        engine.addEntity(player);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color (black in this case)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the color buffer

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
