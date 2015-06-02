package com.mygdx.arkadroid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.arkadroid.game.Arkadroid;
import com.mygdx.arkadroid.model.Assets;
import com.mygdx.arkadroid.model.Settings;


public class RecordsScreen extends ScreenAdapter {

    public RecordsScreen (Arkadroid game) {

        width = game.width;
        height = game.height;
        this.game = game;

        guiCam = new OrthographicCamera(width, height);
        guiCam.position.set(width/2, height/2, 0);

        backBounds = new Rectangle(40, height/20, width/2-40, height/20);

        highscores = Settings.highscores;
        players = Settings.players;
        touchPoint = new Vector3();

    }

    public void update () {

        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                Settings.load();
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
    }

    public void draw() {

        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.begin();
        game.batch.disableBlending();
        Assets.background.draw(game.batch);
        game.batch.enableBlending();
        game.batch.draw(Assets.logo, width/20, 17*height/20, 9*width/10, 9*width/10*Assets.logo.getRegionHeight()/Assets.logo.getRegionWidth());
        for(int i=0; i<highscores.length; i++) {
            Assets.font_small.draw(game.batch, "#"+(i+1)+" "+players[i]+"  "+highscores[i], width/20, 16*height/20-i*height/20);
        }
        Assets.font.draw(game.batch, "BACK", backBounds.x, backBounds.y+backBounds.height);
        game.batch.end();

    }

    @Override
    public void render (float delta) {

        update();
        draw();

    }

    private Arkadroid game;
    private OrthographicCamera guiCam;
    private int width;
    private int height;
    private Rectangle backBounds;
    private Vector3 touchPoint;
    private int[] highscores;
    private String[] players;
}
