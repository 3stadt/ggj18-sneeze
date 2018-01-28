package com.mygdx.sneezetest.StageHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageHandler {
    private static Stage activeStage;

    public static void setActiveStage(Stage stage)
    {
        activeStage = stage;
    }

    public static Stage getActiveStage()
    {
        return activeStage;
    }
}
