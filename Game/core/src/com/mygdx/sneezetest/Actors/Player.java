package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private static final float FRAME_TIME = 1 / 10f;

    private static final int TEXTURE_WIDTH = 4;
    private static final int TEXTURE_HEIGHT = 4;

    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;

    private int direction;
    private Texture texture;
    private Animation<TextureRegion>[] animations;
    private float stateTime = 0f;

    private BodyDef def;
    private Body body;


    public Player(Texture t, World world) {
        texture = t;

        setAnimations();

        def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(300, 300);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / 2, 32 / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.restitution = 0.5f;
        body.setLinearDamping(100f);

        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
    }

    private void setDirection(Vector2 dir) {
        if (dir.x > 0) {
            direction = RIGHT;
        } else if (dir.x < 0) {
            direction = LEFT;
        } else if (dir.y > 0) {
            direction = UP;
        } else if (dir.y < 0) {
            direction = DOWN;
        }

        stateTime += Gdx.graphics.getDeltaTime();
    }

    private void setAnimations() {
        animations = new Animation[4];
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / TEXTURE_WIDTH, texture.getHeight() / TEXTURE_HEIGHT);
        TextureRegion[] tmp2 = new TextureRegion[TEXTURE_HEIGHT * TEXTURE_WIDTH];

        int index = 0;
        for (int i = 0; i < TEXTURE_WIDTH; i++) {
            for (int j = 0; j < TEXTURE_HEIGHT; j++) {
                tmp2[index++] = tmp[j][i];
            }
        }

        animations[DOWN] = new Animation<TextureRegion>(FRAME_TIME, tmp2[0], tmp2[1], tmp2[2], tmp2[3]);
        animations[LEFT] = new Animation<TextureRegion>(FRAME_TIME, tmp2[4], tmp2[5], tmp2[6], tmp2[7]);
        animations[UP] = new Animation<TextureRegion>(FRAME_TIME, tmp2[8], tmp2[9], tmp2[10], tmp2[11]);
        animations[RIGHT] = new Animation<TextureRegion>(FRAME_TIME, tmp2[12], tmp2[13], tmp2[14], tmp2[15]);
    }

    public void setPos(Vector2 direction, float vel) {
        Vector2 pos = body.getPosition();
        direction.sub(pos);
        setDirection(direction);

        direction = direction.nor().scl(vel);
        pos.add(direction);

        body.setLinearVelocity(pos);
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;

        currentFrame = animations[direction].getKeyFrame(stateTime, true);

        batch.draw(currentFrame, body.getPosition().x - 16, body.getPosition().y - 16);
    }

    public Vector2 getHitbox() {
        return body.getPosition();
    }

    public void dispose() {
        texture.dispose();
    }
}
