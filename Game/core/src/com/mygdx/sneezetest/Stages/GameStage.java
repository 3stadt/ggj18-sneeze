package com.mygdx.sneezetest.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sneezetest.Actors.BaseActor;
import com.mygdx.sneezetest.Actors.Player;
import com.mygdx.sneezetest.ScreenInfo.Hud;
import com.mygdx.sneezetest.Supervisor.Supervisor;
import com.mygdx.sneezetest.TiledObjectUtil;

public class GameStage extends Stage {
    public static final float PIXEL_TO_METER = 0.02f;

    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private Player player;
    private Supervisor supervisor;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Hud hud;

    public GameStage(Viewport viewport) {
        super(viewport);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        world = new World(new Vector2(0, 0f), false);
        setContactListener();

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w * PIXEL_TO_METER, h * PIXEL_TO_METER);
        camera.position.x += 10 * PIXEL_TO_METER;
        camera.position.y += 50 * PIXEL_TO_METER;

        player = new Player(new Texture(Gdx.files.internal("betty.png")), world);

        camera.update();

        tiledMap = new TmxMapLoader().load("maps/mall01.tmx", new TmxMapLoader.Parameters());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, PIXEL_TO_METER);

        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("CollPlants").getObjects());
        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("CollWalls").getObjects());
        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("CollShops").getObjects());
        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("CollPlants").getObjects());

        supervisor = new Supervisor(world, tiledMap.getProperties());
        supervisor.createEntities(50);

        debugRenderer = new Box2DDebugRenderer();

        hud = new Hud();
    }

    private void setContactListener() {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if ((fixtureA.isSensor() && fixtureB.getUserData() instanceof BaseActor) ||
                        (fixtureB.isSensor() && fixtureA.getUserData() instanceof BaseActor)){
                    Fixture actor = fixtureA;
                    Fixture sensor = fixtureB;

                    if (fixtureA.isSensor()){
                        actor = fixtureB;
                        sensor = fixtureA;
                    }

                    if (sensor.getUserData().equals(actor.getUserData())){
                        return;
                    }

                    ((BaseActor) sensor.getUserData()).facedEntity = (BaseActor) actor.getUserData();
                }

            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if ((fixtureA.isSensor() && fixtureB.getUserData() instanceof BaseActor) ||
                        (fixtureB.isSensor() && fixtureA.getUserData() instanceof BaseActor)){
                    Fixture actor = fixtureA;
                    Fixture sensor = fixtureB;

                    if (fixtureA.isSensor()){
                        actor = fixtureB;
                        sensor = fixtureA;
                    }

                    if (sensor.getUserData().equals(actor.getUserData())){
                        return;
                    }

                    ((BaseActor) sensor.getUserData()).facedEntity = null;
                }

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
    }

    @Override
    public void draw() {
        setOpenGlOptions();

        batch.setProjectionMatrix(camera.combined);

        movePlayer();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        supervisor.loop();

        drawBatch();

        camera.update();
        debugRenderer.render(world, camera.combined);
        update();

        Hud.TIME_FLOAT += Gdx.graphics.getDeltaTime();
        hud.getStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        hud.update();
        hud.getStage().draw();
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
    }

    private void drawBatch() {
        batch.begin();
        player.draw(batch);
        supervisor.drawEntities(batch);
        batch.end();
    }

    private void setOpenGlOptions() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void movePlayer() {
        Vector2 moveDirection = new Vector2();

        float velocity = 1000000 * Gdx.graphics.getDeltaTime();

        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean diagonal = (leftPressed && upPressed) ||
                           (leftPressed && downPressed) ||
                           (rightPressed && upPressed) ||
                           (rightPressed && downPressed);
        boolean spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        player.body.setLinearVelocity(0, 0);

        if (leftPressed) {
            moveDirection.sub(1f, 0f);
        }
        if (rightPressed) {
            moveDirection.add(1f, 0f);
        }
        if (upPressed) {
            moveDirection.add(0f, 1f);
        }
        if (downPressed) {
            moveDirection.sub(0f, 1f);
        }

        if (diagonal) {
            moveDirection.scl((float) (velocity / Math.sqrt(2)));
        } else {
            moveDirection.scl(velocity);
        }

        if (leftPressed || rightPressed || upPressed || downPressed) {
            player.pushTo(moveDirection);
        } else {
            player.stopMoving();
            player.stand();
        }

        camera.position.y = player.getHitbox().y;
        camera.position.x = player.getHitbox().x;

        if (spacePressed){
            player.heal();
        }
    }


    @Override
    public void dispose() {
        player.dispose();
        tiledMap.dispose();
        batch.dispose();
    }
}
