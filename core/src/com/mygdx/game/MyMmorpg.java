package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Главный класс игры
 */
public class MyMmorpg extends ApplicationAdapter {
    SpriteBatch batch;
    Field field; //поле
    PlayerController player; //игрок
    Texture inventory; //инвентарь
    Texture shop; //магазин
    Texture startMenu; //стартовое меню
    Texture restartMenu; //меню после смерти
    MyMmorpg game = this;
    List<MonsterController> caravan = new ArrayList<MonsterController>(); //караван
    boolean gameStarted = false;
    boolean playerDied = false;
    Module module;
    @Override
    public void create() {
        //Инициализация переменных
        batch = new SpriteBatch();
        field = new Field(false);
        player = new PlayerController(batch, false);
        player.setField(field);
        player.addPlayerActionListener(new MenuManager());
        module = null;
        startMenu = new Texture("startMenu.jpg");
        restartMenu = new Texture("restartMenu.jpg");
        shop = new Texture("shop.png");
        inventory = new Texture("inventory.png");
        //Установка обработчика нажатий
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (gameStarted && !playerDied)
                    player.interact(screenX, screenY);
                else
                    interact(screenX, screenY);
                return true;
            }
        });
        while (field.getItems().size() < 3) {
            Teleport tp = new Teleport(new Position((Math.random() * (field.getWidth() - 65)), Math.random() * (field.getLength() - 85)));
            field.addItem(tp);
        }
        //генерация монстров
        MonsterController mob;
        while (field.getUnits().size() < 15) {
            //с 15% шансом создается сильный монстр, 85% - слабый
            if (new Random().nextFloat() < 0.85f)
                mob = new MonsterController(new Position((Math.random() * (field.getWidth() - 65)), Math.random() * (field.getLength() - 85)),false);
            else
                mob = new MonsterController(new Position((Math.random() * (field.getWidth() - 65)), Math.random() * (field.getLength() - 85)), "bubble");
            field.addUnit(mob);
            mob.addMonsterActionListener(new DropGenerator());
            mob.setField(field);
        }
        //Добавление игрока
        field.addUnit(player);
        //Создание каравана
        Position caravanPos = new Position(400, 350);
        for (int i = 0; i < 4; i++) {
            mob = new MonsterController("caravaner");
            caravan.add(mob);
        }
        mob = new MonsterController("caravanchest");
        caravan.add(mob);
        CaravanThread caravanTh = new CaravanThread();
        caravanTh.start();
    }

    @Override
    public void render() {
        if(Gdx.input.isKeyPressed(Keys.F1)){
            String[] arr = new String[1];
            arr[0] = "..\\out\\production\\classes\\com\\mygdx\\game";
            ModuleEngine.main(arr,this);
        }
        //Если игра идёт и игрок жив
        if (!playerDied && gameStarted) {
            if (module!=null)
                module.run();
            else {
                //Обработка нажатий на кнопки ходьбы
                if (Gdx.input.isKeyPressed(Keys.A)) player.move(Direction.LEFT);
                else if (Gdx.input.isKeyPressed(Keys.D)) player.move(Direction.RIGHT);
                else if (Gdx.input.isKeyPressed(Keys.W)) player.move(Direction.UP);
                else if (Gdx.input.isKeyPressed(Keys.S)) player.move(Direction.DOWN);
                else player.dontRun();
            }
            //Отрисовка фона и монстров
            batch.begin();
            batch.draw(field.getTexture(), 0, 0);
            for (UnitController unit : new ArrayList<UnitController>(field.getUnits()))
                unit.draw(batch);
            for (Effect effect : field.getEffects())
                effect.draw(batch);
            for (Item item : field.getItems())
                item.draw(batch);
            batch.draw(inventory, 895, 670);
            batch.draw(shop, 835, 670);
            batch.end();

        } else if (!gameStarted) {
            batch.begin();
            batch.draw(startMenu, 0, 0);
            batch.end();
        } else if (playerDied) {
            batch.begin();
            batch.draw(restartMenu, 0, 0);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void setModule(Module module){
        this.module = module;
    }
    /**
     * Класс, генерирующий дроп после смерти монстра
     */
    public class DropGenerator implements MonsterActionListener {
        @Override
        public void MonsterDied(MonsterActionEvent e) {
            Random rnd = new Random();
            int hp = rnd.nextInt(10) + 1;
            int pow = rnd.nextInt(10) + 1;
            int agi = rnd.nextInt(10) + 1;
            int time = rnd.nextInt(60) + 30;
            if (rnd.nextBoolean())
                field.addEffect(new Effect(pow, agi, hp, time, e.getMonster().getModel().getPosition()));
            else
                field.addItem(new Item(pow, agi, hp, e.getMonster().getModel().getPosition()));
        }
    }

    /**
     * Поток, создающий караваны
     */
    public class CaravanThread extends Thread {
        public void run() {
            Random rand = new Random();
            //В течение всей игры
            while (true) {
                //Выбираем рандомную позицию
                int randomY = rand.nextInt((700 - 100) + 1) + 100;
                Position caravanPos = new Position(1000, randomY);
                //Создаем 5 монстров, которые идут друг с другом
                game.caravan.get(0).setPosition(new Position(caravanPos.getX() + 20, caravanPos.getY() + 20));
                game.caravan.get(1).setPosition(new Position(caravanPos.getX() + 20, caravanPos.getY() - 20));
                game.caravan.get(2).setPosition(new Position(caravanPos.getX() - 20, caravanPos.getY() + 20));
                game.caravan.get(3).setPosition(new Position(caravanPos.getX() - 20, caravanPos.getY() - 20));
                game.caravan.get(4).setPosition(new Position(caravanPos.getX(), caravanPos.getY()));
                for (MonsterController m : game.caravan) {
                    m.setCurrentHp(m.getHp());
                    game.field.addUnit(m);
                    m.addMonsterActionListener(new DropGenerator());
                    m.setField(field);
                }
                for (int i = 0; i < 500; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (MonsterController m : game.caravan) {
                        if (m.getCurrentHp() > 0)
                            m.move(Direction.LEFT);
                    }
                }
                for (MonsterController m : game.caravan) {
                    game.field.removeUnit(m);
                }
                try {
                    Thread.sleep(25000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Реакция на действия игрока
     */
    public void interact(int x, int y) {
        //Обработка нажатий по кнопкам меню
        if (!gameStarted && x > 350 && y > 270 && x < 700 && y < 400) {
            gameStarted = true;
        }
        if (playerDied && x > 350 && y > 270 && x < 700 && y < 400) {
            gameStarted = true;
            playerDied = false;
            field.units.clear();
            player = new PlayerController(batch,false);
            player.setField(field);
            player.addPlayerActionListener(new MenuManager());
            field.addUnit(player);
            MonsterController mob;
            while (field.getUnits().size() < 15) {
                if (new Random().nextFloat() < 0.65f)
                    mob = new MonsterController(new Position((Math.random() * (field.getWidth() - 65)), Math.random() * (field.getLength() - 85)),false );
                else
                    mob = new MonsterController(new Position((Math.random() * (field.getWidth() - 65)), Math.random() * (field.getLength() - 85)), "bubble");
                field.addUnit(mob);
                mob.addMonsterActionListener(new DropGenerator());
                mob.setField(field);
            }
        }
    }

    /**
     * Класс, реагирующий на смерть игрока
     */
    public class MenuManager implements PlayerActionListener {
        @Override
        public void PlayerDied(PlayerActionEvent e) {
            playerDied = true;
        }
    }
}
