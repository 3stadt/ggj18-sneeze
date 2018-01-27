package com.mygdx.sneezetest.Supervisor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Supervisor {

    Integer amountEntities = 300;
    private Collection<Passerby> entities;

    private int worldStartX = 190;
    private int worldEndX = 600;
    private int worldStartY = 300;
    private int worldEndY = 680;

    public Supervisor() {
        entities = new ArrayList<Passerby>();
        createEntities(amountEntities);
    }

    public void loop() {
        for(Passerby e : entities) {
            String action = e.decide();

            if (action == "continue") {
                e.continueAction();
            }

            if (action == "walk") {
                walkEntity(e);
            }

            if (action == "reinfect") {
                //reinfect()
            }
        }
    }

    public void drawEntities(Batch batch) {
        for(Passerby e : entities) {
            batch.draw(e.texture, e.rect.x, e.rect.y);
        }
    }

    private void walkEntity(Passerby entity) {
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
        Passerby entity = new Passerby(
            getRandomFromRange(20, 40),
            new Texture("betty.png"),
            new Vector2(
                getRandomFromRange(worldStartX, worldEndX),
                getRandomFromRange(worldStartY, worldEndY)
            )
        );

        entities.add(entity);
    }

    private float getRandomFromRange(Integer start, Integer end) {
        Random r = new Random();
        float random = start + r.nextFloat() * (end - start);

        System.out.println(random);

        return random;
    }
}