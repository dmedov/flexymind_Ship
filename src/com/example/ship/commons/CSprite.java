package com.example.ship.commons;

import android.graphics.PointF;
import com.example.ship.game.GameScene;
import org.andengine.entity.sprite.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 5/23/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSprite extends Sprite {

    private static final float RELATIVE_SIZE_AROUND = 1f;
    private static final float RELATIVE_SIZE_BEYOND = 0.5f;
    public static final boolean TO_LEFT = true;
    public static final boolean TO_RIGHT = false;

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

    public static PointF getCenter(Sprite sprite) {
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

        return new PointF(centerX, centerY);
    }

    public static PointF getHalfDimensions(Sprite sprite) {
        return new PointF( sprite.getWidthScaled() * 0.5f
                         , sprite.getHeightScaled() * 0.5f);
    }
    public static void setPerspectiveScale(Sprite sprite, boolean direction, float yPosition) {
        GameScene gameScene = A.a.getSceneSwitcher().getGameScene();
        float lastShipLinePositionFromBottomY =
                yFromBottom(gameScene.getShipLinePosition(GameScene.LAYER_THIRD_SHIP_LINE));

        float skyLinePositionFromBottomY =
                yFromBottom(gameScene.getChildByIndex(GameScene.LAYER_FIRST_WAVE).getFirstChild().getY());

        // увеличение f(x)=a/x + b, равно RELATIVE_SIZE_AROUND на самой ближайшей линии спауна кораблей,
        // RELATIVE_SIZE_BEYOND на горизонте
        float a = (RELATIVE_SIZE_AROUND - RELATIVE_SIZE_BEYOND)
                * (lastShipLinePositionFromBottomY * skyLinePositionFromBottomY)
                / (skyLinePositionFromBottomY - lastShipLinePositionFromBottomY);
        float b = RELATIVE_SIZE_BEYOND - a/skyLinePositionFromBottomY;

        float perspectiveScale = a / (A.a.getCamera().getHeightRaw() - yPosition) + b;
        final PointF LEFT_TOP = new PointF(0f, 0f);
        sprite.setScaleCenter(LEFT_TOP.x, LEFT_TOP.y);
        if (direction == TO_LEFT) {
            sprite.setScale(perspectiveScale, perspectiveScale);
        } else {
            sprite.setScale(-perspectiveScale, perspectiveScale);
        }
    }

    public void setPosition(PointF point) {
        setPosition(point.x, point.y);
    }

    public PointF getPosition() {
        return new PointF(getX(), getY());
    }

    public PointF getHalfDimensions() {
        return new PointF(getWidthScaled() * 0.5f, getHeightScaled() * 0.5f);
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

        return new PointF(centerX, centerY);
    }
    private static float yFromBottom(float position) {
        return A.a.getCamera().getHeightRaw() - position;
    }
}
