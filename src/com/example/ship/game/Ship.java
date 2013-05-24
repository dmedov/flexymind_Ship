package com.example.ship.game;

/*
Created by: IVAN
Date: 07.05.13
*/

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.RootActivity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuadIn;
import org.andengine.util.modifier.ease.EaseQuadInOut;
import org.andengine.util.modifier.ease.EaseQuadOut;

import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.abs;

public class Ship {

    public static final boolean TO_LEFT = true;
    public static final boolean TO_RIGHT = false;
    public static final HashMap<String, Integer> shipsId;
    static {
        shipsId = new HashMap<String, Integer>();
        shipsId.put("missileBoat", R.drawable.missileboat);
        shipsId.put("battleship", R.drawable.battleship);
        shipsId.put("sailfish", R.drawable.sailfish);
    }

    private static final float RELATIVE_WATERLINE = 0.1f;
    private static final float FINISH_OFFSET = 300.0f;
    private static final float MAX_ROTATE_ANGLE = 5.0f;
    private static final float ROTATE_DURATION = 3.0f;
    private static final float RELATIVE_ROTATION_CENTER_Y_OFFSET = 1.75f;
    private static final float RELATIVE_HIT_AREA_OFFSET = 0.1f;
    private static final float SINK_ACCELERATION = 40f;
    private static final float MAX_SINK_ROTATION_ANGLE = 90f;
    private static final float MAX_SINK_ROTATION_VELOCITY = 20f;
    private static final float MIN_SINK_ROTATION_VELOCITY = 2f;
    private static final float MAX_SINK_VELOCITY = 20f;
    private static final float MIN_SINK_VELOCITY = 2f;
    private static final float ALPHA_SINK_TIME = 20f;
    private static final int ROTATION_COUNT = 10;
    private static final float RELATIVE_SIZE_AROUND = 1f;
    private static final float RELATIVE_SIZE_BEYOND = 0.5f;
    private static float velocityDivider = 1;

    private final float yPosition;
    private final RootActivity activity;
    private final int typeId;
    private final boolean direction;

    private PointF startPoint;
    private PointF finishPoint;
    private Sprite shipSprite;
    private Sprite hitAreaSprite;
    private float velocity;
    private int health;
    private int score;
    private Random rand;

    public Ship(RootActivity activity, float yPosition, String shipType, boolean direction) {
        this(activity, yPosition, shipsId.get(shipType), direction);
    }

    public Ship(RootActivity activity, float yPosition, int shipTypeId, boolean direction) {
        this.activity = activity;
        this.yPosition = yPosition;
        this.typeId = shipTypeId;
        this.direction = direction;

        shipSprite = new Sprite( 0
                               , 0
                               , activity.getResourceManager().getLoadedTextureRegion(shipTypeId)
                               , activity.getEngine().getVertexBufferObjectManager());

        initShipParametersById();
        setScale();        // нельзя менять местами с setDirection()
        setDirection();
        shipSprite.setPosition(startPoint.x, startPoint.y);
        createModifier();
    }

    public static void setVelocityDivider(float velocityDivider) {
        Ship.velocityDivider = velocityDivider;
    }

    public Sprite getSprite () {
        return shipSprite;
    }

    public Sprite getHitAreaSprite() {
        return hitAreaSprite;
    }

    public float getVelocity() {
        return velocity;
    }

