package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
    private BodyDef def;
    private Texture texture;
    private Body body;

    public Player(Texture t, World world) {

        texture = t;
        def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(300, 300);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / 2, 32 / 2);
        body.createFixture(shape, 1.0f);
        shape.dispose();
    }

    public void setPos(float x, float y) {
        def.position.set(x, y);
        body.applyForceToCenter(x, y, true);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, def.position.x - 16, def.position.y - 16);
    }

    public Vector2 getHitbox() {
        return def.position;
    }

    public void dispose() {
        texture.dispose();
    }
}
