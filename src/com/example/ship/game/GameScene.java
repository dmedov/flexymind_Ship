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
    public static final int LAYER_BACKGROUND  = layerCount++;
    public static final int LAYER_FIRST_WAVE  = layerCount++;
    public static final int LAYER_FIRST_SHIP_LINE  = layerCount++;
    public static final int LAYER_SECOND_WAVE = layerCount++;
    public static final int LAYER_SECOND_SHIP_LINE = layerCount++;
    public static final int LAYER_THIRD_WAVE  = layerCount++;
    public static final int LAYER_THIRD_SHIP_LINE  = layerCount++;
    public static final int LAYER_FOURTH_WAVE  = layerCount++;
    public static final int LAYER_PERISCOPE   = layerCount++;
    private static final int WAVES_NUMBER = 3;

    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private GameHUD gameHUD;
    private PauseHUD pauseHUD;
    private ShipSpawner shipSpawner;

    public GameScene(final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.resourceManager = activity.getResourceManager();

        for(int i = LAYER_BACKGROUND; i < layerCount; i++) {
            this.attachChild(new Entity());
        }

        createBackground();
        createWaves();

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
        ITextureRegion backgroundTexture = resourceManager.getLoadedTextureRegion(R.drawable.gamebackground);
        Sprite backgroundSprite = new Sprite( 0
                                            , 0
                                            , backgroundTexture
                                            , mEngine.getVertexBufferObjectManager());

        this.getChildByIndex(LAYER_BACKGROUND).attachChild(backgroundSprite);

        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private void createWaves() {
        ITextureRegion waveTexture = resourceManager.getLoadedTextureRegion(R.drawable.wave);
        attachTextureToLayer(waveTexture, LAYER_FIRST_WAVE, 200);
        attachTextureToLayer(waveTexture, LAYER_SECOND_WAVE, 300);
        attachTextureToLayer(waveTexture, LAYER_THIRD_WAVE, 400);
        attachTextureToLayer(waveTexture, LAYER_FOURTH_WAVE, 500);
    }

    private void attachTextureToLayer(ITextureRegion texture, int layerId, float yPosition) {
        Sprite waveSprite = new Sprite( 0
                                      , yPosition
                                      , texture
                                      , mEngine.getVertexBufferObjectManager());

        this.getChildByIndex(layerId).attachChild(waveSprite);
    }

    public ShipSpawner getShipSpawner() {
        return shipSpawner;
    }

    public void setShipSpawner(ShipSpawner shipSpawner) {
        this.shipSpawner = shipSpawner;
    }
}
