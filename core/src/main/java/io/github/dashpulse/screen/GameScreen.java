package io.github.dashpulse.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.dashpulse.component.*;
import io.github.dashpulse.event.MapChangeEvent;
import io.github.dashpulse.input.PlayerKeyboardInputProcessor;
import io.github.dashpulse.system.*;

import java.util.logging.Logger;

public class GameScreen implements Screen {
    private static final Logger logger = Logger.getLogger(GameScreen.class.getName());

    private final Engine engine = new Engine();
    private Stage stage;
    private Vector2 gravity = new Vector2(0, 0);
    World world = new World(gravity, true);

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Entity player;

    // For Tiled Map
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void show() {
        logger.info("Game screen shown");
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("graphics/GameObjects.atlas");

        // Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16f, 9f); // Set camera size to match your viewport (16:9)

        // Load the Tiled map
        tiledMap = new TmxMapLoader().load("maps/mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f); // Scale the map appropriately

        PlayerKeyboardInputProcessor playerKeyboardInputProcessor = new PlayerKeyboardInputProcessor(engine);
        // Add systems to the engine
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new MoveSystem());
        engine.addSystem(new AnimationSystem(textureAtlas));
        engine.addSystem(new PhysicSystem(world));
        engine.addSystem(new DebugSystem(world, camera));

        // Create the player entity
        player = new Entity();
        PlayerComponent playerComponent = new PlayerComponent();
        player.add(playerComponent);

        PositionComponent positionComponent = new PositionComponent(2, 4); // Initial position
        player.add(positionComponent);

        TextureComponent textureComponent = new TextureComponent();
        player.add(textureComponent);

        MoveComponent moveComponent = new MoveComponent();
        player.add(moveComponent);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);

        PhysicsComponent physicsComponent = PhysicsComponent.create(world, 0, 0, BodyDef.BodyType.DynamicBody, circleShape, 1, 0);
        player.add(physicsComponent);

        AnimationComponent animationComponent = new AnimationComponent();
        Animation<TextureRegion> idleAnimation = new Animation<>(1 / 8f, textureAtlas.findRegions("idle"));
        animationComponent.animation = idleAnimation;
        player.add(animationComponent);

        engine.addEntity(player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Set clear color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        // Update the physics world
        world.step(delta, 6, 2); // Step the physics world

        // Retrieve the player's position
        PositionComponent positionComponent = player.getComponent(PositionComponent.class);
        if (positionComponent != null) {
            // Center the camera around the player's position
            camera.position.set(positionComponent.x, positionComponent.y, 0);
        }

        // Update the camera and apply it to the renderer
        camera.update();
        mapRenderer.setView(camera);

        // Render the Tiled map first (as background)
        mapRenderer.render();

        // Render the game entities
        engine.update(delta); // Update all systems including the debug renderer
    }

    @Override
    public void resize(int width, int height) {
        // Update camera on resize
        camera.viewportWidth = 16f;
        camera.viewportHeight = 9f * (float) height / (float) width;
        camera.update();
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
        batch.dispose();
        textureAtlas.dispose();
        tiledMap.dispose();
        mapRenderer.dispose();
        stage.dispose();
        world.dispose();
    }
}

