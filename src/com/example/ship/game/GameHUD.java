package com.example.ship.game;

import android.graphics.PointF;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 03.05.13
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class GameHUD extends HUD {

    private static final float RELATIVE_BUTTON_HEIGHT = 0.15f;
    private static final float RELATIVE_SPACE_BETWEEN_CONTROLS = 0.01f;
    private static final float RELATIVE_SCREEN_BORDER = 0.02f;
    private static final float BUTTON_ALPHA = 0.75f;
    private final SceletonActivity activity;
    private final Engine engine;
    private       PointF cameraSize;
    private       ArrayList<GameButtonSprite> buttons;
    private       ArrayList<HitPoint> healths;

    public GameHUD(SceletonActivity activity) {
        super();
        setOnAreaTouchTraversalFrontToBack();
        buttons = new ArrayList<GameButtonSprite>();
        healths = new ArrayList<HitPoint>();
        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createBorderButton();
        createButtons();
        createHealth();
    }

    private void createButtons() {
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
            if (button.getId() == R.string.GAME_BORDER_BUTTON) {
                continue;
            }
            button.setAlpha(BUTTON_ALPHA);
            button.setScale(cameraSize.y * RELATIVE_BUTTON_HEIGHT / fireButton.getHeight());
            this.registerTouchArea(button);
            this.attachChild(button);
        }

        pauseButton.setPosition( RELATIVE_SCREEN_BORDER * cameraSize.x
                               , RELATIVE_SCREEN_BORDER * cameraSize.y);
        fireButton.setPosition( (1 - RELATIVE_SCREEN_BORDER) * cameraSize.x - fireButton.getWidth()
                              , (1 - RELATIVE_SCREEN_BORDER) * cameraSize.y - fireButton.getHeight());
        moveLeftButton.setPosition( RELATIVE_SCREEN_BORDER * cameraSize.x
                                  , (1 - RELATIVE_SCREEN_BORDER) * cameraSize.y - moveLeftButton.getHeight());
        moveRightButton.setPosition( (RELATIVE_SCREEN_BORDER + RELATIVE_SPACE_BETWEEN_CONTROLS) * cameraSize.x
                                                + moveRightButton.getWidth()
                                   , (1 - RELATIVE_SCREEN_BORDER) * cameraSize.y - moveRightButton.getHeight());
    }

    // this method creates special invisible big button, under left and right controls button
    // to detect situation, while touch move out from controls button, to stop gun rotation
    private void createBorderButton() {
        final float scale = 5.0f;
        GameButtonSprite borderButton;
        borderButton = new GameButtonSprite( activity.getResourceManager()
                                                     .getLoadedTextureRegion(R.drawable.pausebutton)
                                           , engine.getVertexBufferObjectManager()
                                           , R.string.GAME_BORDER_BUTTON);

        buttons.add(borderButton);
        borderButton.setVisible(false);
        borderButton.setScale(scale);

        this.registerTouchArea(borderButton);
        this.attachChild(borderButton);
        borderButton.setPosition( (RELATIVE_SCREEN_BORDER + RELATIVE_SPACE_BETWEEN_CONTROLS) * cameraSize.x
                                , (1 - RELATIVE_SCREEN_BORDER) * cameraSize.y - borderButton.getHeight());
    }

    public void setEventsToChildren(Events events) {
        for (GameButtonSprite button: buttons) {
            button.setEvents(events);
        }
    }

    private void createHealth() {
        float widthHealthTexture = activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth).getWidth();
        PointF positionHitPoint = new PointF( (1 - RELATIVE_SCREEN_BORDER) * cameraSize.x - widthHealthTexture
                                         , RELATIVE_SCREEN_BORDER * cameraSize.y);
        for (int i = 0; i < Player.FULL_HP; i++) {
            HitPoint hitPoint = new HitPoint( activity
                                            , this
                                            , positionHitPoint);
            healths.add(hitPoint);
            positionHitPoint.x -= RELATIVE_SPACE_BETWEEN_CONTROLS * cameraSize.x + widthHealthTexture;
        }
    }

    public void reduceHealth(int health){
        healths.get(health).switchHitPoint();
    }

    public void addHealth(int health){
        healths.get(health).switchHitPoint();
    }
}
