package com.example.ship.bonus;

import com.example.ship.commons.A;
import com.example.ship.game.GameScene;
import com.example.ship.game.Gun;
import com.example.ship.game.Ship;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

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
        int random = rnd.nextInt(COUNT_GOOD_ACTIONS);
        switch (random) {
            case 0: stopAllShips();
                    break;
            case 1: setSmallFireDelay();
                    break;
            case 2: killAllShips();
                    break;
        }
    }

    public static void runBadBonus() { // TODO bad bonus

    }

    public static void stopAllShips() {  // TODO run ship after some seconds
        float shipStopTime = 5.0f;

        for (Ship ship : ships) {
            ship.getSprite().setIgnoreUpdate(true);
        }

        TimerHandler bonusTimerHandler = new TimerHandler(shipStopTime, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler timerHandler) {
                for (Ship ship : ships) {
                    ship.getSprite().setIgnoreUpdate(false);
                }
                A.e.unregisterUpdateHandler(timerHandler);
            }
        });
        A.e.registerUpdateHandler(bonusTimerHandler);
    }

    public static void setSmallFireDelay() { //TODO gun bonus
        float gunBonusTime = 5.0f;
        final float previousFireDelay = gun.getFireDelay();
        gun.setFireDelay(previousFireDelay / 5);

        TimerHandler bonusTimerHandler = new TimerHandler(gunBonusTime, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler timerHandler) {
                gun.setFireDelay(previousFireDelay);
                A.e.unregisterUpdateHandler(timerHandler);
            }
        });
        A.e.registerUpdateHandler(bonusTimerHandler);
    }

    public static void killAllShips() {
        for (Ship ship : ships) {
            ship.killSelf();
        }
        ships.clear();
    }
}
