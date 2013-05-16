package com.example.ship.game;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 16.05.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class Level {

    public static final int   FIRST_LEVEL_GOAL = 10;
    public static final float LEVEL_SCORE_MULTIPLIER = 0.1f;
    public static final float LEVEL_SPAWN_DELAY_MULTIPLIER = 0.1f;
    public static final float LEVEL_GOAL_MULTIPLIER = 0.2f;

    private int currentLevel;
    private int levelGoal;
    private int levelProgress;

    public Level() {
        this.currentLevel = 0;
        this.levelGoal = FIRST_LEVEL_GOAL;
        this.levelProgress = 0;
    }

    public void incrementLevelProgress() {
        levelProgress++;
        if (levelProgress == levelGoal) {
            nextLevel();
        }
    }

    private void nextLevel(){
        currentLevel++;
        levelGoal = (int) (FIRST_LEVEL_GOAL * (1 + LEVEL_GOAL_MULTIPLIER * currentLevel));
    }
}
