package com.example.ship.game;

import com.example.ship.R;
import com.example.ship.SceletonActivity;

public class Player {
    public static final int FULL_HP = 6;
    private Level level;
    private int health;
    private int score;
    private SceletonActivity activity;
    private GameHUD gameHUD;

    public Player(SceletonActivity activity) {
        this.activity = activity;
        this.level = new Level(activity);
        this.health = FULL_HP;
        this.score = 0;
    }

    public void onShipShotDown() {
        level.incrementLevelProgress();
    }

    public void setGameHUD(GameHUD gameHUD) {
        this.gameHUD = gameHUD;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public Level getLevel() {
        return level;
    }

    public void addHealth() {
        if (health < FULL_HP) {
            health++;
        }
        gameHUD.updateHealthIndicators(health);
    }

    public void reduceHealth() {
        health--;
        gameHUD.updateHealthIndicators(health);
        if (health <= 0) {
            activity.getSceneSwitcher().switchToGameOverHUD();
            cleanParameters();
            gameHUD.updateHealthIndicators(health);
            gameHUD.updateScore();
        }
    }

    public void addPoints(int points) {
        score += points;
        gameHUD.updateScore();
    }

    public void reducePoints(int points) {
        score -= points;
        gameHUD.updateScore();
    }

    public String getStringScore() {
        int digitNumber = ("" + score).length();
        String scoreString  = activity.getResources().getString(R.string.SCORE) + ": ";
        // дополняем наше Score нулями в начале
        for (int i = 0; i < FULL_HP - digitNumber; i++) {
            scoreString  += "0";
        }
        scoreString += score;
        return scoreString;
    }

    private void cleanParameters() {
        score = 0;
        health = FULL_HP;
    }
}
