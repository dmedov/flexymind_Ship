package com.example.ship.Menu;

import android.graphics.Point;
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

public class ButtonMenuSprite extends Sprite{

    Text text;

    public ButtonMenuSprite(ITextureRegion pTextureRegion
            , VertexBufferObjectManager pVertexBufferObjectManager
            , String nameButton, Font mFont) {

        super(0, 0, pTextureRegion, pVertexBufferObjectManager);

        text = new Text(0.0f,0.0f, mFont,nameButton, pVertexBufferObjectManager);
        text.setPosition(this.getWidth() * 0.5f - text.getWidth() * 0.5f,this.getHeight() * 0.5f
                        - text.getHeight() * 0.5f);
        this.attachChild(text);
    }

}