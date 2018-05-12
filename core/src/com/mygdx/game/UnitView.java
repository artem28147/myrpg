package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

/**
 * Вью юнита
 */
abstract public class UnitView {
    enum Pose {LEFT, RIGHT, UP, DOWN, STAND, ATTACK}

    ; //позы, в которых может стоять юнит
    Pose currentPose = Pose.STAND; //текущая поза
    List<String> animationsPaths = new LinkedList<String>(); //пути для анимаций
    float frameDuration = 0.08f; //длительность одного фрейма
    protected int FRAME_COLS[] = {8, 7, 7, 7, 1, 5};
    protected int FRAME_ROWS = 1;
    protected Map<String, Animation> animations = new HashMap<String, Animation>(); //контейнер с анимациями
    protected TextureRegion currentFrame; //текущий фрейм
    protected float stateTime;
    protected BitmapFont font = new BitmapFont();

    protected UnitView(String upWalking, String downWalking, String leftWalking, String rightWalking, String standing, String attacking) {
        font.setColor(255.0f, 255.0f, 255.0f, 255.0f);
        animationsPaths.add(upWalking);
        animationsPaths.add(downWalking);
        animationsPaths.add(leftWalking);
        animationsPaths.add(rightWalking);
        animationsPaths.add(standing);
        animationsPaths.add(attacking);
        constructFrames();
    }

    protected UnitView() {
        font.setColor(255.0f, 255.0f, 255.0f, 255.0f);
        animationsPaths.add("vverh.png");
        animationsPaths.add("vniz.png");
        animationsPaths.add("vlevo.png");
        animationsPaths.add("vpravo.png");
        animationsPaths.add("stand.png");
        animationsPaths.add("attack.png");
        constructFrames();
    }

    protected UnitView(String defaultPose) {
        font.setColor(255.0f, 255.0f, 255.0f, 255.0f);
        for (int i = 0; i < 6; i++)
            animationsPaths.add(defaultPose);
        constructFrames();
    }

    public void setSprites(String defaultPose) {
        font.setColor(255.0f, 255.0f, 255.0f, 255.0f);
        animationsPaths.clear();
        for (int i = 0; i < 6; i++)
            animationsPaths.add(defaultPose);
        constructFrames();
    }

    public void draw(SpriteBatch batch, Position pos) {
        stateTime += Gdx.graphics.getDeltaTime();
        switch (currentPose) {
            case LEFT:
                currentFrame = (TextureRegion) animations.get("leftWalking").getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                currentFrame = (TextureRegion) animations.get("rightWalking").getKeyFrame(stateTime, true);
                break;
            case UP:
                currentFrame = (TextureRegion) animations.get("upWalking").getKeyFrame(stateTime, true);
                break;
            case DOWN:
                currentFrame = (TextureRegion) animations.get("downWalking").getKeyFrame(stateTime, true);
                break;
            case STAND:
                currentFrame = (TextureRegion) animations.get("standing").getKeyFrame(stateTime, true);
                break;
            case ATTACK:
                currentFrame = (TextureRegion) animations.get("attacking").getKeyFrame(stateTime, true);
                break;

        }
        batch.draw(currentFrame, pos.getX(), pos.getY());
    }

    public void setLeft() {
        if (currentPose != Pose.LEFT && currentPose != Pose.ATTACK) {
            currentPose = Pose.LEFT;
        }
    }

    public void setRight() {
        if (currentPose != Pose.RIGHT && currentPose != Pose.ATTACK) {
            currentPose = Pose.RIGHT;
        }
    }

    public void setUp() {
        if (currentPose != Pose.UP && currentPose != Pose.ATTACK) {
            currentPose = Pose.UP;
        }
    }

    public void setDown() {
        if (currentPose != Pose.DOWN && currentPose != Pose.ATTACK) {
            currentPose = Pose.DOWN;
        }
    }

    public void setStand() {
        currentPose = Pose.STAND;
    }

    public void setAttack() {
        if (currentPose != Pose.ATTACK) {
            currentPose = Pose.ATTACK;
            //запускаем таймер воспроизведения анимации атаки
            AttackTimer th = new AttackTimer();
            th.start();
        }
    }

    public class AttackTimer extends Thread {
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setStand();
            return;
        }
    }

    protected void constructFrames() {
        //Загрузка всех фреймов юнита
        Random rand = new Random();
        String[] animationsNames = {"upWalking", "downWalking", "leftWalking", "rightWalking", "standing", "attacking"};
        for (int i = 0; i < 6; i++) {
            Texture tmpSheet = new Texture(Gdx.files.internal(animationsPaths.get(i)));
            TextureRegion[][] tmpRegion = TextureRegion.split(tmpSheet, tmpSheet.getWidth() / FRAME_COLS[i], tmpSheet.getHeight() / FRAME_ROWS); // #10
            TextureRegion[] tmpFrames = new TextureRegion[FRAME_COLS[i] * FRAME_ROWS];
            int index = 0;
            for (int j = 0; j < FRAME_ROWS; j++) {
                for (int k = 0; k < FRAME_COLS[i]; k++) {
                    tmpFrames[index++] = tmpRegion[j][k];
                }
            }
            animations.put(animationsNames[i], new Animation(frameDuration, tmpFrames));
        }
        stateTime = 0 + rand.nextFloat() * (1f - 0);
    }
}
