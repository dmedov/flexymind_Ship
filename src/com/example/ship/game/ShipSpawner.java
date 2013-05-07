package com.example.ship.game;

import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 07.05.13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class ShipSpawner{
    private final SceletonActivity activity;
    private TimerHandler timerHandler;
    private float delay;

    public ShipSpawner(SceletonActivity activity) {
        this.activity = activity;
        delay = 0.1f;
    }

    public void startSpawn() {
        timerHandler = new TimerHandler(delay, new TimerTask());
        activity.getEngine().registerUpdateHandler(timerHandler);
    }

    public void stopSpawn() {
        timerHandler.reset();
        timerHandler.setTimerSeconds(0);
    }

    class TimerTask implements ITimerCallback {

        @Override
        public void onTimePassed(TimerHandler timerHandler) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameScene gameScene = activity.getSceneSwitcher().getGameScene();
                    Ship ship = new Ship(activity, 300, R.drawable.sailfish);
                    gameScene.getChildByIndex(GameScene.LAYER_FIRST_WAVE).attachChild(ship.getSprite());
                }
            });
        }
    }
}
