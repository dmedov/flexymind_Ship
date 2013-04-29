package com.example.ship.Game;

import android.graphics.Point;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

public class GameScene extends Scene {

    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private static final int LAYER_COUNT = 5;

    private static final int LAYER_BACKGROUND = 0;
    private static final int LAYER_FIRST_WAVE = LAYER_BACKGROUND + 1;
    private static final int LAYER_SECOND_WAVE = LAYER_FIRST_WAVE + 1;
    private static final int LAYER_THIRD_WAVE = LAYER_SECOND_WAVE + 1;
    private static final int LAYER_PERISCOPE = LAYER_THIRD_WAVE + 1;

    public GameScene(final SceletonActivity activity){
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.resourceManager = activity.getResourceManager();

        createBackground();
    }

    private void createBackground() {

        for(int i = 0; i < LAYER_COUNT; i++) {
            this.attachChild(new Entity());
        }

        ITextureRegion backgroundTexture = resourceManager.getLoadedTextureRegion("gameBackground");
        Sprite backgroundFirstHalfImage = new Sprite( 0
                                                    , 0
                                                    , backgroundTexture
                                                    , mEngine.getVertexBufferObjectManager());
        Sprite backgroundSecondHalfImage = new Sprite( 1740
                                                     , 0
                                                     , backgroundTexture
                                                     , mEngine.getVertexBufferObjectManager());
        ITextureRegion waveTexture = resourceManager.getLoadedTextureRegion("wave");
        Sprite waveImage = new Sprite( 0
                                     , 680
                                     , waveTexture
                                     , mEngine.getVertexBufferObjectManager());
        ITextureRegion periscopeTexture = resourceManager.getLoadedTextureRegion("periscope");
        Sprite periscopeImage = new Sprite( 0
                                          , 0
                                          , periscopeTexture
                                          , mEngine.getVertexBufferObjectManager());
        this.getChildByIndex(LAYER_BACKGROUND).attachChild(backgroundFirstHalfImage);
        this.getChildByIndex(LAYER_BACKGROUND).attachChild(backgroundSecondHalfImage);
        this.getChildByIndex(LAYER_FIRST_WAVE).attachChild(waveImage);
        this.getChildByIndex(LAYER_PERISCOPE).attachChild(periscopeImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

}
