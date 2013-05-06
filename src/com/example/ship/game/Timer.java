package com.example.ship.game;

import android.service.wallpaper.WallpaperService;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;

public class Timer {

    private SceletonActivity activity;
    private Engine mEgine;
    private float controlPoint;
    private float checkPoint;
    private static final float fireTime = 2f;

    public Timer(SceletonActivity activity){
        this.activity = activity;
        this.mEgine = activity.getEngine();
    }

    public void setTime(){
        controlPoint = mEgine.getSecondsElapsedTotal();
    }

    public boolean checkTime(){
        checkPoint = mEgine.getSecondsElapsedTotal();
        if (checkPoint - controlPoint > fireTime){
            controlPoint = checkPoint;
            return true;
        } else {
            return false;
        }
    }

}
