package com.example.ship.game.hud;

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

    public final static float STANDART_RELATIVE_EXTENT_SIDE  = 0.5f;
    public final static float STANDART_RELATIVE_HEIGHT_LEVEL = 0f;
    private final       float KNOB_WIDTH                     = this.getControlKnob().getWidth();
    private final       float BASE_WIDTH                     = this.getControlBase().getWidth();

    public final float KNOB_SIZE_IN_PERCENT = KNOB_WIDTH / BASE_WIDTH;
    public final float RELATIVE_ROUGH_ZONE  = 0.5f * KNOB_SIZE_IN_PERCENT;

    private float extentSide;
    private float heightLevel;

    public HorizontalDigitalOnScreenControl( float pX
                                           , float pY
                                           , float extentSide
                                           , Camera pCamera
                                           , ITextureRegion pControlBaseTextureRegion
                                           , ITextureRegion pControlKnobTextureRegion
                                           , float pTimeBetweenUpdates
                                           , VertexBufferObjectManager pVertexBufferObjectManager
                                           , IOnScreenControlListener pOnScreenControlListener) {
        super( pX
             , pY
             , pCamera
             , pControlBaseTextureRegion
             , pControlKnobTextureRegion
             , pTimeBetweenUpdates
             , pVertexBufferObjectManager
             , pOnScreenControlListener);
        this.extentSide = extentSide;
        this.heightLevel = STANDART_RELATIVE_HEIGHT_LEVEL;
    }

    public HorizontalDigitalOnScreenControl( float pX
                                           , float pY
                                           , Camera pCamera
                                           , ITextureRegion pControlBaseTextureRegion
                                           , ITextureRegion pControlKnobTextureRegion
                                           , float pTimeBetweenUpdates
                                           , VertexBufferObjectManager pVertexBufferObjectManager
                                           , IOnScreenControlListener pOnScreenControlListener) {
        this( pX
            , pY
            , STANDART_RELATIVE_EXTENT_SIDE
            , pCamera, pControlBaseTextureRegion
            , pControlKnobTextureRegion, pTimeBetweenUpdates
            , pVertexBufferObjectManager, pOnScreenControlListener);
    }

    @Override
    protected void onUpdateControlKnob(float pRelativeX, float pRelativeY) {
        if(pRelativeX > RELATIVE_ROUGH_ZONE) {
            super.onUpdateControlKnob(extentSide, heightLevel);
        } else if(pRelativeX < -RELATIVE_ROUGH_ZONE) {
            super.onUpdateControlKnob(-extentSide, heightLevel);
        } else {
            super.onUpdateControlKnob(0, heightLevel);
        }
    }

    public void setExtentSide(float side) {
        this.extentSide = side;
    }
    public void setHeightLevel(float level) {
        this.heightLevel = level;
    }
    public float getHeightLevel() {
        return heightLevel;
    }
    public float getExtentSide(){
        return this.extentSide;
    }

}
