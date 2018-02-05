package com.mygdx.sneezetest.Supervisor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sneezetest.Actors.Passenger;
import com.mygdx.sneezetest.Actors.Terrorist;
import com.mygdx.sneezetest.Stages.GameStage;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Supervisor {

    private final World world;
    public Collection<Passenger> entities;
    private int num_sick_percent = 20;
    private int spawned_sick = 0;
    public static int num_sick = 0;
    public static int current_sick = 0;
    private int badBuddy = getRandomIntFromRange(1, 10);
    boolean badBuddySpawned = false;
    public static float sneezeCooldown = 0.0f;
    private Array<Rectangle> collisionObjects;

    Rectangle mapSize;

    public Supervisor(World w, TiledMap tm) {
        world = w;
        String[] layerNames = {"CollWalls", "CollShops", "CollPlants"};
        collisionObjects = getCollisionObjects(tm, layerNames);
        mapSize = new Rectangle();
        mapSize.x = 128;
        mapSize.y = 128;
        mapSize.width = tm.getProperties().get("width", Integer.class) * 32 - 128;
        mapSize.height = tm.getProperties().get("height", Integer.class) * 32 - 128;
        entities = new ArrayList<Passenger>();
    }

    private Array<Rectangle> getCollisionObjects(TiledMap tm, String[] layerNames) {
        Array<Rectangle> rectangleList = new Array<Rectangle>();
        for (String layerName : layerNames) {
            MapLayer ml = tm.getLayers().get(layerName);
            if (ml == null) {
                continue;
            }
            MapObjects mo = ml.getObjects();
            for (MapObject object : mo) {
                if (object instanceof RectangleMapObject) {
                    Rectangle currentRectangle = ((RectangleMapObject) object).getRectangle();
                    rectangleList.add(currentRectangle);
                }
            }
        }
        return rectangleList;
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
            if (action == Passenger.SNEEZE) {
                e.sneeze();
                playSneezeSound();
            }

            if (action == Passenger.REINFECT) {
                e.reinfect();
            }
        }
    }

    private void playSneezeSound() {
        if (sneezeCooldown <= 0.0) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sneezes/" + ((int) (Math.random() * ((10 - 1) + 1)) + 1) + ".ogg"));
            sound.play(1.0f);
            sneezeCooldown = 5.0f;
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
        num_sick = (amountEntities * num_sick_percent / 100);
        Integer curTex = 0;
        for (int i = 0; i < amountEntities; i++) {

            curTex++;
            if (curTex.equals(badBuddy) && badBuddySpawned) {
                curTex++;
            } else if (curTex.equals(badBuddy)) {
                badBuddySpawned = true;
            }
            if (curTex > 20) {
                curTex = 1;
            }

            createRandomEntity(curTex, badBuddy);
        }
    }

    public void removeEntity(Passenger entity) {
        this.entities.remove(entity);
    }

    private void createRandomEntity(int texNum, int badBuddyTexNum) {
        boolean isBadBuddy = texNum == badBuddyTexNum;
        Passenger entity;

        if (isBadBuddy) {
            entity = new Terrorist(
                    new Texture(String.format("buddy/%02d.png", texNum)),
                    new Texture(String.format("buddy/%02dsick.png", texNum)),
                    world,
                    getSpawnPos(),
                    mapSize
            );
        } else {
            entity = new Passenger(
                    new Texture(String.format("buddy/%02d.png", texNum)),
                    new Texture(String.format("buddy/%02dsick.png", texNum)),
                    world,
                    getSpawnPos(),
                    mapSize
            );
        }

        if (spawned_sick < num_sick) {
            entity.setSick();
            spawned_sick++;
        }

        entities.add(entity);
    }

    private Vector2 getSpawnPos() {
        Vector2 v = new Vector2(
                getRandomFromRange((int) mapSize.x, (int) mapSize.width),
                getRandomFromRange((int) mapSize.y, (int) mapSize.height)
        );
        Rectangle collisionZone = new Rectangle();
        collisionZone.x = v.x;
        collisionZone.y = v.y;
        collisionZone.width = 32;
        collisionZone.height = 32;
        for (Rectangle r : collisionObjects) {
            if (r.overlaps(collisionZone)) {
                return getSpawnPos();
            }
        }
        return v;
    }

    private float getRandomFromRange(Integer start, Integer end) {
        Random r = new Random();
        return start + r.nextFloat() * (end - start);
    }

    private int getRandomIntFromRange(Integer min, Integer max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    private Vector2 createRandomVector() {
        return new Vector2(
                getRandomFromRange((int) mapSize.x, (int) mapSize.width),
                getRandomFromRange((int) mapSize.y, (int) mapSize.height)
        );
    }
}