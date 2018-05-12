package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Временный эффект
 */
public class Effect {
    int power; //сила
    int agility; //ловкость
    int hp; //здоровье
    int time; //время, которое длится эффект
    PlayerController player; //игрок, на которого действует эффект
    Effect e = this;
    protected Texture sprite = new Texture("effect.png"); //текстура
    private Position position = new Position(0, 0); //позиция

    Effect(int p, int a, int h, int t, Position pos) {
        power = p;
        agility = a;
        hp = h;
        time = t;
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

    /**
     * Установить игрока
     */
    public void setPlayer(PlayerController p) {
        this.player = p;
        Ticking tickThread = new Ticking();
        tickThread.start();
    }

    /**
     * Поток для временного действия эффекта
     */
    public class Ticking extends Thread {
        public void run() {
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.finishEffect(e);
            return;
        }
    }
}
