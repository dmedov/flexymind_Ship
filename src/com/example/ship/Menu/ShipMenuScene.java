package com.example.ship.Menu;

import android.graphics.Point;
import android.graphics.Typeface;
import android.widget.Toast;
import com.example.ship.Atlas.ResourceManager;
import com.example.ship.Events;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 25.04.13
 * Time: 5:11
 * To change this template use File | Settings | File Templates.
 */
public class ShipMenuScene extends Scene {
    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private       Point textureSize;
    private       ArrayList<ButtonMenuSprite> buttons;
    private       Font buttonFont;

    public ShipMenuScene( final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.textureSize = activity.getTextureSize();
        this.resourceManager = activity.getResourceManager();

        buttons = new ArrayList<ButtonMenuSprite>();

        createBackground();
        createButtons();
        createTitle();
    }

    private void createTitle() {
        Font titleFont = FontFactory.createFromAsset( activity.getFontManager(), activity.getTextureManager()
                                                    , 256, 256, activity.getAssets(), "zelda.ttf", 50f
                                                    , true, android.graphics.Color.BLACK);
        titleFont.load();
        Text title = new Text(0, 0, titleFont, "SHIPS", mEngine.getVertexBufferObjectManager());
        title.setPosition(textureSize.x * 0.5f - title.getWidth() * 0.5f, textureSize.y * 0.075f);
        this.attachChild(title);
    }

    private void createBackground() {
        Sprite backgroundImage = new Sprite( 0, 0, resourceManager.getLoadedTextureRegion("bg_ship")
                                           , mEngine.getVertexBufferObjectManager());
        this.attachChild(backgroundImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private void createButtons() {
        buttons = new ArrayList<ButtonMenuSprite>();

        buttonFont = FontFactory.create( mEngine.getFontManager(), mEngine.getTextureManager()
                                            , 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                            , 32f, true, Color.BLACK_ABGR_PACKED_INT);
        buttonFont.load();

        ButtonMenuSprite startButtonSprite = createButtonMenuSprite("Start");
        ButtonMenuSprite highscoresButtonSprite = createButtonMenuSprite("High scores");
        ButtonMenuSprite creditsButtonSprite = createButtonMenuSprite("Credits");
        ButtonMenuSprite exitButtonSprite = createButtonMenuSprite("Exit");

        startButtonSprite.setPosition( textureSize.x * 0.25f - startButtonSprite.getWidth() * 0.5f
                                     , textureSize.y * 0.4f);
        highscoresButtonSprite.setPosition( textureSize.x * 0.75f - startButtonSprite.getWidth() * 0.5f
                                          , textureSize.y * 0.4f);
        creditsButtonSprite.setPosition( textureSize.x * 0.25f - startButtonSprite.getWidth() * 0.5f
                                       , textureSize.y * 0.7f);
        exitButtonSprite.setPosition( textureSize.x * 0.75f - startButtonSprite.getWidth() * 0.5f
                                    , textureSize.y * 0.7f);

        buttons.add(startButtonSprite);
        buttons.add(highscoresButtonSprite);
        buttons.add(creditsButtonSprite);
        buttons.add(exitButtonSprite);

        for (ButtonMenuSprite button: buttons){
            this.registerTouchArea(button);
            this.attachChild(button);
        }

        this.setTouchAreaBindingOnActionDownEnabled(true);
    }

    private ButtonMenuSprite createButtonMenuSprite(String label) {
        return new ButtonMenuSprite( resourceManager.getLoadedTextureRegion("button_menu")
                                   , mEngine.getVertexBufferObjectManager()
                                   , label
                                   , buttonFont);
    }

    public void setEventsToChildren(Events events) {
        for (ButtonMenuSprite button: buttons){
            button.setEvents(events);
        }
    }
}
