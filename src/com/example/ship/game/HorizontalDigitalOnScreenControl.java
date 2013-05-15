package com.example.ship.game;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 15.05.13
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class HorizontalDigitalOnScreenControl extends BaseOnScreenControl {

    private final float knobWidth = this.getControlKnob().getWidth();
    private final float baseWidth = this.getControlBase().getWidth();
    private final float knobSizeInPercent = knobWidth / baseWidth;
    private final float EXTENT_SIDE = 0.5f - knobSizeInPercent / 2;

    public HorizontalDigitalOnScreenControl( float pX, float pY, Camera pCamera, ITextureRegion pControlBaseTextureRegion, ITextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, VertexBufferObjectManager pVertexBufferObjectManager, IOnScreenControlListener pOnScreenControlListener) {
        super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pVertexBufferObjectManager, pOnScreenControlListener );
    }

    @Override
    protected void onUpdateControlKnob( float pRelativeX, float pRelativeY ) {
        if( pRelativeX == 0 ) {
            super.onUpdateControlKnob( 0, 0 );
            return;
        }

        if( pRelativeX > 0 ) {
            super.onUpdateControlKnob( EXTENT_SIDE, 0 );
        } else if( pRelativeX < 0 ) {
            super.onUpdateControlKnob( -EXTENT_SIDE, 0 );
        } else if( pRelativeX == 0 ) {
            super.onUpdateControlKnob( 0, 0 );
        }
    }
}
