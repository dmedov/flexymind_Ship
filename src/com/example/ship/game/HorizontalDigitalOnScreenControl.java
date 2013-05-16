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

    public final static float STANDART_EXTENT_SIDE = 0.5f;
    private final float KNOB_WIDTH = this.getControlKnob().getWidth();
    private final float BASE_WIDTH = this.getControlBase().getWidth();

    public final float KNOB_SIZE_IN_PERCENT = KNOB_WIDTH / BASE_WIDTH;

    private float extentSide;

    public HorizontalDigitalOnScreenControl( float pX, float pY, float extentSide
                                           , Camera pCamera
                                           , ITextureRegion pControlBaseTextureRegion
                                           , ITextureRegion pControlKnobTextureRegion
                                           , float pTimeBetweenUpdates
                                           , VertexBufferObjectManager pVertexBufferObjectManager
                                           , IOnScreenControlListener pOnScreenControlListener ) {
        super( pX, pY
             , pCamera
             , pControlBaseTextureRegion
             , pControlKnobTextureRegion
             , pTimeBetweenUpdates
             , pVertexBufferObjectManager
             , pOnScreenControlListener );
        this.extentSide = extentSide;
    }

    public HorizontalDigitalOnScreenControl( float pX, float pY
                                           , Camera pCamera
                                           , ITextureRegion pControlBaseTextureRegion
                                           , ITextureRegion pControlKnobTextureRegion
                                           , float pTimeBetweenUpdates
                                           , VertexBufferObjectManager pVertexBufferObjectManager
                                           , IOnScreenControlListener pOnScreenControlListener ) {
        this( pX, pY, STANDART_EXTENT_SIDE
            , pCamera, pControlBaseTextureRegion
            , pControlKnobTextureRegion, pTimeBetweenUpdates
            , pVertexBufferObjectManager, pOnScreenControlListener );
    }

    @Override
    protected void onUpdateControlKnob( float pRelativeX, float pRelativeY ) {
        if( pRelativeX == 0 ) {
            super.onUpdateControlKnob( 0, 0 );
            return;
        }

        if( pRelativeX > 0 ) {
            super.onUpdateControlKnob( extentSide, 0 );
        } else if( pRelativeX < 0 ) {
            super.onUpdateControlKnob( -extentSide, 0 );
        } else if( pRelativeX == 0 ) {
            super.onUpdateControlKnob( 0, 0 );
        }
    }

    public void setExtentSide( float side ){
        this.extentSide = side;
    }
    public float getExtentSide(){
        return this.extentSide;
    }
}
