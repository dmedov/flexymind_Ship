package com.example.ship.game;

public class Player {
    private String name;
    private int health;
    private int score;

    public Player(String name) {
        this.name = name;
        this.health = 6;
        this.score = 0;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth() {
        health++;
    }

    public void reduceHealth() {
        health--;
    }

    public void addPoints(int points) {
        score += points;
    }

    public void reducePoints(int points) {
        score -= points;
    }
}
