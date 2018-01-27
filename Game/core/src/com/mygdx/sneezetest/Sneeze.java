package com.mygdx.sneezetest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.sneezetest.Actors.Player;
import com.mygdx.sneezetest.StageHandler.StageHandler;
import com.mygdx.sneezetest.Stages.GameStage;
import com.mygdx.sneezetest.Stages.MenuStage;
import com.mygdx.sneezetest.Supervisor.Supervisor;

public class Sneeze extends ApplicationAdapter {

    @Override
    public void create() {
        StageHandler.setActiveStage(new MenuStage(new ScreenViewport()));
    }

    @Override
    public void render() {
        Stage activeStage = StageHandler.getActiveStage();
        activeStage.act();
        activeStage.draw();

        Gdx.input.setInputProcessor(activeStage);
    }

    @Override
    public void dispose() {Stage activeStage = StageHandler.getActiveStage();
        activeStage.act();
        activeStage.draw();
    }
}
