package com.example.ship.game;

import com.example.ship.SceletonActivity;

public class Player {
    public static final int FULL_HP = 6;
    private String name;
    private int health;
    private int score;
    private SceletonActivity activity;

    public Player(SceletonActivity activity, String name) {
        this.name = name;
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
    }

    public void reducePoints(int points) {
        score -= points;
    }

    public int getScore(){
        return score;
    }
}
