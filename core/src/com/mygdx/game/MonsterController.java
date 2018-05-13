package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import com.mygdx.game.events.*;

/**
 * Контроллер монстра
 */
public class MonsterController extends UnitController {
    private Field field = null; //поле, на котором находится монстр

    MonsterController(Position pos, boolean test) {
        super(test);
        setModel(new MonsterModel());
        if (!test)
            setView(new MonsterView());
        getModel().setPosition(pos);
    }

    MonsterController() {
        super(false);
        setModel(new MonsterModel());
        setView(new MonsterView());
        getModel().setPosition(new Position(0, 0));
    }

    MonsterController(String name) {
        super(false);
        setModel(new MonsterModel());
        setView(new MonsterView());
        //В зависимости от введенного названия монстра, меняем его
        if (name == "bubble")
            turnToBubble();
        else if (name == "caravaner")
            turnToCaravaner();
        else if (name == "caravanchest")
            turnToCaravanchest();
    }

    MonsterController(Position pos, String name) {
        super(false);
        setModel(new MonsterModel());
        setView(new MonsterView());
        getModel().setPosition(pos);
        //В зависимости от введенного названия монстра, меняем его
        if (name == "bubble")
            turnToBubble();
        else if (name == "caravaner")
            turnToCaravaner();
        else if (name == "caravanchest")
            turnToCaravanchest();
    }

    /**
     * Реакция на атаку
     */
    public void reactOnAttack(UnitController attacker) {
        //Если hp меньше или равно 0, умереть
        if (super.getModel().getCurrentHp() <= 0)
            die(attacker);
        else
            attack(attacker);
    }

    /**
     * Умереть
     */
    protected void die(UnitController killer) {
        die();
        //Увеличить опыт убийцы
        if (killer instanceof PlayerController) {
            PlayerController pc = (PlayerController) killer;
            MonsterModel mm = (MonsterModel) getModel();
            pc.increaseXp(mm.getXpReward());
            if (this.getHp() == 1)
                pc.increaseGold(50);
        }
    }

    protected void die() {
        getField().removeUnit(this);
        fireMonsterDied();
    }

    /**
     * Отрисовка
     */
    @Override
    public void draw(SpriteBatch batch) {
        getView().draw(batch, getModel().getPosition());
    }

    /**
     * Превратиться в пузырь
     */
    public void turnToBubble() {
        getView().FRAME_COLS = new int[]{6, 6, 6, 6, 6, 6};
        getView().setSprites("bubble.png");
        getView().constructFrames();
        getModel().setHp(500);
        getModel().setCurrentHp(500);
        getModel().setPower(1);
        MonsterModel mm = (MonsterModel) getModel();
        mm.setXpReward(7);
    }

    /**
     * Превратиться в караванщика
     */
    public void turnToCaravaner() {
        getView().setSprites("caravaner.png");
        getView().constructFrames();
        getModel().setHp(1);
        getModel().setCurrentHp(1);
        getModel().setPower(0);
        MonsterModel mm = (MonsterModel) getModel();
        mm.setXpReward(5);
    }

    /**
     * Превратиться в сундук каравана
     */
    public void turnToCaravanchest() {
        getView().setSprites("caravanchest.png");
        getView().constructFrames();
        getModel().setHp(1);
        getModel().setCurrentHp(1);
        getModel().setPower(0);
        MonsterModel mm = (MonsterModel) getModel();
        mm.setXpReward(0);
    }

    /**
     * Слушатели
     */
    private ArrayList<MonsterActionListener> _listeners = new ArrayList();

    public void addMonsterActionListener(MonsterActionListener l) {
        _listeners.add(l);
    }

    public void removeMonsterActionListener(MonsterActionListener l) {
        _listeners.remove(l);
    }

    /**
     * Подать сигнал о смерти
     */
    protected void fireMonsterDied() {
        MonsterActionEvent event = new MonsterActionEvent(this);
        event.setMonster(this);
        for (MonsterActionListener listener : _listeners)
            listener.MonsterDied(event);
    }
}
