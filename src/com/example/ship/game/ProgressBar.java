package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.RootActivity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitriy
 * Date: 22.05.13
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public class ProgressBar {
    public static final int FULL_PROGRESS = 100;
    private static final float RELATIVE_TORPEDO_INDICATOR_HEIGHT = 0.70f;

    private Sprite progressBarSprite;
    private RootActivity activity;
    MaskRectangle maskRectangle;
    private float progress;

    public ProgressBar(RootActivity activity, GameHUD gameHUD) {

        this.activity = activity;
        this.progress = FULL_PROGRESS;

        progressBarSprite = new Sprite( 0
                                      , 0
                                      , this.getLoadedTextureRegion(R.drawable.ontorpedoindicator)
                                      , activity.getEngine().getVertexBufferObjectManager());

        progressBarSprite.setAlpha(0.75f);
        setScaleToTorpedoIndicator();

        maskRectangle = new MaskRectangle( 0
                                         , 0
                                         , progressBarSprite.getWidthScaled()
                                         , 0
                                         , activity.getVertexBufferObjectManager());

        gameHUD.attachChild(maskRectangle);
        maskRectangle.attachChild(progressBarSprite);
    }

    public void setProgress(int progress) {
        maskRectangle.setHeight( progressBarSprite.getHeightScaled() * (FULL_PROGRESS - progress)
                                 / FULL_PROGRESS);
    }

    public float getWidthProgressBar() {
        return progressBarSprite.getWidthScaled();
    }

    public float getHeightProgressBar() {
        return progressBarSprite.getHeight();
    }

    public void setPosition(PointF point) {
        maskRectangle.setPosition(point.x, point.y);
    }

    private void setScaleToTorpedoIndicator() {
        float scale = activity.getCamera().getHeightRaw() * RELATIVE_TORPEDO_INDICATOR_HEIGHT
                      / progressBarSprite.getHeight();

        progressBarSprite.setScaleCenter(0, 0);
        progressBarSprite.setScale(scale);
    }

    private ITextureRegion getLoadedTextureRegion(int TextureID) {
        return activity.getResourceManager().getLoadedTextureRegion(TextureID);
    }
}
