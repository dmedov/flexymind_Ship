package com.example.ship.Menu;

import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Toast;
import com.example.ship.Events;
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

    private Text text;
    private ButtonMenuSpriteTouchable buttonMenuSpriteTouchable;

    public ButtonMenuSprite(ITextureRegion pTextureRegion
            , VertexBufferObjectManager pVertexBufferObjectManager
            , String nameButton, Font mFont) {

        super(0, 0, pTextureRegion, pVertexBufferObjectManager);

        text = new Text(0.0f,0.0f, mFont,nameButton, pVertexBufferObjectManager);
        text.setPosition(this.getWidth() * 0.5f - text.getWidth() * 0.5f,this.getHeight() * 0.5f
                        - text.getHeight() * 0.5f);
        this.attachChild(text);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if ( pSceneTouchEvent.isActionDown() ){
            buttonMenuSpriteTouchable.onAreaButtonMenuTouched(text.getText().toString());
        }
        return true;
    }

    public void setEvents(Events events) {
        this.buttonMenuSpriteTouchable = events;
    }

    public interface ButtonMenuSpriteTouchable {
        public void onAreaButtonMenuTouched(String buttonName);
        public void onAreaButtonMenuReleased();
    }
}