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

    private  static final float RELATIVE_WATERLINE = 0.1f;
    private PointF birthPoint;
    private PointF finishPoint;
    private Sprite shipSprite;
    private Sprite hitAreaSprite;
    private final float velocity;
    public int health;

    public Ship( SceletonActivity activity, float yBirthPoint, int shipID ) {
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

        birthPoint = new PointF(activity.getCamera().getXMax(),yBirthPoint - shipSprite.getHeight() * (1 - RELATIVE_WATERLINE));
        shipSprite.setPosition(birthPoint.x, birthPoint.y);

        finishPoint = new PointF(activity.getCamera().getXMin() - shipSprite.getWidth(), birthPoint.y);
        createModifier();
    }

    public Sprite getSprite ( ) {
        return shipSprite;
    }

    public float getVelocity() {
        return velocity;
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

