package com.mygdx.arkadroid.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.arkadroid.model.Assets;
import com.mygdx.arkadroid.model.Settings;
import com.mygdx.arkadroid.screen.MainMenuScreen;

/*
* This is a game made by Ettore Passaro.
* Whomever wants to use this code, is free to explore it, copy it and improve it.
* I do not own the assets file, but I found them on the Internet.
* The background images are from http://opengameart.org/content/space-backgrounds-3
* The sfx files are from http://opengameart.org/content/space-battle-game-sounds-astromenace
* And the music comes from:
* http://opengameart.org/content/music-mix-more-music-inside
* Alexandr Zhelanov, https://soundcloud.com/alexandr-zhelanov
*/
public class Arkadroid extends Game {

    public SpriteBatch batch;
    public int width;
    public int height;

	@Override
	public void create () {
        batch = new SpriteBatch();
        Application.ApplicationType appType = Gdx.app.getType();
        if(appType == Application.ApplicationType.Android) {
            width = Gdx.app.getGraphics().getWidth();
            height = Gdx.app.getGraphics().getHeight();
        }
        else {
            width = 420;
            height = 700;
            Gdx.graphics.setDisplayMode(width, height, false);
        }
        resize(width, height);
        Settings.load();
        Settings.save();
        Assets.load();
        mms = new MainMenuScreen(this);
        setScreen(mms);
	}

	@Override
	public void render () {
		super.render();
	}

    @Override
    public void dispose() {

        Settings.save();
        Assets.dispose();
        mms.dispose();
        batch.dispose();
    }

    private MainMenuScreen mms;
}
