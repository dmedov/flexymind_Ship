package com.example.ship.Atlas;

import org.andengine.opengl.texture.region.ITextureRegion;

/*
Created by: IVAN
Date: 22.04.13
 */

public class Texture {

    public int ID;
    public ITextureRegion textureRegion;

    public Texture (int ID, ITextureRegion textureRegion) {
        this.ID = ID;
        this.textureRegion = textureRegion;
    }

}
