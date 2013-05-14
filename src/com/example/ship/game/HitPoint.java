package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.sprite.Sprite;

public class HitPoint {
    private SceletonActivity activity;
    private Sprite onHitPoint;
    private Sprite offHitPoint;

    public HitPoint(SceletonActivity activity, GameHUD gameHUD, PointF point, float scale) {
        this.activity = activity;

        onHitPoint = new Sprite( point.x
                          , point.y
                          , activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth)
                          , activity.getEngine().getVertexBufferObjectManager());
        onHitPoint.setScale(scale);
        onHitPoint.setVisible(true);

        offHitPoint = new Sprite( point.x
                           , point.y
                           , activity.getResourceManager().getLoadedTextureRegion(R.drawable.offhealth)
                           , activity.getEngine().getVertexBufferObjectManager());
        offHitPoint.setScale(scale);
        offHitPoint.setVisible(false);

        gameHUD.attachChild(onHitPoint);
        gameHUD.attachChild(offHitPoint);
    }

    public void switchHitPoint() {
        offHitPoint.setVisible(!offHitPoint.isVisible());
        onHitPoint.setVisible(!onHitPoint.isVisible());
    }
}
