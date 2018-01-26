package com.mygdx.sneezetest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Sneeze extends ApplicationAdapter {
    private TiledMap tiledMap;
    private Texture bettyImage;
    private Rectangle betty;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private MapObjects objects;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.position.x += 10;
        camera.position.y += 50;

        bettyImage = new Texture(Gdx.files.internal("betty.png"));
        betty = new Rectangle();
        betty.x = camera.position.x;
        betty.y = camera.position.y;
        betty.width = 32;
        betty.height = 32;

        camera.update();

        tiledMap = new TmxMapLoader().load("maps/main.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        int objectLayerId = 4;
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(objectLayerId);
        objects = collisionObjectLayer.getObjects();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        float oldPosX = betty.x;
        float oldPosY = betty.y;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            betty.x -= 200 * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            betty.x += 200 * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            betty.y += 200 * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            betty.y -= 200 * Gdx.graphics.getDeltaTime();
        }

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, betty)) {
                betty.x = oldPosX;
                betty.y = oldPosY;
            }
        }

        camera.position.y = betty.y;
        camera.position.x = betty.x;

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        batch.draw(bettyImage, betty.x, betty.y);
        batch.end();
        camera.update();
    }

    @Override
    public void dispose() {
        bettyImage.dispose();
        tiledMap.dispose();
        batch.dispose();
    }
}
