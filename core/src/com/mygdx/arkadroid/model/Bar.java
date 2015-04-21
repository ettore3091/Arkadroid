package com.mygdx.arkadroid.model;

import com.badlogic.gdx.math.Vector2;


public class Bar extends DynamicGameObject {

    private float[] sections;
    private static int NUM_SECTIONS = 16;

    public Bar(float x, float y, float width, float height) {

        super(x,y,width,height);
        sections = new float[NUM_SECTIONS];
        float swidth = (width-width*0.16f)/(NUM_SECTIONS-1);
        sections[0] =width*0.08f;
        for(int i=1; i<NUM_SECTIONS; i++) {
            sections[i]= sections[0]+i*swidth;
        }

    }

    public Vector2 ballDirection(float dx) {

        int i=0;
        while(i<NUM_SECTIONS&& dx > sections[i]) {
            i++;
        }

        float x, y;
        float alpha = 180/(NUM_SECTIONS+4);

        x=(float) Math.cos(Math.toRadians(180-alpha*(i+2)));
        y=(float) Math.sin(Math.toRadians(180-alpha*(i+2)));

        return new Vector2(x,y);

    }

    public void update (float move) {

        position.x += move;
        bounds.x = (int)position.x;

    }

}
