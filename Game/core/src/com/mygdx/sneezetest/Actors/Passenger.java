package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sneezetest.Stages.GameStage;

import java.util.Random;

public class Passenger extends BaseActor {

    public static final int CONTINUE = 0;
    public static final int WALK = 1;
    public static final int IDLE = 2;
    Rectangle mapSize;
    private Vector2 target;

    private boolean locked = false;

    public Passenger(Texture t, World world, Vector2 pos, Rectangle mapsize) {
        mapSize = mapsize;
        texture = t;
        setAnimations();
        createBody(pos, world);
        target = new Vector2(
                getRandomFromRange((int) mapSize.x, (int) mapSize.width),
                getRandomFromRange((int) mapSize.y, (int) mapSize.height)
        );
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
        if (isNearTarget()) {
            target = generateTarget();
        }
        pushTo(generateForceVector());
    }

    private Vector2 generateTarget() {
        return new Vector2(
                getRandomFromRange((int) mapSize.x, (int) mapSize.width),
                getRandomFromRange((int) mapSize.y, (int) mapSize.height)
        );
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
        return dir.nor().scl(400f);
    }

    public boolean isLocked() {
        return locked;
    }

    private float getRandomFromRange(Integer start, Integer end) {
        Random r = new Random();
        return (start + r.nextFloat() * (end - start)) * GameStage.PIXEL_TO_METER;
    }
}
