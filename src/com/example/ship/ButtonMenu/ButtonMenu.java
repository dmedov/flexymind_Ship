package com.example.ship.ButtonMenu;

import android.graphics.Typeface;
import android.util.Log;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class ButtonMenu extends Sprite{

    private static final int CAMERA_WIDTH = 960;
    private static final int CAMERA_HEIGHT = 600;
    Font mFont;
    Text text;

    public ButtonMenu(float multiplier, ITextureRegion pTextureRegion,
            VertexBufferObjectManager pVertexBufferObjectManager, String nameButton, Font mFont) {

        super( CAMERA_WIDTH * 0.5f - pTextureRegion.getWidth() * 0.5f
             , CAMERA_HEIGHT * 0.2f * multiplier + 0.1f
             , pTextureRegion
             , pVertexBufferObjectManager);

        text = new Text(0.0f,0.0f, mFont,nameButton, pVertexBufferObjectManager);
        text.setPosition(this.getWidth() * 0.5f - text.getWidth() * 0.5f,this.getHeight() * 0.5f - text.getHeight() * 0.5f);
        this.attachChild(text);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                 float pTouchAreaLocalX, float pTouchAreaLocalY){
        if ( pSceneTouchEvent.isActionDown() ){
            Log.d("AAAAAAAAAwwwwwwwwwwwwwwwwwwAAAAAAAA:", "AAAAAAAAAAwwwwwwwwwwwwwwwwAAAAAAAAAAAA");
        }
        return true;
    }

}