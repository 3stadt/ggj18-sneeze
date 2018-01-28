package com.mygdx.sneezetest.Supervisor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sneezetest.Actors.Passenger;
import com.mygdx.sneezetest.Stages.GameStage;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Supervisor {

    private final World world;
    private Collection<Passenger> entities;

    Rectangle mapSize;

    public Supervisor(World w, MapProperties prop) {
        world = w;
        mapSize = new Rectangle();
        mapSize.x = 32;
        mapSize.y = 32;
        mapSize.width = prop.get("width", Integer.class) * 32 - 32;
        mapSize.height = prop.get("height", Integer.class) * 32 - 32;
        entities = new ArrayList<Passenger>();
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

        entity.walk();
    }

    public void createEntities(Integer amountEntities) {
        for (int i = 0; i < amountEntities; i++) {
            createRandomEntity();
        }
    }

    private void createRandomEntity() {
        Passenger entity = new Passenger(
                new Texture("betty.png"),
                world,
                getSpawnPos(),
                mapSize
        );

        entities.add(entity);
    }

    private Vector2 getSpawnPos() {
        return new Vector2(
                getRandomFromRange((int) mapSize.x, (int) mapSize.width),
                getRandomFromRange((int) mapSize.y, (int) mapSize.height)
        );
    }

    private float getRandomFromRange(Integer start, Integer end) {
        Random r = new Random();
        return start + r.nextFloat() * (end - start);
    }
}