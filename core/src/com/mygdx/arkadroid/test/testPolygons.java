package com.mygdx.arkadroid.test;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.arkadroid.model.Ball;
import com.mygdx.arkadroid.model.Brick;

public class testPolygons {

    public static void execute() {

        Vector2[] coords = {new Vector2()};

        Ball ball = new Ball(80, 40, 20, 20);
        Brick brick = new Brick(0, 0, 80, 40, 1);
        float x=1, y=1;

        Polygon[] aoe = brick.areaOfEffect(ball);
        Vector2 center = new Vector2(ball.position.x+ball.bounds.width/2, ball.position.y+ball.bounds.height/2);

        if(aoe[0].contains(center.x, center.y) || aoe[2].contains(center.x, center.y))
            x=-1;
        if(aoe[1].contains(center.x, center.y) || aoe[3].contains(center.x, center.y))
            y=-1;

        for(Polygon p: aoe) {
            float v[] = p.getVertices();
            if(containsBall(ball, v[4], v[5], v[0], v[1])) {
                x=-1;
                y=-1;
                break;
            }
        }

        System.out.println("Collision results -> x: "+x+"    y: "+y);

    }

    private static boolean containsBall(Ball b, float x1, float y1, float x2, float y2) {

        float cx = b.position.x+b.bounds.width/2;
        float cy = b.position.y+b.bounds.height/2;

        float y;

        y=(y2-y1)/(x2-x1)*(cx-x1)+y1;

        return y==cy;

    }

}
