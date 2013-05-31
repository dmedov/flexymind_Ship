package com.example.ship.game;

import com.example.ship.RootActivity;
import com.example.ship.game.hud.GameHUD;

public class Player {
    public static final int FULL_HP = 6;
    private Level level;
    private int health;
    private int score;
    private RootActivity activity;
    private GameHUD gameHUD;

    public Player(RootActivity activity) {
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
        if (health < FULL_HP && health > 0) {
            health++;
        }
        gameHUD.updateHealthIndicators(health);
    }

    public void reduceHealth() {
        if (!level.isBonus()) {
            health--;
        }

        gameHUD.updateHealthIndicators(health);

        if (health < 1) {
            activity.getSceneSwitcher().switchToGameOverHUD();
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
        String scoreString  = " ";
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
