package com.example.ship.game;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseExponentialOut;

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
    public static final int TEXT_LENGHT = 32;
    private final SceletonActivity activity;
    private final Engine engine;
    private PointF positionHitPoint;
    private Text scoreText;
    private Text levelInfoText;
    private Font statFont;
    private PointF cameraSize;
    private ArrayList<GameButtonSprite> buttons;
    private ArrayList<HealthIndicator> healthIndicators;

    public GameHUD(SceletonActivity activity) {
        super();
        setOnAreaTouchTraversalFrontToBack();
        buttons = new ArrayList<GameButtonSprite>();
        healthIndicators = new ArrayList<HealthIndicator>();
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
        fireButton.setPosition((1 - RELATIVE_SCREEN_BORDER) * cameraSize.x - fireButton.getWidth()
                , (1 - RELATIVE_SCREEN_BORDER) * cameraSize.y - fireButton.getHeight());
        moveLeftButton.setPosition(RELATIVE_SCREEN_BORDER * cameraSize.x
                , (1 - RELATIVE_SCREEN_BORDER) * cameraSize.y - moveLeftButton.getHeight());
        moveRightButton.setPosition((RELATIVE_SCREEN_BORDER + RELATIVE_SPACE_BETWEEN_CONTROLS) * cameraSize.x
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
            HealthIndicator healthIndicator = new HealthIndicator( activity
                                                                 , this
                                                                 , positionHitPoint
                                                                 , scale);
            healthIndicators.add(healthIndicator);
            positionHitPoint.x -= RELATIVE_SPACE_BETWEEN_CONTROLS * cameraSize.x + healthTextureWidth;
        }

        statFont = FontFactory.create( activity.getEngine().getFontManager()
                                      , activity.getEngine().getTextureManager()
                                      , FONT_ATLAS_SIDE
                                      , FONT_ATLAS_SIDE
                                      , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                      , healthTextureHeight * scale
                                      , true
                                      , Color.WHITE_ABGR_PACKED_INT);
        statFont.load();

        // создаем изначальные очки
        scoreText = new Text( positionHitPoint.x
                            , positionHitPoint.y
                            , statFont
                            , activity.getResources().getString(R.string.SCORE) + ": 000000"
                            , TEXT_LENGHT
                            , activity.getEngine().getVertexBufferObjectManager());
        scoreText.setPosition(cameraSize.x * 0.5f - scoreText.getWidth() * 0.5f
                , RELATIVE_SCREEN_BORDER * cameraSize.y);

        levelInfoText = new Text( 0
                                , 0
                                , statFont
                                , ""
                                , TEXT_LENGHT
                                , activity.getEngine().getVertexBufferObjectManager());

        this.attachChild(scoreText);
        this.attachChild(levelInfoText);
    }

    public void updateScore() {
        scoreText.setText(activity.getSceneSwitcher().getGameScene().getPlayer().getStringScore());
        scoreText.setPosition( positionHitPoint.x - scoreText.getWidth()
                             , RELATIVE_SCREEN_BORDER * cameraSize.y);
    }

    public void updateLevelInfo(String text) {
        levelInfoText.setText(text);
        levelInfoText.setPosition( cameraSize.x * 0.25f - levelInfoText.getWidth() * 0.5f
                                 , RELATIVE_SCREEN_BORDER * cameraSize.y);
    }

    public void updateHealthIndicators(int health) {
        for (int i = 0; i < healthIndicators.size(); i++) {
            if (i < health) {
                healthIndicators.get(i).setState(HealthIndicator.ALIVE_STATE);
            } else {
                healthIndicators.get(i).setState(HealthIndicator.DEAD_STATE);
            }
        }
    }

    public void showNewLevelMessage(int level) {
        final float messageHoldTime = 1.0f;
        final float moveDuration = 3.0f;

        Text newLevelText = new Text( 0
                                    , 0
                                    , statFont
                                    , activity.getStringResource(R.string.NEW_LEVEL_MESSAGE) + " " + level
                                    , TEXT_LENGHT
                                    , activity.getEngine().getVertexBufferObjectManager());
        this.attachChild(newLevelText);

        createMessageEntityModifiers(messageHoldTime, moveDuration, newLevelText);
    }

    private void createMessageEntityModifiers( final float messageHoldTime
                                             , float moveDuration
                                             , final RectangularShape shape) {

        MoveModifier moveToCenterModifier =
                new MoveModifier( moveDuration
                                , cameraSize.x * 0.5f - shape.getWidth() * 0.5f
                                , cameraSize.x * 0.5f - shape.getWidth() * 0.5f
                                , - shape.getHeight()
                                , cameraSize.y * 0.5f - shape.getHeight() * 0.5f
                                , EaseExponentialOut.getInstance());

        AlphaModifier alphaUpModifier =
                new AlphaModifier(moveDuration, 0.0f, 0.75f, EaseExponentialOut.getInstance());
        final ParallelEntityModifier parallelToCenterModifier =
                new ParallelEntityModifier(moveToCenterModifier, alphaUpModifier);

        MoveModifier moveToBottomModifier =
                new MoveModifier( moveDuration
                                , cameraSize.x * 0.5f - shape.getWidth() * 0.5f
                                , cameraSize.x * 0.5f - shape.getWidth() * 0.5f
                                , cameraSize.y * 0.5f - shape.getHeight() * 0.5f
                                , cameraSize.y
                                , EaseExponentialIn.getInstance());

        AlphaModifier alphaDownModifier =
                new AlphaModifier(moveDuration, 0.75f, 0.0f, EaseExponentialIn.getInstance());
        final ParallelEntityModifier parallelToBottomModifier =
                new ParallelEntityModifier(moveToBottomModifier, alphaDownModifier);

        parallelToCenterModifier.addModifierListener(new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> iEntityIModifier, IEntity iEntity) {
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> iEntityIModifier, IEntity iEntity) {

                TimerHandler timerHandler = new TimerHandler(messageHoldTime, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler timerHandler) {
                        shape.registerEntityModifier(parallelToBottomModifier);
                    }
                });
                activity.getEngine().registerUpdateHandler(timerHandler);
            }
        });
        shape.registerEntityModifier(parallelToCenterModifier);
    }
}
