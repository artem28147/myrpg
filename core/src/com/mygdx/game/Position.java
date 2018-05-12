package com.mygdx.game;

/**
 * Позиция
 */
public class Position {
    Position(double xx, double yy) {
        x = (float) xx;
        y = (float) yy;
    }

    private float x = 0;
    private float y = 0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
