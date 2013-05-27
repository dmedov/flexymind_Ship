package com.example.ship.game.hud;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.commons.A;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 27.05.13
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class HighScoresHUD extends HUD {

    private static final float BACKGROUND_ALPHA = 0.75f;
    private static final float RELATIVE_BUTTON_HEIGHT = 0.15f;
    private static final float RELATIVE_BORDER = 0.02f;
    private PointF cameraSize;

    public HighScoresHUD() {
        super();

        cameraSize = new PointF( A.a.getCamera().getWidthRaw()
                               , A.a.getCamera().getHeightRaw());

        createBackground();
        createButtons();
    }

    private void createButtons() {

        GameButtonSprite backButton;
        backButton = new GameButtonSprite( A.a.getResourceManager()
                                                .getLoadedTextureRegion(R.drawable.backbutton)
                                         , A.e.getVertexBufferObjectManager()
                                         , R.string.HIGHSCORES_BACK_BUTTON);

        backButton.setScale(cameraSize.y * RELATIVE_BUTTON_HEIGHT / backButton.getHeight());
        backButton.setPosition( (1 - RELATIVE_BORDER) * cameraSize.x - backButton.getWidth()
                              , (1 - RELATIVE_BORDER) * cameraSize.y - backButton.getHeight());

        backButton.setEvents(A.a.getEvents());

        this.registerTouchArea(backButton);
        this.attachChild(backButton);
    }

    private void createBackground() {
        Rectangle background = new Rectangle( 0
                                            , 0
                                            , cameraSize.x
                                            , cameraSize.y
                                            , A.e.getVertexBufferObjectManager());

        Color backgroundColor = new Color(0.0f, 0.0f, 0.0f);

        background.setColor(backgroundColor);
        background.setAlpha(BACKGROUND_ALPHA);

        this.attachChild(background);
    }
}
