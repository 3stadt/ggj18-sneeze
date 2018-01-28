package com.mygdx.sneezetest;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.sneezetest.Stages.GameStage.PIXEL_TO_METER;

public class TiledObjectUtil {

    public static void parseTiledObjectLayer(World world, MapObjects objects) {
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject) object);
            } else {
                continue;
            }

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 100f;
            fixtureDef.restitution = 0f;

            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(fixtureDef);
            shape.dispose();
        }
    }

    private static Shape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();

        Vector2 size = new Vector2((rectangle.x * PIXEL_TO_METER + rectangle.width * PIXEL_TO_METER * 0.5f),
                (rectangle.y * PIXEL_TO_METER + rectangle.height * PIXEL_TO_METER * 0.5f));

        polygon.setAsBox(rectangle.width * PIXEL_TO_METER * 0.5f,
                rectangle.height * PIXEL_TO_METER * 0.5f,
                size,
                0.0f);

        return polygon;
    }
}