    public boolean getDirection() {
        return direction;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public void missionDone() {
        hitAreaSprite.detachSelf();
        shipSprite.detachSelf();
    }

    public void killSelf() {
        shipSprite.detachChild(hitAreaSprite);
        shipSprite.clearEntityModifiers();
        createSinkModifier();
    }

    public boolean hit(int hitPoints) {
        health -= hitPoints;
        if ( health <= 0) {
            activity.getSceneSwitcher().getGameScene().getPlayer().getLevel().incrementLevelProgress();
            killSelf();
            return true;
        }

        return false;
    }

    private void initShipParametersById() {
        switch (typeId) {
            case R.drawable.sailfish:
                this.velocity = activity.getIntResource(R.integer.SAILFISH_VELOCITY);
                this.health =   activity.getIntResource(R.integer.SAILFISH_HEALTH);
                this.score =    activity.getIntResource(R.integer.SAILFISH_SCORE_POINTS);
                createHitArea ( activity.getIntResource(R.integer.SAILFISH_HITAREA_START_PIXEL)
                              , activity.getIntResource(R.integer.SAILFISH_HITAREA_END_PIXEL));
                break;
            case R.drawable.missileboat:
                this.velocity = activity.getIntResource(R.integer.MISSILEBOAT_VELOCITY);
                this.health =   activity.getIntResource(R.integer.MISSILEBOAT_HEALTH);
                this.score =    activity.getIntResource(R.integer.MISSILEBOAT_SCORE_POINTS);
                createHitArea ( activity.getIntResource(R.integer.MISSILEBOAT_HITAREA_START_PIXEL)
                              , activity.getIntResource(R.integer.MISSILEBOAT_HITAREA_END_PIXEL));
                break;
            case R.drawable.battleship:
                this.velocity = activity.getIntResource(R.integer.BATTLESHIP_VELOCITY);
                this.health =   activity.getIntResource(R.integer.BATTLESHIP_HEALTH);
                this.score =    activity.getIntResource(R.integer.BATTLESHIP_SCORE_POINTS);
                createHitArea ( activity.getIntResource(R.integer.BATTLESHIP_HITAREA_START_PIXEL)
                              , activity.getIntResource(R.integer.BATTLESHIP_HITAREA_END_PIXEL));
                break;
            default:
                this.velocity = activity.getIntResource(R.integer.MISSILEBOAT_VELOCITY);
                this.health =   activity.getIntResource(R.integer.MISSILEBOAT_HEALTH);
                this.score =    activity.getIntResource(R.integer.MISSILEBOAT_SCORE_POINTS);
                createHitArea ( activity.getIntResource(R.integer.MISSILEBOAT_HITAREA_START_PIXEL)
                              , activity.getIntResource(R.integer.MISSILEBOAT_HITAREA_END_PIXEL));
                break;
        }
        this.velocity /= velocityDivider;
    }

    private void setScale() {
        GameScene gameScene = activity.getSceneSwitcher().getGameScene();
        float lastShipLinePositionFromBottomY =
                activity.getCamera().getHeightRaw() - gameScene.getShipLinePosition( GameScene.LAYER_THIRD_SHIP_LINE );
        float skyLinePositionFromBottomY =
                activity.getCamera().getHeightRaw() - gameScene.getChildByIndex( GameScene.LAYER_FIRST_WAVE )
                        .getFirstChild().getY();

        // увеличение f(x)=a/x + b, равно RELATIVE_SIZE_AROUND на самой ближайшей линии спауна кораблей,
        // RELATIVE_SIZE_BEYOND на горизонте
        float a = ( RELATIVE_SIZE_AROUND - RELATIVE_SIZE_BEYOND )
                * ( lastShipLinePositionFromBottomY * skyLinePositionFromBottomY )
                / ( skyLinePositionFromBottomY - lastShipLinePositionFromBottomY);
        float b = RELATIVE_SIZE_BEYOND - a/skyLinePositionFromBottomY;
        float perspectiveScale = a / ( activity.getCamera().getHeightRaw() - yPosition ) + b;
        final PointF LEFT_TOP= new PointF( 0f, 0f );  //по умолчанию scaleCenter не всегда в левом верхнем углу
        shipSprite.setScaleCenter( LEFT_TOP.x, LEFT_TOP.y );
        if (direction == TO_LEFT) {
            shipSprite.setScale( perspectiveScale, perspectiveScale );
        } else {
            shipSprite.setScale( -perspectiveScale, perspectiveScale );
        }
    }
    private void setDirection() {
        if (direction == TO_LEFT) {
            startPoint = new PointF( activity.getCamera().getXMax()
                                   , yPosition - shipSprite.getHeightScaled() * (1 - RELATIVE_WATERLINE));

            finishPoint = new PointF( activity.getCamera().getXMin() - shipSprite.getWidthScaled()
                                                                     - FINISH_OFFSET
                                    , startPoint.y);
        } else {
            startPoint = new PointF( activity.getCamera().getXMin() - shipSprite.getWidth()
                                   , yPosition - shipSprite.getHeightScaled() * (1 - RELATIVE_WATERLINE));
            finishPoint = new PointF( activity.getCamera().getXMax() + FINISH_OFFSET
                                    , startPoint.y);
        }
    }

    private void createModifier() {
        MoveModifier moveModifier = new MoveModifier( velocity
                                                    , startPoint.x
                                                    , finishPoint.x
                                                    , startPoint.y
                                                    , finishPoint.y
                                                    , EaseLinear.getInstance());

        shipSprite.setRotationCenterX( 0.5f * shipSprite.getWidthScaled() );
        shipSprite.setRotationCenterY(abs( shipSprite.getRotationCenterX() ) * RELATIVE_ROTATION_CENTER_Y_OFFSET);

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

    private void createHitArea(float hitAreaFromPixel, float hitAreaToPixel) {
        hitAreaSprite = new Sprite( 0
                , shipSprite.getHeight() * ( 1 - RELATIVE_HIT_AREA_OFFSET )
                , activity.getResourceManager().getLoadedTextureRegion(R.drawable.hitarea)
                , activity.getVertexBufferObjectManager() );
        hitAreaSprite.setScaleCenterX(0);
        hitAreaSprite.setScaleX((hitAreaToPixel - hitAreaFromPixel) / hitAreaSprite.getWidthScaled());
        hitAreaSprite.setX(hitAreaFromPixel);
        shipSprite.attachChild(hitAreaSprite);
    }

    private void createSinkModifier() {
        final float START_SPEED = abs(finishPoint.x - startPoint.x) / velocity;
        rand = new Random();
        float sinkPointX = (direction == TO_LEFT) ?
                (shipSprite.getX() - START_SPEED * START_SPEED / (2 * SINK_ACCELERATION))
                : (shipSprite.getX() + START_SPEED * START_SPEED / (2 * SINK_ACCELERATION));

        float sinkRotationAngle = MAX_SINK_ROTATION_ANGLE * (2 * rand.nextFloat() - 1);
        float sinkRotationVelocity = (MAX_SINK_ROTATION_VELOCITY - MIN_SINK_ROTATION_VELOCITY) * rand.nextFloat()
                + MIN_SINK_ROTATION_VELOCITY;
        float sinkVelocity = (MAX_SINK_VELOCITY - MIN_SINK_VELOCITY) * rand.nextFloat()
                + MIN_SINK_VELOCITY;

        MoveModifier moveModifierX = new MoveModifier( START_SPEED / SINK_ACCELERATION
                , shipSprite.getX()
                , sinkPointX
                , shipSprite.getY()
                , shipSprite.getY()
                , EaseQuadOut.getInstance() );

        MoveModifier moveModifierY = new MoveModifier( sinkVelocity
                , sinkPointX
                , sinkPointX
                , shipSprite.getY()
                , shipSprite.getY() + shipSprite.getHeightScaled()
                , EaseQuadIn.getInstance() );

        RotationModifier rotation = new RotationModifier( sinkRotationVelocity
                , shipSprite.getRotation()
                , sinkRotationAngle );

        AlphaModifier alphaModifier = new AlphaModifier(ALPHA_SINK_TIME, 1, 0);

        ParallelEntityModifier parallel = new ParallelEntityModifier(moveModifierY,rotation);
        SequenceEntityModifier moveShip = new SequenceEntityModifier(moveModifierX,parallel,alphaModifier) {
            @Override
            public void onModifierFinished(IEntity pItem) {
                activity.runOnUpdateThread(new Runnable() {
                    @Override
                    public void run() {
                        shipSprite.detachSelf();
                    }
                });
            }
        };

        shipSprite.registerEntityModifier(moveShip);
    }
}

