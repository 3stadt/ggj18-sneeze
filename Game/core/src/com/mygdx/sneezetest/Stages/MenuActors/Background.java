package com.mygdx.sneezetest.Stages.MenuActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor
{
    private Texture itemTexture;
    public Background() {
        itemTexture = new Texture(Gdx.files.internal("menu_background.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(itemTexture, 0,0);
    }
}
