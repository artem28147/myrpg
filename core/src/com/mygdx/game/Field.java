package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * Поле
 */
public class Field {
    private int length = 768; //длина
    private int width = 1024; //ширина
    private Texture texture = new Texture("map.jpg"); //текстура поля
    List<UnitController> units = new ArrayList<UnitController>(); //список юнитов на поле
    List<Item> items = new ArrayList<Item>(); //список предметов на поле
    List<Effect> effects = new ArrayList<Effect>(); //список эффектов на поле

    /**
     * Получить текстуру
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Получить длину
     */
    public int getLength() {
        return length;
    }

    /**
     * Получить ширину
     */
    public int getWidth() {
        return width;
    }

    /**
     * Получить юнитов
     */
    public List<UnitController> getUnits() {
        return units;
    }

    /**
     * Получить эффекты
     */
    public List<Effect> getEffects() {
        return effects;
    }

    /**
     * Получить предметы
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Установить длину
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Установить ширину
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Добавить юнита
     */
    public void addUnit(UnitController u) {
        units.add(u);
    }

    /**
     * Добавить эффект
     */
    public void addEffect(Effect e) {
        effects.add(e);
    }

    /**
     * Добавить предмет
     */
    public void addItem(Item i) {
        items.add(i);
    }

    /**
     * Удалить юнита
     */
    public void removeUnit(UnitController u) {
        units.remove(u);
    }

    /**
     * Удалить эффект
     */
    public void removeEffect(Effect e) {
        effects.remove(e);
    }

    /**
     * Удалить предмет
     */
    public void removeItem(Item i) {
        items.remove(i);
    }
}
