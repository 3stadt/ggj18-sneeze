package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Passenger extends BaseActor {

    public static final int CONTINUE = 0;
    public static final int WALK = 1;
    public static final int IDLE = 2;

    private boolean locked = false;

    public Passenger(Texture t, World world, Vector2 pos) {
        texture = t;
        setAnimations();
        createBody(pos, world);
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

    public void continueAction(){

    }

    public void walk(Vector2 target){

    }

    public boolean isLocked() {
        return locked;
    }
}
