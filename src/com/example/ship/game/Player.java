package com.example.ship.game;

import com.example.ship.SceletonActivity;

public class Player {
    public static final int FULL_HP = 6;
    private static final int NUMBER_ZERO = 6;
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
        activity.getSceneSwitcher().getGameScene().getGameHUD().updateScore(convertScore(score));
    }

    public void reducePoints(int points) {
        score -= points;
        activity.getSceneSwitcher().getGameScene().getGameHUD().updateScore(convertScore(score));
    }

    public int getScore(){
        return score;
    }

    private String convertScore(int score) {
        // длина числа текущих Score
        int rankNumber = ("" + score).length();
        String convertScore = "Score: ";
        // дополняем наше Score нулями в начале
        for (int i = 0; i < NUMBER_ZERO - rankNumber; i++) {
            convertScore += "0";
        }
        convertScore += score;
        return convertScore;
    }
}
