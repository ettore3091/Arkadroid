package com.mygdx.arkadroid.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Brick extends GameObject {

    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int GREEN = 2;
    public static final int YELLOW = 3;
    public static final int GREY = 4;
    public static final int BLACK = 5;

    public int color;
    public TextureRegion texture;

    public Brick(float x, float y, float width, float height, int color) {

        super(x, y, width, height);
        this.color = color;

        switch(color) {
            case 0:
                texture = Assets.redBrick;
                break;
            case 1:
                texture = Assets.blueBrick;
                break;
            case 2:
                texture = Assets.greenBrick;
                break;
            case 3:
                texture = Assets.yellowBrick;
                break;
            case 4:
                texture = Assets.darkStoneBrick;
                break;
            case 5:
                texture = Assets.blackBrick;
                break;
        }

    }

    public Polygon[] areaOfEffect(GameObject obj) {

        float[] vertices = new float[6];

        Polygon[] polygons = new Polygon[4];

        //left triangle
        vertices[0] = bounds.x-obj.bounds.width;
        vertices[1] = bounds.y-obj.bounds.height;
        vertices[2] = vertices[0];
        vertices[3] = vertices[1]+ bounds.height+obj.bounds.height*2;
        vertices[4] = bounds.x+bounds.width/4;
        vertices[5] = bounds.y+bounds.height/2;
        polygons[0] = new Polygon(vertices.clone());

        //up triangle
        vertices[1] = vertices[3];
        vertices[2] = vertices[0]+bounds.width+obj.bounds.width*2;
        vertices[4] = bounds.x+bounds.width/2;
        vertices[5] = bounds.y;
        polygons[1] = new Polygon(vertices.clone());

        //right triangle
        vertices[0] = vertices[2];
        vertices[3] = bounds.y-obj.bounds.height;
        vertices[4] = bounds.x+3*bounds.width/4;
        vertices[5] = bounds.y+bounds.height/2;
        polygons[2] = new Polygon(vertices.clone());

        //down triangle
        vertices[1] = vertices[3];
        vertices[2] = bounds.x-obj.bounds.width;
        vertices[4] = bounds.x + bounds.width/2;
        vertices[5] = bounds.y + bounds.height;
        polygons[3] = new Polygon(vertices.clone());

        return polygons;

    }
}
