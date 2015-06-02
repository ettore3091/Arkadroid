package com.mygdx.arkadroid.model;

public class PowerUp extends GameObject {

    public int type;
    public String[] types = {"Bigger Ship", "Smaller Ship", ""};

    public PowerUp(float x, float y, float width, float height) {
        super(x, y, width, height);
    }


}
