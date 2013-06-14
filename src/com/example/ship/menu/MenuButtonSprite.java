package com.example.ship.menu;

import com.example.ship.Events;
import com.example.ship.commons.A;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class MenuButtonSprite extends Sprite {

    private final int id;
    private TouchableMenuButtonSprite touchableMenuButtonSprite;

    public MenuButtonSprite( int id
                           , ITextureRegion buttonPoppedTexture
                           , ITextureRegion buttonPushedTexture) {

        super(0, 0, buttonPushedTexture, A.a.getVertexBufferObjectManager());
        this.id = id;

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