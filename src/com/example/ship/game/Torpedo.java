package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseExponentialOut;
import org.andengine.util.modifier.ease.EaseLinear;

public class Torpedo extends Sprite {

    private PointF startPoint;
    private PointF finishPoint;
    private SceletonActivity activity;
    private double radians;



    public Torpedo(SceletonActivity activity, PointF point, float angle){
        super( point.x
             , point.y
             , activity.getResourceManager().getLoadedTextureRegion(R.drawable.torpedo)
             , activity.getEngine().getVertexBufferObjectManager());

        startPoint = new PointF(point.x, point.y);
        finishPoint = new PointF(0, 0);
        this.activity = activity;
        this.radians = Math.toRadians(angle);

        createModifier();
    }

    private void createModifier(){

        finishPoint.y = 0;
        finishPoint.x = startPoint.x + (float) Math.tan(radians) * startPoint.y;

        LoopEntityModifier alphaLoopEntityModifier = new LoopEntityModifier(new AlphaModifier(1, 1, 0));
        ParallelEntityModifier parallelEntityModifier = new ParallelEntityModifier(alphaLoopEntityModifier,
                new MoveModifier(25, startPoint.x, finishPoint.x, startPoint.y, finishPoint.y, EaseExponentialOut.getInstance()));
        LoopEntityModifier loopEntityModifier = new LoopEntityModifier(parallelEntityModifier);
        this.registerEntityModifier(loopEntityModifier);
    }

}
