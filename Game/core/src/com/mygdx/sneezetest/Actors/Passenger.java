package com.mygdx.sneezetest.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sneezetest.ScreenInfo.Hud;
import com.mygdx.sneezetest.Stages.GameStage;
import com.mygdx.sneezetest.StageHandler.StageHandler;

import java.util.Random;

import static com.mygdx.sneezetest.Stages.GameStage.PIXEL_TO_METER;

public class Passenger extends BaseActor {

    public static final int CONTINUE = 0;
    public static final int WALK = 1;
    public static final int IDLE = 2;
    public static final int SNEEZE = 3;
    public static final int REINFECT = 4;

    protected final Array<Body> bodies;
    protected Rectangle mapSize;
    protected Vector2 target;

    protected boolean locked = false;
    protected float walkTime = 0.0f;
    protected boolean sick;

    private Texture normalTex;
    private Texture sickTex;

    public Passenger(Texture normalTexture, Texture sickTexture, World world, Vector2 pos, Rectangle mapsize) {
        normalTex = normalTexture;
        sickTex = sickTexture;
        mapSize = mapsize;
        texture = normalTex;
        setAnimations();
        createBody(pos, world);
        bodies = new Array<Body>();
        world.getBodies(bodies);
        target = generateTarget(0);
    }

    public int decide() {
        if (locked) {
            return CONTINUE;
        }

        int rand = (int) (Math.random() * 500);

        if (rand == 1) {
            return WALK;
        }

        if (rand == 2 && sick) {
            return SNEEZE;
        }

        return IDLE;
    }

    public void continueAction() {
        walkTime += Gdx.graphics.getDeltaTime();
        if (isNearTarget() || walkTime > 10) {
            locked = false;
            walkTime = 0;
            target = generateTarget(0);
            return;
        }
        pushTo(generateForceVector());
    }

    @Override
    void setDirection(Vector2 dir) {
        float x = Math.abs(dir.x);
        float y = Math.abs(dir.y);

        if (dir.x > 0) {
            direction = RIGHT;
            if (x < y) {
                if (dir.y < 0) {
                    direction = DOWN;
                    direction = UP;
                }
            }
        } else if (dir.x < 0) {
            direction = LEFT;
            if (x < y) {
                if (dir.y < 0) {
                    direction = DOWN;
                } else {
                    direction = UP;
                }
            }
        } else if (dir.y > 0) {
            direction = UP;
        } else if (dir.y < 0) {
            direction = DOWN;
        }

        stateTime += Gdx.graphics.getDeltaTime();
    }

    private Vector2 generateTarget(int tries) {
        Vector2 pos = new Vector2(
                getRandomFromRange((int) mapSize.x, (int) mapSize.width),
                getRandomFromRange((int) mapSize.y, (int) mapSize.height)
        );
        if (tries > 9) {
            return pos;
        }

        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getFixtureList().first().testPoint(pos)) {
                return generateTarget(tries + 1);
            }
        }
        return pos;
    }

    public void reinfect() {
        GameStage gameStage = (GameStage) StageHandler.getActiveStage();
        Vector2 bettyPos = gameStage.player.getBody().getPosition();
        float cameraWidth = Gdx.graphics.getWidth() * PIXEL_TO_METER;

        double bettyRadius = cameraWidth / 2 * Math.sqrt(2);

        double bettyTerroristDistance = Math.sqrt(
            Math.pow(bettyPos.x - body.getPosition().x, 2) + Math.pow(bettyPos.y - body.getPosition().y, 2)
        );

        //Player in range
        if (bettyTerroristDistance < bettyRadius) {
            return;
        }

        setSick();
    }

    public void walk() {
        locked = true;
        continueAction();
    }

    private boolean isNearTarget() {
        Vector2 pos = body.getPosition();
        float range = 100 * PIXEL_TO_METER;
        float x = pos.x - target.x;
        float y = pos.y - target.y;
        return Math.abs(x) <= range && Math.abs(y) <= range;
    }

    private Vector2 generateForceVector() {
        Vector2 pos = body.getPosition();
        Vector2 dir = new Vector2();
        dir.x = target.x - pos.x;
        dir.y = target.y - pos.y;
        return dir.nor().scl(1200f);
    }

    public boolean isLocked() {
        return locked;
    }

    private float getRandomFromRange(Integer start, Integer end) {
        Random r = new Random();
        return (start + r.nextFloat() * (end - start)) * PIXEL_TO_METER;
    }

    public void getHealed() {
        setHealed();
    }

    public void setHealed() {
        sick = false;
        texture = normalTex;
        setAnimations();
    }
    public void setSick() {
        sick = true;
        texture = sickTex;
        setAnimations();
    }

    public void getSneezedAt() {
        setSick();
    }

    public void sneeze() {
        if (facedEntity != null && facedEntity instanceof Passenger) {
            System.out.println("Sneeze");
            ((Passenger) facedEntity).getSneezedAt();
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sneeze.wav"));
            sound.play(1.0f);
        }
    }

    public void getKilled()
    {
        GameStage gameStage = (GameStage) StageHandler.getActiveStage();
        gameStage.supervisor.removeEntity(this);

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("killing.ogg"));
        sound.play(1.0f);

        world.destroyBody(collisionBody.fixture.getBody());
        world.destroyBody(getBody());

        this.dispose();
        this.body = null;
    }

    @Override
    protected BaseCollisionSensor setCollisionSensor(World world) {
        return new BaseCollisionSensor(world, this, 15, 10);
    }
}
