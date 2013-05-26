package com.example.ship.game.highscores;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 26.05.13
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
public class ScoreRecord {

    private String playerName;
    private int score;

    public ScoreRecord(String playerName, int score) {

        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return score + "\t" + playerName;
    }
}
