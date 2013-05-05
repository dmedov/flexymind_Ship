package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import com.example.ship.atlas.ResourceManager;
import org.andengine.engine.Engine;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

public class GameScene extends Scene {
    private static int layerCount = 0;
    private static final int LAYER_BACKGROUND  = layerCount++;
    private static final int LAYER_FIRST_WAVE  = layerCount++;
    private static final int LAYER_SECOND_WAVE = layerCount++;
    private static final int LAYER_THIRD_WAVE  = layerCount++;
    private static final int LAYER_GUN         = layerCount++;
    private static final int WAVES_NUMBER = 3;

    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private GameHUD gameHUD;
    private PauseHUD pauseHUD;

    public GameScene(final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.resourceManager = activity.getResourceManager();

        createBackground();
        createGun();

        gameHUD = new GameHUD(activity);
        gameHUD.setEventsToChildren(activity.getEvents());

        pauseHUD = new PauseHUD(activity);
        pauseHUD.setEventsToChildren(activity.getEvents());
    }

    public void switchToPauseHUD() {
        activity.getCamera().setHUD(pauseHUD);
    }

    public void switchToGameHUD() {
        activity.getCamera().setHUD(gameHUD);
    }

    private void createBackground() {

        for(int i = 0; i < layerCount; i++) {
            this.attachChild(new Entity());
        }

        ITextureRegion backgroundTexture = resourceManager.getLoadedTextureRegion(R.drawable.gamebackground);
        Sprite backgroundSprite = new Sprite( 0
                                            , 0
                                            , backgroundTexture
                                            , mEngine.getVertexBufferObjectManager());

        ITextureRegion waveSprite = resourceManager.getLoadedTextureRegion(R.drawable.wave);
        Sprite waveImage = new Sprite( 0
                                     , backgroundTexture.getHeight() / WAVES_NUMBER
                                     , waveSprite
                                     , mEngine.getVertexBufferObjectManager());

        this.getChildByIndex(LAYER_BACKGROUND).attachChild(backgroundSprite);
        this.getChildByIndex(LAYER_FIRST_WAVE).attachChild(waveImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private void createGun() {
        ITextureRegion gunTexture = resourceManager.getLoadedTextureRegion(R.drawable.gun);

        PointF gunPosition = new PointF( activity.getCamera().getCenterX()
                                       , activity.getCamera().getYMax() -
                                         gunTexture.getHeight() * activity.getCamera().getZoomFactor() * 0.6f);
        Sprite gunSprite = new Sprite( gunPosition.x
                                     , gunPosition.y
                                     , gunTexture
                                     , mEngine.getVertexBufferObjectManager());

        RotationModifier rotationModifier = new RotationModifier( 100.0f   // duration
                                                                , 1.0f   // start
                                                                , 300.0f); //  end )
        //gunSprite.setRotation(20.0f);
        gunSprite.registerEntityModifier(new LoopEntityModifier(rotationModifier));

        this.getChildByIndex(LAYER_GUN).attachChild(gunSprite);
    }
}
