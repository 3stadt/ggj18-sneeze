package com.mygdx.sneezetest.Stages.MenuActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.beans.VetoableChangeListener;

public class IntoAnimation extends Actor {
    private Texture texture;
    private TextureRegion region;
    private Animation<TextureRegion> animation;
    private Vector2 pos;
    private float stateTime;
    private boolean isPlayed = false;

    private Sound sound;

    public IntoAnimation(){
        texture = new Texture(Gdx.files.internal("intro_animation.png"));
        region = new TextureRegion(texture);
        pos = new Vector2(850, 300);

        sound = Gdx.audio.newSound(Gdx.files.internal("sneeze_betty.ogg"));

        //28
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth(),texture.getHeight() / 28);
        TextureRegion[] tmp2 = new TextureRegion[28];
        int index = 0;
        for (int i = 0 ; i < 28 ; i++)
        {
            for ( int j = 0 ; j < 1 ; j++ ){
                tmp2[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(1/12f, tmp2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        stateTime += Gdx.graphics.getDeltaTime();

        if (Math.floor(stateTime / (1/12f)) == 18 && !isPlayed){
            sound.play(1f);
            isPlayed = true;
        }

        batch.draw(currentFrame, pos.x, pos.y);
    }
}
