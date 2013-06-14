package com.example.ship.menu;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.commons.A;
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
    private ArrayList<MenuButtonSprite> buttons;
    private Engine engine;
    private PointF cameraSize;
    private Font buttonFont;

    public MenuHUD() {
        super();

        engine = A.e;
        cameraSize = new PointF( A.a.getCamera().getWidthRaw()
                               , A.a.getCamera().getHeightRaw());

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
        MenuButtonSprite startButtonSprite =
                new MenuButtonSprite( R.string.MENU_START_BUTTON
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_play_button_pushed)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_play_button_popped));
        MenuButtonSprite highscoresButtonSprite =
                new MenuButtonSprite( R.string.MENU_HS_BUTTON
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_records_button_pushed)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_records_button_popped));
        MenuButtonSprite creditsButtonSprite =
                new MenuButtonSprite( R.string.MENU_CREDITS_BUTTON
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_credits_button_pushed)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_credits_button_popped));
        MenuButtonSprite exitButtonSprite =
                new MenuButtonSprite( R.string.MENU_EXIT_BUTTON
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_exit_button_pushed)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_exit_button_popped));

        MenuButtonSprite musicButtonSprite =
                new MenuButtonSprite( R.string.MENU_MUSIC_BUTTON
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_sound_button_on)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_sound_button_off));
        MenuButtonSprite soundButtonSprite =
                new MenuButtonSprite( R.string.MENU_SOUND_BUTTON
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_sound_button_on)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_sound_button_off));

        buttons.add(startButtonSprite);
        buttons.add(highscoresButtonSprite);
        buttons.add(creditsButtonSprite);
        buttons.add(exitButtonSprite);
        buttons.add(musicButtonSprite);
        buttons.add(soundButtonSprite);

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

    private String getStringResource(int id) {
        return A.a.getResources().getString(id);
    }
}
