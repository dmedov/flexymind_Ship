package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.sprite.Sprite;

public class HitPoint {
    private SceletonActivity activity;
    private Sprite onHit;
    private Sprite offHit;
    private GameHUD gameHUD;

    public HitPoint(SceletonActivity activity, GameHUD gameHUD, PointF point) {
        this.activity = activity;
        this.gameHUD = gameHUD;

        onHit = new Sprite( point.x
                          , point.y
                          , activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth)
                          , activity.getEngine().getVertexBufferObjectManager());
        onHit.setVisible(true);

        offHit = new Sprite( point.x
                           , point.y
                           , activity.getResourceManager().getLoadedTextureRegion(R.drawable.offhealth)
                           , activity.getEngine().getVertexBufferObjectManager());
        offHit.setVisible(false);

        gameHUD.attachChild(onHit);
        gameHUD.attachChild(offHit);
    }

    public void switchHitPoint(){
        if (onHit.isVisible()){
            onHit.setVisible(false);
            offHit.setVisible(true);
        } else {
            onHit.setVisible(true);
            offHit.setVisible(false);
        }
    }
}
