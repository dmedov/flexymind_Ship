package com.example.ship.menu;

import android.graphics.PointF;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.commons.A;
import org.andengine.engine.camera.hud.HUD;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 30.04.13
 * Time: 8:06
 * To change this template use File | Settings | File Templates.
 */
public class MenuHUD extends HUD {
    private static final float RELATIVE_BUTTON_HEIGHT = 0.15f;
    private static final float RELATIVE_BOTTOM_BUTTONS_Y_OFFSET = 0.8f;
    private static final float RELATIVE_TOP_BUTTONS_Y_OFFSET = 0.1f;
    public static final float RELATIVE_MUSIC_BUTTON_X_POSITION = 0.7f;
    public static final float RELATIVE_SOUND_BUTTON_X_POSITION = 0.8f;
    public static final float RELATIVE_SPACE_BETWEEN_BOTTOM_BUTTONS = 0.2f;
    private ArrayList<MenuButtonSprite> bottomButtons;
    private ArrayList<MenuButtonSprite> topButtons;
    private PointF cameraSize;

    public MenuHUD() {
        super();

        cameraSize = new PointF( A.a.getCamera().getWidthRaw()
                               , A.a.getCamera().getHeightRaw());

        createButtons();
    }

    public void setEventsToChildren(Events events) {
        for (MenuButtonSprite button: bottomButtons) {
            button.setEvents(events);
        }
        for (MenuButtonSprite button: topButtons) {
            button.setEvents(events);
        }
    }

    private void createButtons() {
        bottomButtons = new ArrayList<MenuButtonSprite>();
        topButtons = new ArrayList<MenuButtonSprite>();

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
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_music_button_on)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_music_button_off));
        MenuButtonSprite soundButtonSprite =
                new MenuButtonSprite( R.string.MENU_SOUND_BUTTON
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_sound_button_on)
                                    , A.rm.getLoadedTextureRegion(R.drawable.menu_sound_button_off));

        bottomButtons.add(startButtonSprite);
        bottomButtons.add(highscoresButtonSprite);
        bottomButtons.add(creditsButtonSprite);
        bottomButtons.add(exitButtonSprite);
        topButtons.add(musicButtonSprite);
        topButtons.add(soundButtonSprite);

        float positionOffset = cameraSize.x * RELATIVE_SPACE_BETWEEN_BOTTOM_BUTTONS;

        for (MenuButtonSprite button: bottomButtons) {
            float buttonScale = cameraSize.y * RELATIVE_BUTTON_HEIGHT / button.getHeight();
            button.setScale(buttonScale);

            button.setPosition( positionOffset - button.getWidthScaled() * 0.5f
                              , cameraSize.y * RELATIVE_BOTTOM_BUTTONS_Y_OFFSET);
            positionOffset += cameraSize.x * 0.2f;

            this.registerTouchArea(button);
            this.attachChild(button);
        }

        for (MenuButtonSprite button: topButtons) {
            float buttonScale = cameraSize.y * RELATIVE_BUTTON_HEIGHT * 0.5f / button.getHeight();
            button.setScale(buttonScale);

            this.registerTouchArea(button);
            this.attachChild(button);
        }

        musicButtonSprite.setPosition( cameraSize.x * RELATIVE_MUSIC_BUTTON_X_POSITION
                                     , cameraSize.y * RELATIVE_TOP_BUTTONS_Y_OFFSET
                                       - musicButtonSprite.getHeightScaled());
        soundButtonSprite.setPosition( cameraSize.x * RELATIVE_SOUND_BUTTON_X_POSITION
                                     , cameraSize.y * RELATIVE_TOP_BUTTONS_Y_OFFSET
                                       - musicButtonSprite.getHeightScaled());
        positionOffset += cameraSize.x * 0.2f;

    }
}
