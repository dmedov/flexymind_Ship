package com.example.ship.sceletone;

import android.graphics.Point;
import android.graphics.PointF;
import com.example.ship.RootActivity;
import com.example.ship.atlas.ResourceManager;
import com.example.ship.Events;
import com.example.ship.R;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 25.04.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class RootScene extends Scene {
    private final RootActivity activity;
    private final Engine engine;
    private final ResourceManager resourceManager;
    private       Point textureSize;
    private       TouchableSceletonSprite touchableSceletonSprite;
    private       Sprite shipSprite;

    public RootScene(final RootActivity activity) {
        super();

        this.activity = activity;
        this.engine = activity.getEngine();
        this.textureSize = activity.getTextureSize();
        this.resourceManager = activity.getResourceManager();

        createBackground();
        createShipLogo();
    }

    public void setEvents(Events events) {
        this.touchableSceletonSprite = events;
    }

    public void unregisterTouchArea() {
        this.unregisterTouchArea(shipSprite);
    }

    public void registerTouchArea() {
        this.registerTouchArea(shipSprite);
    }

    private void createBackground() {
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private void createShipLogo() {
        ITextureRegion shipTextureRegion = resourceManager.getLoadedTextureRegion(R.drawable.ship);

        final PointF shipPosition = new PointF( textureSize.x * 0.5f - shipTextureRegion.getWidth() * 0.5f
                                              , textureSize.y * 0.5f - shipTextureRegion.getHeight() * 0.5f);

        shipSprite = new Sprite( shipPosition.x
                                      , shipPosition.y
                                      , shipTextureRegion
                                      , engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched( TouchEvent pSceneTouchEvent
                                        , float pTouchAreaLocalX
                                        , float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionUp()) {
                    touchableSceletonSprite.onSceletoneSpriteReleased();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea();
        this.attachChild(shipSprite);
    }
}
