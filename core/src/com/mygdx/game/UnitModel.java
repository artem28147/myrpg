package com.mygdx.game;

/**
 * Модель юнита
 */
abstract public class UnitModel {
    private int hp = 5; //максимальное здоровье
    private int currentHp = 5; //текущее здоровье
    private int power = 1; //сила
    private int agility = 1; //уклонение
    private Position position = new Position(0, 0); //позиция
    private IntPair size = new IntPair(50, 50); //размер
    private int level = 1; //уровень

    public int getAgility() {
        return agility;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public IntPair getSize() {
        return size;
    }

    public Position getPosition() {
        return position;
    }

    public int getLevel() {
        return level;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setSize(IntPair size) {
        this.size = size;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
