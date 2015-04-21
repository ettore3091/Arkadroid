package com.mygdx.arkadroid.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.arkadroid.game.GameWorld;

import java.util.ArrayList;

public class Level {

    public int level;
    public ArrayList<Brick> bricks;
    private String filename;
    public static final int NROWS = 16;
    public static final int NCOLS = 10;
    private float brickW;
    private float brickH;
    private float width;
    private float height;

    public Level (int level, GameWorld world) {

        this.level = level;
        bricks = new ArrayList<Brick>();
        filename="Levels/level"+level+".txt";

        width = world.width;
        height = world.height;
        brickW = width/10;
        brickH = brickW*Assets.redBrick.getRegionHeight()/Assets.redBrick.getRegionWidth();

        load();

    }

    private void load() {

        try {
            FileHandle file = Gdx.files.internal(filename);
            String[] rows = file.readString().split("\n");
            String[] cols = {" "};
            for(int i=0; i<NROWS; i++) {
                if(rows[i].startsWith(".")) {
                    cols = rows[i].trim().split(" ");
                    for(int j=0; j<NCOLS; j++) {
                        if(!cols[j+1].trim().equals("-")) {
                            if(Integer.parseInt(cols[j+1])==4)
                                bricks.add(new GreyBrick(brickW*j,height-(brickH*i)-height/5, brickW, brickH));
                            else
                                bricks.add(new Brick(brickW*j, height-(brickH*i)-height/5, brickW, brickH, Integer.parseInt(cols[j+1])));
                        }
                    }
                }
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
            System.out.println("ERROR WHILE OPENING THE FILE");
        }

    }

}
