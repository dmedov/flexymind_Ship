package com.example.ship.game;

/*
Created by: IVAN
Date: 07.05.13
*/

import android.graphics.PointF;
import android.widget.Toast;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.modifier.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuadInOut;

public class Ship {

    public static final boolean TO_LEFT = true;
    public static final boolean TO_RIGHT = false;

    private static final float RELATIVE_WATERLINE = 0.1f;
    private static final float FINISH_OFFSET = 300.0f;
    private static final float MAX_ROTATE_ANGLE = 5.0f;
    private static final float ROTATE_DURATION = 3.0f;
    private static final float RELATIVE_ROTATION_CENTER_Y_OFFSET = 1.75f;
    private static final int ROTATION_COUNT = 10;

    private final SceletonActivity activity;
    private final float yPosition;
    private final int typeId;
    private final boolean direction;

    private PointF startPoint;
    private PointF finishPoint;
    private Sprite shipSprite;
    private Sprite hitAreaSprite;
    private float velocity;
    private int health;

    public Ship(SceletonActivity activity, float yPosition, int shipTypeId, boolean direction) {
        this.activity = activity;
        this.yPosition = yPosition;
        this.typeId = shipTypeId;
        this.direction = direction;

        initShipParametersById();

        shipSprite = new Sprite( 0
                               , 0
                               , activity.getResourceManager().getLoadedTextureRegion(shipTypeId)
                               , activity.getEngine().getVertexBufferObjectManager());

        setDirection();
        shipSprite.setPosition(startPoint.x, startPoint.y);
        createModifier();
    }

    public Sprite getSprite () {
        return shipSprite;
    }

    public float getVelocity() {
        return velocity;
    }

    public boolean getDirection() {
        return direction;
    }

    private void initShipParametersById() {
        switch (typeId) {
            case R.drawable.sailfish:
                this.velocity = activity.getIntResource(R.integer.SAILFISH_VELOCITY);
                this.health = activity.getIntResource(R.integer.SAILFISH_HEALTH);
                break;
            case R.drawable.missileboat:
                this.velocity = activity.getIntResource(R.integer.MISSILEBOAT_VELOCITY);
                this.health = activity.getIntResource(R.integer.MISSILEBOAT_HEALTH);
                break;
            case R.drawable.battleship:
                this.velocity = activity.getIntResource(R.integer.BATTLESHIP_VELOCITY);
                this.health = activity.getIntResource(R.integer.BATTLESHIP_HEALTH);
                break;
            default:
                this.velocity = activity.getIntResource(R.integer.MISSILEBOAT_VELOCITY);
                this.health = activity.getIntResource(R.integer.MISSILEBOAT_HEALTH);
                break;
        }
    }

    private void setDirection() {
        if (direction) {
            startPoint = new PointF( activity.getCamera().getXMax()
                                   , yPosition - shipSprite.getHeight() * (1 - RELATIVE_WATERLINE));
            finishPoint = new PointF( activity.getCamera().getXMin() - shipSprite.getWidth()
                                                                     - FINISH_OFFSET
                                    , startPoint.y);
        } else {
            startPoint = new PointF( activity.getCamera().getXMin() - shipSprite.getWidth()
                                   , yPosition - shipSprite.getHeight() * (1 - RELATIVE_WATERLINE));
            finishPoint = new PointF( activity.getCamera().getXMax() + FINISH_OFFSET
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

        shipSprite.setRotationCenterY(shipSprite.getRotationCenterX() * RELATIVE_ROTATION_CENTER_Y_OFFSET);

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

    public void missionDone() {
        shipSprite.detachSelf();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Корабль проплыл линию", Toast.LENGTH_LONG);
            }
        });
    }
}

