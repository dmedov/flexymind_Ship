package com.example.ship.menu;

import android.graphics.PointF;
import android.graphics.Typeface;
import com.example.ship.R;
import com.example.ship.commons.A;
import com.example.ship.game.highscores.ScoreRecord;
import com.example.ship.game.hud.GameButtonSprite;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;

import java.util.ArrayList;

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
    private static final int FONT_ATLAS_SIDE = 256;
    private PointF cameraSize;
    private Text label;
    private Text scoreListText;

    public HighScoresHUD() {
        super();

        cameraSize = new PointF( A.a.getCamera().getWidthRaw()
                               , A.a.getCamera().getHeightRaw());

        createBackground();
        createTexts();
        createButtons();
    }

    public void updateScores() {
        String scoresString = "";
        ArrayList<ScoreRecord> scoreRecords = A.a.getHighScoresManager().getHighScores();
        if (scoreRecords.size() == 0) {
            scoresString = A.a.getStringResource(R.string.NO_RECORDS);
        } else {
            for (int i = 0; i < scoreRecords.size(); i++) {
                scoresString += String.format( "%d.\t%d\t%s\n"
                                             , i + 1
                                             , scoreRecords.get(i).getScore()
                                             , scoreRecords.get(i).getPlayerName());
            }
        }

        scoreListText.setText(scoresString);

        scoreListText.setPosition( cameraSize.x * 0.5f - scoreListText.getWidth() * 0.5f
                                 , label.getY() + label.getHeight() + cameraSize.y * 0.05f);
    }

    private void createTexts() {
        Font labelFont = FontFactory.create( A.a.getFontManager()
                                           , A.a.getTextureManager()
                                           , FONT_ATLAS_SIDE
                                           , FONT_ATLAS_SIDE
                                           , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                           , cameraSize.y * 0.08f
                                           , true
                                           , Color.WHITE_ABGR_PACKED_INT);
        labelFont.load();

        label = new Text( 0
                        , 0
                        , labelFont
                        , A.a.getStringResource(R.string.MENU_HS_BUTTON_LABEL)
                        , A.a.getVertexBufferObjectManager());
        label.setPosition( cameraSize.x * 0.5f - label.getWidth() * 0.5f
                         , cameraSize.y * 0.05f);

        this.attachChild(label);

        Font scoreListFont = FontFactory.create( A.a.getFontManager()
                                                , A.a.getTextureManager()
                                                , FONT_ATLAS_SIDE
                                                , FONT_ATLAS_SIDE
                                                , Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                                , cameraSize.y * 0.04f
                                                , true
                                                , Color.WHITE_ABGR_PACKED_INT);
        scoreListFont.load();

        scoreListText = new Text(0, 0, scoreListFont, "", 512, A.a.getVertexBufferObjectManager());
        this.attachChild(scoreListText);

        updateScores();
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
