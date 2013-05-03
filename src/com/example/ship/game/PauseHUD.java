package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 03.05.13
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public class PauseHUD extends HUD {

    private static final float RELATIVE_BUTTON_HEIGHT = 0.15f;
    private static final float RELATIVE_BORDER = 0.02f;
    private final SceletonActivity activity;
    private final Engine engine;
    private final PointF cameraSize;
    private static final float BACKGROUND_ALPHA = 0.7f;
    private ArrayList<GameButtonSprite> buttons;

    public PauseHUD(SceletonActivity activity) {
        super();

        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createBackground();
        createButtons();
    }

    private void createButtons() {
        buttons = new ArrayList<GameButtonSprite>();

        GameButtonSprite backButton;
        backButton = new GameButtonSprite( activity.getResourceManager()
                                                   .getLoadedTextureRegion(R.drawable.playbutton)
                                          , engine.getVertexBufferObjectManager()
                                          , R.string.GAME_PAUSE_BACK_BUTTON);

        backButton.setScale(cameraSize.y * RELATIVE_BUTTON_HEIGHT / backButton.getHeight());
        backButton.setPosition( RELATIVE_BORDER * cameraSize.x
                              , RELATIVE_BORDER * cameraSize.y);

        GameButtonSprite menuButton;
        menuButton = new GameButtonSprite( activity.getResourceManager()
                                                    .getLoadedTextureRegion(R.drawable.backbutton)
                                          , engine.getVertexBufferObjectManager()
                                          , R.string.GAME_PAUSE_MENU_BUTTON);

        menuButton.setScale(cameraSize.y * RELATIVE_BUTTON_HEIGHT / menuButton.getHeight());
        menuButton.setPosition( (1 - RELATIVE_BORDER) * cameraSize.x - menuButton.getWidth()
                              , (1 - RELATIVE_BORDER) * cameraSize.y - menuButton.getHeight());

        buttons.add(backButton);
        buttons.add(menuButton);
        for (GameButtonSprite button: buttons) {
            this.registerTouchArea(button);
            this.attachChild(button);
        }
    }

    private void createBackground() {
        Rectangle background = new Rectangle(0, 0, cameraSize.x, cameraSize.y, engine.getVertexBufferObjectManager());
        Color backgroundColor = new Color(0.0f, 0.0f, 0.0f);

        background.setColor(backgroundColor);
        background.setAlpha(BACKGROUND_ALPHA);

        this.attachChild(background);
    }

    public void setEventsToChildren(Events events) {
        for (GameButtonSprite button: buttons) {
            button.setEvents(events);
        }
    }
}
