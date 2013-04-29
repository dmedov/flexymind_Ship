package com.example.ship;

import android.graphics.Point;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.Menu.ShipMenuScene;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import com.example.ship.sceletone.SceletonScene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;

public class SceletonActivity extends BaseGameActivity {
    private static final int TEXTURE_WIDTH = 1739;
    private static final int TEXTURE_HEIGHT = 900;
    private SceletonScene sceletonScene;
    private ShipMenuScene menuScene;
    private ResourceManager resourceManager;
    private Events events;
    private ZoomCamera zoomCamera;
    private SceneSwitcher sceneSwitcher;

    private ZoomCamera createZoomCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ZoomCamera camera;

        float cameraWidth = TEXTURE_WIDTH;
        float cameraHeight = cameraWidth * metrics.heightPixels / metrics.widthPixels;
        camera = new ZoomCamera(0, 0, cameraWidth, cameraHeight);

        final PointF cameraCenter = new PointF(0.5f * TEXTURE_WIDTH, 0.5f * TEXTURE_HEIGHT);
        camera.setCenter(cameraCenter.x, cameraCenter.y );

        final float zoomFactor = cameraHeight / TEXTURE_HEIGHT;
        camera.setZoomFactor(zoomFactor/4);
        return camera;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        zoomCamera = createZoomCamera();
        EngineOptions engineOptions = new EngineOptions( true
                                                       , ScreenOrientation.LANDSCAPE_FIXED
                                                       , new FillResolutionPolicy()
                                                       , zoomCamera);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
        resourceManager = new ResourceManager();
        resourceManager.loadAllTextures(this, mEngine.getTextureManager());

        FontFactory.setAssetBasePath(getResources().getString(R.string.FONT_BASE_ASSET_PATH));

        events = new Events(this);

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        sceneSwitcher = new SceneSwitcher(this);

        pOnCreateSceneCallback.onCreateSceneFinished(sceneSwitcher.getRootScene());
    }

    @Override
    public void onPopulateScene( Scene pScene
                               , OnPopulateSceneCallback pOnPopulateSceneCallback) {

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    public Events getEvents() {
        return events;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public Point getTextureSize() {
        return new Point(TEXTURE_WIDTH, TEXTURE_HEIGHT);
    }

    public Camera getCamera() {
        return zoomCamera;
    }

    public SceneSwitcher getSceneSwitcher() {
        return sceneSwitcher;
    }
}