package com.example.ship.game;

/*
Created by: IVAN
Date: 07.05.13
*/

import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseLinear;
import android.graphics.PointF;

public class Ship {
    private PointF birthPoint;
    private PointF finishPoint;
    private Sprite shipSprite;
    private Sprite hitAreaSprite;

    private final float velocity;
    public int health;

    public Ship( SceletonActivity activity, float yBirthPoint, int shipID ) {
        this.velocity = 30;
        birthPoint = new PointF(activity.getCamera().getXMax(),yBirthPoint);
        switch (shipID) {
            case R.drawable.sailfish:
                shipSprite = new Sprite( birthPoint.x
                                         , birthPoint.y
                                         , activity.getResourceManager().getLoadedTextureRegion(shipID)
                                         , activity.getEngine().getVertexBufferObjectManager());
        }
        finishPoint = new PointF(activity.getCamera().getXMin() - shipSprite.getWidth(),yBirthPoint);
        createModifier();
    }

    public Sprite getSprite ( ) {
        return shipSprite;
    }

    private void createModifier() {
        MoveModifier moveModifier = new MoveModifier(     velocity
                                                        , birthPoint.x
                                                        , finishPoint.x
                                                        , birthPoint.y
                                                        , finishPoint.y
                                                        , EaseLinear.getInstance());
        shipSprite.registerEntityModifier(moveModifier);
    }
}

