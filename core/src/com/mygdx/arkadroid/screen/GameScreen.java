package com.mygdx.arkadroid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.arkadroid.game.Arkadroid;
import com.mygdx.arkadroid.game.GameWorld;
import com.mygdx.arkadroid.model.Assets;
import com.mygdx.arkadroid.model.Bar;
import com.mygdx.arkadroid.model.Settings;

public class GameScreen extends ScreenAdapter {

    public static final int GAME_READY = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_LEVEL_END = 2;
    public static final int GAME_OVER = 3;

    private final int width;
    private final int height;
    private final OrthographicCamera guiCam;
    private final Rectangle nextBounds;
    private Bar bar;
    public Arkadroid game;
    private int state;
    private GameWorld world;
    private GameWorldRenderer renderer;
    private int[] highscores;
    private String[] players;
    private Vector3 touchPoint;

    public GameScreen (Arkadroid game, int level) {

        this.game = game;

        width = game.width;
        height = game.height;

        guiCam = new OrthographicCamera(width, height);
        guiCam.position.set(width/2, height/2, 0);

        world = new GameWorld(game, level);
        renderer = new GameWorldRenderer(game.batch, world);

        bar = new Bar(3*width/8, 2*height/16, width/4, Assets.bar.getRegionHeight()*width/4/Assets.bar.getRegionWidth());
        nextBounds = new Rectangle(width/2+20, height/20, width/2-40, height/20);

        touchPoint = new Vector3();

        renderer = new GameWorldRenderer(game.batch, world);

        highscores = Settings.highscores;
        players = Settings.players;

        state = GAME_READY;
        world.state = GameWorld.WORLD_STATE_READY;

        Assets.playMusic(Assets.gameTheme);

    }

    public void update (float deltaTime) {

        if (deltaTime > 0.1f) deltaTime = 0.1f;

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_LEVEL_END:
                updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateReady() {
        if(Gdx.input.isTouched()) {
            world.state = GameWorld.WORLD_STATE_RUNNING;
            state = GAME_RUNNING;
        }
    }

    private void updateRunning(float deltaTime) {

        if(Gdx.input.isTouched())
            world.update(deltaTime, Gdx.input.getDeltaX());
        else
            world.update(deltaTime, 0);

        if (world.state == GameWorld.WORLD_STATE_READY)
            state = GAME_READY;
        if (world.state == GameWorld.WORLD_STATE_NEXT_LEVEL)
            state = GAME_LEVEL_END;
        if (world.state == GameWorld.WORLD_STATE_GAME_OVER)
            state = GAME_OVER;

    }

    private void updateLevelEnd() {

        int p = checkHighscore();
        if(p>0) {
            if(Gdx.input.isTouched()) {
                guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if(nextBounds.contains(touchPoint.x, touchPoint.y)) {
                    game.setScreen(new NameScreen(game, world.score));
                    this.dispose();
                }
            }
        }
        else {
            if(Gdx.input.isTouched()) {
                guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if(nextBounds.contains(touchPoint.x, touchPoint.y)) {
                    game.setScreen(new MainMenuScreen(game));
                    this.dispose();
                }
            }
        }

    }

    private void updateGameOver() {

        if(Gdx.input.isTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(nextBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenuScreen(game));
                this.dispose();
            }
        }

    }

    public void draw () {

        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.enableBlending();
        game.batch.begin();
        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            case GAME_LEVEL_END:
                presentLevelEnd();
                int p = checkHighscore();
                if(p>0)
                    presentScore(p);
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }
        presentRunning();
        game.batch.end();

    }

    private void presentReady() {

        Assets.font.draw(game.batch, "READY", 20, height/2);

    }

    private void presentRunning() {

        Assets.font_small.draw(game.batch, "Life x" + world.lives, 20, 19 * height / 20);
        Assets.font_small.draw(game.batch, "" + (int) world.time, 2 * width / 6, 19 * height / 20);
        Assets.font_small.draw(game.batch, "Score: "+world.score, 4*width/6-60, 19*height/20);

    }

    private void presentLevelEnd() {

        Assets.font.draw(game.batch, "WINNER!!", 2*width/10, 6*height/8);
        Assets.font.draw(game.batch, "NEXT", nextBounds.x, nextBounds.y+nextBounds.height);

    }

    private void presentGameOver() {

        Assets.font.draw(game.batch, "GAME OVER", width/10, 5*height/8);
        Assets.font_small.draw(game.batch, "NO SCORE FOR YOU", 2*width/20, 4 * height / 8);
        Assets.font.draw(game.batch, "NEXT", nextBounds.x, nextBounds.y+nextBounds.height);
    }

    private void presentScore(int p) {

        Assets.font.draw(game.batch, "GOOD JOB!", width/10, 4*height/8);
        Assets.font.draw(game.batch, "#"+p+" "+world.score+"pts", width/20, 3*height/8);

    }

    private int checkHighscore() {

        if(world.score > highscores[9]) {
            for (int i = 0; i < highscores.length; i++) {
                if (highscores[i] < world.score) {
                    return i+1;
                }
            }
        }

        return -1;

    }

    public void render(float delta) {

        update(delta);
        draw();


    }

    @Override
    public void dispose() {

        Assets.stopMusic(Assets.gameTheme);

    }


}
