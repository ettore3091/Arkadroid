package com.mygdx.arkadroid.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {

    public static boolean musicOn = true;
    public static boolean soundsOn = true;
    public static float musicVolume = 1f;
    public static float soundsVolume = 1f;
    public static int lives = 3;
    public static int time = 360;
    public static int difficulty = 1;
    public static int[] highscores = {400, 375, 350, 300, 250, 200, 190, 150, 100, 50};
    public static String[] players = {"---", "---", "---", "---", "---", "---", "---", "---", "---", "---"};
    public static String lastPlayer = "---";
    private static final String filename = ".arkadroid";


    public static void load() {

        try {
            FileHandle file = Gdx.files.local(filename);
            String[] input = file.readString().split("\n");

            musicOn = Boolean.parseBoolean(input[0]);
            soundsOn = Boolean.parseBoolean(input[1]);
            musicVolume = Float.parseFloat(input[2]);
            soundsVolume = Float.parseFloat(input[3]);
            lives = Integer.parseInt(input[4]);
            time = Integer.parseInt(input[5]);
            difficulty = Integer.parseInt(input[6]);
            for(int i=0; i<highscores.length; i++)
                highscores[i] = Integer.parseInt(input[7+i]);
            for(int i=0; i<highscores.length; i++)
                players[i] = input[7+highscores.length+i];
            lastPlayer = input[7+highscores.length+players.length];

        }
        catch (Throwable t) {
            musicOn = true;
            soundsOn = true;
            musicVolume = 1f;
            soundsVolume = 1f;
            lives = 3;
            time = 360;
            difficulty = 1;
            highscores = new int[]{400, 375, 350, 300, 250, 200, 190, 150, 100, 50};
            players = new String[]{"---", "---", "---", "---", "---", "---", "---", "---", "---", "---"};
            lastPlayer = "---";
        }

    }

    public static void save() {

        try {
            FileHandle file = Gdx.files.local(filename);

            if(!file.exists())
                if(!file.file().createNewFile())
                    throw new Throwable("IMPOSSIBLE TO CREATE THE FILE. ");

            file.writeString(Boolean.toString(musicOn)+"\n", false);
            file.writeString(Boolean.toString(soundsOn)+"\n", true);
            file.writeString(Float.toString(musicVolume)+"\n", true);
            file.writeString(Float.toString(soundsVolume)+"\n", true);
            file.writeString(Integer.toString(lives)+"\n", true);
            file.writeString(Integer.toString(time)+"\n", true);
            file.writeString(Integer.toString(difficulty)+"\n", true);
            for(int x: highscores)
                file.writeString(Integer.toString(x)+"\n",true);
            for(String x: players)
                file.writeString(x+"\n", true);
            file.writeString(lastPlayer+"\n", true);
        }
        catch (Throwable t) {
            musicOn = true;
            soundsOn = true;
            musicVolume = 1f;
            soundsVolume = 1f;
            lives = 3;
            time = 360;
            difficulty = 1;
            highscores = new int[]{400, 375, 350, 300, 250, 200, 190, 150, 100, 50};
            players = new String[]{"---", "---", "---", "---", "---", "---", "---", "---", "---", "---"};
            lastPlayer = "---";
        }

    }

    public static void addHighscore(int score, String player) {

        for (int i = 0; i < highscores.length; i++) {
            if (highscores[i] < score) {
                for (int j = highscores.length-1; j > i; j--) {
                    highscores[j] = highscores[j - 1];
                    players[j] = players[j-1];
                }
                highscores[i] = score;
                players[i] = player;
                break;
            }
        }

        save();

    }


}
