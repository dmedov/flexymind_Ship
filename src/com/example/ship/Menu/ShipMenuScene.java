package com.example.ship.Menu;

import android.graphics.Point;
import android.graphics.Typeface;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
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
    private       ArrayList<MenuButtonSprite> buttons;
    private       Font buttonFont;
    private       HUD hud;

    public ShipMenuScene( final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.textureSize = activity.getTextureSize();
        this.resourceManager = activity.getResourceManager();

        buttons = new ArrayList<MenuButtonSprite>();

        createBackground();
        createButtons();
        createTitle();
    }

    public void setEventsToChildren(Events events) {
        for (MenuButtonSprite button: buttons) {
            button.setEvents(events);
        }
    }

    public HUD getHud() {
        return hud;
    }

    private void createTitle() {
        Font titleFont = FontFactory.createFromAsset( activity.getFontManager()
                                                    , activity.getTextureManager()
                                                    , FONT_ATLAS_SIDE
                                                    , FONT_ATLAS_SIDE
                                                    , activity.getAssets()
                                                    , getStringResource(R.string.FONT_PLOK_FILE)
                                                    , TITLE_FONT_HEIGHT
                                                    , true
                                                    , android.graphics.Color.BLACK);
        titleFont.load();
        Text title = new Text( 0
                             , 0
                             , titleFont
                             , getStringResource(R.string.APP_TITLE)
                             , mEngine.getVertexBufferObjectManager());

        title.setPosition(textureSize.x * 0.5f - title.getWidth() * 0.5f, textureSize.y * 0.075f);
        this.attachChild(title);
    }

    private void createBackground() {
        ITextureRegion backgroundTexture = resourceManager.getLoadedTextureRegion(
                getStringResource(R.string.MENU_BACKGROUND_TEXTURE));
        Sprite backgroundImage = new Sprite( 0
                , 0
                , backgroundTexture
                , mEngine.getVertexBufferObjectManager());
        this.attachChild(backgroundImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private void createButtons() {
        buttons = new ArrayList<MenuButtonSprite>();

        buttonFont = FontFactory.create( mEngine.getFontManager()
                                       , mEngine.getTextureManager()
                                       , FONT_ATLAS_SIDE
                                       , FONT_ATLAS_SIDE
                                       , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                       , BUTTON_FONT_HEIGHT
                                       , true
                                       , Color.WHITE_ABGR_PACKED_INT);
        buttonFont.load();

        MenuButtonSprite startButtonSprite = createMenuButtonSprite( R.string.MENU_START_BUTTON_LABEL
                                                                   , R.string.MENU_START_BUTTON);
        MenuButtonSprite highscoresButtonSprite = createMenuButtonSprite( R.string.MENU_HS_BUTTON_LABEL
                                                                        , R.string.MENU_HS_BUTTON);
        MenuButtonSprite creditsButtonSprite = createMenuButtonSprite( R.string.MENU_CREDITS_BUTTON_LABEL
                                                                     , R.string.MENU_CREDITS_BUTTON);
        MenuButtonSprite exitButtonSprite = createMenuButtonSprite( R.string.MENU_EXIT_BUTTON_LABEL
                                                                  , R.string.MENU_EXIT_BUTTON);

        buttons.add(startButtonSprite);
        buttons.add(highscoresButtonSprite);
        buttons.add(creditsButtonSprite);
        buttons.add(exitButtonSprite);

        hud = new HUD();
        float positionOffset = textureSize.y * 0.25f;

        for (MenuButtonSprite button: buttons) {
            button.setPosition( textureSize.x * 0.5f - startButtonSprite.getWidth() * 0.5f
                    , positionOffset);
            positionOffset += startButtonSprite.getHeight() + textureSize.y * 0.02f;

            hud.registerTouchArea(button);
            hud.attachChild(button);
        }

        this.setTouchAreaBindingOnActionDownEnabled(true);
    }

    private MenuButtonSprite createMenuButtonSprite(int labelId, int buttonId) {
        return new MenuButtonSprite( resourceManager.getLoadedTextureRegion(
                                            getStringResource(R.string.MENU_BUTTON_TEXTURE))
                                   , mEngine.getVertexBufferObjectManager()
                                   , buttonId
                                   , getStringResource(labelId)
                                   , buttonFont);
    }

    private String getStringResource(int id) {
        return activity.getResources().getString(id);
    }
}
