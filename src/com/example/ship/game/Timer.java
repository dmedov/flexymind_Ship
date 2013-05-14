package com.example.ship.game;

import android.service.wallpaper.WallpaperService;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;

public class Timer {

    private static final float fireTimePeriod = 2f;

    private Engine mEgine;
    private float temporaryCheckpoint;
    private float checkTime;

    public Timer(SceletonActivity activity) {
        this.mEgine = activity.getEngine();
    }

    public void setTemporaryCheckpoint() {
        temporaryCheckpoint = mEgine.getSecondsElapsedTotal();
    }

    // temporaryCheckpoint - время последнего выстрела,  checkTime - данный момент
    public boolean checkTimeShoot() {
        checkTime = mEgine.getSecondsElapsedTotal();
        if (checkTime - temporaryCheckpoint > fireTimePeriod) {
            temporaryCheckpoint = checkTime;
            return true;
        } else {
            return false;
        }
    }

}
