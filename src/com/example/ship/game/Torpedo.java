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

    float pX;
    float pY;
    PointF startPoint;
    SceletonActivity activity;



    public Torpedo(SceletonActivity activity, PointF point, float angle){
        super( point.x
             , point.y
             , activity.getResourceManager().getLoadedTextureRegion(R.drawable.torpedo)
             , activity.getEngine().getVertexBufferObjectManager());

        startPoint = new PointF(point.x, point.y);
        this.activity = activity;

        createModifier();
    }

    private void createModifier(){
        LoopEntityModifier alphaLoopEntityModifier = new LoopEntityModifier(new AlphaModifier(1, 1, 0));
        ParallelEntityModifier parallelEntityModifier = new ParallelEntityModifier(alphaLoopEntityModifier,
                new MoveModifier(5, startPoint.x, 500, startPoint.y, 0, EaseLinear.getInstance()));
        LoopEntityModifier loopEntityModifier = new LoopEntityModifier(parallelEntityModifier);
        this.registerEntityModifier(loopEntityModifier);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        if (activity.getSceneSwitcher().getGameScene().getBackgroundSprite().collidesWith(this)){
            this.detachSelf();
        }

        super.onManagedUpdate(pSecondsElapsed);
    }
}
