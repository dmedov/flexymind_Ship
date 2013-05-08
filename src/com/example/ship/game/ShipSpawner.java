package com.example.ship.game;

import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 07.05.13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class ShipSpawner{
    public static final float REAL_SPEED_MULTIPLIER = 0.0007f;
    public static final float MAX_SPAWN_DELAY = 10f;
    public static final float FIRST_SPAWN_DELAY = 0.1f;
    private final SceletonActivity activity;
    private TimerHandler timerHandler;
    private float delay;
    private boolean spawning;

    public ShipSpawner(SceletonActivity activity) {
        this.activity = activity;
        delay = FIRST_SPAWN_DELAY;
        spawning = false;
    }

    public void startSpawn() {
        spawning = true;
        timerHandler = new TimerHandler(delay, new TimerTask());
        activity.getEngine().registerUpdateHandler(timerHandler);
    }

    public void stopSpawn() {
        spawning = false;
        timerHandler = null;
    }

    private class TimerTask implements ITimerCallback {

        @Override
        public void onTimePassed(final TimerHandler timerHandler) {

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (spawning) {
                        delay += new Random().nextFloat() * MAX_SPAWN_DELAY;
                        timerHandler.setTimerSeconds(delay);
                        timerHandler.reset();

                        GameScene gameScene = activity.getSceneSwitcher().getGameScene();
                        Ship ship = new Ship( activity
                                            , gameScene.getShipLinePosition(GameScene.LAYER_FIRST_SHIP_LINE)
                                            , R.drawable.sailfish);
                        gameScene.getChildByIndex(GameScene.LAYER_FIRST_SHIP_LINE).attachChild(ship.getSprite());

                        delay = ship.getSprite().getWidth() * ship.getVelocity() * REAL_SPEED_MULTIPLIER;
                    }
                }
            });
        }
    }
}
