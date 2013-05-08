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

import java.util.HashMap;

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

    private final static float RELATIVE_SKY_HEIGHT = 0.15f;
    private final static float RELATIVE_WAVE_HEIGHT = 0.125f;

    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private GameHUD gameHUD;
    private PauseHUD pauseHUD;
    private ShipSpawner shipSpawner;
    private HashMap<Integer, Float> shipLinesPosition;

    public GameScene(final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.resourceManager = activity.getResourceManager();

        for(int i = LAYER_BACKGROUND; i < layerCount; i++) {
            this.attachChild(new Entity());
        }

        shipLinesPosition = new HashMap<Integer, Float>();

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

    public ShipSpawner getShipSpawner() {
        return shipSpawner;
    }

    public void setShipSpawner(ShipSpawner shipSpawner) {
        this.shipSpawner = shipSpawner;
    }

    public float getShipLinePosition(int lineId) {
        return shipLinesPosition.get(lineId);
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
        float offset = activity.getCamera().getHeightRaw() * RELATIVE_SKY_HEIGHT;

        attachTextureToLayer(waveTexture, LAYER_FIRST_WAVE, offset);

        offset += activity.getCamera().getHeightRaw() * RELATIVE_WAVE_HEIGHT;
        shipLinesPosition.put(LAYER_FIRST_SHIP_LINE, offset);
        attachTextureToLayer(waveTexture, LAYER_SECOND_WAVE, offset);

        offset += activity.getCamera().getHeightRaw() * RELATIVE_WAVE_HEIGHT;
        shipLinesPosition.put(LAYER_SECOND_SHIP_LINE, offset);
        attachTextureToLayer(waveTexture, LAYER_THIRD_WAVE, offset);

        offset += activity.getCamera().getHeightRaw() * RELATIVE_WAVE_HEIGHT;
        shipLinesPosition.put(LAYER_THIRD_SHIP_LINE, offset);
        attachTextureToLayer(waveTexture, LAYER_FOURTH_WAVE, offset);
    }

    private void attachTextureToLayer(ITextureRegion texture, int layerId, float yPosition) {
        Sprite waveSprite = new Sprite( 0
                                      , yPosition
                                      , texture
                                      , mEngine.getVertexBufferObjectManager());

        this.getChildByIndex(layerId).attachChild(waveSprite);
    }
}