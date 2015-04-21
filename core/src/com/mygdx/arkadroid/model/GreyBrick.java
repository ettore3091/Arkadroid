package com.mygdx.arkadroid.model;

public class GreyBrick extends Brick {

    public int state = 1;

    public GreyBrick(float x, float y, float width, float height) {
        super(x, y, width, height, Brick.GREY);
    }

    public void crack() {

        texture = Assets.lightStoneBrick;
        state = 0;

    }
}
