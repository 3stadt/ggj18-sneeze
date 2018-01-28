package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.sneezetest.Stages.GameStage.PIXEL_TO_METER;

public class Player extends BaseActor{

    public Player(Texture t, World world) {
        texture = t;
        setAnimations();
        createBody(world);
    }
}
