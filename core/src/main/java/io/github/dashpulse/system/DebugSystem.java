package io.github.dashpulse.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class DebugSystem extends IntervalSystem {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private final Camera camera;

    public DebugSystem(World world, Camera camera) {
        super(1 / 60f); // Update at the same interval as the physics
        this.world = world;
        this.camera = camera;
        this.debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    protected void updateInterval() {
        // Render the debug information
        debugRenderer.render(world, camera.combined);
    }
}

