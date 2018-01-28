package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.sneezetest.Stages.GameStage.PIXEL_TO_METER;

class BaseActor {
    private static final float FRAME_TIME = 1 / 10f;

    private static final int TEXTURE_WIDTH = 4;
    private static final int TEXTURE_HEIGHT = 4;

    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;

    private int direction;
    Texture texture;
    private Animation<TextureRegion>[] animations;
    private float stateTime = 0f;

    public Body body;

    void createBody(World world) {
        createBody(null, world);
    }

    void createBody(Vector2 pos, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(300 * PIXEL_TO_METER, 300 * PIXEL_TO_METER);
        if(pos != null){
            def.position.set(pos.x * PIXEL_TO_METER, pos.y * PIXEL_TO_METER);
        }
        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(4f * PIXEL_TO_METER, 4f * PIXEL_TO_METER));
        shape.setRadius(16f * PIXEL_TO_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        body.setLinearDamping(10f);

        MassData md = new MassData();
        md.mass = 60f;
        body.setMassData(md);

        body.setUserData(this);
        shape.dispose();
    }

    public void pushTo(Vector2 direction) {
        setDirection(direction);
        body.applyForceToCenter(direction, true);
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

    void setAnimations() {
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

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;

        currentFrame = animations[direction].getKeyFrame(stateTime, true);

        //batch.draw(currentFrame, body.getPosition().x - 16 * PIXEL_TO_METER, body.getPosition().y - 16 * PIXEL_TO_METER);
        batch.draw(currentFrame, body.getPosition().x - 16 * PIXEL_TO_METER, body.getPosition().y - 16 * PIXEL_TO_METER,
                0.7f, 0.7f);
    }

    public Vector2 getHitbox() {
        return body.getPosition();
    }

    public void dispose() {
        texture.dispose();
    }

    public void stopMoving() {
        body.setLinearVelocity(0, 0);
    }

    public void stand() {
        stateTime = 0;
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }
}
