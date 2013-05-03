package com.example.ship.menu;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
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
    private final static float RELATIVE_TITLE_HEIGHT = 0.25f;
    private final static float RELATIVE_BUTTON_HEIGHT = 0.15f;
    private final static float RELATIVE_BUTTON_OFFSET_HEIGHT = 0.175f;
    private final SceletonActivity activity;
    private ArrayList<MenuButtonSprite> buttons;
    private Engine engine;
    private PointF cameraSize;
    private Font buttonFont;

    public MenuHUD(final SceletonActivity activity) {
        super();

        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidth() * activity.getCamera().getZoomFactor()
                               , this.activity.getCamera().getHeight() * activity.getCamera().getZoomFactor());

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

        float positionOffset = cameraSize.y * RELATIVE_TITLE_HEIGHT;

        for (MenuButtonSprite button: buttons) {
            float buttonScale = cameraSize.y * RELATIVE_BUTTON_HEIGHT / button.getHeight();
            button.setScale(buttonScale);

            button.setPosition( cameraSize.x * 0.5f - button.getWidth() * 0.5f
                              , positionOffset);
            positionOffset += cameraSize.y * RELATIVE_BUTTON_OFFSET_HEIGHT;

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
        return new MenuButtonSprite( activity.getResourceManager().getLoadedTextureRegion(R.drawable.menubutton)
                                   , engine.getVertexBufferObjectManager()
                                   , buttonId
                                   , getStringResource(labelId)
                                   , buttonFont);
    }

    private String getStringResource(int id) {
        return activity.getResources().getString(id);
    }
}
