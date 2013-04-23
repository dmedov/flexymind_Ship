package com.example.ship;

import com.example.ship.Atlas.ResourceManager;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class SceletonActivity extends BaseGameActivity {
    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private TextureRegion shipTextureRegion;
    private Scene scene;
    private ResourceManager resMan;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
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
        final float shipX = CAMERA_WIDTH * 0.5f;
        final float shipY = CAMERA_HEIGHT * 0.5f;

        // create ship sprite
        Sprite shipSprite = new Sprite(shipX
                                        , shipY
                                        , resMan.getLoadedTextureRegion("ship")
                                        , mEngine.getVertexBufferObjectManager());

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