package com.example.ship.menu;

import android.graphics.Point;
import com.example.ship.atlas.ResourceManager;
import com.example.ship.R;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 25.04.13
 * Time: 5:11
 * To change this template use File | Settings | File Templates.
 */
public class ShipMenuScene extends Scene {
    private final static int FONT_ATLAS_SIDE = 256;
    private final static int TITLE_FONT_HEIGHT = 70;
    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;
    private       Point textureSize;

    public ShipMenuScene( final SceletonActivity activity) {
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.textureSize = activity.getTextureSize();
        this.resourceManager = activity.getResourceManager();

        createBackground();
        createTitle();
    }

    private void createTitle() {
        Font titleFont = FontFactory.createFromAsset( activity.getFontManager()
                                                    , activity.getTextureManager()
                                                    , FONT_ATLAS_SIDE
                                                    , FONT_ATLAS_SIDE
                                                    , activity.getAssets()
                                                    , getStringResource(R.string.FONT_PLOK_FILE)
                                                    , TITLE_FONT_HEIGHT
                                                    , true
                                                    , android.graphics.Color.BLACK);
        titleFont.load();
        Text title = new Text( 0
                             , 0
                             , titleFont
                             , getStringResource(R.string.APP_TITLE)
                             , mEngine.getVertexBufferObjectManager());

        title.setPosition(textureSize.x * 0.5f - title.getWidth() * 0.5f, textureSize.y * 0.075f);
        this.attachChild(title);
    }

    private void createBackground() {
        ITextureRegion backgroundTexture = resourceManager.getLoadedTextureRegion(R.drawable.menubackground);
        Sprite backgroundImage = new Sprite( 0
                                           , 0
                                           , backgroundTexture
                                           , mEngine.getVertexBufferObjectManager());
        this.attachChild(backgroundImage);
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

    private String getStringResource(int id) {
        return activity.getResources().getString(id);
    }
}
