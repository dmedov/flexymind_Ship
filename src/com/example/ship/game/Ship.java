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

    private static final float RELATIVE_WATERLINE = 0.1f;
    private PointF startPoint;
    private PointF finishPoint;
    private Sprite shipSprite;
    private Sprite hitAreaSprite;
    private final float velocity;
    private final SceletonActivity activity;
    private final float y;
    public int health;

    public Ship(SceletonActivity activity, float y, int shipID, boolean direction) {
        this.activity = activity;
        this.y = y;

        switch (shipID) {
            case R.drawable.sailfish:
                this.velocity = 30;
                this.health = 100;
                break;

            default:
                this.velocity = 30;
                this.health = 100;
                break;
        }

        shipSprite = new Sprite( 0
                               , 0
                               , activity.getResourceManager().getLoadedTextureRegion(shipID)
                               , activity.getEngine().getVertexBufferObjectManager());

        setDirection(direction);

        shipSprite.setPosition(startPoint.x, startPoint.y);

        createModifier();

    }

    private void setDirection(boolean direction) {
        if (direction) {
            startPoint = new PointF( activity.getCamera().getXMax()
                                   , y - shipSprite.getHeight() * (1 - RELATIVE_WATERLINE));
            finishPoint = new PointF( activity.getCamera().getXMin() - shipSprite.getWidth()
                                    , startPoint.y);
        } else {
            startPoint = new PointF( activity.getCamera().getXMin() - shipSprite.getWidth()
                                   , y - shipSprite.getHeight() * (1 - RELATIVE_WATERLINE));
            finishPoint = new PointF( activity.getCamera().getXMax()
                                    , startPoint.y);
            shipSprite.setScaleX(-1);
        }
    }

    public Sprite getSprite ( ) {
        return shipSprite;
    }

    public float getVelocity() {
        return velocity;
    }

    private void createModifier() {
        MoveModifier moveModifier = new MoveModifier( velocity
                                                    , startPoint.x
                                                    , finishPoint.x
                                                    , startPoint.y
                                                    , finishPoint.y
                                                    , EaseLinear.getInstance());
        shipSprite.registerEntityModifier(moveModifier);
    }
}

