package com.example.ship.game;

import com.example.ship.Events;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 03.05.13
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class GameButtonSprite extends Sprite {

    private final int id;
    private TouchableGameButtonSprite touchableGameButtonSprite;

    public GameButtonSprite( ITextureRegion pTextureRegion
                           , VertexBufferObjectManager pVertexBufferObjectManager
                           , int id) {

        super(0, 0, pTextureRegion, pVertexBufferObjectManager);

        this.id = id;
    }

    public void setEvents (Events events) {
        this.touchableGameButtonSprite = events;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
                                , float pTouchAreaLocalX
                                , float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {
            touchableGameButtonSprite.onAreaGameButtonTouched(this);
        }
        if (pSceneTouchEvent.isActionUp()) {
            touchableGameButtonSprite.onAreaGameButtonReleased(this);
        }
        if (pSceneTouchEvent.isActionMove()) { // when still pressed
            touchableGameButtonSprite.onAreaGameButtonPressed(this);
        }

        return true;
    }
}
