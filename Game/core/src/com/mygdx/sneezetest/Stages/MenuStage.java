package com.mygdx.sneezetest.Stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sneezetest.Stages.MenuActors.Background;
import com.mygdx.sneezetest.Stages.MenuActors.Logo;
import com.mygdx.sneezetest.Stages.MenuActors.StartButton;

public class MenuStage extends Stage {

    public MenuStage(Viewport viewport) {
        super(viewport);

        addActor(new Background());
        addActor(new StartButton());
        addActor(new Logo());
    }
}
