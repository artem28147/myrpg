package com.mygdx.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Модель игрока
 */
public class PlayerModel extends UnitModel {
    PlayerModel() {
        setPower(5);
    }

    private List<Item> items = new LinkedList<Item>(); //предметы
    private List<Effect> effects = new LinkedList<Effect>(); //эффекты
    private int xp = 0; //опыт
    private int gold = 0; //золото

    public List<Item> getItems() {
        return items;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        effects.remove(effect);
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int count) {
        gold = count;
    }
}
