package com.mygdx.sneezetest.Stages.MenuActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.sneezetest.StageHandler.StageHandler;
import com.mygdx.sneezetest.Stages.GameStage;

public class StartButton extends Actor {

    private Texture itemTexture;
    private Vector2 pos;

    public StartButton() {
        itemTexture = new Texture(Gdx.files.internal("start_button.png"));
        pos = new Vector2(640 - itemTexture.getWidth() / 2, 200);
        setBounds(pos.x, pos.y, itemTexture.getWidth(), itemTexture.getHeight());

        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                StageHandler.setActiveStage(new GameStage(new ScreenViewport()));
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(itemTexture, pos.x, pos.y);
    }
}
