package com.example.ship.menu;

import com.example.ship.Events;
import com.example.ship.commons.A;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class MenuButtonSprite extends Sprite {

    private final int id;
    private TouchableMenuButtonSprite touchableMenuButtonSprite;
    private Sprite poppedSprite;

    public MenuButtonSprite( int id
                           , ITextureRegion buttonPushedTexture
                           , ITextureRegion buttonPoppedTexture) {

        super(0, 0, buttonPushedTexture, A.a.getVertexBufferObjectManager());
        this.id = id;

        poppedSprite = new Sprite(0, 0, buttonPoppedTexture, A.a.getVertexBufferObjectManager());
        poppedSprite.setVisible(false);
        this.attachChild(poppedSprite);

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
            this.setAlpha(0.0f);
            poppedSprite.setVisible(true);
            touchableMenuButtonSprite.onAreaButtonMenuTouched(this);
        }
        if (pSceneTouchEvent.isActionUp()) {
            poppedSprite.setVisible(false);
            this.setAlpha(1.0f);
            touchableMenuButtonSprite.onAreaButtonMenuReleased(this);
        }
        return true;
    }
}