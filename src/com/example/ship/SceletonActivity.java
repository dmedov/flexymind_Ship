package com.example.ship;

import android.graphics.Typeface;
import android.widget.Toast;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.Menu.ButtonMenu;
import com.example.ship.Menu.ShipMenuScene;
import org.andengine.engine.camera.Camera;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import org.andengine.engine.camera.ZoomCamera;
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
    private static final int TEXTURE_WIDTH = 960;
    private static final int TEXTURE_HEIGHT = 600;
    private Scene sceletoneScene;
    private Scene menuScene;
    private ResourceManager resMan;
    private Font mFont;

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
        mFont = FontFactory.create(mEngine.getFontManager(), mEngine.
                getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT,
                Typeface.NORMAL), 32f, true, Color.BLACK_ABGR_PACKED_INT);
        mFont.load();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        sceletoneScene = new Scene();
        final PointF ship = new PointF(TEXTURE_WIDTH * 0.5f - shipTextureRegion.getWidth() * 0.5f
                , TEXTURE_HEIGHT * 0.5f - shipTextureRegion.getHeight() * 0.5f);
        // create ship sprite
        Sprite shipSprite = new Sprite(ship.x
                , ship.y
                , shipTextureRegion
                , mEngine.getVertexBufferObjectManager());

        final PointF back = new PointF(TEXTURE_WIDTH * 0.5f - backTextureRegion.getWidth() * 0.5f
                , TEXTURE_HEIGHT * 0.5f - backTextureRegion.getHeight() * 0.5f);

        Sprite backSprite = new Sprite(back.x
                , back.y
                , backTextureRegion
                , mEngine.getVertexBufferObjectManager());

        sceletoneScene.attachChild(backSprite);
        sceletoneScene.attachChild(shipSprite);

        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        sceletoneScene.setBackground(new Background(backgroundColor));
        menuScene = new ShipMenuScene(this, CAMERA_WIDTH, CAMERA_HEIGHT, resMan);
        pOnCreateSceneCallback.onCreateSceneFinished(sceletoneScene);
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}