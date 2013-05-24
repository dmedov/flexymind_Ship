package com.example.ship.level;

import com.example.ship.RootActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 07.05.13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShipSpawner {

    protected final RootActivity activity;
    protected PauseableTimerHandler timerHandler;
    protected float delay;
    protected float spawnDelay;

    public ShipSpawner(RootActivity activity, float firstSpawnIn, float spawnDelay) {
        this.activity = activity;
        this.delay = firstSpawnIn;
        this.spawnDelay = spawnDelay;
    }

    public void startSpawn() {
        if (timerHandler != null) {
            activity.getEngine().registerUpdateHandler(timerHandler);
        }
    }

    public void pauseSpawn() {
        if (timerHandler != null) {
            timerHandler.pause();
        }
    }

    public void resumeSpawn() {
        if (timerHandler != null) {
            timerHandler.resume();
        }
    }

    public void stopSpawn() {
        activity.getEngine().unregisterUpdateHandler(timerHandler);
        timerHandler = null;
    }

}
