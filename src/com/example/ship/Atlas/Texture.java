package com.example.ship.Atlas;

import org.andengine.opengl.texture.region.ITextureRegion;

/*
Created by: IVAN
Date: 22.04.13
 */

public class Texture {

    public String name;
    public ITextureRegion textureRegion;

    public Texture (String name, ITextureRegion textureRegion) {
        this.name = name;
        this.textureRegion = textureRegion;
    }

}
