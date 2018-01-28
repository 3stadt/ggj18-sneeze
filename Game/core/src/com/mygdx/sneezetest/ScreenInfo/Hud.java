package com.mygdx.sneezetest.ScreenInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.sneezetest.Supervisor.Supervisor;


public class Hud {
    public static boolean IS_SYRINGE_USED = false;
    private static int TIME_IN_SECONDS;
    public static float TIME_FLOAT;
    //public static int INFECTED;

    private Image syringe;

    private TextureRegionDrawable drawableFull;
    private TextureRegionDrawable drawableEmpty;

    private Stage stage;

    private Label timeLabel;
    private Label infectedLabel;

    public Hud() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
        Texture syringeFull = new Texture(Gdx.files.internal("health.png"));
        Texture syringeEmpty = new Texture(Gdx.files.internal("Health2.png"));

        drawableFull = new TextureRegionDrawable(new TextureRegion(syringeFull));
        drawableEmpty = new TextureRegionDrawable(new TextureRegion(syringeEmpty));
        syringe = new Image(drawableFull);

        timeLabel = new Label(String.format("Time: %03d", TIME_IN_SECONDS), new Label.LabelStyle(font, Color.GRAY));
        infectedLabel = new Label( String.format("Infeced: %03d", Supervisor.num_sick ), new Label.LabelStyle(font, Color.GRAY));

        Table table = new Table();
        table.top();
        table.align(Align.topLeft);
        table.setFillParent(true);
        table.add(syringe);
        table.row();
        table.add(infectedLabel);
        table.row();
        table.add(timeLabel);
        stage.addActor(table);
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        if (IS_SYRINGE_USED)
        {
            syringe.setDrawable(drawableEmpty);
        }
        else {
            syringe.setDrawable(drawableFull);
        }
        TIME_IN_SECONDS = Math.round(TIME_FLOAT);
        timeLabel.setText(String.format("Time: %03d", TIME_IN_SECONDS));
        infectedLabel.setText(String.format("Infeced: %03d", Supervisor.num_sick));
    }
}