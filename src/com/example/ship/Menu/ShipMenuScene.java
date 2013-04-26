package com.example.ship.Menu;

import android.graphics.Point;
import android.graphics.Typeface;
import com.example.ship.Atlas.ResourceManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 25.04.13
 * Time: 5:11
 * To change this template use File | Settings | File Templates.
 */
public class ShipMenuScene extends Scene {
    private final static int FONT_ATLAS_SIDE = 256;
    private final static int TITLE_FONT_HEIGHT = 70;
    private final static int BUTTON_FONT_HEIGHT = 50;
    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private       Point textureSize;
    private       ArrayList<ButtonMenuSprite> buttons;
    private       Font buttonFont;
    private       HUD hud;

    public ShipMenuScene( final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.textureSize = activity.getTextureSize();
        this.resourceManager = activity.getResourceManager();

        buttons = new ArrayList<ButtonMenuSprite>();

        createBackground();
        createButtons();
        createTitle();
    }

    private void createTitle() {
        Font titleFont = FontFactory.createFromAsset( activity.getFontManager()
                                                    , activity.getTextureManager()
                                                    , FONT_ATLAS_SIDE
                                                    , FONT_ATLAS_SIDE
                                                    , activity.getAssets()
                                                    , "Plok.ttf"
                                                    , TITLE_FONT_HEIGHT
                                                    , true
                                                    , android.graphics.Color.BLACK);
        titleFont.load();
        Text title = new Text(0, 0, titleFont, "SHIPS", mEngine.getVertexBufferObjectManager());
        title.setPosition(textureSize.x * 0.5f - title.getWidth() * 0.5f, textureSize.y * 0.075f);
        this.attachChild(title);
    }

    private void createBackground() {
        Sprite backgroundImage = new Sprite( 0, 0, resourceManager.getLoadedTextureRegion("bg_ship")
                                           , mEngine.getVertexBufferObjectManager());
        this.attachChild(backgroundImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private void createButtons() {
        buttons = new ArrayList<ButtonMenuSprite>();

        buttonFont = FontFactory.create( mEngine.getFontManager()
                                       , mEngine.getTextureManager()
                                       , FONT_ATLAS_SIDE
                                       , FONT_ATLAS_SIDE
                                       , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                       , BUTTON_FONT_HEIGHT
                                       , true
                                       , Color.BLACK_ABGR_PACKED_INT);
        buttonFont.load();

        ButtonMenuSprite startButtonSprite = createButtonMenuSprite("Start");
        ButtonMenuSprite highscoresButtonSprite = createButtonMenuSprite("High scores");
        ButtonMenuSprite creditsButtonSprite = createButtonMenuSprite("Credits");
        ButtonMenuSprite exitButtonSprite = createButtonMenuSprite("Exit");

        startButtonSprite.setPosition( textureSize.x * 0.25f - startButtonSprite.getWidth() * 0.5f
                                     , textureSize.y * 0.4f);
        highscoresButtonSprite.setPosition( textureSize.x * 0.75f - startButtonSprite.getWidth() * 0.5f
                                          , textureSize.y * 0.4f);
        creditsButtonSprite.setPosition( textureSize.x * 0.25f - startButtonSprite.getWidth() * 0.5f
                                       , textureSize.y * 0.7f);
        exitButtonSprite.setPosition( textureSize.x * 0.75f - startButtonSprite.getWidth() * 0.5f
                                    , textureSize.y * 0.7f);

        buttons.add(startButtonSprite);
        buttons.add(highscoresButtonSprite);
        buttons.add(creditsButtonSprite);
        buttons.add(exitButtonSprite);

        hud = new HUD();

        for (ButtonMenuSprite button: buttons) {
            this.registerTouchArea(button);
            hud.attachChild(button);
        }

        this.setTouchAreaBindingOnActionDownEnabled(true);
    }

    private ButtonMenuSprite createButtonMenuSprite(String label) {
        return new ButtonMenuSprite( resourceManager.getLoadedTextureRegion("button_menu")
                                   , mEngine.getVertexBufferObjectManager()
                                   , label
                                   , buttonFont);
    }

    public void setEventsToChildren(Events events) {
        for (ButtonMenuSprite button: buttons) {
            button.setEvents(events);
        }
    }

    public HUD getHud() {
        return hud;
    }
}
