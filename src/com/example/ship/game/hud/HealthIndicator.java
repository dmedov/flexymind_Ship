package com.example.ship.game.hud;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.RootActivity;
import com.example.ship.game.Player;
import org.andengine.entity.sprite.Sprite;

import java.util.ArrayList;

public class HealthIndicator {
    private static final float RELATIVE_HP_HEIGHT = 0.4f;
    private static final float CLOUD_ALPHA = 0.75f;
    private Sprite hitPointSprite;
    private Sprite cloudSprite;
    private ArrayList<Sprite> hitPoints;

    public HealthIndicator(RootActivity activity, GameHUD gameHUD, PointF position, float scale) {

        hitPoints = new ArrayList<Sprite>();

        cloudSprite = new Sprite( 0
                                , 0
                                , activity.getResourceManager().getLoadedTextureRegion(R.drawable.cloud)
                                , activity.getEngine().getVertexBufferObjectManager());

        cloudSprite.setScaleCenter(cloudSprite.getWidth(), 0);
        cloudSprite.setScale(scale);
        cloudSprite.setPosition(position.x - cloudSprite.getWidth(), position.y);
        cloudSprite.setAlpha(CLOUD_ALPHA);
        gameHUD.attachChild(cloudSprite);

        float cloudHeight =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth).getHeight();
        float hitPointScale = cloudSprite.getHeight() * RELATIVE_HP_HEIGHT / cloudHeight;
        // 0.1f и 0.3f взяты под данную текстуру облака
        PointF positionHitPoint = new PointF( cloudSprite.getWidth() * 0.1f
                                            , cloudSprite.getHeight() * 0.35f);

        for (int i = 0; i < Player.FULL_HP; i++) {
            hitPointSprite = new Sprite( 0
                                       , 0
                                       , activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth)
                                       , activity.getEngine().getVertexBufferObjectManager());
            hitPointSprite.setScale(hitPointScale);
            hitPointSprite.setPosition(positionHitPoint.x, positionHitPoint.y);
            hitPointSprite.setVisible(true);
            cloudSprite.attachChild(hitPointSprite);
            hitPoints.add(hitPointSprite);
            positionHitPoint.x += hitPointSprite.getWidthScaled();
        }
    }

    public void updateHealth(int health) {
        for (int i = 0; i < hitPoints.size(); i++) {
            if (i < health) {
                hitPoints.get(i).setVisible(true);
            } else {
                hitPoints.get(i).setVisible(false);
            }
        }
    }
}
