package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.atlas.ResourceManager;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseExponentialOut;
import org.andengine.util.modifier.ease.EaseLinear;

public class GameScene extends Scene {
    private static int layerCount = 0;
    private static final int LAYER_BACKGROUND  = layerCount++;
    private static final int LAYER_FIRST_WAVE  = layerCount++;
    private static final int LAYER_SECOND_WAVE = layerCount++;
    private static final int LAYER_THIRD_WAVE  = layerCount++;
    private static final int LAYER_PERISCOPE   = layerCount++;
    private static final int WAVES_NUMBER = 3;

    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private GameHUD gameHUD;
    private PauseHUD pauseHUD;
    private ZoomCamera camera;

    public GameScene(final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.resourceManager = activity.getResourceManager();
        this.camera = activity.getCamera();

        createBackground();

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
                                     , backgroundTexture.getHeight()
                                     , waveSprite
                                     , mEngine.getVertexBufferObjectManager());

       // Torpedo torpedo = new Torpedo(activity, new PointF(camera.getCenterX(), camera.getYMax()), 0);

        //this.getChildByIndex(LAYER_PERISCOPE).attachChild(torpedo);
        this.getChildByIndex(LAYER_BACKGROUND).attachChild(backgroundSprite);
        this.getChildByIndex(LAYER_FIRST_WAVE).attachChild(waveImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }
}
