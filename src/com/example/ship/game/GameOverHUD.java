package com.example.ship.game;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.Events;
import com.example.ship.R;
import com.example.ship.RootActivity;
import com.example.ship.atlas.ResourceManager;
import com.example.ship.menu.MenuButtonSprite;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitriy
 * Date: 16.05.13
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public class GameOverHUD extends HUD {

    private static final int FONT_ATLAS_SIDE = 256;
    private static final float RELATIVE_FONT_SIZE = 0.05f;
    private static final float RELATIVE_GAME_OVER_BUTTON = 0.15f;
    private static final float RELATIVE_SPACE_BETWEEN_ELEMENTS_HEIGHT = 0.05f;
    private static final float BACKGROUND_ALPHA = 0.75f;

    private final PointF cameraSize;

    private ResourceManager resourceManager;
    private RootActivity activity;
    private Font gameOverFont;
    private Text gameOverText;
    private Text scoreText;
    private float yPositionOfElement;
    private MenuButtonSprite restartButtonSprite;
    private MenuButtonSprite exitButtonSprite;
    private Rectangle background;

    public GameOverHUD(RootActivity activity) {
        super();

        this.activity = activity;
        this.resourceManager = activity.getResourceManager();
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createGameOverBackground();
        createGameOverFont();
        createGameOverHUDLabels();
        createGameOverButtons();
    }

    private void createGameOverFont() {
        gameOverFont = FontFactory.create( activity.getEngine().getFontManager()
                                         , activity.getEngine().getTextureManager()
                                         , FONT_ATLAS_SIDE
                                         , FONT_ATLAS_SIDE
                                         , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                         , cameraSize.y * RELATIVE_FONT_SIZE
                                         , true
                                         , Color.WHITE_ABGR_PACKED_INT);
        gameOverFont.load();
    }

    private void createGameOverBackground() {
        background = new Rectangle( 0
                                  , 0
                                  , cameraSize.x
                                  , cameraSize.y
                                  , activity.getEngine().getVertexBufferObjectManager());

        Color backgroundColor = new Color(0.0f, 0.0f, 0.0f);

        background.setColor(backgroundColor);
        background.setAlpha(BACKGROUND_ALPHA);

        this.attachChild(background);
    }

    private void createGameOverHUDLabels() {
        gameOverText = new Text( 0
                               , 0
                               , gameOverFont
                               , "Game Over"
                               , activity.getEngine().getVertexBufferObjectManager());
        gameOverText.setPosition( cameraSize.x * 0.5f - gameOverText.getWidth() * 0.5f
                                , cameraSize.y * 0.25f);

        scoreText = new Text( 0
                            , 0
                            , gameOverFont
                            , activity.getResources().getString(R.string.SCORE) + ": 000000"
                            , activity.getEngine().getVertexBufferObjectManager());
        scoreText.setPosition( cameraSize.x * 0.5f - scoreText.getWidth() * 0.5f
                             , gameOverText.getY() + gameOverText.getHeight()
                               + cameraSize.y * RELATIVE_SPACE_BETWEEN_ELEMENTS_HEIGHT);

        this.attachChild(gameOverText);
        this.attachChild(scoreText);
    }

    private void createGameOverButtons() {

        restartButtonSprite =
                new MenuButtonSprite( resourceManager.getLoadedTextureRegion(R.drawable.menubutton)
                                    , activity.getEngine().getVertexBufferObjectManager()
                                    , R.string.GAME_OVER_RESTART_BUTTON
                                    , getStringResource(R.string.GAME_OVER_RESTART_BUTTON_LABEL)
                                    , gameOverFont);

        restartButtonSprite.setScale(cameraSize.y * RELATIVE_GAME_OVER_BUTTON / restartButtonSprite.getHeight());
        restartButtonSprite.setPosition( cameraSize.x * 0.5f - restartButtonSprite.getWidth() * 0.5f
                                       , scoreText.getY() + scoreText.getHeight()
                                         + cameraSize.y * RELATIVE_SPACE_BETWEEN_ELEMENTS_HEIGHT);

        yPositionOfElement += restartButtonSprite.getHeight();

        exitButtonSprite =
                new MenuButtonSprite( resourceManager.getLoadedTextureRegion(R.drawable.menubutton)
                                    , activity.getEngine().getVertexBufferObjectManager()
                                    , R.string.GAME_OVER_EXIT_BUTTON
                                    , getStringResource(R.string.GAME_OVER_EXIT_BUTTON_LABEL)
                                    , gameOverFont);

        exitButtonSprite.setScale(cameraSize.y * RELATIVE_GAME_OVER_BUTTON / exitButtonSprite.getHeight());
        exitButtonSprite.setPosition( cameraSize.x * 0.5f - exitButtonSprite.getWidth() * 0.5f
                                    , restartButtonSprite.getY() + restartButtonSprite.getHeight()
                                      + cameraSize.y * RELATIVE_SPACE_BETWEEN_ELEMENTS_HEIGHT);

        this.registerTouchArea(restartButtonSprite);
        this.registerTouchArea(exitButtonSprite);

        this.attachChild(restartButtonSprite);
        this.attachChild(exitButtonSprite);
    }

    public void setScoreToGameOverHUD(String scoreString) {
        scoreText.setText(activity.getResources().getString(R.string.SCORE) + scoreString);
    }

    private String getStringResource(int id) {
        return activity.getResources().getString(id);
    }

    public void setEventsToChildren(Events events) {
        restartButtonSprite.setEvents(events);
        exitButtonSprite.setEvents(events);
    }
}
