package com.mygdx.game.events;

import com.mygdx.game.MonsterController;

import java.util.EventObject;

public class MonsterActionEvent extends EventObject {
    public MonsterActionEvent(Object source) {
        super(source);
    }
    MonsterController monster;

    public void setMonster(MonsterController monster) {
        this.monster = monster;
    }

    public MonsterController getMonster(){return monster;}
}
