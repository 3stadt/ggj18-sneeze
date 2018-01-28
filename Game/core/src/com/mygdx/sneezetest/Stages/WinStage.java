package com.mygdx.sneezetest.Stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sneezetest.Stages.MenuActors.WinBackground;

public class WinStage extends Stage {

    public WinStage(Viewport viewport) {
        super(viewport);

        addActor(new WinBackground());
    }
}
