package com.example.ship.game;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
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
    private static final float RELATIVE_TOP_BORDER = 0.05f;
    private static final float RELATIVE_BETWEEN_ELEMENTS = 0.22f;
    private final PointF cameraSize;
    private SceletonActivity activity;
    private Font gameOverFont;
    private Sprite gameOverBackgound;
    private Text gameOverText;
    private Text scoreText;

    public GameOverHUD(SceletonActivity activity){
        super();

        this.activity = activity;
        cameraSize = new PointF( this.activity.getCamera().getWidthRaw()
                               , this.activity.getCamera().getHeightRaw());

        createGameOverBackground();
        createGameOverFont();
        createGameOverHUDLabels();
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
        this.attachChild(gameOverBackgound);
    }

    private void createGameOverHUDLabels() {
        gameOverText = new Text( 0
                                    , 0
                                    , gameOverFont
                                    , "Game Over"
                                    , activity.getEngine().getVertexBufferObjectManager());
        gameOverText.setPosition( gameOverBackgound.getWidth() / 2 - gameOverText.getWidth() / 2
                                , gameOverBackgound.getHeight() * RELATIVE_TOP_BORDER);

        scoreText = new Text( 0
                                 , 0
                                 , gameOverFont
                                 , activity.getResources().getString(R.string.SCORE) + ": 000000"
                                 , activity.getEngine().getVertexBufferObjectManager());
        scoreText.setPosition( gameOverBackgound.getWidth() / 2 - scoreText.getWidth() / 2
                             , gameOverBackgound.getHeight() * RELATIVE_BETWEEN_ELEMENTS + gameOverText.getY());

        gameOverBackgound.attachChild(gameOverText);
        gameOverBackgound.attachChild(scoreText);
    }

    public void setScoreToGameOverHUD(String scoreString) {
        scoreText.setText(scoreString);
    }
}
