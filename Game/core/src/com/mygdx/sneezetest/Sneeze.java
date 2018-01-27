package com.mygdx.sneezetest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sneezetest.Actors.Player;

public class Sneeze extends ApplicationAdapter {
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private Player player;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        world = new World(new Vector2(0, 0), true);
        batch = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.position.x += 10;
        camera.position.y += 50;

        player = new Player(new Texture(Gdx.files.internal("Betty_32x32.png")), world);

        camera.update();

        tiledMap = new TmxMapLoader().load("maps/mall01.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("CollPlants").getObjects());
        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("CollWalls").getObjects());
        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("CollShops").getObjects());

        world.setContactListener(new CollisionHandler());
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render() {
        update();
        setOpenGlOptions();

        batch.setProjectionMatrix(camera.combined);

        movePlayer();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        drawBatch();

        camera.update();
        debugRenderer.render(world, camera.combined);
    }

    private void update(){
        world.step(1/60f, 6, 2);
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
        Vector2 playerVec = player.getHitbox();

        float velocity = 4;
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed && upPressed || leftPressed && downPressed || rightPressed && downPressed || rightPressed && upPressed) {
            velocity = (float) Math.sqrt(2) * (velocity / 2);
        }

        if (leftPressed) {
            player.setPos(playerVec.x - velocity, playerVec.y);
        }
        if (rightPressed) {
            player.setPos(playerVec.x + velocity, playerVec.y);
        }
        if (upPressed) {
            player.setPos(playerVec.x, playerVec.y + velocity);
        }
        if (downPressed) {
            player.setPos(playerVec.x, playerVec.y - velocity);
        }

        camera.position.y = playerVec.y;
        camera.position.x = playerVec.x;
    }

    @Override
    public void dispose() {
        player.dispose();
        tiledMap.dispose();
        batch.dispose();
    }
}
