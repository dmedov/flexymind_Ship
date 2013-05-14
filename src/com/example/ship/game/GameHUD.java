package com.example.ship.game;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;

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
    private static final float RELATIVE_HP_HEIGHT = 0.05f;
    private static final float BUTTON_ALPHA = 0.75f;
    private static final int FONT_ATLAS_SIDE = 256;
    private static final int NUMBER_ZERO = 6;
    private final SceletonActivity activity;
    private final Engine engine;
    private PointF positionHitPoint;
    private Text scoreText;
    private Font scoreFont;
    private       PointF cameraSize;
    private       ArrayList<GameButtonSprite> buttons;
    private       ArrayList<HitPoint> hitPoints;

    public GameHUD(SceletonActivity activity) {
        super();
        setOnAreaTouchTraversalFrontToBack();
        buttons = new ArrayList<GameButtonSprite>();
        hitPoints = new ArrayList<HitPoint>();
        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createBorderButton();
        createButtons();
        createStats();
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

    private void createStats() {
        float healthTextureHeight =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth).getHeight();
        float scale = cameraSize.y * RELATIVE_HP_HEIGHT / healthTextureHeight;
        float healthTextureWidth =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.onhealth).getWidth() * scale;

        positionHitPoint = new PointF( (1 - RELATIVE_SCREEN_BORDER) * cameraSize.x - healthTextureWidth
                                     , RELATIVE_SCREEN_BORDER * cameraSize.y * scale);

        for (int i = 0; i < Player.FULL_HP; i++) {
            HitPoint hitPoint = new HitPoint( activity
                                            , this
                                            , positionHitPoint
                                            , scale);
            hitPoints.add(hitPoint);
            positionHitPoint.x -= RELATIVE_SPACE_BETWEEN_CONTROLS * cameraSize.x + healthTextureWidth;
        }

        scoreFont = FontFactory.create( activity.getEngine().getFontManager()
                                      , activity.getEngine().getTextureManager()
                                      , FONT_ATLAS_SIDE
                                      , FONT_ATLAS_SIDE
                                      , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                      , healthTextureHeight * scale
                                      , true
                                      , Color.WHITE_ABGR_PACKED_INT);
        scoreFont.load();

        // создаем изначальные очки
        scoreText = new Text( positionHitPoint.x
                            , positionHitPoint.y
                            , scoreFont
                            , activity.getResources().getString(R.string.SCORE) + ": 000000"
                            , activity.getEngine().getVertexBufferObjectManager());
        scoreText.setPosition( positionHitPoint.x - scoreText.getWidth()
                             , RELATIVE_SCREEN_BORDER * cameraSize.y);

        this.attachChild(scoreText);
    }

    public void updateScore(int score) {
        scoreText.detachSelf();
        scoreText = new Text( positionHitPoint.x
                            , positionHitPoint.y
                            , scoreFont
                            , getStringScore(score)
                            , activity.getEngine().getVertexBufferObjectManager());
        scoreText.setPosition( positionHitPoint.x - scoreText.getWidth()
                             , RELATIVE_SCREEN_BORDER * cameraSize.y);
        this.attachChild(scoreText);
    }

    public void updateHealth(int health) {
        hitPoints.get(health).switchHitPoint();
    }

    private String getStringScore(int score) {
        // длина числа текущих Score
        int digitNumber = ("" + score).length();
        String scoreString  = activity.getResources().getString(R.string.SCORE) + ": ";
        // дополняем наше Score нулями в начале
        for (int i = 0; i < NUMBER_ZERO - digitNumber; i++) {
            scoreString  += "0";
        }
        scoreString  += score;
        return scoreString;
    }
}
