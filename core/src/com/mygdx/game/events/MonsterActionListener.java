package com.mygdx.game.events;

import java.util.EventListener;

public interface MonsterActionListener extends EventListener {
    void MonsterDied(MonsterActionEvent e);
}
