package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseExponentialOut;
import org.andengine.util.modifier.ease.EaseLinear;

public class Torpedo extends Sprite {

    private PointF startPoint;
    private PointF finishPoint;
    private SceletonActivity activity;
    private double radians;
    private float angle;
    private static final float TIME_OF_FLIGHT = 25;



    public Torpedo(SceletonActivity activity, PointF point, float angle){
        super( point.x
             , point.y
             , activity.getResourceManager().getLoadedTextureRegion(R.drawable.torpedo)
             , activity.getEngine().getVertexBufferObjectManager());

        startPoint = new PointF(point.x, point.y);
        finishPoint = new PointF(0, 0);
        this.activity = activity;
        this.radians = Math.toRadians(angle);
        this.angle = angle;

        createModifier();
    }

    private void createModifier(){

        finishPoint.y = 0;
        finishPoint.x = startPoint.x + (float) Math.tan(radians) * startPoint.y;

        RotationModifier rotationModifier = new RotationAtModifier( 0.01f
                                                                  , 0
                                                                  , angle
                                                                  , this.getRotationCenterX()
                                                                  , this.getRotationCenterY());

        LoopEntityModifier alphaLoopEntityModifier = new LoopEntityModifier(new AlphaModifier(1, 1, 0));

        MoveModifier moveModifier = new MoveModifier( TIME_OF_FLIGHT
                                                    , startPoint.x
                                                    , finishPoint.x
                                                    , startPoint.y
                                                    , finishPoint.y
                                                    , EaseExponentialOut.getInstance());

        ParallelEntityModifier parallelEntityModifier =
                new ParallelEntityModifier(alphaLoopEntityModifier, moveModifier);

        LoopEntityModifier loopEntityModifier = new LoopEntityModifier(parallelEntityModifier);

        SequenceEntityModifier sequenceEntityModifier =
                new SequenceEntityModifier(rotationModifier, loopEntityModifier);

        this.registerEntityModifier(sequenceEntityModifier);
    }

}
