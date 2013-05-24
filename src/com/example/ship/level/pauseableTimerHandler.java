package com.example.ship.level;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 24.05.13
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */
public class PauseableTimerHandler extends TimerHandler {

    private boolean pause = false;

    public PauseableTimerHandler(float pTimerSeconds, ITimerCallback pTimerCallback) {
        super(pTimerSeconds, pTimerCallback);
    }

    public void pause() {
        this.pause = true;
    }

    public void resume() {
        this.pause = false;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if(!this.pause) {
            super.onUpdate(pSecondsElapsed);
        }
    }
}
