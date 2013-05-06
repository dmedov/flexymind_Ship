package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.sprite.Sprite;
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
                new MoveModifier(5, startPoint.x, finishPoint.x, startPoint.y, finishPoint.y, EaseLinear.getInstance()));
        LoopEntityModifier loopEntityModifier = new LoopEntityModifier(parallelEntityModifier);
        this.registerEntityModifier(loopEntityModifier);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        if (this.collidesWith(activity.getSceneSwitcher().getGameScene().getBackgroundSprite())){
        //    this.detachSelf();
        }

        super.onManagedUpdate(pSecondsElapsed);
    }
}
