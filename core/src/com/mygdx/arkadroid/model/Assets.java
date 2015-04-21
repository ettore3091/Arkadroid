package com.mygdx.arkadroid.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Assets {

    public static TextureRegion logo;
    public static Texture items;
    public static TextureRegion redBrick;
    public static TextureRegion blueBrick;
    public static TextureRegion greenBrick;
    public static TextureRegion yellowBrick;
    public static TextureRegion darkStoneBrick;
    public static TextureRegion lightStoneBrick;
    public static TextureRegion blackBrick;
    public static TextureRegion bar;
    public static TextureRegion ball;
    public static TextureRegion powerUp;
    public static Sprite background;
    public static BitmapFont font;
    public static BitmapFont font_small;

    public static Music menuTheme;
    public static Music gameTheme;
    public static Sound clickSound;
    public static Sound hitSound;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {

        logo = new TextureRegion(loadTexture("Images/logo.png"), 0, 0, 838, 170);

        items = loadTexture("Images/items.png");

        redBrick = new TextureRegion(items, 0, 0, 200, 100);
        blueBrick = new TextureRegion(items, 0, 100, 200, 100);
        greenBrick = new TextureRegion(items, 0, 200, 200, 100);
        yellowBrick = new TextureRegion(items, 0, 300, 200, 100);
        darkStoneBrick = new TextureRegion(items, 200, 0, 200, 100);
        lightStoneBrick = new TextureRegion(items, 200, 100, 200, 100);
        blackBrick = new TextureRegion(items, 200, 200, 200, 100);

        bar = new TextureRegion(items, 0, 400, 650, 104);
        ball = new TextureRegion(items, 0, 504, 120, 120);
        powerUp = new TextureRegion(items, 400, 0, 300, 150);

        font = new BitmapFont(Gdx.files.internal("Images/font.fnt"), Gdx.files.internal("Images/font.png"), false);
        font_small = new BitmapFont(Gdx.files.internal("Images/font_small.fnt"), Gdx.files.internal("Images/font_small.png"), false);

        menuTheme = Gdx.audio.newMusic(Gdx.files.internal("Music/I didn't do it_0.mp3"));
        gameTheme = Gdx.audio.newMusic(Gdx.files.internal("Music/Dream about space.mp3"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu_online.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu_nclick.wav"));

    }

    public static void loadBackground(int i, float height) {

        switch(i) {
            case 0:
                background = new Sprite(loadTexture("Images/background0.png"));
                break;
            case 1:
                background = new Sprite(loadTexture("Images/background1.png"));
                break;
            case 2:
                background = new Sprite(loadTexture("Images/background2.png"));
                break;
            case 3:
                background = new Sprite(loadTexture("Images/background3.png"));
                break;
        }

        float scale = height/background.getHeight();
        background.scale(scale);


    }

    public static void playMusic(Music music) {

        if(Settings.musicOn) {
            music.setLooping(true);
            music.setVolume(Settings.musicVolume/5);
            music.play();
        }

    }

    public static void pauseMusic(Music music) {

        music.pause();

    }

    public static void stopMusic(Music music) {

        music.stop();

    }

    public static void playSound(Sound sound) {

        if(Settings.soundsOn)
            sound.play(Settings.soundsVolume/5);

    }

    public static void dispose() {
        font.dispose();
        items.dispose();
        menuTheme.dispose();
        gameTheme.dispose();
    }

}
