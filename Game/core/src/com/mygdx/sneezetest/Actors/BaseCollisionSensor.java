package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.sneezetest.Actors.BaseActor.*;
import static com.mygdx.sneezetest.Stages.GameStage.PIXEL_TO_METER;

public class BaseCollisionSensor {
    private Body collisionBody;
    private BaseActor sensorOwner;
    private Fixture fixture;

    public BaseCollisionSensor(World world, final BaseActor sowner, int hx, int hy) {
        this.sensorOwner = sowner;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        collisionBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hx * PIXEL_TO_METER, hy * PIXEL_TO_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixture = collisionBody.createFixture(fixtureDef);
        fixture.setUserData(sensorOwner);
        shape.dispose();
    }

    public void updatePosition(Vector2 position, int direction){
        position.add(3 * PIXEL_TO_METER, 2 * PIXEL_TO_METER);

        if (direction == LEFT) {
            position.x -= 20 * PIXEL_TO_METER;
        } else if (direction == RIGHT) {
            position.x += 20 * PIXEL_TO_METER;
        } else if (direction == UP) {
            position.y += 20 * PIXEL_TO_METER;
        } else if (direction == DOWN) {
            position.y -= 20 * PIXEL_TO_METER;
        }

        collisionBody.setTransform(position, 0);
    }
}
