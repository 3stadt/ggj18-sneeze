package com.mygdx.sneezetest.Stages.MenuActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Logo extends Actor {

    private Texture itemTexture;
    private Vector2 pos;

    public Logo() {
        itemTexture = new Texture(Gdx.files.internal("game_logo.png"));
        pos = new Vector2(640 - itemTexture.getWidth() / 2, 300);
        setBounds(pos.x, pos.y, itemTexture.getWidth(), itemTexture.getHeight());
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(itemTexture, pos.x, pos.y);
    }
}
