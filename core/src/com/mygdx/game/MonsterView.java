package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Вью монстра
 */
public class MonsterView extends UnitView {
    MonsterView() {
        super("storm.png");
        FRAME_COLS = new int[]{8, 8, 8, 8, 8, 8};
        constructFrames();
    }
}
