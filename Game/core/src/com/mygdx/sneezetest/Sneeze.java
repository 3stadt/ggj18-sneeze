package com.mygdx.sneezetest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.sneezetest.StageHandler.StageHandler;
import com.mygdx.sneezetest.Stages.MenuStage;

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
