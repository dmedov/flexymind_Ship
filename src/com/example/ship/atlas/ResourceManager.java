package com.example.ship.atlas;

import android.content.Context;
import android.util.Log;
import com.example.ship.R;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
Created by: IVAN
Date: 22.04.13

Для загрузки текстур нужно вписать их в res/xml/atlas.xml в таком виде:

<?xml version="1.0" encoding="utf-8" ?>
<resources>
            <Atlas type=" [Сюда писать тип атласа ( BILINEAR, REPEATE, ... )] "
                   format=" [Сюда писать формат атласа (RGBA_8888, RGBA_4444, RGB_565, A_8)] "
                   width=" [Сюда писать ширину атласа] "
                   height=" [Сюда писать высоту атласа] ">

                [Здесь перечисляем все текстуры, которые нужно впихнуть в этот атлас]
                <texture name=" [Сюда пишем имя файла текстуры без расширения] " />

            </Atlas>
</resources>
Сами текстурв кладём в папку res/drawable/

Теперь в BaseGameActivity создаём экземпляр класса ResourceManager.
И в методе OnCreateResources у BaseGameActivity вызываем метод класса
loadAllTextures ( this, mEngine.getTextureManager() );
Посде этого все ресурсы загружены

Для получения ITextureRegion нужного ресурса вызываем метод класса
getLoadedTextureRegion ( " [Сюда пишем ID ресурса (например: R.drawable.ship)] " );
*/

public class ResourceManager {
    private final Map<Integer, ITextureRegion> loadedTextures;
    protected final ArrayList<BuildableBitmapTextureAtlas> atlasList;

    private XmlPullParser parser = null;
    private TextureManager textureManager = null;
    private MusicManager musicManager = null;
    private Context context;
    private int currentAtlasId;

    public ResourceManager(Context context) {
        this.context = context;
        loadedTextures = new HashMap<Integer, ITextureRegion>();
        atlasList = new ArrayList<BuildableBitmapTextureAtlas>();
    }

    public ITextureRegion getLoadedTextureRegion(int resourceID) {
        return loadedTextures.get(resourceID);
    }

    public void loadAllMusic(MusicManager musicManager) {
        this.musicManager = musicManager;
        for ( Field field : R.raw.class.getDeclaredFields() ) {
            try {
                Music music
                     = MusicFactory.createMusicFromResource( musicManager
                                                           , context
                                                           , context.getResources()
                                                                    .getIdentifier( field.getName()
                                                                                  , "raw"
                                                                                  , context.getPackageName() ) );
            } catch ( IOException e ) {
                Log.d("ship_music","failed to load music");
            }
        }
    }

    public void loadAllTextures(TextureManager textureManager) {
        this.textureManager = textureManager;
        this.currentAtlasId = 0;
        int eventType = 0;

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
            int textureID = 0;
            String textureName;

            for (int i = 0; i < parser.getAttributeCount(); i++) {
                if (parser.getAttributeName(i).equals("name")) {
                    textureName = parser.getAttributeValue(i);
                    textureID = context.getResources().getIdentifier(
                                                     textureName
                                                   , "drawable"
                                                   , context.getPackageName());
                }
            }

            ITextureRegion textureRegion =
                    BitmapTextureAtlasTextureRegionFactory.createFromResource(
                                                                            atlasList.get(currentAtlasId)
                                                                          , context
                                                                          , textureID);
            loadedTextures.put(textureID, textureRegion);
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
