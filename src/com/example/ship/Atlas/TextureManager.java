package com.example.ship.Atlas;

import com.example.ship.R;
import android.content.Context;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/*
Created by: IVAN
Date: 22.04.13
 */

public class TextureManager {
    public final ArrayList<Texture> loadedTextures;                     // List для записи загруженных текстур
    public final ArrayList<BuildableBitmapTextureAtlas> atlasList;      // List для записи всех атласов

    //=== Constructor ===//
    public TextureManager () {
        loadedTextures = new ArrayList<Texture>();
        atlasList = new ArrayList<BuildableBitmapTextureAtlas>();
    }

    //=== Methods ===//
    public ITextureRegion getLoadedTextureRegion ( String pname ) {
        for (int i = 0; i < loadedTextures.size(); i++ )
            if ( loadedTextures.get(i).name.equalsIgnoreCase(pname) )   // IgnoreCase - сравнение без учёта регистра
                return loadedTextures.get(i).textureRegion;             //Возвращаем Texture Region по имени текстуры.
        return null;
    }

    public void loadAllTextures ( Context context, String xmlpath ) {
        try {
            XmlPullParser parser = context.getResources().getXml( R.xml.atlas );

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT ) {
                if ( (parser.getEventType() == XmlPullParser.START_TAG)
                        && parser.getName().equals("Atlas") ) {
                    // XXX; Выборка тэгов //
                }
            }
        }
        catch ( Throwable t ) {
            // XXX: Обработка исключения
            }
    }
}
