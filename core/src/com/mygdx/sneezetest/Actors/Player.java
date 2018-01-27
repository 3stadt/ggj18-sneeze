package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Texture texture;
    private Rectangle hitbox;

    public Player(Texture t) {
        texture = t;
        hitbox = new Rectangle();
        setPos(300, 300);
        hitbox.width = 32;
        hitbox.height = 32;
    }

    public void setPos(float x, float y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, hitbox.x, hitbox.y);
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void dispose() {
        texture.dispose();
    }
}
