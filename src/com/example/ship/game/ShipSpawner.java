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
public class ShipSpawner implements Runnable {
    private final SceletonActivity activity;
    private TimerHandler timerHandler;

    public ShipSpawner(SceletonActivity activity) {
        this.activity = activity;

    }

    @Override
    public void run() {
        timerHandler = new TimerHandler(0.1f, new ITimerCallback() {
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
        });
        activity.getEngine().registerUpdateHandler(timerHandler);
    }
}
