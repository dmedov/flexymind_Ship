package com.example.ship.menu;

import com.example.ship.Events;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class MenuButtonSprite extends Sprite {

    private final int id;
    private Text text;
    private TouchableMenuButtonSprite touchableMenuButtonSprite;

    public MenuButtonSprite( ITextureRegion pTextureRegion
                           , VertexBufferObjectManager pVertexBufferObjectManager
                           , int id
                           , String buttonName
                           , Font font) {

        super(0, 0, pTextureRegion, pVertexBufferObjectManager);
        this.id = id;

        text = new Text(0, 0, font, buttonName, pVertexBufferObjectManager);
        text.setPosition( this.getWidth() * 0.5f - text.getWidth() * 0.5f
                        , this.getHeight() * 0.5f - text.getHeight() * 0.5f);

        this.attachChild(text);
    }

    public void setEvents(Events events) {
        this.touchableMenuButtonSprite = events;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
                                , float pTouchAreaLocalX
                                , float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {
            touchableMenuButtonSprite.onAreaButtonMenuTouched(this);
        }
        if (pSceneTouchEvent.isActionUp()) {
            touchableMenuButtonSprite.onAreaButtonMenuReleased(this);
        }
        return true;
    }
}