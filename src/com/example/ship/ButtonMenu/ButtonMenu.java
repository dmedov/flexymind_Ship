package com.example.ship.ButtonMenu;

import android.util.Log;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class ButtonMenu extends Sprite{

    private static final int CAMERA_WIDTH = 960;
    private static final int CAMERA_HEIGHT = 600;

    public ButtonMenu(float multiplier, ITextureRegion pTextureRegion,
            VertexBufferObjectManager pVertexBufferObjectManager, String nameButton) {
        super( CAMERA_WIDTH * 0.5f - pTextureRegion.getWidth() * 0.5f
             , CAMERA_HEIGHT * 0.2f * multiplier
             , pTextureRegion
             , pVertexBufferObjectManager);

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