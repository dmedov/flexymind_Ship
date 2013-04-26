package com.example.ship;

import android.graphics.Typeface;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.Menu.ShipMenuScene;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import com.example.ship.sceletone.SceletoneScene;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class SceletonActivity extends BaseGameActivity {
    private static final int TEXTURE_WIDTH = 960;
    private static final int TEXTURE_HEIGHT = 600;
    private Scene sceletoneScene;
    private Scene menuScene;
    private ResourceManager resourceManager;

    private ZoomCamera createZoomCamera(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ZoomCamera camera;

        float cameraWidth = TEXTURE_WIDTH;
        float cameraHeight = cameraWidth * metrics.heightPixels / metrics.widthPixels;
        camera = new ZoomCamera(0, 0, cameraWidth, cameraHeight);

        final PointF cameraCenter = new PointF(0.5f * TEXTURE_WIDTH, 0.5f * TEXTURE_HEIGHT);
        camera.setCenter(cameraCenter.x, cameraCenter.y );

        final float zoomFactor = cameraHeight / TEXTURE_HEIGHT;
        camera.setZoomFactor(zoomFactor);
        return camera;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        ZoomCamera camera = createZoomCamera();
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new FillResolutionPolicy(), camera);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
        resourceManager = new ResourceManager();
        resourceManager.loadAllTextures(this, mEngine.getTextureManager());

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        menuScene = new ShipMenuScene(this, TEXTURE_WIDTH, TEXTURE_HEIGHT, resourceManager);
        sceletoneScene = new SceletoneScene(this, TEXTURE_WIDTH, TEXTURE_HEIGHT, resourceManager, menuScene);

        pOnCreateSceneCallback.onCreateSceneFinished(sceletoneScene);
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}