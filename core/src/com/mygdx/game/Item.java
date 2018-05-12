package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Предмет
 */
public class Item {
    int power; //сила
    int agility; //ловкость
    int hp; //здоровье
    private Position position = new Position(0, 0); //позиция
    protected Texture sprite = new Texture("item.png"); //текстура

    Item(int p, int a, int h, Position pos) {
        power = p;
        agility = a;
        hp = h;
        position = pos;
    }

    /**
     * Отрисовка
     */
    public void draw(SpriteBatch batch) {
        batch.draw(sprite, position.getX(), position.getY());
    }

    /**
     * Получить позицию
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Получить силу
     */
    public int getPower() {
        return power;
    }

    /**
     * Получить здоровье
     */
    public int getHp() {
        return hp;
    }

    /**
     * Получить уклонение
     */
    public int getAgility() {
        return agility;
    }
}