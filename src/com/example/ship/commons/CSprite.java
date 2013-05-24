package com.example.ship.commons;

import android.graphics.PointF;
import org.andengine.entity.sprite.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 5/23/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSprite extends Sprite {
    public CSprite(PointF position, int rID) {
        super( position.x
             , position.y
             , A.rm.getLoadedTextureRegion(rID)
             , A.e.getVertexBufferObjectManager());
    }

    public CSprite(int rID) {
        super( 0
             , 0
             , A.rm.getLoadedTextureRegion(rID)
             , A.e.getVertexBufferObjectManager());
    }

    public void setPosition(PointF point) {
        setX(point.x);
        setY(point.y);
    }

    public PointF getHalfDimensions() {
        return new PointF(getWidth() * 0.5f, getHeight() * 0.5f);
    }

    public void setCenterInPosition(PointF point) {
        PointF halfDimensions = getHalfDimensions();
        setX(point.x - halfDimensions.x);
        setY(point.y - halfDimensions.y);
    }

    public PointF getCenter() {
        return new PointF(getX() + getWidthScaled() * 0.5f, getY() + getHeightScaled());
    }
}
