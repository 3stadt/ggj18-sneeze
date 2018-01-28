package com.mygdx.sneezetest.Stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sneezetest.StageHandler.StageHandler;
import com.mygdx.sneezetest.Stages.MenuActors.Background;
import com.mygdx.sneezetest.Stages.MenuActors.IntoAnimation;
import com.mygdx.sneezetest.Stages.MenuActors.Logo;
import com.mygdx.sneezetest.Stages.MenuActors.StartButton;

public class MenuStage extends Stage {

    public MenuStage(Viewport viewport) {
        super(viewport);

        addActor(new Background());
        addActor(new StartButton());
        addActor(new Logo());
        addActor(new IntoAnimation());
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    StageHandler.setActiveStage(new GameStage(new ScreenViewport()));
                }

                return true;
            }
        });
    }
}
