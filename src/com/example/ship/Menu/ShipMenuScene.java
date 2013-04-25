package com.example.ship.Menu;

import android.graphics.Point;
import android.graphics.Typeface;
import android.widget.Toast;
import com.example.ship.Atlas.ResourceManager;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 25.04.13
 * Time: 5:11
 * To change this template use File | Settings | File Templates.
 */
public class ShipMenuScene extends Scene {
    private final BaseGameActivity activity;
    private Engine mEngine;
    private Font font;

    public ShipMenuScene(final BaseGameActivity activity, int textureWidth, int textureHeight, ResourceManager resourceManager) {
        this(activity, new Point(textureWidth, textureHeight), resourceManager);
    }

    public ShipMenuScene(final BaseGameActivity activity, Point textureSize, ResourceManager resourceManager) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        font = FontFactory.create(mEngine.getFontManager(), mEngine.
                getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT,
                Typeface.NORMAL), 32f, true, Color.BLACK_ABGR_PACKED_INT);
        font.load();

        ButtonMenu startButtonSprite = new ButtonMenu( 1
                ,resourceManager.getLoadedTextureRegion("button_menu")
                , mEngine.getVertexBufferObjectManager()
                , "Start"
                , font ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                         float pTouchAreaLocalX, float pTouchAreaLocalY){
                if ( pSceneTouchEvent.isActionDown() ){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(), "Start Button Pressed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
                        pTouchAreaLocalY);
            }
        };

        ButtonMenu highscoresButtonSprite = new ButtonMenu( 2
                , resourceManager.getLoadedTextureRegion("button_menu")
                , mEngine.getVertexBufferObjectManager()
                , "HighScores"
                , font ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                         float pTouchAreaLocalX, float pTouchAreaLocalY){
                if ( pSceneTouchEvent.isActionDown() ){
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(), "HighScores Button Pressed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
                        pTouchAreaLocalY);
            }
        };

        ButtonMenu creditsButtonSprite = new ButtonMenu( 3
                , resourceManager.getLoadedTextureRegion("button_menu")
                , mEngine.getVertexBufferObjectManager()
                , "Credit"
                , font ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                         float pTouchAreaLocalX, float pTouchAreaLocalY){
                if ( pSceneTouchEvent.isActionDown() ){
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(), "Credit Button Pressed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
                        pTouchAreaLocalY);
            }
        };

        ButtonMenu exitButtonSprite = new ButtonMenu( 4
                , resourceManager.getLoadedTextureRegion("button_menu")
                , mEngine.getVertexBufferObjectManager()
                , "Exit"
                , font ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                         float pTouchAreaLocalX, float pTouchAreaLocalY){
                if ( pSceneTouchEvent.isActionDown() ){
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                           Toast.makeText(activity.getApplicationContext(), "Exit Button Pressed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
                        pTouchAreaLocalY);
            }
        };
        this.registerTouchArea(startButtonSprite);
        this.registerTouchArea(highscoresButtonSprite);
        this.registerTouchArea(creditsButtonSprite);
        this.registerTouchArea(exitButtonSprite);
        this.attachChild(startButtonSprite);
        this.attachChild(highscoresButtonSprite);
        this.attachChild(creditsButtonSprite);
        this.attachChild(exitButtonSprite);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
        this.setTouchAreaBindingOnActionDownEnabled(true);
    }
}
