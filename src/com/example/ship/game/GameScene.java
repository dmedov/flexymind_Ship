package com.example.ship.game;

import com.example.ship.R;
import com.example.ship.atlas.ResourceManager;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.entity.Entity;
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
    private static final int LAYER_PERISCOPE   = layerCount++;

    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;


    public GameScene(final SceletonActivity activity){
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.resourceManager = activity.getResourceManager();

        createBackground();
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
                                     , backgroundTexture.getHeight() / 3
                                     , waveSprite
                                     , mEngine.getVertexBufferObjectManager());

        this.getChildByIndex(LAYER_BACKGROUND).attachChild(backgroundSprite);
        this.getChildByIndex(LAYER_FIRST_WAVE).attachChild(waveImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

}
