package com.mygdx.arkadroid.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.arkadroid.model.Assets;
import com.mygdx.arkadroid.model.Ball;
import com.mygdx.arkadroid.model.Bar;
import com.mygdx.arkadroid.model.Brick;
import com.mygdx.arkadroid.model.GreyBrick;
import com.mygdx.arkadroid.model.Level;
import com.mygdx.arkadroid.model.Settings;

import com.badlogic.gdx.math.Polygon;
import java.util.ArrayList;

public class GameWorld {

    public static final int WORLD_STATE_READY = 0;
    public static final int WORLD_STATE_RUNNING = 1;
    public static final int WORLD_STATE_NEXT_LEVEL = 2;
    public static final int WORLD_STATE_GAME_OVER = 3;

    public ArrayList<Brick> bricks;
    public int level;
    public int state;
    public int lives = Settings.lives;
    public float time = Settings.time;
    public int score;
    private static final int BRICK_POINTS = 5;
    private static final int SECOND_POINTS = 10;
    private static final int LIFE_POINTS = 20;
    public Bar bar;
    public Ball ball = null;
    private Arkadroid game;
    public int width;
    public int height;


    public GameWorld (Arkadroid game, int level) {

        this.game = game;
        width = game.width;
        height = game.height;
        this.level = level;
        bar = new Bar(3*width/8, 2*height/16, width/4, Assets.bar.getRegionHeight()*width/4/Assets.bar.getRegionWidth());
        ball=createBall();
        generateLevel();
        state = WORLD_STATE_READY;

    }

    private void generateLevel() {

        bricks=(new Level(level, this)).bricks;

    }

    public void update(float deltaTime, float move) {

        bar.update(move);
        if(state == WORLD_STATE_RUNNING) {
            if(ball==null)
                ball=createBall();
            time -= deltaTime;
            updateBall(deltaTime);
        }

        if(bricks.size() < 1)
            nextLevel();
        if(time<0)
            gameOver();


    }

    private void updateBall(float deltaTime) {

        if(ball!=null) {
            if(!checkBrickCollision())
                if(!checkBoundsCollision())
                    checkBarCollision();
            ball.update(deltaTime);
        }

    }

    private boolean checkBrickCollision() {

        int x=1, y=1;

        for(int i=0; i<bricks.size(); i++) {
            Brick br = bricks.get(i);
            if(br.intersects(ball)) {
                if(br.color==Brick.GREY) {
                    if(((GreyBrick) br).state != 0)
                        ((GreyBrick) br).crack();
                    else {
                        bricks.remove(i);
                        score++;
                    }
                }
                else {
                    bricks.remove(i);
                    score++;
                }
                Assets.playSound(Assets.hitSound);

                Polygon[] aoe = br.areaOfEffect(ball);
                float cx = ball.position.x+ball.bounds.width/2;
                float cy = ball.position.y+ball.bounds.height/2;

                if(aoe[0].contains(cx, cy) || aoe[2].contains(cx, cy))
                    x=-1;
                else if(aoe[1].contains(cx, cy) || aoe[3].contains(cx, cy))
                    y=-1;

                for(Polygon p: aoe) {
                    float v[] = p.getVertices();
                    if(pointOnLine(cx, cy, v[4], v[5], v[0], v[1])) {
                        x=-1;
                        y=-1;
                        break;
                    }
                }

                ball.hit(x, y);
                return true;
            }
        }
        return false;
    }

    private boolean pointOnLine(float cx, float cy, float x1, float y1, float x2, float y2) {

        return cy==(y2-y1)/(x2-x1)*(cx-x1)+y1;

    }

    private boolean checkBoundsCollision() {

        if(ball.position.x < 0) {
            ball.position.x=-ball.position.x;
            ball.hit(-1, 1);
            Assets.playSound(Assets.hitSound);
            return true;
        }
        if (ball.position.x+ball.bounds.width > width) {
            ball.position.x -=(ball.position.x+ball.bounds.width-width);
            ball.hit(-1, 1);
            Assets.playSound(Assets.hitSound);
            return true;
        }
        if(ball.position.y+ball.bounds.height > height) {
            ball.position.y-=(ball.position.y+ball.bounds.height-height);
            ball.hit(1, -1);
            Assets.playSound(Assets.hitSound);
            return true;
        }
        if(ball.position.y < 0) {
            if(lives > 1)
                loseLife();
            else {
                lives--;
                gameOver();
            }
        }
        return false;
    }

    private void checkBarCollision() {

        if(bar.intersects(ball)) {
            float delta = ball.position.x - bar.position.x;
            Vector2 dir = bar.ballDirection(delta);
            ball.hitBar(dir.x, dir.y);
            Assets.playSound(Assets.hitSound);
        }

    }

    private void loseLife() {

        lives--;
        state = WORLD_STATE_READY;
        ball=createBall();

    }

    private void gameOver() {

        score = 0;
        state = WORLD_STATE_GAME_OVER;

    }

    private void nextLevel() {

        calcScore();
        state = WORLD_STATE_NEXT_LEVEL;

    }

    private void calcScore() {

        score *= BRICK_POINTS;
        score += ((int) ((time/Settings.time)*SECOND_POINTS) + lives/Settings.lives*LIFE_POINTS)*Settings.difficulty+1;

    }

    private Ball createBall() {

        float dimball = Assets.ball.getRegionWidth()*bar.bounds.width/Assets.bar.getRegionWidth();
        float ballpos = (bar.bounds.width-dimball)/2f;
        return new Ball(bar.position.x+ballpos, bar.position.y+bar.bounds.height, dimball, dimball);

    }

}
