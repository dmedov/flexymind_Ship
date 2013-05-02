package com.example.ship.atlas;

import org.andengine.opengl.texture.region.ITextureRegion;

/*
Created by: IVAN
Date: 22.04.13
 */

public class Texture {

    public int id;
    public ITextureRegion textureRegion;

    public Texture (int id, ITextureRegion textureRegion) {
        this.id = id;
        this.textureRegion = textureRegion;
    }

}
