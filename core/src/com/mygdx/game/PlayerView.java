package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Вью игрока
 */
public class PlayerView extends UnitView {
    PlayerView() {
        super();
    }

    public void draw(SpriteBatch batch, Position pos, int currentHp, int hp, int level, int xp, int agility, int power, int gold) {
        super.draw(batch, pos);
        //иконка
        batch.draw(currentFrame, pos.getX(), pos.getY());
        //уровень
        font.draw(batch, "Level: " + String.valueOf(level), 950, 725);
        //опыт
        font.draw(batch, "XP:" + String.valueOf(xp) + "/10", 950, 700);
        //хп
        font.draw(batch, "HP:" + String.valueOf(currentHp) + "/" + String.valueOf(hp), 950, 675);
        //ловкость
        font.draw(batch, "Agility:" + String.valueOf(agility), 950, 650);
        //сила
        font.draw(batch, "Power:" + String.valueOf(power), 950, 625);
        //золото
        font.draw(batch, "Gold:" + String.valueOf(gold), 950, 600);
    }

    public boolean isAttacking() {
        if (super.currentPose == Pose.ATTACK)
            return true;
        return false;
    }
}
