package com.example.ship.Menu;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 30.04.13
 * Time: 8:06
 * To change this template use File | Settings | File Templates.
 */
public class MenuHUD extends HUD {
    private final static int BUTTON_FONT_HEIGHT = 50;
    private final static int FONT_ATLAS_SIDE = 256;
    private final SceletonActivity activity;
    private ArrayList<MenuButtonSprite> buttons;
    private Engine engine;
    private PointF cameraSize;
    private Font buttonFont;

    public MenuHUD(final SceletonActivity activity) {
        super();

        engine = activity.getEngine();
        cameraSize = new PointF( activity.getCamera().getWidth()
                               , activity.getCamera().getHeight());
        this.activity = activity;

        createFont();
        createButtons();

    }

    public void setEventsToChildren(Events events) {
        for (MenuButtonSprite button: buttons) {
            button.setEvents(events);
        }
    }

    private void createButtons() {
        buttons = new ArrayList<MenuButtonSprite>();
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

        float positionOffset = cameraSize.y * 0.25f;

        for (MenuButtonSprite button: buttons) {
            button.setPosition( cameraSize.x * 0.5f - startButtonSprite.getWidth() * 0.5f
                              , positionOffset);
            positionOffset += startButtonSprite.getHeight() + cameraSize.y * 0.02f;

            this.registerTouchArea(button);
            this.attachChild(button);
        }
    }

    private void createFont() {
        buttonFont = FontFactory.create( engine.getFontManager()
                                       , engine.getTextureManager()
                                       , FONT_ATLAS_SIDE
                                       , FONT_ATLAS_SIDE
                                       , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                       , BUTTON_FONT_HEIGHT
                                       , true
                                       , Color.WHITE_ABGR_PACKED_INT);
        buttonFont.load();
    }

    private MenuButtonSprite createMenuButtonSprite(int labelId, int buttonId) {
        return new MenuButtonSprite( activity.getResourceManager().getLoadedTextureRegion(
                                            getStringResource(R.string.MENU_BUTTON_TEXTURE))
                                   , engine.getVertexBufferObjectManager()
                                   , buttonId
                                   , getStringResource(labelId)
                                   , buttonFont);
    }

    private String getStringResource(int id) {
        return activity.getResources().getString(id);
    }
}
