package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.sprite.Sprite;

public class HealthIndicator {
    private SceletonActivity activity;
    private Sprite onHitPointSprite;
    private Sprite offHitPointSprite;

    public HealthIndicator(SceletonActivity activity, GameHUD gameHUD, PointF point, float scale) {
        this.activity = activity;

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

    public void setToAliveState() {
        onHitPointSprite.setVisible(true);
        offHitPointSprite.setVisible(false);
    }

    public void setToDeadState() {
        onHitPointSprite.setVisible(false);
        offHitPointSprite.setVisible(true);
    }
}
