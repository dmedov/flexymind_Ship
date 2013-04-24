package com.example.ship;

import android.graphics.Typeface;
import android.util.Log;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.ButtonMenu.ButtonMenu;
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
        scene = new Scene();

        ButtonMenu startButtonSprite = new ButtonMenu( 1
                                                     , resMan.getLoadedTextureRegion("button_menu")
                                                     , mEngine.getVertexBufferObjectManager()
                                                     , "Start"
                                                     , mFont );

        ButtonMenu highscoresButtonSprite = new ButtonMenu( 2
                                                          , resMan.getLoadedTextureRegion("button_menu")
                                                          , mEngine.getVertexBufferObjectManager()
                                                          , "HighScores"
                                                          , mFont );

        ButtonMenu creditsButtonSprite = new ButtonMenu( 3
                                                       , resMan.getLoadedTextureRegion("button_menu")
                                                       , mEngine.getVertexBufferObjectManager()
                                                       , "Credit"
                                                       , mFont );

        ButtonMenu exitButtonSprite = new ButtonMenu( 4
                                                    , resMan.getLoadedTextureRegion("button_menu")
                                                    , mEngine.getVertexBufferObjectManager()
                                                    , "Exit"
                                                    , mFont );

        scene.registerTouchArea(startButtonSprite);
        scene.registerTouchArea(highscoresButtonSprite);
        scene.registerTouchArea(creditsButtonSprite);
        scene.registerTouchArea(exitButtonSprite);
        scene.attachChild(startButtonSprite);
        scene.attachChild(highscoresButtonSprite);
        scene.attachChild(creditsButtonSprite);
        scene.attachChild(exitButtonSprite);
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