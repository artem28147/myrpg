package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Контроллер юнита
 */
abstract public class UnitController {
    private UnitView view;
    public UnitModel model;
    private Field field;
    boolean test = false;
    UnitController(boolean test){
        this.test = test;
    }
    /**
     * Двигаться в указанном направлении
     */
    public void move(Direction d) {
        Position newPos = model.getPosition();
        switch (d) {
            case UP:
                if (newPos.getY() < field.getLength() - model.getSize().y) {
                    if (!test)
                        view.setUp();
                    newPos.setY(newPos.getY() + 2);
                }
                break;
            case DOWN:
                if (newPos.getY() > 0) {
                    if (!test)
                        view.setDown();
                    newPos.setY(newPos.getY() - 2);
                }
                break;
            case LEFT:
                if (newPos.getX() > 0) {
                    if (!test)
                        view.setLeft();
                    newPos.setX(newPos.getX() - 2);
                }
                break;
            case RIGHT:
                if (newPos.getX() < field.getWidth() - model.getSize().x) {
                    if (!test)
                        view.setRight();
                    newPos.setX(newPos.getX() + 2);
                }
                break;
        }
    }

    /**
     * Атаковать
     */
    public void attack(UnitController enemy) {
        Random rnd = new Random();
        float chance = rnd.nextFloat();
        //В зависимости от уворота можно промахнуться
        if (chance < (((float) 50 / (float) enemy.getModel().getAgility())))
            enemy.decreaseHp(this.model.getPower());
        enemy.reactOnAttack(this);
    }

    /**
     * Реакция на атаку
     */
    abstract public void reactOnAttack(UnitController attacker);

    /**
     * Умереть
     */
    abstract protected void die();

    public void decreaseHp(int count) {
        this.model.setCurrentHp(this.model.getCurrentHp() - count);
    }

    abstract public void draw(SpriteBatch batch);

    public UnitModel getModel() {
        return model;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public UnitView getView() {
        return view;
    }

    public void setModel(UnitModel model) {
        this.model = model;
    }

    public void setView(UnitView view) {
        this.view = view;
    }

    public void setPosition(Position pos) {
        model.setPosition(pos);
    }

    public int getHp() {
        return model.getHp();
    }

    public void setCurrentHp(int hp) {
        model.setCurrentHp(hp);
    }

    public int getCurrentHp() {
        return model.getCurrentHp();
    }
}
