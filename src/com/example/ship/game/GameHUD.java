package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 03.05.13
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class GameHUD extends HUD {

    private final SceletonActivity activity;
    private final Engine engine;
    private static final float RELATIVE_BUTTON_HEIGHT = 0.15f;
    private static final float RELATIVE_SPACE_BETWEEN_CONTROLS = 0.01f;
    private static final float RELATIVE_BORDER = 0.02f;
    private static final float BUTTON_ALPHA = 0.75f;
    private       PointF cameraSize;
    private       ArrayList<GameButtonSprite> buttons;

    public GameHUD(SceletonActivity activity) {
        super();

        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createButtons();

    }

    private void createButtons() {
        buttons = new ArrayList<GameButtonSprite>();

        GameButtonSprite pauseButton;
        pauseButton = new GameButtonSprite( activity.getResourceManager()
                                                    .getLoadedTextureRegion(R.drawable.pausebutton)
                                          , engine.getVertexBufferObjectManager()
                                          , R.string.GAME_PAUSE_BUTTON);
        buttons.add(pauseButton);

        GameButtonSprite fireButton;
        fireButton = new GameButtonSprite( activity.getResourceManager()
                                                    .getLoadedTextureRegion(R.drawable.firebutton)
                                         , engine.getVertexBufferObjectManager()
                                         , R.string.GAME_FIRE_BUTTON);
        buttons.add(fireButton);

        GameButtonSprite moveLeftButton;
        moveLeftButton = new GameButtonSprite( activity.getResourceManager()
                                                    .getLoadedTextureRegion(R.drawable.leftbutton)
                                          , engine.getVertexBufferObjectManager()
                                          , R.string.GAME_LEFT_BUTTON);
        buttons.add(moveLeftButton);

        GameButtonSprite moveRightButton;
        moveRightButton = new GameButtonSprite( activity.getResourceManager()
                                                        .getLoadedTextureRegion(R.drawable.rightbutton)
                                              , engine.getVertexBufferObjectManager()
                                              , R.string.GAME_RIGHT_BUTTON);
        buttons.add(moveRightButton);

        for (GameButtonSprite button: buttons) {
            button.setAlpha(BUTTON_ALPHA);
            button.setScale(cameraSize.y * RELATIVE_BUTTON_HEIGHT / fireButton.getHeight());
            this.registerTouchArea(button);
            this.attachChild(button);
        }

        pauseButton.setPosition( RELATIVE_BORDER * cameraSize.x
                               , RELATIVE_BORDER * cameraSize.y);
        fireButton.setPosition( (1 - RELATIVE_BORDER) * cameraSize.x - fireButton.getWidth()
                              , (1 - RELATIVE_BORDER) * cameraSize.y - fireButton.getHeight());
        moveLeftButton.setPosition( RELATIVE_BORDER * cameraSize.x
                                  , (1 - RELATIVE_BORDER) * cameraSize.y - moveLeftButton.getHeight());
        moveRightButton.setPosition( (RELATIVE_BORDER + RELATIVE_SPACE_BETWEEN_CONTROLS) * cameraSize.x
                                                + moveRightButton.getWidth()
                                   , (1 - RELATIVE_BORDER) * cameraSize.y - moveRightButton.getHeight());
    }

    public void setEventsToChildren(Events events) {
        for (GameButtonSprite button: buttons) {
            button.setEvents(events);
        }
    }
}
