package com.example.ship;

import android.graphics.Typeface;
import android.widget.Toast;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.Menu.ButtonMenu;
import com.example.ship.Menu.ShipMenuScene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class SceletonActivity extends BaseGameActivity {
    private static final int CAMERA_WIDTH = 960;
    private static final int CAMERA_HEIGHT = 600;
    private Camera camera;
    private Scene scene;
    private ResourceManager resMan;
    private Font mFont;


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
        mFont = FontFactory.create(mEngine.getFontManager(), mEngine.
                getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT,
                Typeface.NORMAL), 32f, true, Color.BLACK_ABGR_PACKED_INT);
        mFont.load();
        pOnCreateResourcesCallback.onCreateResourcesFinished();

    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        scene = new ShipMenuScene(this, CAMERA_WIDTH, CAMERA_HEIGHT, resMan);
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}