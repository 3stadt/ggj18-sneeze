package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.sneezetest.ScreenInfo.Hud;

public class Player extends BaseActor{
    public boolean isPassengerInFront = false;
    public Passenger passengerInFront;

    public Player(Texture t, World world) {
        texture = t;
        setAnimations();
        createBody(world);
    }

    @Override
    protected BaseCollisionSensor setCollisionSensor(World world) {
        return new BaseCollisionSensor(world, this, 10, 10);
    }

    @Override
    protected void updateCollisionSensorPos(Vector2 position, int direction) {
        collisionBody.updatePosition(position, direction);
    }

    public void heal() {
        if (facedEntity != null){
            if ( ((Passenger) facedEntity).direction == direction) {
                ((Passenger) facedEntity).getHealed();
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("healing.ogg"));
                sound.play(1.0f);
                Hud.IS_SYRINGE_USED = true;
            }
        }
    }

    public void kill() {
        if (facedEntity != null){
            ((Passenger) facedEntity).getKilled();
        }
    }
}
