package com.mygdx.arkadroid.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Key {

    public char key;
    public Rectangle bounds;

    public Key(char key, Rectangle bounds) {

        this.key = key;
        this.bounds = bounds;
    }

    public boolean isPressed(Vector3 touchPoint) {

        if(bounds.contains(touchPoint.x, touchPoint.y))
            return true;
        return false;
    }

}
