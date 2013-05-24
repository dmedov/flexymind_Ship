package com.example.ship.bonus;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.commons.CSprite;
import com.example.ship.game.Ship;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseQuadInOut;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 5/24/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */

public class Bonus {
    static public float bonusShipKillProbability = 0.1f;
    static public float bonusGoodPropability = 0.8f;
    static public float bonusWaterLine = 0.6f;


    private static final float MAX_ROTATE_ANGLE = 2.0f;
    private static final float ROTATE_DURATION = 2.0f;
    private static final int ROTATION_COUNT = 20;

    private CSprite bonusSprite;

    public Bonus(PointF bonusLocation, int type) {
        bonusSprite = new CSprite(bonusLocation, R.drawable.bonus);
        createModifier();
    }

    public Bonus(Ship killedShip) {
        Sprite killedShipSprite = killedShip.getSprite();
        PointF killedShipCenter = CSprite.getCenter(killedShipSprite);
        bonusSprite = new CSprite(R.drawable.bonus);
        bonusSprite.setCenterInPosition(killedShipCenter);
        bonusSprite.setY(bonusSprite.getY() + bonusSprite.getHeightScaled() * bonusWaterLine);
        killedShip.getSprite().getParent().attachChild(bonusSprite);
        createModifier();
    }

    static void setBonusShipKillProbability(float probability) {
        bonusShipKillProbability = probability;
    }

    public static boolean canCreate() {
        return true;
    }

    private void createModifier() {
        PointF bonusCenter = bonusSprite.getCenter();
        bonusSprite.setRotationCenter(bonusCenter.x, bonusCenter.y);

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

        //ParallelEntityModifier moveWithRotationModifier = new ParallelEntityModifier( loopRotate
        //                                                                            , moveModifier);

        bonusSprite.registerEntityModifier(loopRotate);
    }
}

