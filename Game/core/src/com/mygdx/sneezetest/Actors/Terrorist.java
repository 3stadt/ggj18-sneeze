package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.sneezetest.StageHandler.StageHandler;
import com.mygdx.sneezetest.Stages.GameStage;
import com.mygdx.sneezetest.Stages.WinStage;

public class Terrorist extends Passenger {
    public Terrorist(Texture normalTexture, Texture sickTexture, World world, Vector2 pos, Rectangle mapsize) {
        super(normalTexture, sickTexture, world, pos, mapsize);
    }

    public int decide() {
        if (locked) {
            return CONTINUE;
        }

        int rand = (int) (Math.random() * 500);

        if (rand == 1) {
            return WALK;
        }

        if (rand == 2 || rand == 3) {
            return SNEEZE;
        }

        if (rand == 4 || rand == 5) {
            return REINFECT;
        }

        return IDLE;
    }

    @Override
    public void getKilled() {
        GameStage gameStage = (GameStage) StageHandler.getActiveStage();
        gameStage.backgroundMusic1.stop();
        gameStage.backgroundMusic2.stop();

        StageHandler.setActiveStage(new WinStage(new ScreenViewport()));

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("winning.ogg"));
        sound.play();

    }

    protected BaseCollisionSensor setCollisionSensor(World world) {
        return new BaseCollisionSensor(world, this, 15, 15);
    }
}
