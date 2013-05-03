package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;

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
    private static final float RELATIVE_BUTTON_HEIGHT = 0.1f;
    private static final float RELATIVE_BORDER = 0.05f;
    private       PointF cameraSize;

    public GameHUD(SceletonActivity activity) {
        super();

        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidth() * activity.getCamera().getZoomFactor()
                               , this.activity.getCamera().getHeight() * activity.getCamera().getZoomFactor());

        createButtons();

    }

    private void createButtons() {
        GameButtonSprite pauseButton;
        pauseButton = new GameButtonSprite( activity.getResourceManager().getLoadedTextureRegion(R.drawable.menubutton)
                                          , engine.getVertexBufferObjectManager()
                                          , R.string.GAME_PAUSE_BUTTON);

        pauseButton.setScale(cameraSize.y * RELATIVE_BUTTON_HEIGHT / pauseButton.getHeight());
        pauseButton.setPosition( RELATIVE_BORDER * cameraSize.x
                               , RELATIVE_BORDER * cameraSize.y);

        this.registerTouchArea(pauseButton);
        this.attachChild(pauseButton);
    }
}
