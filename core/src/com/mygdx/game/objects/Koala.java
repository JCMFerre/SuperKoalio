package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Koala extends Actor {

    private float WIDTH;
    private float HEIGHT;
    private float MAX_VELOCITY = 10f;
    private float JUMP_VELOCITY = 40f;
    private float DAMPING = 0.87f;

    enum State {
        Standing, Walking, Jumping
    }

    final Vector2 position = new Vector2();
    final Vector2 velocity = new Vector2();
    Koala.State state = Koala.State.Walking;
    float stateTime = 0;
    boolean facesRight = true;
    boolean grounded = false;
}
