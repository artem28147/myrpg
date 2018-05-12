package com.mygdx.game;

import java.util.ArrayList;

public class ModuleBot implements Module{

    MyMmorpg game;

    @Override
    public void load(MyMmorpg game, Module batch) {
        this.game = game;
        this.game.setModule(batch);
    }

    @Override
    public int run() {

        for (UnitController unit : new ArrayList<UnitController>(game.field.getUnits())){
            if (unit instanceof MonsterController) {
                if (game.player.getModel().getPosition().getX() - unit.getModel().getPosition().getX() < 0)
                    game.player.move(Direction.RIGHT);
                else
                    game.player.move(Direction.LEFT);
                if (game.player.getModel().getPosition().getY() - unit.getModel().getPosition().getY() < 0)
                    game.player.move(Direction.UP);
                else
                    game.player.move(Direction.DOWN);
                if (Math.abs(game.player.getModel().getPosition().getX() - unit.getModel().getPosition().getX()) < unit.getModel().getSize().x && Math.abs(game.player.getModel().getPosition().getY() - unit.getModel().getPosition().getY()) < unit.getModel().getSize().y)
                    game.player.attack(unit, new Position((int) unit.getModel().getPosition().getX(),(int) unit.getModel().getPosition().getY()));
                break;
            }
        }
        return 0;
    }

    @Override
    public void unload() {
        System.out.println("unload");
    }

}
