package com.example.ship.game.hud;

import org.andengine.engine.camera.Camera;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.opengl.GLES20;


public class MaskRectangle extends Rectangle{

    private boolean mMaskingEnabled = true;


    public MaskRectangle( float pX
                        , float pY
                        , float pWidth
                        , float pHeight
                        , VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }

    public boolean isEnabled() {
        return mMaskingEnabled;
    }

    public void setMaskingEnabled(final boolean pMaskingEnabled) {
        mMaskingEnabled = pMaskingEnabled;
    }



    @Override
    protected void onManagedDraw(final GLState pGLState, final Camera pCamera) {
        if (mMaskingEnabled) {

            GLES20.glEnable(GLES20.GL_STENCIL_TEST);
            GLES20.glClearStencil(0);
            GLES20.glStencilMask(1);

            GLES20.glClear(GLES20.GL_STENCIL_BUFFER_BIT);

            GLES20.glScissor(0, 0, 20, 100);

            super.onManagedDraw(pGLState, pCamera);

            GLES20.glDisable(GLES20.GL_STENCIL_TEST);

        } else {
            super.onManagedDraw(pGLState, pCamera);
        }
    }

    @Override
    protected void preDraw(final GLState pGLState, final Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        GLES20.glStencilFunc(GLES20.GL_NEVER, 1, 1);
        GLES20.glStencilOp(GLES20.GL_REPLACE, GLES20.GL_KEEP, GLES20.GL_KEEP);
    }

    @Override
    protected void postDraw(final GLState pGLState, final Camera pCamera) {
        super.postDraw(pGLState, pCamera);
        GLES20.glStencilFunc(GLES20.GL_EQUAL, 2, 1);
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_KEEP);
    }

}
