package com.example.ship.game;

/*
Created by: IVAN
Date: 07.05.13
*/

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.modifier.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuadInOut;

public class Ship {

    private static final float RELATIVE_WATERLINE = 0.1f;
    private static final float MAX_ROTATE_ANGLE = 7.0f;
    private static final float ROTATE_DURATION = 3.0f;
    private static final int ROTATION_COUNT = 5;
    private final SceletonActivity activity;
    private final float y;
    private final int typeId;
    private PointF startPoint;
    private PointF finishPoint;
    private Sprite shipSprite;
    private Sprite hitAreaSprite;
    private float velocity;
    private int health;

    public Ship(SceletonActivity activity, float y, int shipTypeId, boolean direction) {
        this.activity = activity;
        this.y = y;
        this.typeId = shipTypeId;

        initShipParametersById();

        shipSprite = new Sprite( 0
                               , 0
                               , activity.getResourceManager().getLoadedTextureRegion(shipTypeId)
                               , activity.getEngine().getVertexBufferObjectManager());

        setDirection(direction);
        shipSprite.setPosition(startPoint.x, startPoint.y);
        createModifier();
    }

    public Sprite getSprite ( ) {
        return shipSprite;
    }

    public float getVelocity() {
        return velocity;
    }

    private void initShipParametersById() {
        switch (typeId) {
            case R.drawable.sailfish:
                this.velocity = 30;
                this.health = 100;
                break;
            default:
                this.velocity = 30;
                this.health = 100;
                break;
        }
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

    private void createModifier() {
        MoveModifier moveModifier = new MoveModifier( velocity
                                                    , startPoint.x
                                                    , finishPoint.x
                                                    , startPoint.y
                                                    , finishPoint.y
                                                    , EaseLinear.getInstance());

        RotationModifier rotateUpModifier = new RotationModifier( ROTATE_DURATION
                                                                , MAX_ROTATE_ANGLE
                                                                , - MAX_ROTATE_ANGLE
                                                                , EaseQuadInOut.getInstance());
        RotationModifier rotateDownModifier = new RotationModifier( ROTATE_DURATION
                                                                  , - MAX_ROTATE_ANGLE
                                                                  , MAX_ROTATE_ANGLE
                                                                  , EaseQuadInOut.getInstance());

        SequenceEntityModifier rotateSequence = new SequenceEntityModifier( rotateUpModifier
                                                                          , rotateDownModifier);
        LoopEntityModifier loopRotate = new LoopEntityModifier( rotateSequence
                                                              , ROTATION_COUNT);

        ParallelEntityModifier moveWithRotationModifier = new ParallelEntityModifier( loopRotate
                                                                                    , moveModifier);

        shipSprite.registerEntityModifier(moveWithRotationModifier);
    }
}

