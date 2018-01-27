package com.mygdx.sneezetest.Supervisor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sneezetest.Actors.Passenger;
import com.mygdx.sneezetest.Actors.Passerby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Supervisor {

    private final World world;
    private Integer amountEntities = 60;
    private Collection<Passenger> entities;

    private int worldStartX = 32;
    private int worldEndX = 1216;
    private int worldStartY = 32;
    private int worldEndY = 1856;

    public Supervisor(World w) {
        world = w;
        entities = new ArrayList<Passenger>();
        createEntities(amountEntities);
    }

    public void loop() {
        for (Passenger e : entities) {
            int action = e.decide();

            if (action == Passenger.CONTINUE) {
                e.continueAction();
            }

            if (action == Passenger.WALK) {
                walkEntity(e);
            }
        }
    }

    public void drawEntities(SpriteBatch batch) {
        for (Passenger e : entities) {
            e.draw(batch);
        }
    }

    private void walkEntity(Passenger entity) {
        if (entity.isLocked()) {
            return;
        }

        entity.walk(
                new Vector2(
                        (int) getRandomFromRange(worldStartX, worldEndX),
                        (int) getRandomFromRange(worldStartY, worldEndY)
                )
        );
    }

    private void createEntities(Integer amountEntities) {
        for (int i = 0; i < amountEntities; i++) {
            createRandomEntity();
        }
    }

    private void createRandomEntity() {
        Passenger entity = new Passenger(
                new Texture("betty.png"),
                world,
                new Vector2(
                        getRandomFromRange(worldStartX, worldEndX),
                        getRandomFromRange(worldStartY, worldEndY)
                )
        );

        entities.add(entity);
    }

    private float getRandomFromRange(Integer start, Integer end) {
        Random r = new Random();
        return start + r.nextFloat() * (end - start);
    }
}