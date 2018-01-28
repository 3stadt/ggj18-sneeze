package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Passerby {
    float speed;
    boolean locked = false;
    boolean sick = false;

    Vector2 walkTarget;
    public Texture texture;
    public Rectangle rect;

    public Passerby(float s, Texture t, Vector2 pos) {
        speed = s;
        texture = t;

        rect = new Rectangle(pos.x, pos.y, t.getWidth(), t.getHeight());
    }

    public String decide() {
        if (locked) {
            return "continue";
        }

        int rand = (int) (Math.random() * 500);

        if (rand == 1) {
            return "walk";
        }

        return "idle";
    }

    public void walk(Vector2 targetPos) {
        walkTarget = targetPos;
        locked = true;
        continueAction();
    }

    public void continueAction() {
        float maxDistance = speed * Gdx.graphics.getDeltaTime();
        Vector2 tmp = new Vector2();

        tmp.set(walkTarget.x, walkTarget.y).sub(rect.x, rect.y);

        if (tmp.len() <= maxDistance) {
            rect.x = walkTarget.x;
            rect.y = walkTarget.y;
            locked = false;
        } else {
            tmp.nor().scl(maxDistance);
            rect.x += tmp.x;
            rect.y += tmp.y;
        }
    }

    public boolean isLocked() {
        return locked;
    }
}