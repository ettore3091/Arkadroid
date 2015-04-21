package com.mygdx.arkadroid.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

    public final Vector2 position;
    public final Rectangle bounds;

    public GameObject (float x, float y, float width, float height) {

        position = new Vector2 (x,y);
        bounds = new Rectangle(x, y, width, height);

    }

    public boolean intersects(GameObject obj) {

        float x1 = position.x+bounds.width/2;
        float x2 = obj.position.x+obj.bounds.width/2;
        float y1 = position.y+bounds.height/2;
        float y2 = obj.position.y+bounds.height/2;

        float distanceX = Math.abs(x1-x2);
        float distanceY = Math.abs(y1-y2);

        float sumW = (bounds.width+obj.bounds.width)/2;
        float sumH = (bounds.height+obj.bounds.height)/2;

        return distanceX < sumW && distanceY < sumH;

    }

}
