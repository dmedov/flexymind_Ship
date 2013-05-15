package com.example.ship.game;

import com.example.ship.SceletonActivity;

public class Player {
    public static final int FULL_HP = 6;
    private int health;
    private int score;
    private SceletonActivity activity;

    public Player(SceletonActivity activity) {
        this.activity = activity;
        this.health = FULL_HP;
        this.score = 0;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth() {
        health++;
        activity.getSceneSwitcher().getGameScene().getGameHUD().addHealth(health);
    }

    public void reduceHealth() {
        health--;
        activity.getSceneSwitcher().getGameScene().getGameHUD().reduceHealth(health);
    }

    public void addPoints(int points) {
        score += points;
        activity.getSceneSwitcher().getGameScene().getGameHUD().updateScore(score);
    }

    public void reducePoints(int points) {
        score -= points;
        activity.getSceneSwitcher().getGameScene().getGameHUD().updateScore(score);
    }

    public int getScore(){
        return score;
    }
}
