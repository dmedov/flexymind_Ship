package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import static android.util.FloatMath.cos;
import static android.util.FloatMath.sin;
import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 5/5/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gun {

    public  static final float GRAD_TO_RADIAN_KOEF = 3.1415f / 180f;
    private static final float ROTATION_VELOCITY   = 0.4f;
    private static final float ROTATION_MAX_ANGLE  = 40.0f;
    private static final float GUN_PART_ON_SCENE   = 0.6f;
    private static final float FIRE_DELAY = 2f;

    private boolean rotateLeft;
    private boolean rotationEnabled;
    private Sprite gunSprite;
    private SceletonActivity activity;
    private TimerHandler fireTimerHandler;
    private boolean fireAvaible = true;

    public Gun(SceletonActivity activity) {
        this.activity = activity;
        rotationEnabled = false;
        ITextureRegion gunTexture = activity.getResourceManager().getLoadedTextureRegion(R.drawable.gun);

        ZoomCamera camera = activity.getCamera();

        PointF gunPosition = new PointF( camera.getCenterX() - gunTexture.getWidth()  * 0.5f
                                       , camera.getYMax() - gunTexture.getHeight() * GUN_PART_ON_SCENE);

        createTimer();
        activity.getEngine().registerUpdateHandler(fireTimerHandler);

        gunSprite = new Sprite( gunPosition.x
                              , gunPosition.y
                              , gunTexture
                              , activity.getEngine().getVertexBufferObjectManager()) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);

                if (!rotationEnabled) {
                    return;
                }

                if (rotateLeft) {
                    if (gunSprite.getRotation() > -ROTATION_MAX_ANGLE) {
                        gunSprite.setRotation(gunSprite.getRotation() - ROTATION_VELOCITY);
                    }
                } else {
                    if (gunSprite.getRotation() < ROTATION_MAX_ANGLE) {
                        gunSprite.setRotation(gunSprite.getRotation() + ROTATION_VELOCITY);
                    }
                }
            }

        };

        gunSprite.setRotationCenter(gunSprite.getWidth() * 0.5f, gunSprite.getHeight());
    }

    public void rotateLeft() {
        rotateLeft = true;
        rotationEnabled = true;
    }

    public void rotateRight() {
        rotateLeft = false;
        rotationEnabled = true;
    }

    public void stopRotate() {
        rotationEnabled = false;
    }

    public PointF getShootStartPoint() {
        float gunAngle = gunSprite.getRotation();
        PointF beginGunPoint = new PointF( gunSprite.getX() + gunSprite.getWidth() * 0.5f
                                         , gunSprite.getY() + gunSprite.getHeight());

        float gunWidth = gunSprite.getHeight();
        PointF shootStartPoint = new PointF( beginGunPoint.x + gunWidth * sin(gunAngle * GRAD_TO_RADIAN_KOEF)
                                           , beginGunPoint.y - gunWidth * abs(cos(gunAngle * GRAD_TO_RADIAN_KOEF)));
        return shootStartPoint;
    }

    public float getGunAngle() {
        return gunSprite.getRotation();
    }

    public Sprite getSprite() {
        return gunSprite;
    }

    public void fire() {
        if (fireAvaible) {
            Torpedo torpedo = new Torpedo( activity
                                         , getShootStartPoint()
                                         , getGunAngle());
            GameScene gameScene = activity.getSceneSwitcher().getGameScene();
            gameScene.attachSpriteToLayer(torpedo, GameScene.LAYER_TORPEDO);
            fireAvaible = false;
            fireTimerHandler.reset();
        }
    }

    private void createTimer() {
        fireTimerHandler = new TimerHandler(FIRE_DELAY, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler timerHandler) {
                fireAvaible = true;
            }
        });
    }

}
