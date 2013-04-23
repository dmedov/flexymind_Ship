package com.example.ship;

import android.util.DisplayMetrics;
import android.util.Log;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class SceletonActivity extends BaseGameActivity {
    private static final int TEXTURE_WIDTH = 960;
    private static final int TEXTURE_HEIGHT = 600;
    private TextureRegion shipTextureRegion;
    private TextureRegion backTextureRegion;
    private Scene scene;

    private ZoomCamera getZoomCamera(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ZoomCamera camera;

        float cameraWidth = TEXTURE_WIDTH;
        float cameraHeight = cameraWidth * metrics.heightPixels / metrics.widthPixels;
        camera = new ZoomCamera(0, 0, cameraWidth, cameraHeight);

        final float cameraCenterX = 0.5f * TEXTURE_WIDTH;
        final float cameraCenterY = 0.5f * TEXTURE_HEIGHT;
        camera.setCenter(cameraCenterX, cameraCenterY);

        final float zoomFactor = cameraHeight / TEXTURE_HEIGHT;
        camera.setZoomFactor(zoomFactor);
        return camera;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        ZoomCamera camera = getZoomCamera();
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new FillResolutionPolicy(), camera);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
        final int atlasWidth  = 1024;
        final int atlasHeight = 1024;
        // full path assets/gfx
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        // create atlas
        BuildableBitmapTextureAtlas atlas =
                new BuildableBitmapTextureAtlas(mEngine.getTextureManager()
                                                , atlasWidth
                                                , atlasHeight
                                                , TextureOptions.BILINEAR);

        shipTextureRegion = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(atlas, this, "ship.png");  // ship.png in assets/gfx folder

        backTextureRegion = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(atlas, this, "back.png");
        // Build the bitmap texture atlas
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource
                    , BitmapTextureAtlas>(0, 1, 1));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        // Load the bitmap texture atlas into the device's gpu memory
        atlas.load();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        scene = new Scene();
        final float shipX = TEXTURE_WIDTH * 0.5f - shipTextureRegion.getWidth() * 0.5f;
        final float shipY = TEXTURE_HEIGHT * 0.5f - shipTextureRegion.getHeight() * 0.5f;

        // create ship sprite
        Sprite shipSprite = new Sprite(shipX
                                        , shipY
                                        , shipTextureRegion
                                        , mEngine.getVertexBufferObjectManager());

        Sprite backSprite = new Sprite(0
                , 0
                , backTextureRegion
                , mEngine.getVertexBufferObjectManager());

        scene.attachChild(backSprite);
        scene.attachChild(shipSprite);

        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        scene.setBackground(new Background(backgroundColor));
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }


}