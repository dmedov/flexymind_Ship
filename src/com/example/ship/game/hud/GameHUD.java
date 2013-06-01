package com.example.ship.game.hud;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.RootActivity;
import com.example.ship.commons.A;
import com.example.ship.game.*;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.modifier.*;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;
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

    private static final float RELATIVE_PAUSE_BUTTON_HEIGHT = 0.15f;
    private static final float RELATIVE_FIRE_BUTTON_HEIGHT = 0.25f;
    private static final float RELATIVE_SPACE_BETWEEN_CONTROLS = 0.01f;
    private static final float RELATIVE_SCREEN_BORDER = 0.02f;
    private static final float RELATIVE_CLOUD_HEIGHT = 0.15f;
    private static final float RELATIVE_SCORE_HEIGHT = 0.05f;
    private static final float RELATIVE_FIRE_SCREEN_BORDER = 0.12f;
    private static final float RELATIVE_HP_HEIGHT = 0.05f;
    private static final float BUTTON_ALPHA = 0.75f;
    private static final int FONT_ATLAS_SIDE = 256;
    
    private static final float TIME_PERIOD_CHECK_CONTROL = 0.1f;
    private static final float RELATIVE_CONTROL_HEIGHT = 0.5f;
    private static final float RELATIVE_CONTROL_MIDDLE_Y = 0.25f;
    private static final float RELATIVE_MEASURED_TEXT_MIDDLE = 0.7f;
    private static final float CONTROL_ALPHA = 0.5f;

    public static final int TEXT_LENGTH = 32;

    private final RootActivity activity;
    private final Engine engine;
    private PointF positionHitPoint;
    private Text scoreText;
    private Text levelInfoText;
    private Sprite scoreSprite;
    private Sprite remainingShipsSprite;
    private Text currentLevelText;
    private Text remainingShipsText;
    private Font statFont;
    private PointF cameraSize;
    private HealthIndicator healthIndicator;
    private ArrayList<GameButtonSprite> buttons;
    private HorizontalDigitalOnScreenControl rotateGunDigitalControl;
    private ProgressBar progressBar;
    private TouchableGameButtonSprite touchableGameButtonSprite;

    public GameHUD(RootActivity activity) {
        super();
        setOnAreaTouchTraversalFrontToBack();
        buttons = new ArrayList<GameButtonSprite>();
        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createButtons();
        createStats();
        createRotateGunDigitalControl();
        createProgressBar();
    }

    public void setEventsToChildren(Events events) {
        for (GameButtonSprite button: buttons) {
            button.setEvents(events);
        }
        this.touchableGameButtonSprite = events;
    }

    private void createButtons() {
        GameButtonSprite fireScreenButton;
        fireScreenButton = new GameButtonSprite( activity.getResourceManager()
                                                         .getLoadedTextureRegion(R.drawable.firebutton)
                                               , engine.getVertexBufferObjectManager()
                                               , R.string.GAME_FIRE_BUTTON);
        buttons.add(fireScreenButton);
        fireScreenButton.setVisible(false);
        fireScreenButton.setScaleCenter(0, 0);
        fireScreenButton.setScale( cameraSize.x
                                   / Math.min(fireScreenButton.getWidth(), fireScreenButton.getHeight()));

        GameButtonSprite pauseButton;
        pauseButton = new GameButtonSprite( activity.getResourceManager()
                                                    .getLoadedTextureRegion(R.drawable.pausebutton)
                                          , engine.getVertexBufferObjectManager()
                                          , R.string.GAME_PAUSE_BUTTON);
        buttons.add(pauseButton);
        pauseButton.setScale(cameraSize.y * RELATIVE_PAUSE_BUTTON_HEIGHT / pauseButton.getHeight());

        GameButtonSprite fireButton;
        fireButton = new GameButtonSprite( activity.getResourceManager()
                                                   .getLoadedTextureRegion(R.drawable.firebutton)
                                         , engine.getVertexBufferObjectManager()
                                         , R.string.GAME_FIRE_BUTTON);
        buttons.add(fireButton);
        fireButton.setScaleCenter(fireButton.getWidth(), fireButton.getHeight());
        fireButton.setScale(cameraSize.y * RELATIVE_FIRE_BUTTON_HEIGHT / fireButton.getHeight());

        for (GameButtonSprite button: buttons) {
            button.setAlpha(BUTTON_ALPHA);
            this.registerTouchArea(button);
            this.attachChild(button);
        }

        pauseButton.setPosition( RELATIVE_SCREEN_BORDER * cameraSize.x
                               , RELATIVE_SCREEN_BORDER * cameraSize.y);
        fireButton.setPosition( (1 - RELATIVE_FIRE_SCREEN_BORDER) * cameraSize.x - fireButton.getWidth()
                              , (1 - RELATIVE_SCREEN_BORDER) * cameraSize.y - fireButton.getHeight());
        fireScreenButton.setPosition(0, 0);
    }

    private void createRotateGunDigitalControl() {
        ITextureRegion rotateGunDigitalControlBaseTextureRegion =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.onscreen_control_base);
        ITextureRegion rotateGunDigitalControlKnobTextureRegion =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.onscreen_control_knob);

        final PointF BASE_TEXTURE_LEFT_BOTTOM =
                new PointF(0f , rotateGunDigitalControlBaseTextureRegion.getHeight());
        final float CONTROL_BASE_TEXTURE_HEIGHT = rotateGunDigitalControlBaseTextureRegion.getHeight();
        final PointF GUN_DIGITAL_CONTROL_COORDINATE =
                new PointF( 0f
                          , (1 - RELATIVE_SCREEN_BORDER) * (cameraSize.y - CONTROL_BASE_TEXTURE_HEIGHT));

        rotateGunDigitalControl =
                new HorizontalDigitalOnScreenControl( GUN_DIGITAL_CONTROL_COORDINATE.x
                                                    , GUN_DIGITAL_CONTROL_COORDINATE.y
                                                    , activity.getCamera()
                                                    , rotateGunDigitalControlBaseTextureRegion
                                                    , rotateGunDigitalControlKnobTextureRegion
                                                    , TIME_PERIOD_CHECK_CONTROL
                                                    , activity.getVertexBufferObjectManager()
                                                    , new BaseOnScreenControl.IOnScreenControlListener() {
            @Override
            public void onControlChange(BaseOnScreenControl baseOnScreenControl, float xShift, float yShift) {
                if          (xShift < 0) {
                    getGun().rotateLeft();
                } else if   (xShift > 0) {
                    getGun().rotateRight();
                } else {
                    getGun().stopRotate();
                }
            }
        });
        rotateGunDigitalControl.getControlBase().setAlpha(CONTROL_ALPHA);
        // Чтобы текстура не выходила за границы экрана при масштабировании
        rotateGunDigitalControl.getControlBase()
                .setScaleCenter(BASE_TEXTURE_LEFT_BOTTOM.x, BASE_TEXTURE_LEFT_BOTTOM.y);
        rotateGunDigitalControl.getControlBase()
                .setScale(cameraSize.y * RELATIVE_CONTROL_HEIGHT
                        / rotateGunDigitalControlBaseTextureRegion.getHeight());
        rotateGunDigitalControl.getControlKnob()
                .setScale(cameraSize.y * RELATIVE_CONTROL_HEIGHT
                        / rotateGunDigitalControlBaseTextureRegion.getHeight());
        // 13f измерено по текстуре, обеспечивает правильный отступ
        final float KNOB_BORDER = 13f / rotateGunDigitalControl.getControlBase().getWidth();
        final float EXTENT_SIDE = HorizontalDigitalOnScreenControl.STANDART_RELATIVE_EXTENT_SIDE
                - 0.5f * rotateGunDigitalControl.KNOB_SIZE_IN_PERCENT - KNOB_BORDER;
        rotateGunDigitalControl.setExtentSide(EXTENT_SIDE);
        rotateGunDigitalControl.setHeightLevel(RELATIVE_CONTROL_MIDDLE_Y);

        rotateGunDigitalControl.refreshControlKnobPosition();
        this.setChildScene(rotateGunDigitalControl);
    }

    private void createStats() {
        float fontSize = cameraSize.y * RELATIVE_SCORE_HEIGHT;
        float scoreTextureHeight =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.score).getHeight();
        float scoreScale = cameraSize.y * RELATIVE_SCORE_HEIGHT / scoreTextureHeight;
        float cloudTextureHeight =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.cloud).getHeight();
        float cloudScale = cameraSize.y * RELATIVE_CLOUD_HEIGHT / cloudTextureHeight;

        positionHitPoint = new PointF( (1 - RELATIVE_SCREEN_BORDER) * cameraSize.x
                                     , RELATIVE_SCREEN_BORDER * cameraSize.y);

        healthIndicator = new HealthIndicator(activity, this, positionHitPoint, cloudScale);

        statFont = FontFactory.create( activity.getEngine().getFontManager()
                                     , activity.getEngine().getTextureManager()
                                     , FONT_ATLAS_SIDE
                                     , FONT_ATLAS_SIDE
                                     , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                     , fontSize
                                     , true
                                     , Color.WHITE_ABGR_PACKED_INT);
        statFont.load();

        // создаем изначальные очки
        scoreText = new Text( positionHitPoint.x
                            , positionHitPoint.y
                            , statFont
                            , " 000000"
                            , TEXT_LENGTH
                            , activity.getEngine().getVertexBufferObjectManager());
        scoreText.setPosition( cameraSize.x * 0.5f - scoreText.getWidth() * 0.5f
                             , RELATIVE_SCREEN_BORDER * cameraSize.y);

        currentLevelText = new Text( 0
                                   , 0
                                   , statFont
                                   , ""
                                   , TEXT_LENGTH
                                   , activity.getEngine().getVertexBufferObjectManager());
        ITextureRegion shipsLeftToDestroyTexture =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.ships_left);

        remainingShipsSprite = new Sprite( 0
                                         , 0
                                         , shipsLeftToDestroyTexture
                                         , activity.getEngine().getVertexBufferObjectManager());
        remainingShipsText = new Text( 0
                                     , 0
                                     , statFont
                                     , ""
                                     , TEXT_LENGTH
                                     , activity.getEngine().getVertexBufferObjectManager());

        scoreSprite = new Sprite( 0
                                , 0
                                , activity.getResourceManager().getLoadedTextureRegion(R.drawable.score)
                                , activity.getVertexBufferObjectManager());
        scoreSprite.setScaleCenter(scoreSprite.getWidth(), scoreSprite.getHeight() / 2.0f);
        scoreSprite.setScale(scoreScale);
        scoreSprite.setPosition( scoreText.getX() - scoreSprite.getWidth()
                               , scoreText.getY() + (scoreText.getHeight()- scoreSprite.getHeight()) / 2.0f);

        this.attachChild(scoreSprite);
        this.attachChild(scoreText);
        this.attachChild(currentLevelText);
        this.attachChild(remainingShipsSprite);
        this.attachChild(remainingShipsText);
    }

    private void createProgressBar() {
        progressBar = new ProgressBar(activity, this);

        float positionX = (1 - RELATIVE_SCREEN_BORDER) * cameraSize.x - progressBar.getWidthProgressBar();
        // временно, потом будет ограничивается по облаку
        float positionY = cameraSize.y / 4.0f;

        progressBar.setPosition(new PointF(positionX, positionY));

    }

    public void updateProgressBar(float progress) {
        progressBar.setProgress(progress);
    }

    public void updateScore() {
        scoreText.setText(activity.getSceneSwitcher().getGameScene().getPlayer().getStringScore());
        scoreText.setPosition( cameraSize.x * 0.5f - scoreText.getWidth() * 0.5f
                             , RELATIVE_SCREEN_BORDER * cameraSize.y);
    }

    public void updateLevelInfo(String currentLevel, String shipsToDestroy) {
        currentLevelText.setText(currentLevel);
        currentLevelText.setPosition( cameraSize.x * 0.25f - currentLevelText.getWidth() * 0.5f
                                    , RELATIVE_SCREEN_BORDER * cameraSize.y);
        remainingShipsSprite.setScale(remainingShipsText.getHeight() / remainingShipsSprite.getHeight());
        remainingShipsSprite.setPosition( currentLevelText.getX()
                                        , currentLevelText.getY() + currentLevelText.getHeight()
                                          + 0.5f * remainingShipsSprite.getHeight());
        remainingShipsText.setText(shipsToDestroy);
        remainingShipsText.setPosition( remainingShipsSprite.getX() + remainingShipsSprite.getWidth()
                                        + 0.5f * remainingShipsSprite.getWidthScaled()
                                      , remainingShipsSprite.getY()  + 0.5f * remainingShipsSprite.getHeightScaled()
                                        - RELATIVE_MEASURED_TEXT_MIDDLE * remainingShipsText.getHeight());
    }

    public void updateHealthIndicators(int health) {
        healthIndicator.updateHealth(health);
    }

    public void showNewLevelMessage(int level, boolean bonus) {
        final float messageHoldTime = 1.0f;
        final float moveDuration = 3.0f;
        String message;
        if (bonus) {
            message = String.format( "%s %d(%s)"
                                   , A.a.getStringResource(R.string.NEW_LEVEL_MESSAGE)
                                   , level
                                   , A.a.getStringResource(R.string.BONUS_LEVEL));
        } else {
            message = String.format( "%s %d"
                                   , A.a.getStringResource(R.string.NEW_LEVEL_MESSAGE)
                                   , level);
        }

        Text newLevelText = new Text( 0
                                    , 0
                                    , statFont
                                    , message
                                    , TEXT_LENGTH
                                    , A.a.getEngine().getVertexBufferObjectManager());
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

        MoveModifier moveToBottomModifier =
                new MoveModifier( moveDuration
                                , cameraSize.x * 0.5f - shape.getWidth() * 0.5f
                                , cameraSize.x * 0.5f - shape.getWidth() * 0.5f
                                , cameraSize.y * 0.5f - shape.getHeight() * 0.5f
                                , cameraSize.y
                                , EaseExponentialIn.getInstance());

        AlphaModifier alphaDownModifier =
                new AlphaModifier(moveDuration, 0.75f, 0.0f, EaseExponentialIn.getInstance());
        AlphaModifier alphaUpModifier =
                new AlphaModifier(moveDuration, 0.0f, 0.75f, EaseExponentialOut.getInstance());

        ParallelEntityModifier parallelToBottomModifier =
                new ParallelEntityModifier(moveToBottomModifier, alphaDownModifier);
        ParallelEntityModifier parallelToCenterModifier =
                new ParallelEntityModifier(moveToCenterModifier, alphaUpModifier);

        DelayModifier delayModifier = new DelayModifier(messageHoldTime);

        SequenceEntityModifier sequenceEntityModifier =
                new SequenceEntityModifier( parallelToCenterModifier
                                          , delayModifier
                                          , parallelToBottomModifier);

        shape.registerEntityModifier(sequenceEntityModifier);
    }

    private Gun getGun(){
        return activity.getSceneSwitcher().getGameScene().getGun();
    }
}
