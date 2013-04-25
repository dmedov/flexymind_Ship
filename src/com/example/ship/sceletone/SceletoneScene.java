package com.example.ship.sceletone;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Atlas.ResourceManager;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 25.04.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class SceletoneScene extends Scene {
    private final BaseGameActivity activity;
    private final Engine engine;
    private final ResourceManager resourceManager;
    private final Scene childScene;
    private Point textureSize;

    public SceletoneScene( final BaseGameActivity activity, int textureWidth
                         , int textureHeight, ResourceManager resourceManager, final Scene childScene) {

        this(activity, new Point(textureWidth, textureHeight), resourceManager, childScene);
    }

    public SceletoneScene( final BaseGameActivity activity, Point textureSize
                         , ResourceManager resourceManager, final Scene childScene) {
        super();

        this.activity = activity;
        this.engine = activity.getEngine();
        this.textureSize = textureSize;
        this.resourceManager = resourceManager;
        this.childScene = childScene;

        createBackground();
        createShipLogo();
    }

    private void createBackground() {
        ITextureRegion backTextureRegion = resourceManager.getLoadedTextureRegion("back");

        final PointF backgroundPosition = new PointF(textureSize.x * 0.5f - backTextureRegion.getWidth() * 0.5f
                , textureSize.y * 0.5f - backTextureRegion.getHeight() * 0.5f);

        Sprite backSprite = new Sprite(backgroundPosition.x
                , backgroundPosition.y
                , backTextureRegion
                , engine.getVertexBufferObjectManager());

        this.attachChild(backSprite);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private void createShipLogo() {
        ITextureRegion shipTextureRegion = resourceManager.getLoadedTextureRegion("ship");

        final PointF shipPosition = new PointF(textureSize.x * 0.5f - shipTextureRegion.getWidth() * 0.5f
                , textureSize.y * 0.5f - shipTextureRegion.getHeight() * 0.5f);

        Sprite shipSprite = new Sprite(shipPosition.x
                , shipPosition.y
                , shipTextureRegion
                , engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if ( pSceneTouchEvent.isActionDown() ){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            engine.setScene(childScene);
                        }
                    });
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        this.registerTouchArea(shipSprite);
        this.attachChild(shipSprite);
    }
}
