package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Teleport extends Item{
    Teleport(Position pos) {
        super(0,0,0,pos);
        super.sprite = new Texture("teleport.png"); //текс
    }
}
