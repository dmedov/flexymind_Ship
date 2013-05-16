package com.example.ship.game;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import com.example.ship.atlas.ResourceManager;
import com.example.ship.menu.MenuButtonSprite;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitriy
 * Date: 16.05.13
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public class GameOverHUD extends HUD {
    private static final int FONT_ATLAS_SIDE = 512;
    private static final float RELATIVE_GAME_OVER_BACKGROUND = 0.7f;
    private static final float RELATIVE_GAME_OVER_FONT = 0.13f;
    private static final float RELATIVE_GAME_OVER_BUTTON = 0.21f;
    private static final float RELATIVE_TOP_BORDER = 0.05f;
    private static final float RELATIVE_BETWEEN_ELEMENTS = 0.001f;
    private final PointF cameraSize;
    private ResourceManager resourceManager;
    private SceletonActivity activity;
    private Font gameOverFont;
    private Sprite gameOverBackgound;
    private Text gameOverText;
    private Text scoreText;
    private float spaceBetweenElements;
    private float yPositionOfElement;

    public GameOverHUD(SceletonActivity activity){
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
                                         , gameOverBackgound.getHeight() * RELATIVE_GAME_OVER_FONT
                                         , true
                                         , Color.WHITE_ABGR_PACKED_INT);
        gameOverFont.load();
    }

    private void createGameOverBackground() {
        ITextureRegion gameOverTexture =
                activity.getResourceManager().getLoadedTextureRegion(R.drawable.gameoverbackground);
        gameOverBackgound = new Sprite( 0
                                      , 0
                                      , gameOverTexture
                                      , activity.getEngine().getVertexBufferObjectManager());

        gameOverBackgound.setPosition( cameraSize.x / 2 - gameOverBackgound.getWidth() / 2
                                     , cameraSize.y / 2 - gameOverBackgound.getHeight() / 2);
        gameOverBackgound.setScale(cameraSize.y * RELATIVE_GAME_OVER_BACKGROUND / gameOverBackgound.getHeight());

        spaceBetweenElements = gameOverBackgound.getHeight() * RELATIVE_BETWEEN_ELEMENTS;
        yPositionOfElement = gameOverBackgound.getHeight() * RELATIVE_TOP_BORDER;

        this.attachChild(gameOverBackgound);
    }

    private void createGameOverHUDLabels() {
        gameOverText = new Text( 0
                               , 0
                               , gameOverFont
                               , "Game Over"
                               , activity.getEngine().getVertexBufferObjectManager());
        gameOverText.setPosition( gameOverBackgound.getWidth() / 2 - gameOverText.getWidth() / 2
                                , yPositionOfElement);

        yPositionOfElement += gameOverText.getHeight();

        scoreText = new Text( 0
                            , 0
                            , gameOverFont
                            , activity.getResources().getString(R.string.SCORE) + ": 000000"
                            , activity.getEngine().getVertexBufferObjectManager());
        scoreText.setPosition( gameOverBackgound.getWidth() / 2 - scoreText.getWidth() / 2
                             , yPositionOfElement);

        yPositionOfElement += scoreText.getHeight();

        gameOverBackgound.attachChild(gameOverText);
        gameOverBackgound.attachChild(scoreText);
    }

    private void createGameOverButtons() {
        MenuButtonSprite menuButtonSprite =
                new MenuButtonSprite( resourceManager.getLoadedTextureRegion(R.drawable.menubutton)
                                    , activity.getEngine().getVertexBufferObjectManager()
                                    , R.string.GAME_OVER_MENU_BUTTON
                                    , getStringResource(R.string.GAME_OVER_MENU_BUTTON_LABEL)
                                    , gameOverFont);

        menuButtonSprite.setScale(gameOverBackgound.getHeight() * RELATIVE_GAME_OVER_BUTTON / menuButtonSprite.getHeight());
        menuButtonSprite.setPosition( gameOverBackgound.getWidth() / 2 - scoreText.getWidth() / 2
                                    , yPositionOfElement);

        yPositionOfElement += menuButtonSprite.getHeight();

        MenuButtonSprite exitButtonSprite =
                new MenuButtonSprite( resourceManager.getLoadedTextureRegion(R.drawable.menubutton)
                                    , activity.getEngine().getVertexBufferObjectManager()
                                    , R.string.GAME_OVER_EXIT_BUTTON
                                    , getStringResource(R.string.GAME_OVER_EXIT_BUTTON_LABEL)
                                    , gameOverFont);

        exitButtonSprite.setScale(gameOverBackgound.getHeight() * RELATIVE_GAME_OVER_BUTTON / exitButtonSprite.getHeight());
        exitButtonSprite.setPosition( gameOverBackgound.getWidth() / 2 - scoreText.getWidth() / 2
                                    , yPositionOfElement);

        this.registerTouchArea(menuButtonSprite);
        this.registerTouchArea(exitButtonSprite);
        gameOverBackgound.attachChild(menuButtonSprite);
        gameOverBackgound.attachChild(exitButtonSprite);
        menuButtonSprite.setEvents(activity.getEvents());
        exitButtonSprite.setEvents(activity.getEvents());
    }

    public void setScoreToGameOverHUD(String scoreString) {
        scoreText.setText(scoreString);
    }

    private String getStringResource(int id) {
        return activity.getResources().getString(id);
    }
}
