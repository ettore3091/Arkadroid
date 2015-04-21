package com.mygdx.arkadroid.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.arkadroid.game.GameWorld;
import com.mygdx.arkadroid.model.Assets;
import com.mygdx.arkadroid.model.Brick;

public class GameWorldRenderer {

    private GameWorld world;
    private OrthographicCamera cam;
    private SpriteBatch batch;

    public GameWorldRenderer (SpriteBatch batch, GameWorld world) {

        this.batch = batch;
        this.world = world;
        this.cam = new OrthographicCamera(world.width, world.height);
        this.cam.position.set(world.width/2, world.height/2, 0);

        Assets.loadBackground(world.level, world.height);

    }

    public void render() {

        renderBackground();
        renderObjects();

    }

    private void renderBackground() {

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        batch.disableBlending();
        Assets.background.draw(batch);
        batch.end();

    }

    private void renderObjects() {

        cam.update();
        batch.enableBlending();
        batch.begin();
        batch.draw(Assets.bar, world.bar.position.x, world.bar.position.y, world.bar.bounds.width, world.bar.bounds.height);
        renderBall();
        renderBricks();
        batch.end();

    }

    private  void renderBall() {

        if(world.state == GameWorld.WORLD_STATE_RUNNING)
            batch.draw(Assets.ball, world.ball.position.x, world.ball.position.y, world.ball.bounds.width, world.ball.bounds.height);

    }

    private void renderBricks() {

        for(Brick br: world.bricks) {
            batch.draw(br.texture, br.position.x, br.position.y, br.bounds.width, br.bounds.height);
        }

    }

}
