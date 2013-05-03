package com.example.ship.atlas;

import android.content.Context;
import android.util.Log;
import com.example.ship.R;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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

    private XmlPullParser parser = null;
    private TextureManager textureManager = null;
    private Context context;
    private int currentAtlasId;

    public ResourceManager() {
        loadedTextures = new ArrayList<Texture>();
        atlasList = new ArrayList<BuildableBitmapTextureAtlas>();
    }

    public ITextureRegion getLoadedTextureRegion(String textureName) {
        for (Texture loadedTexture : loadedTextures) {
            if (loadedTexture.name.equalsIgnoreCase(textureName)) {
                return loadedTexture.textureRegion;
            }
        }
        return null;
    }

    public void loadAllTextures(Context context, TextureManager textureManager) {
        this.textureManager = textureManager;
        this.currentAtlasId = 0;
        this.context = context;
        int eventType = 0;
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        try {
            parser = context.getResources().getXml(R.xml.atlas);
            eventType = parser.getEventType();
        } catch (XmlPullParserException e) {
            handleException(e, "xml parser initialization error");
        }

        while (eventType != XmlPullParser.END_DOCUMENT) {
            try {
                // try parse atlas start tag
                if ((parser.getEventType() == XmlPullParser.START_TAG)
                        && (parser.getName().equals("Atlas"))
                        && (parser.getDepth() == 2)) {

                    parseAtlasStartTag();
                } else
                // try parse atlas end tag
                if ((parser.getEventType() == XmlPullParser.END_TAG)
                        && (parser.getName().equals("Atlas"))
                        && (parser.getDepth() == 2)) {

                    parseAtlasEndTag();
                } else
                // try parse texture start tag
                if ((parser.getEventType() == XmlPullParser.START_TAG)
                        && (parser.getName().equals("texture"))
                        && (parser.getDepth() == 3)) {

                    parseTextureStartTag();
                }
                parser.next();
                eventType = parser.getEventType();

            } catch (XmlPullParserException e) {
                String error = "xml parsing error with"
                        + parser.getName()
                        + "in atlas number"
                        + Integer.toString(currentAtlasId);
                handleException(e, error);
            } catch (IOException e) {
                String error = "io error in xml parsing with"
                        + parser.getName()
                        + "in atlas number"
                        + Integer.toString(currentAtlasId);
                handleException(e, error);
            }
        }
    }

    private void parseAtlasStartTag() {
        int atlasHeight = 1;
        int atlasWidth = 1;
        TextureOptions textureOptions = TextureOptions.DEFAULT;
        BitmapTextureFormat textureFormat = BitmapTextureFormat.RGBA_4444;

        // parse atlas attributes
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attribute = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (attribute.equals("width")) {
                atlasWidth = Integer.parseInt(value);
            } else if (attribute.equals("height")) {
                atlasHeight = Integer.parseInt(value);
            } else if (attribute.equals("type")) {
                textureOptions = stringToTextureOptions(value);
            } else if (attribute.equals("format")) {
                textureFormat = stringToTextureFormat(value);
            }
        }

        atlasList.add(new BuildableBitmapTextureAtlas( textureManager
                                                     , atlasWidth
                                                     , atlasHeight
                                                     , textureFormat
                                                     , textureOptions));
    }

    private void parseAtlasEndTag() {
        try {
            atlasList.get(currentAtlasId).build(
                    new BlackPawnTextureAtlasBuilder< IBitmapTextureAtlasSource
                                                    , BitmapTextureAtlas>(0, 1, 1));

        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            handleException(e, "problem with build atlas number" + Integer.toString(currentAtlasId));
        }
        atlasList.get(currentAtlasId).load();
        currentAtlasId++;
    }

    private void parseTextureStartTag() {
            String texturePath = "";
            String textureName = "";

            for (int i = 0; i < parser.getAttributeCount(); i++) {
                if (parser.getAttributeName(i).equals("name")) {
                    textureName = parser.getAttributeValue(i);
                }
                if (parser.getAttributeName(i).equals("path")) {
                    texturePath = parser.getAttributeValue(i);
                }
            }

            ITextureRegion textureRegion =
                    BitmapTextureAtlasTextureRegionFactory.createFromAsset( atlasList.get(currentAtlasId)
                                                                          , context
                                                                          , texturePath);
            loadedTextures.add(new Texture(textureName, textureRegion));
    }

    private TextureOptions stringToTextureOptions(String option) {
        if (option.equals("NEAREST")) {
            return TextureOptions.NEAREST;
        }
        if (option.equals("BILINEAR")) {
            return TextureOptions.BILINEAR;
        }
        if (option.equals("REPEATING_NEAREST")) {
            return TextureOptions.REPEATING_NEAREST;
        }
        if (option.equals("REPEATING_BILINEAR")) {
            return TextureOptions.REPEATING_BILINEAR;
        }
        if (option.equals("NEAREST_PREMULTIPLYALPHA")) {
            return TextureOptions.NEAREST_PREMULTIPLYALPHA;
        }
        if (option.equals("BILINEAR_PREMULTIPLYALPHA")) {
            return TextureOptions.BILINEAR_PREMULTIPLYALPHA;
        }
        if (option.equals("REPEATING_NEAREST_PREMULTIPLYALPHA")) {
            return TextureOptions.REPEATING_NEAREST_PREMULTIPLYALPHA;
        }
        if (option.equals("REPEATING_BILINEAR_PREMULTIPLYALPHA")) {
            return TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA;
        }
        return TextureOptions.DEFAULT;
    }

    private BitmapTextureFormat stringToTextureFormat (String option) {
        if (option.equals("RGBA_8888")) {
            return BitmapTextureFormat.RGBA_8888;
        }
        if (option.equals("RGBA_4444")) {
            return BitmapTextureFormat.RGBA_4444;
        }
        if (option.equals("RGB_565")) {
            return BitmapTextureFormat.RGB_565;
        }
        if (option.equals("A_8")) {
            return BitmapTextureFormat.A_8;
        }
        return BitmapTextureFormat.RGBA_4444;
    }

    private void handleException(Exception e, String error) {
        Log.e("ATLAS", e.getClass() + ":" + error);
    }
}
