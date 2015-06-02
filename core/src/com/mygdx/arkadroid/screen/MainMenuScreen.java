package com.mygdx.arkadroid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.arkadroid.game.Arkadroid;
import com.mygdx.arkadroid.model.Assets;

public class MainMenuScreen extends ScreenAdapter {

    public MainMenuScreen (Arkadroid game) {

        this.game = game;

        width = game.width;
        height = game.height;

        guiCam = new OrthographicCamera(width, height);
        guiCam.position.set(width/2, height/2, 0);

        playBounds = new Rectangle(width/5, 11*height/20, width/2, height/20);
        recordsBounds = new Rectangle(width/5, (int) (9*height/20), width/2, height/20);
        settingsBounds = new Rectangle(width/5, 7*height/20, width/2, height/20);
        touchPoint = new Vector3();

        Assets.loadBackground(0, height);
        Assets.playMusic(Assets.menuTheme);

    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);

                Assets.pauseMusic(Assets.menuTheme);

                game.setScreen(new GameScreen(game, 1));
                this.dispose();
            }
            if (recordsBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new RecordsScreen(game));
                this.dispose();
            }
            if (settingsBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new SettingsScreen(game));
                this.dispose();
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
        Assets.font.draw(game.batch, "PLAY", playBounds.getX(), playBounds.getY()+playBounds.height);
        Assets.font.draw(game.batch, "RECORDS", recordsBounds.getX(), recordsBounds.getY()+recordsBounds.height);
        Assets.font.draw(game.batch, "SETTINGS", settingsBounds.getX(), settingsBounds.getY()+settingsBounds.height);
        game.batch.end();

    }

    @Override
    public void render (float delta) {

        update();
        draw();

    }

    @Override
    public void pause () {

    }

    public void dispose() {

    }

    private Arkadroid game;
    private OrthographicCamera guiCam;
    private Rectangle playBounds;
    private Rectangle recordsBounds;
    private Rectangle settingsBounds;
    private Vector3 touchPoint;
    private float width;
    private float height;

}
