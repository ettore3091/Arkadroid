package com.mygdx.arkadroid.model;

public class Ball extends DynamicGameObject {

    public static final float VELOCITY_SCALE = 7.5f;

    public Ball(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.position.set(x, y);
        this.velocity.x = VELOCITY_SCALE;
        this.velocity.y = VELOCITY_SCALE;
    }

    public void update(float deltaTime) {

        position.x += velocity.x;
        position.y += velocity.y;

    }

    public void hit(float x, float y) {

        velocity.x *= x;
        velocity.y *= y;

    }

    public void hitBar(float x, float y) {

        velocity.x = x*VELOCITY_SCALE;
        velocity.y = y*VELOCITY_SCALE;

    }

}
