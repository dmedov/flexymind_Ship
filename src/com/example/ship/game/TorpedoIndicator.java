package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitriy
 * Date: 17.05.13
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public class TorpedoIndicator {
    public static final boolean ON_STATE = true;
    public static final boolean OFF_STATE = false;
    private static final float RELATIVE_TORPEDO_INDICATOR_HEIGHT = 0.15f;

    private Sprite onTorpedoIndicator;
    private Sprite offTorpedoIndicator;
    private SceletonActivity activity;

    public TorpedoIndicator(SceletonActivity activity, GameHUD gameHUD) {

        this.activity = activity;

        onTorpedoIndicator = new Sprite( 0
                                       , 0
                                       , this.getLoadedTextureRegion(R.drawable.ontorpedoindicator)
                                       , activity.getEngine().getVertexBufferObjectManager());

        offTorpedoIndicator = new Sprite( 0
                                        , 0
                                        , this.getLoadedTextureRegion(R.drawable.offtorpedoindicator)
                                        , activity.getEngine().getVertexBufferObjectManager());

        setScaleToTorpedoIndicator();

        onTorpedoIndicator.setVisible(true);
        offTorpedoIndicator.setVisible(false);

        gameHUD.attachChild(onTorpedoIndicator);
        gameHUD.attachChild(offTorpedoIndicator);
    }

    public float getWidthTorpedoIndicator() {
        return onTorpedoIndicator.getWidth();
    }

    public float getHeightTorpedoIndicator() {
        return onTorpedoIndicator.getHeight();
    }

    public void setPosition(PointF point) {
        onTorpedoIndicator.setPosition(point.x , point.y);
        offTorpedoIndicator.setPosition(point.x, point.y);
    }

    public void setState(boolean state) {
        onTorpedoIndicator.setVisible(state);
        offTorpedoIndicator.setVisible(!state);
    }

    private void setScaleToTorpedoIndicator() {
        float scale = activity.getCamera().getHeightRaw() * RELATIVE_TORPEDO_INDICATOR_HEIGHT
                    / onTorpedoIndicator.getHeight();

        onTorpedoIndicator.setScale(scale);
        offTorpedoIndicator.setScale(scale);
    }

    private ITextureRegion getLoadedTextureRegion(int TextureID) {
        return activity.getResourceManager().getLoadedTextureRegion(TextureID);
    }
}
