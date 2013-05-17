package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.sprite.Sprite;

public class HealthIndicator {
    public static final boolean ALIVE_STATE = true;
    public static final boolean DEAD_STATE = false;
    private Sprite onHitPointSprite;
    private Sprite offHitPointSprite;

    public HealthIndicator(SceletonActivity activity, GameHUD gameHUD, PointF point, float scale) {

        onHitPointSprite = new Sprite( point.x
                                     , point.y
                                     , activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth)
                                     , activity.getEngine().getVertexBufferObjectManager());
        onHitPointSprite.setScale(scale);
        onHitPointSprite.setVisible(true);

        offHitPointSprite = new Sprite( point.x
                                      , point.y
                                      , activity.getResourceManager().getLoadedTextureRegion(R.drawable.offhealth)
                                      , activity.getEngine().getVertexBufferObjectManager());
        offHitPointSprite.setScale(scale);
        offHitPointSprite.setVisible(false);

        gameHUD.attachChild(onHitPointSprite);
        gameHUD.attachChild(offHitPointSprite);
    }

    public void setState(boolean aliveState) {
        onHitPointSprite.setVisible(aliveState);
        offHitPointSprite.setVisible(!aliveState);
    }
}
