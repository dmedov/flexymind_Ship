package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: user8
 * Date: 5/5/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gun {

    private static final float ROTATION_VELOCITY = 0.1f;

    private Sprite gunSprite;

    public Gun(SceletonActivity activity, PointF gunPosition) {
        ITextureRegion gunTexture = activity.getResourceManager().getLoadedTextureRegion(R.drawable.gun);
        gunSprite = new Sprite( gunPosition.x
                , gunPosition.y
                , gunTexture
                , activity.getEngine().getVertexBufferObjectManager());
    }

    public void create() {

    }

    public void rotateLeft() {

    }

    public void rotateRight() {

    }

    public PointF getShootStartPoint() {
        return null;
    }

    public Sprite getSprite() {
        return gunSprite;
    }
}
