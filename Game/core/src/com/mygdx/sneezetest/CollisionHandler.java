package com.mygdx.sneezetest;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        System.out.println("BEGIN CONTACT");
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("END CONTACT");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
