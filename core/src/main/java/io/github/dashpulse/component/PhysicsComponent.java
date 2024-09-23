package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsComponent implements Component {
    public Body body;
    public Vector2 impulse = new Vector2();

    public static PhysicsComponent create(World world, float x, float y, BodyDef.BodyType bodyType, Shape shape, float density, float friction) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x + shape.getRadius(), y + shape.getRadius()); // Adjust as needed

        // Create the body in the provided world
        physicsComponent.body = world.createBody(bodyDef);

        // Add the fixture to the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        physicsComponent.body.createFixture(fixtureDef);

        shape.dispose();
        return physicsComponent;
    }
}

