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
        setPosition(point.x, point.y);
    }

    public PointF getHalfDimensions() {
        return new PointF(getWidthScaled() * 0.5f, getHeightScaled() * 0.5f);
    }

    public static PointF getHalfDimensions(Sprite sprite) {
        return new PointF( sprite.getWidthScaled() * 0.5f
                         , sprite.getHeightScaled() * 0.5f);
    }

    public void setCenterInPosition(PointF point) {
        PointF halfDimensions = getHalfDimensions();
        setX(point.x - halfDimensions.x);
        setY(point.y - halfDimensions.y);
    }

    public PointF getCenter() {
        float centerX = getX();
        float centerY = getY();
        PointF halfDimensions = getHalfDimensions();
        if (getScaleX() < 0) {
            centerX -= halfDimensions.x;
        } else {
            centerX += halfDimensions.x;
        }
        if (getScaleY() < 0) {
            centerY -= halfDimensions.y;
        } else {
            centerY += halfDimensions.y;
        }
        //float centerX = getX()_+ (getScaleX() > 0 ? getWidthScaled() * 0.5f : -getWidthScaled() * 0.5f);
        return new PointF(centerX, centerY);
        //return new PointF(getX() + getWidthScaled() * 0.5f, getY() + getHeightScaled());
    }

    public static PointF getCenter(Sprite sprite) {
       // return new PointF( sprite.getX() + sprite.getWidthScaled() * 0.5f
       //                  , sprite.getY() + sprite.getHeightScaled() * 0.5f);
        float centerX = sprite.getX();
        float centerY = sprite.getY();
        PointF halfDimensions = CSprite.getHalfDimensions(sprite);
        if (sprite.getScaleX() < 0) {
            centerX -= halfDimensions.x;
        } else {
            centerX += halfDimensions.x;
        }
        if (sprite.getScaleY() < 0) {
            centerY -= halfDimensions.y;
        } else {
            centerY += halfDimensions.y;
        }
        //float centerX = getX()_+ (getScaleX() > 0 ? getWidthScaled() * 0.5f : -getWidthScaled() * 0.5f);
        return new PointF(centerX, centerY);
    }
}
