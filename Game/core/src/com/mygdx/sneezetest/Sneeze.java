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
import com.badlogic.gdx.math.Vector2;
import com.mygdx.sneezetest.Actors.Player;

public class Sneeze extends ApplicationAdapter {
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private MapObjects objects;
    private Player player;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.position.x += 10;
        camera.position.y += 50;

        player = new Player(new Texture(Gdx.files.internal("betty.png")));

        camera.update();

        tiledMap = new TmxMapLoader().load("maps/main.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        MapLayer collisionObjectLayer = tiledMap.getLayers().get("Bump");
        objects = collisionObjectLayer.getObjects();
    }

    @Override
    public void render() {
        setOpenGlOptions();

        batch.setProjectionMatrix(camera.combined);

        movePlayer();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        drawBatch();

        camera.update();
    }

    private void drawBatch() {
        batch.begin();
        player.draw(batch);
        batch.end();
    }

    private void setOpenGlOptions() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void movePlayer() {
        Rectangle hitbox = player.getHitbox();

        float oldPosX = hitbox.x;
        float oldPosY = hitbox.y;
        float newPosX = oldPosX;
        float newPosY = oldPosY;

        float velocity = 4 * 40 * Gdx.graphics.getDeltaTime();
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) {
            newPosX -= velocity;
        }
        if (rightPressed) {
            newPosX += velocity;
        }
        if (upPressed) {
            newPosY += velocity;
        }
        if (downPressed) {
            newPosY -= velocity;
        }
        if (leftPressed || rightPressed || upPressed || downPressed)
            player.setPos(new Vector2(newPosX, newPosY), velocity);

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, hitbox)) {
                player.setPos(new Vector2(oldPosX, oldPosY), velocity);
            }
        }

        camera.position.y = hitbox.y;
        camera.position.x = hitbox.x;
    }

    @Override
    public void dispose() {
        player.dispose();
        tiledMap.dispose();
        batch.dispose();
    }
}
