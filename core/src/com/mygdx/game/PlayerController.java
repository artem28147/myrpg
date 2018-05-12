package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.events.PlayerActionEvent;
import com.mygdx.game.events.PlayerActionListener;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер игрока
 */
public class PlayerController extends UnitController {
    SpriteBatch batch;
    boolean inventoryOpened = false;
    boolean shopOpened = false;
    Texture shopBackground = new Texture("shopBackground.jpg");
    Texture inventoryBackground = new Texture("inventoryBackground.jpg");
    BitmapFont font = new BitmapFont();

    PlayerController(SpriteBatch b) {
        batch = b;
        setModel(new PlayerModel());
        setView(new PlayerView());
    }

    @Override
    public PlayerView getView() {
        return (PlayerView) super.getView();
    }

    @Override
    public PlayerModel getModel() {
        return (PlayerModel) super.getModel();
    }

    /**
     * Атака монстра
     */
    public void attack(UnitController enemy, Position pos) {
        super.attack(enemy);
        getView().setAttack();
    }

    /**
     * Открыть инвентарь
     */
    private void openInventory() {
        inventoryOpened = true;
    }

    /**
     * Реакция на действия пользователя
     */
    public void interact(int x, int y) {
        if (shopOpened) {
            if (x > 625 && y > 220 && x < 750 && y < 270 && getModel().getGold() >= 100) {
                getModel().setPower(getModel().getPower() + 50);
                getModel().setGold(getModel().getGold() - 100);
            }
            if (x > 625 && y > 355 && x < 750 && y < 405 && getModel().getGold() >= 50) {
                getModel().setHp(getModel().getHp() + 100);
                getModel().setGold(getModel().getGold() - 50);
            }
            if (x > 625 && y > 500 && x < 750 && y < 550 && getModel().getGold() >= 25) {
                getModel().setAgility(getModel().getAgility() + 25);
                getModel().setGold(getModel().getGold() - 25);
            }
        }
        if (x > 895 && y > 15 && x < 945 && y < 65) {
            if (inventoryOpened == true)
                inventoryOpened = false;
            else {
                shopOpened = false;
                inventoryOpened = true;
            }
        } else if (x > 845 && y > 15 && x < 895 && y < 65) {
            if (shopOpened == true)
                shopOpened = false;
            else {
                inventoryOpened = false;
                shopOpened = true;
            }
        } else {
            for (UnitController mob : new ArrayList<UnitController>(getField().getUnits()))
                if (mob instanceof MonsterController)
                    //Проверяем, что курсор на монстрве
                    if (Math.abs(x - 35 - mob.getModel().getPosition().getX()) < mob.getModel().getSize().x && Math.abs(700 - y - mob.getModel().getPosition().getY()) < mob.getModel().getSize().y)
                        //Проверяем, что персонаж рядом с монстром
                        if (Math.abs(getModel().getPosition().getX() - mob.getModel().getPosition().getX()) < mob.getModel().getSize().x && Math.abs(getModel().getPosition().getY() - mob.getModel().getPosition().getY()) < mob.getModel().getSize().y) {
                            attack(mob, new Position(x, y));
                            return;
                        }
            for (Item item : new ArrayList<Item>(getField().getItems()))
                //Проверяем, что курсор на монстрве
                if (Math.abs(x - item.getPosition().getX()) < 100 && Math.abs(768 - y - item.getPosition().getY()) < 100)
                    //Проверяем, что персонаж рядом с монстром
                    if (Math.abs(getModel().getPosition().getX() - item.getPosition().getX()) < 100 && Math.abs(getModel().getPosition().getY() - item.getPosition().getY()) < 100) {
                        if (!(item instanceof Teleport))
                            equipItem(item);
                        else
                        {
                            java.util.Random rand = new java.util.Random();
                            this.setPosition(new Position(rand.nextInt((1024 - 0) + 1),rand.nextInt((768 - 0) + 1)));
                        }
                        return;
                    }
            for (Effect effect : new ArrayList<Effect>(getField().getEffects()))
                //Проверяем, что курсор на монстрве
                if (Math.abs(x - effect.getPosition().getX()) < 100 && Math.abs(768 - y - effect.getPosition().getY()) < 100)
                    //Проверяем, что персонаж рядом с монстром
                    if (Math.abs(getModel().getPosition().getX() - effect.getPosition().getX()) < 100 && Math.abs(getModel().getPosition().getY() - effect.getPosition().getY()) < 100) {
                        useEffect(effect);
                        return;
                    }
        }
    }

