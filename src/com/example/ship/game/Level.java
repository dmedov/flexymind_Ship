package com.example.ship.game;

import android.util.Log;
import com.example.ship.SceletonActivity;

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
    public static final float LEVEL_SPAWN_DELAY_MULTIPLIER = 0.9f;
    public static final float LEVEL_GOAL_MULTIPLIER = 1.5f;

    public final SceletonActivity activity;

    private int currentLevel;
    private int levelGoal;
    private int levelProgress;

    public Level(SceletonActivity activity) {
        this.activity = activity;
        this.currentLevel = 0;
    }

    public void startLevel(int level) {
        currentLevel = level;
        levelGoal = (int) (FIRST_LEVEL_GOAL * (1 + LEVEL_GOAL_MULTIPLIER * (currentLevel - 1)));
        levelProgress = 0;
        float newSpawnDelay = (float) ( ShipSpawner.MIN_SPAWN_DELAY
                                        + (ShipSpawner.MAX_SPAWN_DELAY - ShipSpawner.MIN_SPAWN_DELAY)
                                        * Math.pow(LEVEL_SPAWN_DELAY_MULTIPLIER, currentLevel - 1));
        activity.getSceneSwitcher().getGameScene().getShipSpawner()
                .setSpawnDelay(newSpawnDelay);

        Log.d("1log", "delay..." + newSpawnDelay);
        Log.d("1log", "goal..." + levelGoal);
    }

    public void incrementLevelProgress() {
        levelProgress++;
        Log.d("1log", "level progress..." + levelProgress);
        if (levelProgress >= levelGoal) {
            nextLevel();
        }
    }

    private void nextLevel(){
        currentLevel++;
        levelGoal = (int) (FIRST_LEVEL_GOAL * Math.pow(LEVEL_GOAL_MULTIPLIER, currentLevel - 1));
        levelProgress = 0;
        float newSpawnDelay = (float) (ShipSpawner.MAX_SPAWN_DELAY
                * Math.pow(LEVEL_SPAWN_DELAY_MULTIPLIER, currentLevel - 1));
        activity.getSceneSwitcher().getGameScene().getShipSpawner()
                .setSpawnDelay(newSpawnDelay);

        Log.d("1log", "delay..." + newSpawnDelay);
        Log.d("1log", "goal..." + levelGoal);
     }
}
