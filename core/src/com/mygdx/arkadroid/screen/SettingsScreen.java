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

public class SettingsScreen extends ScreenAdapter {

    private final int width;
    private final int height;
    private final OrthographicCamera guiCam;
    private Arkadroid game;
    private Rectangle musicBounds;
    private Rectangle soundsBounds;
    private Rectangle diffLabelBounds;
    private Rectangle diffBounds;
    private Rectangle timeBounds;
    private Rectangle livesBounds;
    private Rectangle saveBounds;
    private Rectangle backBounds;
    private Vector3 touchPoint;

    private String[] volumeOptions = {"OFF", "1", "2", "3", "4", "5"};
    private int musicVol = 5;
    private int soundsVol = 5;
    private String[] diffOptions = {"EASY", "MEDIUM", "HARD", "CUSTOM"};
    private int diff=0;
    private String[] livesOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private int lives=2;
    private String[] timeOptions = {"120", "180", "240", "300", "360"};
    private int time=4;


    public SettingsScreen (Arkadroid game) {

        this.game = game;

        width = game.width;
        height = game.height;

        guiCam = new OrthographicCamera(width, height);
        guiCam.position.set(width/2, height/2, 0);

        musicBounds = new Rectangle(20, 14*height/20, width-20, height/20);
        soundsBounds = new Rectangle(20, 13*height/20, width-20, height/20);
        diffLabelBounds = new Rectangle(20, 11*height/20, width-20, height/20);
        diffBounds = new Rectangle(width/4, 9*height/20, width-20, height/20);
        livesBounds = new Rectangle(width/3, 7*height/20, width/2-20, height/20);
        timeBounds = new Rectangle(width/3, 6*height/20, width/2-20, height/20);

        backBounds = new Rectangle(40, height/20, width/2-40, height/20);
        saveBounds = new Rectangle(width/2+20, height/20, width/2-40, height/20);

        if(!Settings.musicOn)
            musicVol = 0;
        else
            musicVol = (int)Settings.musicVolume;
        if(!Settings.soundsOn)
            soundsVol = 0;
        else
            soundsVol = (int)Settings.soundsVolume;
        diff = Settings.difficulty;
        lives = Settings.lives-1;
        time = Settings.time/60-2;

        touchPoint = new Vector3();

    }

    public void update() {

        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (musicBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                musicVol = (musicVol+1)%6;
                if(musicVol==0) {
                    Settings.musicOn = false;
                    Settings.musicVolume = 0;
                }
                else {
                    Settings.musicOn = true;
                    Settings.musicVolume = Float.parseFloat(volumeOptions[musicVol]);
                }
            }
            if (soundsBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                soundsVol = (soundsVol+1)%6;
                if(soundsVol==0) {
                    Settings.soundsOn = false;
                    Settings.soundsVolume = 0;
                }
                else {
                    Settings.soundsOn = true;
                    Settings.soundsVolume = Float.parseFloat(volumeOptions[soundsVol]);
                }
            }
            if (diffLabelBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                diff = (diff+1)%4;
                switch (diff) {
                    case 0:
                        lives = 5;
                        time = 4;
                        break;
                    case 1:
                        lives = 3;
                        time = 3;
                        break;
                    case 2:
                        lives = 1;
                        time = 2;
                        break;
                }
                Settings.difficulty = diff;
                Settings.lives = Integer.parseInt(livesOptions[lives]);
                Settings.time = Integer.parseInt(timeOptions[time]);
            }
            if (livesBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                lives = (lives+1)%10;
                if(diff!=3)
                    diff=3;
                Settings.lives = Integer.parseInt(livesOptions[lives]);
                Settings.difficulty =diff;
            }
            if (timeBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                time = (time+1)%5;
                if(diff!=3)
                    diff=3;
                Settings.time = Integer.parseInt(timeOptions[time]);
                Settings.difficulty=diff;
            }
            if (saveBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                Settings.save();
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
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
        game.batch.draw(Assets.logo, width / 20, 17 * height / 20, 9 * width / 10, 9 * width / 10 * Assets.logo.getRegionHeight() / Assets.logo.getRegionWidth());
        Assets.font.draw(game.batch, "MUSIC VOL: " + volumeOptions[musicVol], musicBounds.x, musicBounds.y + musicBounds.height);
        Assets.font.draw(game.batch, "SOUND VOL: "+volumeOptions[soundsVol], soundsBounds.x, soundsBounds.y+soundsBounds.height);
        Assets.font.draw(game.batch, "DIFFICULTY", diffLabelBounds.x, diffLabelBounds.y+diffLabelBounds.height);
        Assets.font.draw(game.batch, diffOptions[diff], diffBounds.x, diffBounds.y+diffBounds.height);
        Assets.font.draw(game.batch, "LIVES: "+livesOptions[lives], livesBounds.x, livesBounds.y+livesBounds.height);
        Assets.font.draw(game.batch, "TIME: "+timeOptions[time], timeBounds.x, timeBounds.y+timeBounds.height);
        Assets.font.draw(game.batch, "SAVE", saveBounds.x, saveBounds.y+saveBounds.height);
        Assets.font.draw(game.batch, "BACK", backBounds.x, backBounds.y+backBounds.height);
        game.batch.end();

    }

    @Override
    public void render (float delta) {

        update();
        draw();

    }

}
