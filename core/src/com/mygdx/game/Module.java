package com.mygdx.game;
public interface Module {

    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_FAILURE = 1;

    public void load(MyMmorpg game,Module batch);
    public int run();
    public void unload();
}