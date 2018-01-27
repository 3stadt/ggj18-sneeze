package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sneezetest.Stages.GameStage;

import java.util.Random;

public class Passenger extends BaseActor {

    public static final int CONTINUE = 0;
    public static final int WALK = 1;
    public static final int IDLE = 2;
    private final Array<Body> bodies;
    private Rectangle mapSize;
    private Vector2 target;

    private boolean locked = false;
    private float walkTime = 0.0f;
    private boolean sick;


    public Passenger(Texture t, World world, Vector2 pos, Rectangle mapsize) {
        mapSize = mapsize;
        texture = t;
        setAnimations();
        createBody(pos, world);
        bodies = new Array<Body>();
        world.getBodies(bodies);
        target = generateTarget(0);
    }

    public int decide() {
        if (locked) {
            return CONTINUE;
        }

        int rand = (int) (Math.random() * 500);

        if (rand == 1) {
            return WALK;
        }

        return IDLE;
    }

    public void continueAction() {
        walkTime += Gdx.graphics.getDeltaTime();
        if (isNearTarget() || walkTime > 10) {
            locked = false;
            walkTime = 0;
            target = generateTarget(0);
            return;
        }
        pushTo(generateForceVector());
    }

    @Override
    void setDirection(Vector2 dir) {
        if (dir.x > 0) {
            direction = RIGHT;
            if (dir.x < dir.y) {
                if (dir.y < 0) {
                    direction = DOWN;
                } else {
                    direction = UP;
                }
            }
        } else if (dir.x < 0) {
            direction = LEFT;
            if (dir.x < dir.y) {
                if (dir.y < 0) {
                    direction = DOWN;
                } else {
                    direction = UP;
                }
            }
        } else if (dir.y > 0) {
            direction = UP;
        } else if (dir.y < 0) {
            direction = DOWN;
        }

        stateTime += Gdx.graphics.getDeltaTime();
    }

    private Vector2 generateTarget(int tries) {
        Vector2 pos = new Vector2(
                getRandomFromRange((int) mapSize.x, (int) mapSize.width),
                getRandomFromRange((int) mapSize.y, (int) mapSize.height)
        );
        if (tries > 9) {
            return pos;
        }
        for (Body body : bodies) {
            if (body.getFixtureList().first().testPoint(pos)) {
                return generateTarget(tries + 1);
            }
        }
        return pos;
    }

    public void walk() {
        locked = true;
        continueAction();
    }

    private boolean isNearTarget() {
        Vector2 pos = body.getPosition();
        float range = 100 * GameStage.PIXEL_TO_METER;
        float x = pos.x - target.x;
        float y = pos.y - target.y;
        return Math.abs(x) <= range && Math.abs(y) <= range;
    }

    private Vector2 generateForceVector() {
        Vector2 pos = body.getPosition();
        Vector2 dir = new Vector2();
        dir.x = target.x - pos.x;
        dir.y = target.y - pos.y;
        return dir.nor().scl(1200f);
    }

    public boolean isLocked() {
        return locked;
    }

    private float getRandomFromRange(Integer start, Integer end) {
        Random r = new Random();
        return (start + r.nextFloat() * (end - start)) * GameStage.PIXEL_TO_METER;
    }

    public void getHealed(){
        sick = false;
    }

    @Override
    protected BaseCollisionSensor setCollisionSensor(World world) {
        return new BaseCollisionSensor(world, this, 15, 10);
    }
}
