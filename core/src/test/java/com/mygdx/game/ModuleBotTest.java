package com.mygdx.game;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModuleBotTest {
    @Test
    public void test_BotKillsMob() {
        MyMmorpg game = new MyMmorpg();
        ModuleBot bot = new ModuleBot();
        game.module = bot;
        bot.load(game,null);
        game.field = new Field(true);
        game.player = new PlayerController(null,true);
        game.player.setField(game.field);
        UnitController mob = new MonsterController(new Position(0,0),true);
        game.field.addUnit(mob);
        mob.setField(game.field);
        bot.run();
        Assert.assertEquals(game.field.units.size(), 0);
    }

    @Test
    public void test_BotMoveToMobUp() {
        MyMmorpg game = new MyMmorpg();
        ModuleBot bot = new ModuleBot();
        game.module = bot;
        bot.load(game,null);
        game.field = new Field(true);
        game.player = new PlayerController(null,true);
        game.player.setField(game.field);
        UnitController mob = new MonsterController(new Position(0,100),true);
        game.field.addUnit(mob);
        mob.setField(game.field);
        bot.run();
        Assert.assertEquals(game.field.units.size(), 1);
        Assert.assertEquals(game.player.model.getPosition().getX(), 0.0,0.0001);
        Assert.assertEquals(game.player.model.getPosition().getY(), 2.0,0.0001);
    }

    @Test
    public void test_BotMoveToMobRight() {
        MyMmorpg game = new MyMmorpg();
        ModuleBot bot = new ModuleBot();
        game.module = bot;
        bot.load(game,null);
        game.field = new Field(true);
        game.player = new PlayerController(null,true);
        game.player.setField(game.field);
        UnitController mob = new MonsterController(new Position(100,0),true);
        game.field.addUnit(mob);
        mob.setField(game.field);
        bot.run();
        Assert.assertEquals(game.field.units.size(), 1);
        Assert.assertEquals(game.player.model.getPosition().getX(), 2.0,0.0001);
        Assert.assertEquals(game.player.model.getPosition().getY(), 0.0,0.0001);
    }

    @Test
    public void test_BotMoveToMobRightTop() {
        MyMmorpg game = new MyMmorpg();
        ModuleBot bot = new ModuleBot();
        game.module = bot;
        bot.load(game,null);
        game.field = new Field(true);
        game.player = new PlayerController(null,true);
        game.player.setField(game.field);
        UnitController mob = new MonsterController(new Position(100,100),true);
        game.field.addUnit(mob);
        mob.setField(game.field);
        bot.run();
        Assert.assertEquals(game.field.units.size(), 1);
        Assert.assertEquals(game.player.model.getPosition().getX(), 2.0,0.0001);
        Assert.assertEquals(game.player.model.getPosition().getY(), 2.0,0.0001);
    }

    @Test
    public void test_BotMoveToMobLeft() {
        MyMmorpg game = new MyMmorpg();
        ModuleBot bot = new ModuleBot();
        game.module = bot;
        bot.load(game,null);
        game.field = new Field(true);
        game.player = new PlayerController(null,true);
        game.player.setField(game.field);
        game.player.setPosition(new Position(100,0));
        UnitController mob = new MonsterController(new Position(0,0),true);
        game.field.addUnit(mob);
        mob.setField(game.field);
        bot.run();
        Assert.assertEquals(game.field.units.size(), 1);
        Assert.assertEquals(game.player.model.getPosition().getX(), 96.0,0.0001);
        Assert.assertEquals(game.player.model.getPosition().getY(), 0.0,0.0001);
    }
}

