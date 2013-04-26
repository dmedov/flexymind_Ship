package com.example.ship;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import org.andengine.engine.camera.ZoomCamera;
import com.example.ship.Atlas.ResourceManager;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class SceletonActivity extends BaseGameActivity {
    private static final int TEXTURE_WIDTH = 960;
    private static final int TEXTURE_HEIGHT = 600;
    private Scene scene;
    private ResourceManager resMan;

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
        resMan = new ResourceManager();
        resMan.loadAllTextures(this,mEngine.getTextureManager());
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        scene = new Scene();
        ITextureRegion shipTextureRegion = resMan.getLoadedTextureRegion("ship");
        final PointF ship = new PointF(TEXTURE_WIDTH * 0.5f - shipTextureRegion.getWidth() * 0.5f
                                    , TEXTURE_HEIGHT * 0.5f - shipTextureRegion.getHeight() * 0.5f);
        // create ship sprite
        Sprite shipSprite = new Sprite(ship.x
                                        , ship.y
                                        , shipTextureRegion
                                        , mEngine.getVertexBufferObjectManager());

        ITextureRegion backTextureRegion = resMan.getLoadedTextureRegion("back");
        final PointF back = new PointF(TEXTURE_WIDTH * 0.5f - backTextureRegion.getWidth() * 0.5f
                                        , TEXTURE_HEIGHT * 0.5f - backTextureRegion.getHeight() * 0.5f);

        
        Sprite backSprite = new Sprite(back.x
                                        , back.y
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