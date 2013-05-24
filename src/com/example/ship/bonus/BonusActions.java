package com.example.ship.bonus;

import com.example.ship.commons.A;
import com.example.ship.game.GameScene;
import com.example.ship.game.Gun;
import com.example.ship.game.Ship;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 5/24/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class BonusActions {
    private static int COUNT_GOOD_ACTIONS = 3;
    private static int COUNT_BAD_ACTIONS = 1;

    private static GameScene gameScene = A.a.getSceneSwitcher().getGameScene();
    private static Gun gun = gameScene.getGun();
    private static ArrayList<Ship> ships = gameScene.getShips();
    private static Random rnd = new Random();

    public static void runGoodBonus() {
        int random = rnd.nextInt(1);
        switch (random) {
            case 0: stopAllShips();
                    break;
            case 1: setSmallFireDelay();
                    break;
            case 2: killAllShips();
                    break;
        }
    }

    public static void runBadBonus() {

    }

    public static void stopAllShips() {
        for (Ship ship : ships) {
            ship.getSprite().setIgnoreUpdate(true);
        }
    }

    public static void setSmallFireDelay() { //TODO bonus

    }

    public static void killAllShips() {
        for (Ship ship : ships) {
            ship.killSelf();
        }
        ships.clear();
    }
}
