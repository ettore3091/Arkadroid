package com.mygdx.arkadroid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.arkadroid.game.Arkadroid;
import com.mygdx.arkadroid.model.Assets;
import com.mygdx.arkadroid.model.Key;
import com.mygdx.arkadroid.model.Keyboard;
import com.mygdx.arkadroid.model.Settings;

public class NameScreen extends ScreenAdapter {

    public NameScreen(Arkadroid game, int score) {

        this.game = game;
        keyboard = new Keyboard(game);
        batch = game.batch;
        this.score = score;

        guiCam = new OrthographicCamera(game.width, game.height);
        guiCam.position.set(game.width/2, game.height/2, 0);

        delBounds = new Rectangle(40, 3*game.height/20, game.width/2-40, game.height/20);
        backBounds = new Rectangle(40, game.height/20, game.width/2-40, game.height/20);
        saveBounds = new Rectangle(game.width/2+20, game.height/20, game.width/2-40, game.height/20);

        name = Settings.lastPlayer;
        if (name.equals("---"))
            name = " ";
        touchPoint = new Vector3();

    }

    public void update() {

        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            Key k = keyboard.type(touchPoint);
            if(k!=null) {
                name = (name+""+k.key).trim();
            }
            if(delBounds.contains(touchPoint.x, touchPoint.y)) {
                if(name.length()>1)
                    name = name.substring(0, name.length()-1);
                else if(name.length() == 1)
                    name = " ";
            }
            if(backBounds.contains(touchPoint.x, touchPoint.y)){
                Settings.addHighscore(score, name);
                Settings.lastPlayer = name;
                Settings.save();
                game.setScreen(new MainMenuScreen(game));
                Assets.stopMusic(Assets.gameTheme);
                this.dispose();
            }
            if(saveBounds.contains(touchPoint.x, touchPoint.y)) {
                Settings.addHighscore(score, name);
                Settings.lastPlayer = name;
                Settings.save();
                game.setScreen(new RecordsScreen(game));
                Assets.stopMusic(Assets.gameTheme);
                this.dispose();
            }
        }
    }

    public void draw() {

        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);

        batch.begin();
        batch.disableBlending();
        Assets.background.draw(batch);
        batch.enableBlending();
        Assets.font.draw(batch, name, 20, game.height-30);
        for(Key k: keyboard.keyboard) {
            Assets.font.draw(batch, ""+k.key, k.bounds.x, k.bounds.y+k.bounds.height);
        }
        Assets.font.draw(game.batch, "DEL", delBounds.x, delBounds.y+delBounds.height);
        Assets.font.draw(game.batch, "SAVE", saveBounds.x, saveBounds.y+saveBounds.height);
        Assets.font.draw(game.batch, "BACK", backBounds.x, backBounds.y+backBounds.height);
        batch.end();

    }

    @Override
    public void render(float delta) {

        update();
        draw();

    }

    public void dispose() {

        super.dispose();

    }

    private final OrthographicCamera guiCam;
    private final Rectangle backBounds;
    private final Rectangle saveBounds;
    private final Rectangle delBounds;
    private Arkadroid game;
    private Keyboard keyboard;
    private SpriteBatch batch;
    private Vector3 touchPoint;
    private String name;
    private int score;

}
