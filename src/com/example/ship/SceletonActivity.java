package com.example.ship;

import android.graphics.Typeface;
import android.util.Log;
import com.example.ship.Atlas.ResourceManager;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class SceletonActivity extends BaseGameActivity {
    private static final int CAMERA_WIDTH = 960;
    private static final int CAMERA_HEIGHT = 600;
    private Camera camera;
    private TextureRegion shipTextureRegion;
    private Scene scene;
    private ResourceManager resMan;
    Font mFont;


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
        scene = new Scene();

        Sprite startButtonSprite = new Sprite( CAMERA_WIDTH * 0.5f - resMan.getLoadedTextureRegion("button_menu").getWidth() * 0.5f
                                             , CAMERA_HEIGHT * 0.2f
                                             , resMan.getLoadedTextureRegion("button_menu")
                                             , mEngine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                float pTouchAreaLocalX, float pTouchAreaLocalY){

                if ( pSceneTouchEvent.isActionDown() ){
                    Log.d("AAAAAAAAAwwwwwwwwwwwwwwwwwwAAAAAAAA:","AAAAAAAAAAwwwwwwwwwwwwwwwwAAAAAAAAAAAA");
                }
                return true;
            }
        };

        Sprite exitButtonSprite = new Sprite( CAMERA_WIDTH * 0.5f - resMan.getLoadedTextureRegion("button_menu").getWidth() * 0.5f
                                            , CAMERA_HEIGHT * 0.4f
                                            , resMan.getLoadedTextureRegion("button_menu")
                                            , mEngine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                         float pTouchAreaLocalX, float pTouchAreaLocalY){

                if ( pSceneTouchEvent.isActionDown() ){
                    Log.d("AAAAAAAAAwwwwwwwwwwwwwwwwwwAAAAAAAA:","AAAAAAAAAAwwwwwwwwwwwwwwwwAAAAAAAAAAAA");
                }
                return true;
            }
        };

        Text text = new Text(startButtonSprite.getRotationCenterX(), 0.0f, mFont,"Start", mEngine.getVertexBufferObjectManager());


        scene.registerTouchArea(startButtonSprite);
        scene.registerTouchArea(exitButtonSprite);
        scene.attachChild(startButtonSprite);
        scene.attachChild(exitButtonSprite);
        startButtonSprite.attachChild(text);
        text.setPosition(startButtonSprite.getWidth() * 0.5f - text.getWidth() * 0.5f,startButtonSprite.getHeight() * 0.5f - text.getHeight() * 0.5f);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        scene.setBackground(new Background(backgroundColor));
        scene.setTouchAreaBindingOnActionDownEnabled(true);
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}