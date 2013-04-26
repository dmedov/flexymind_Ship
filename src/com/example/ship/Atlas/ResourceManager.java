package com.example.ship.Atlas;

import com.example.ship.R;
import android.content.Context;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/*
Created by: IVAN
Date: 22.04.13

Для загрузки текстур нужно вписать их в res/xml/atlas.xml в таком виде:

<?xml version="1.0" encoding="utf-8" ?>
<resources>
            <Atlas type=" [Сюда писать тип атласа ( BILINEAR, REPEATE, ... )] "
                   width=" [Сюда писать ширину атласа] "
                   height=" [Сюда писать высоту атласа] ">

                [Здесь перечисляем все текстуры, которые нужно впихнуть в этот атлас]
                <texture path=" [Сюда пишем путь к текстуре относительно assets/gfx/] "
                         name=" [Сюда пишем имя текстуры, по которому мы будем к ней обращаться из программы] " />

            </Atlas>
</resources>

Теперь в BaseGameActivity создаём экземпляр класса ResourceManager.
И в методе OnCreateResources у BaseGameActivity вызываем метод класса
loadAllTextures ( this, mEngine.getTextureManager() );
Посде этого все ресурсы загружены

Для получения ITextureRegion нужного ресурса вызываем метод класса
getLoadedTextureRegion ( " [Сюда пишем имя, которое мы забили в xml] " );
*/

public class ResourceManager {

    private final ArrayList<Texture> loadedTextures;                     
    protected final ArrayList<BuildableBitmapTextureAtlas> atlasList;      

    public ResourceManager () {

        loadedTextures = new ArrayList<Texture>();
        atlasList = new ArrayList<BuildableBitmapTextureAtlas>();
    }

    public ITextureRegion getLoadedTextureRegion ( String pname ) {

        for (Texture loadedTexture : loadedTextures) {
            if ( loadedTexture.name.equalsIgnoreCase(pname) ) {   
                return loadedTexture.textureRegion;
				}
			}             
        return null;
    }

    public void loadAllTextures ( Context context, TextureManager pTextureManager ) {

        int currAtlas = 0;
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/"); 

        try {
            XmlPullParser parser = context.getResources().getXml( R.xml.atlas );

            while ( parser.getEventType() != XmlPullParser.END_DOCUMENT ) {
                if (       ( parser.getEventType() == XmlPullParser.START_TAG )
                        && ( parser.getName().equals("Atlas") )
                        && ( parser.getDepth() == 2 ) ) {                        
                    int atlasHeight = 1;
                    int atlasWidth = 1;
                    TextureOptions textureOptions = TextureOptions.DEFAULT;

                    for (int i = 0; i < parser.getAttributeCount(); i++ ) {
                        if( parser.getAttributeName(i).equals("width") )
                            atlasWidth = Integer.parseInt(parser.getAttributeValue(i));
                        if( parser.getAttributeName(i).equals("height") )               
                            atlasHeight = Integer.parseInt( parser.getAttributeValue(i) );
                        if( parser.getAttributeName(i).equals("type") )
                            textureOptions = stringToTextureOptions(parser.getAttributeValue(i));
                    }

                    atlasList.add( new BuildableBitmapTextureAtlas(   pTextureManager
                                                                    , atlasWidth
                                                                    , atlasHeight
                                                                    , textureOptions ) ); 
                    // XXX: ДОБАВИТЬ TEXTURE FORMAT!
                }

                if (       ( parser.getEventType() == XmlPullParser.END_TAG )
                        && ( parser.getName().equals("Atlas") )
                        && ( parser.getDepth() == 2 ) ) {

                    try {
                        atlasList.get(currAtlas).build(       
                                new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,1,1));
                                                               
                    }
                    catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
                        // XXX: Обработка исключения
                    }
                    atlasList.get(currAtlas).load();          
                    currAtlas++;
                }
               
                if (       ( parser.getEventType() == XmlPullParser.START_TAG )
                        && ( parser.getName().equals("texture") )
                        && ( parser.getDepth() == 3 ) ) {           
                    try {
						String path = "";
						String tname = "";

						for ( int i = 0; i < parser.getAttributeCount(); i++ ) { 
							if ( parser.getAttributeName(i).equals("name") ) tname = parser.getAttributeValue(i);
							if ( parser.getAttributeName(i).equals("path") ) path = parser.getAttributeValue(i);
						}

                    
						ITextureRegion textReg =
								BitmapTextureAtlasTextureRegionFactory.createFromAsset(   atlasList.get(currAtlas)
																						, context
																						, path );
						loadedTextures.add( new Texture(tname, textReg) );
						}
						catch ( Throwable t ) {
							// XXX: Обработка исключения
						}
                }               
                parser.next();
            }
        }
        catch ( Throwable t ) {
            // XXX: Обработка исключения
            }
    }

    private TextureOptions stringToTextureOptions ( String opt ) {

        if ( opt.equals("NEAREST") ) return TextureOptions.NEAREST;
        if ( opt.equals("BILINEAR") ) return TextureOptions.BILINEAR;
        if ( opt.equals("REPEATING_NEAREST") ) return TextureOptions.REPEATING_NEAREST;
        if ( opt.equals("REPEATING_BILINEAR") ) return TextureOptions.REPEATING_BILINEAR;
        if ( opt.equals("NEAREST_PREMULTIPLYALPHA") ) return TextureOptions.NEAREST_PREMULTIPLYALPHA;
        if ( opt.equals("BILINEAR_PREMULTIPLYALPHA") ) return TextureOptions.BILINEAR_PREMULTIPLYALPHA;
        if ( opt.equals("REPEATING_NEAREST_PREMULTIPLYALPHA") ) return TextureOptions.REPEATING_NEAREST_PREMULTIPLYALPHA;
        if ( opt.equals("REPEATING_BILINEAR_PREMULTIPLYALPHA") ) return TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA;

        return TextureOptions.DEFAULT;
    }
}
