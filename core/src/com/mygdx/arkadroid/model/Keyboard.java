package com.mygdx.arkadroid.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.arkadroid.game.Arkadroid;

import java.util.ArrayList;

public class Keyboard {

    public ArrayList<Key> keyboard;
    private char[] symbols = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                              'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public Keyboard(Arkadroid game) {

        keyboard = new ArrayList<Key>();
        int s =0;
        for(int i=0; i<4; i++) {
            for(int j=0; j<8; j++) {
                if(s>25)
                    break;
                keyboard.add(new Key(symbols[s], new Rectangle(5+1.1f*game.width/9*j, 13*game.height/20-game.height/8*i,
                                                               game.width/9, game.height/8)));
                s++;
            }
            if(s>25)
                break;
        }
    }

    public Key type(Vector3 touchPoint) {

        for(int i=0; i<keyboard.size(); i++) {
            if(keyboard.get(i).isPressed(touchPoint))
                return keyboard.get(i);
        }

        return null;

    }

}
