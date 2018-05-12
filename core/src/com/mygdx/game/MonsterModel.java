package com.mygdx.game;

/**
 * Модель монстра
 */
public class MonsterModel extends UnitModel {
    int xpReward = 2;

    MonsterModel() {
        setSize(new IntPair(50, 60));
    }

    /**
     * Получить опыт
     */
    public int getXpReward() {
        return xpReward;
    }

    /**
     * Установить опыт
     */
    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

}
