package com.example.ship.game;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
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
    private static final float TIME_PERIOD_CHECK_CONTROL = 0.1f;
    private static final float RELATIVE_CONTROL_HEIGHT = 0.2f;

    private final SceletonActivity activity;
    private final Engine engine;
    private PointF positionHitPoint;
    private Text scoreText;
    private Font scoreFont;
    private PointF cameraSize;
    private ArrayList<GameButtonSprite> buttons;
    private ArrayList<HealthIndicator> healthIndicators;
    private HorizontalDigitalOnScreenControl rotateGunDigitalControl;

    public GameHUD(SceletonActivity activity) {
        super();
        setOnAreaTouchTraversalFrontToBack();
        buttons = new ArrayList<GameButtonSprite>();
        healthIndicators = new ArrayList<HealthIndicator>();
        this.activity = activity;
        engine = this.activity.getEngine();
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createButtons();
        createStats();
        createRotateGunDigitalControl();
    }

    public void setEventsToChildren(Events events) {
        for (GameButtonSprite button: buttons) {
            button.setEvents(events);
        }
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
    }

    private void createRotateGunDigitalControl() {
        ITextureRegion rotateGunDigitalControlBaseTextureRegion = activity.getResourceManager()
                                                       .getLoadedTextureRegion( R.drawable.onscreen_control_base );
        ITextureRegion rotateGunDigitalControlKnobTextureRegion = activity.getResourceManager()
                                                       .getLoadedTextureRegion( R.drawable.onscreen_control_knob );



        final PointF BASE_TEXTURE_LEFT_BOTTOM =
                new PointF( 0f , rotateGunDigitalControlBaseTextureRegion.getHeight() );
        final float CONTROL_BASE_TEXTURE_HEIGHT = rotateGunDigitalControlBaseTextureRegion.getHeight();
        final PointF GUN_DIGITAL_CONTROL_COORDINATE =
                new PointF( RELATIVE_SCREEN_BORDER * cameraSize.x
                          , ( 1 - RELATIVE_SCREEN_BORDER ) * ( cameraSize.y - CONTROL_BASE_TEXTURE_HEIGHT ) );

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
            public void onControlChange( BaseOnScreenControl baseOnScreenControl, float xShift, float yShift ) {
                if          ( xShift < 0 ) {
                    getGun().rotateLeft();
                } else if   ( xShift > 0 ) {
                    getGun().rotateRight();
                } else {
                    getGun().stopRotate();
                }
            }
        });
        rotateGunDigitalControl.getControlBase().setAlpha(BUTTON_ALPHA);
        // Чтобы текстура не выходила за границы экрана при масштабировании
        rotateGunDigitalControl.getControlBase()
                .setScaleCenter( BASE_TEXTURE_LEFT_BOTTOM.x, BASE_TEXTURE_LEFT_BOTTOM.y );
        rotateGunDigitalControl.getControlBase()
                .setScale( cameraSize.y * RELATIVE_CONTROL_HEIGHT
                           / rotateGunDigitalControlBaseTextureRegion.getHeight() );
        rotateGunDigitalControl.getControlKnob()
                .setScale( cameraSize.y * RELATIVE_CONTROL_HEIGHT
                           / rotateGunDigitalControlBaseTextureRegion.getHeight() );
        // 36f измерено по текстуре, обеспечивает правильный отступ
        final float KNOB_BORDER = 36f / rotateGunDigitalControl.getControlBase().getWidth();
        final float EXTENT_SIDE = HorizontalDigitalOnScreenControl.STANDART_EXTENT_SIDE
                                  - 0.5f * rotateGunDigitalControl.KNOB_SIZE_IN_PERCENT - KNOB_BORDER;
        rotateGunDigitalControl.setExtentSide( EXTENT_SIDE );

        rotateGunDigitalControl.refreshControlKnobPosition();
        this.setChildScene( rotateGunDigitalControl );
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

    public void updateScore() {
        scoreText.detachSelf();
        scoreText.setText(activity.getSceneSwitcher().getGameScene().getPlayer().getStringScore());
        scoreText.setPosition( positionHitPoint.x - scoreText.getWidth()
                             , RELATIVE_SCREEN_BORDER * cameraSize.y);
        this.attachChild(scoreText);
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

    private Gun getGun(){
        return activity.getSceneSwitcher().getGameScene().getGun();
    }
}
