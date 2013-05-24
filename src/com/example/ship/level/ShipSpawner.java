package com.example.ship.level;

import com.example.ship.RootActivity;
import org.andengine.engine.handler.timer.ITimerCallback;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 07.05.13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShipSpawner {

    private final RootActivity activity;
    private PauseableTimerHandler timerHandler;
    private float delay;
    private float spawnDelay;

    public ShipSpawner(RootActivity activity, float firstSpawnIn, float spawnDelay) {
        this.activity = activity;
        this.delay = firstSpawnIn;
        this.spawnDelay = spawnDelay;
    }

    public abstract void startSpawn();

    public void pauseSpawn() {
        timerHandler.pause();
    }

    public void resumeSpawn() {
        timerHandler.resume();
    }

    public void stopSpawn() {
        activity.getEngine().unregisterUpdateHandler(timerHandler);
        timerHandler = null;
    }

    public void setSpawnDelay(float spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    private abstract class TimerTask implements ITimerCallback{}
}
