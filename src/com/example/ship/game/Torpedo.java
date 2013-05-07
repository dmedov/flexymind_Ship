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
    private double radians;
    private float angle;
    private static final float TIME_OF_FLIGHT = 30;

    public Torpedo(SceletonActivity activity, PointF point, float angle){
        super( point.x - activity.getResourceManager().getLoadedTextureRegion(R.drawable.torpedo)
                         .getWidth()/2
             , point.y - activity.getResourceManager().getLoadedTextureRegion(R.drawable.torpedo)
                         .getHeight()/2
             , activity.getResourceManager().getLoadedTextureRegion(R.drawable.torpedo)
             , activity.getEngine().getVertexBufferObjectManager());

        startPoint = new PointF(point.x - activity.getResourceManager()
                                          .getLoadedTextureRegion(R.drawable.torpedo).getWidth()/2
                              , point.y - activity.getResourceManager()
                                          .getLoadedTextureRegion(R.drawable.torpedo).getHeight()/2);
        finishPoint = new PointF(0, 0);
        this.radians = Math.toRadians(angle);
        this.angle = angle;

        createModifier();
    }

    private void createModifier(){

        finishPoint.y = 0;
        finishPoint.x = startPoint.x + (float) Math.tan(radians) * startPoint.y;

        // RotationModifier(время исполнения, угол сначала, угол в конце, относительно чего крутим, -//-)
     /*   RotationModifier rotationModifier = new RotationAtModifier( 0.01f
                                                                  , 0
                                                                  , angle
                                                                  , this.getRotationCenterX()
                                                                  , this.getRotationCenterY());
      */
        // AlphaModifier(время исполнения, изначальная прозрачность, конечная прозрачность)
        // LoopEntityModifier - выполняет модификатор пока не отменим
        LoopEntityModifier alphaLoopEntityModifier = new LoopEntityModifier(new AlphaModifier(1, 1, 0));

        // EaseExponentialOut.getInstance() - способ передвижения
        MoveModifier moveModifier = new MoveModifier( TIME_OF_FLIGHT
                                                    , startPoint.x
                                                    , finishPoint.x
                                                    , startPoint.y
                                                    , finishPoint.y
                                                    , EaseExponentialOut.getInstance());

        // ParallelEntityModifier - выполняет модификаторы параллельно
        ParallelEntityModifier parallelEntityModifier =
                new ParallelEntityModifier(alphaLoopEntityModifier, moveModifier);

        LoopEntityModifier loopEntityModifier = new LoopEntityModifier(parallelEntityModifier);

        // SequenceEntityModifier - выполняет модификаторы последовательно
    //    SequenceEntityModifier sequenceEntityModifier =
    //            new SequenceEntityModifier(rotationModifier, loopEntityModifier);

        this.registerEntityModifier(loopEntityModifier);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        // постепенное уменьшение торпеды
        this.setScaleY( (float) ( this.getY() / startPoint.y) );
        super.onManagedUpdate(pSecondsElapsed);
    }

}