    /**
     * Надеть предмет
     */
    private void equipItem(Item item) {
        getField().removeItem(item);
        getModel().addItem(item);
        //Добавляем статы
        getModel().setHp(getModel().getHp() + item.getHp());
        getModel().setPower(getModel().getPower() + item.getPower());
        getModel().setAgility(getModel().getAgility() + item.getAgility());
    }

    /**
     * Использовать эффект
     */
    private void useEffect(Effect effect) {
        getField().removeEffect(effect);
        getModel().addEffect(effect);
        //Добавляем статы
        getModel().setHp(getModel().getHp() + effect.getHp());
        getModel().setPower(getModel().getPower() + effect.getPower());
        getModel().setAgility(getModel().getAgility() + effect.getAgility());
    }

    /**
     * Закончить действие эффекта
     */
    public void finishEffect(Effect effect) {
        PlayerModel pm = (PlayerModel) getModel();
        //Возвращаем статы обратно
        pm.setHp(pm.getHp() - effect.getHp());
        pm.setPower(pm.getPower() - effect.getPower());
        pm.setAgility(pm.getAgility() - effect.getAgility());
        pm.removeEffect(effect);
    }

    /**
     * Перестать бежать
     */
    public void dontRun() {
        if (!getView().isAttacking())
            getView().setStand();
    }

    /**
     * Удалить эффект
     */
    private void removeEffect(Effect effect) {
        getModel().removeEffect(effect);
    }

    public PlayerController outer() {
        return PlayerController.this;
    }


    /**
     * Отрисовка
     */
    @Override
    public void draw(SpriteBatch batch) {
        getView().draw(batch, getModel().getPosition(), getModel().getCurrentHp(), getModel().getHp(), getModel().getLevel(), getModel().getXp(), getModel().getAgility(), getModel().getPower(), getModel().getGold());
        if (inventoryOpened) {
            batch.draw(inventoryBackground, 270, 170);
            int y = 570;
            for (Item item : new ArrayList<Item>(getModel().getItems())) {
                font.draw(batch, "power:" + String.valueOf(item.getPower()) + " hp:" + String.valueOf(item.getHp()) + " agility:" + String.valueOf(item.getAgility()), 460, y);
                y -= 30;
            }
        }
        if (shopOpened) {
            batch.draw(shopBackground, 270, 150);
        }
    }

    /**
     * Реакция на атаку
     */
    public void reactOnAttack(UnitController attacker) {
        if (super.getModel().getCurrentHp() <= 0)
            die();
    }

    /**
     * Умереть
     */
    @Override
    protected void die() {
        getModel().setCurrentHp(0);
        firePlayerDied();
    }

    /**
     * Увеличить количество опыта
     */
    public void increaseXp(int count) {
        getModel().setXp(getModel().getXp() + count);
        while (getModel().getXp() > 10) {
            getModel().setXp(getModel().getXp() - 10);
            getModel().setLevel(getModel().getLevel() + 1);
            getModel().setCurrentHp(getModel().getHp());
        }
        getView().setStand();
    }

    /**
     * Увеличить количество золота
     */
    public void increaseGold(int count) {
        getModel().setGold(getModel().getGold() + count);
    }

    /**
     * Слушатели
     */
    private ArrayList<PlayerActionListener> _listeners = new ArrayList();

    public void addPlayerActionListener(PlayerActionListener l) {
        _listeners.add(l);
    }

    public void removeMonsterActionListener(PlayerActionListener l) {
        _listeners.remove(l);
    }

    /**
     * Подать сигнал о смерти игрока
     */
    protected void firePlayerDied() {
        PlayerActionEvent event = new PlayerActionEvent();
        for (PlayerActionListener listener : _listeners)
            listener.PlayerDied(event);
    }
}