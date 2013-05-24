package com.example.ship.resource;

import android.content.Context;
import android.util.Log;
import com.example.ship.R;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundManager;
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
    private final Map<Integer, Music> loadedMusic;
    private final Map<Integer, Sound> loadedSound;
    protected final ArrayList<BuildableBitmapTextureAtlas> atlasList;

    private XmlPullParser parser = null;
    private TextureManager textureManager = null;
    private MusicManager musicManager = null;
    private SoundManager soundManager = null;
    private Context context;
    private int currentAtlasId;

    public final boolean FROM_THE_BEGINING = true;

    public ResourceManager(Context context) {
        this.context = context;
        loadedTextures = new HashMap<Integer, ITextureRegion>();
        loadedMusic = new HashMap<Integer, Music>();
        loadedSound = new HashMap<Integer, Sound>();
        atlasList = new ArrayList<BuildableBitmapTextureAtlas>();
    }

    // ================= SOUND ==================== //

    public Sound getLoadedSound(int resourceID) {
        return loadedSound.get(resourceID);
    }

    private void playSound(int resourceID, int volume, boolean looping) {
        Sound sound = loadedSound.get(resourceID);
        sound.setLooping(looping);
        sound.setVolume( ((float)volume)/100f );
        sound.play();
    }

    public void stopSound(int resourceID) {
        loadedSound.get(resourceID).stop();
    }

    public void pauseSound(int resourceID) {
        loadedSound.get(resourceID).pause();
    }

    public void playOnceSound(int resourceID, int volume) {
        playSound(resourceID, volume, false);
    }

    public void playOnceSound(int resourceID) {
        playSound(resourceID, 100, false);
    }

    public void playLoopSound(int resourceID, int volume) {
        playSound(resourceID, volume, true);
    }

    public void playLoopSound(int resourceID) {
        playSound(resourceID, 100, true);
    }

    public void stopAllSounds() {
        for (HashMap.Entry<Integer, Sound> e : loadedSound.entrySet()) {
            e.getValue().stop();
        }
    }

    public void pauseAllSound() {
        for (HashMap.Entry<Integer, Sound> e : loadedSound.entrySet()) {
            e.getValue().pause();
        }
    }

    public void loadAllSound(SoundManager soundManager) {
        this.soundManager = soundManager;
        int soundId;
        for ( Field field : R.raw.class.getDeclaredFields() ) {
            if (field.getName().substring(0,1).equals("s")) {
                try {
                    soundId = context.getResources().getIdentifier( field.getName()
                            , "raw"
                            , context.getPackageName() );

                    Music music = MusicFactory.createMusicFromResource( musicManager
                            , context
                            , soundId );
                    loadedMusic.put(soundId, music);
                } catch (IOException e) {
                    Log.d("ship_music","failed to load sound " + field.getName());
                }
            }
        }
    }

    // ================= MUSIC ==================== //

    public Music getLoadedMusic(int resourceID) {
        return loadedMusic.get(resourceID);
    }

    private void playMusic(int resourceID, int volume, boolean looping, boolean fromBegining) {
        Music music = loadedMusic.get(resourceID);
        if (fromBegining) {
            music.seekTo(0);
        }
        music.setLooping(looping);
        music.setVolume( ((float)volume)/100f );
        music.play();
    }

    public void playOnceMusic(int resourceID, int volume, boolean fromBegining) {
        playMusic(resourceID, volume, false, fromBegining);
    }

    public void playOnceMusic(int resourceID, int volume) {
        playMusic(resourceID, volume, false, false);
    }

    public void playOnceMusic(int resourceID, boolean fromBegining) {
        playMusic(resourceID, 100, false, fromBegining);
    }

    public void playOnceMusic(int resourceID) {
        playMusic(resourceID, 100, false, false);
    }

    public void playLoopMusic(int resourceID, int volume, boolean fromBegining) {
        playMusic(resourceID, volume, true, fromBegining);
    }

    public void playLoopMusic(int resourceID, int volume) {
        playMusic(resourceID, volume, true, false );
    }

    public void playLoopMusic(int resourceID, boolean fromBegining) {
        playMusic(resourceID, 100, true, fromBegining );
    }

    public void playLoopMusic(int resourceID) {
        playMusic(resourceID, 100, true, false );
    }

    public void stopAllMusic() {
        Music music;
        for (HashMap.Entry<Integer, Music> e : loadedMusic.entrySet()) {
            music = e.getValue();
            if (music.isPlaying()) {
                music.stop();
            }
        }
    }

    public void pauseAllMusic() {
        Music music;
        for (HashMap.Entry<Integer, Music> e : loadedMusic.entrySet()) {
            music = e.getValue();
            if (music.isPlaying()) {
                music.pause();
            }
        }
    }

    public void loadAllMusic(MusicManager musicManager) {
        this.musicManager = musicManager;
        int musicId;
        for ( Field field : R.raw.class.getDeclaredFields() ) {
            if (field.getName().substring(0,1).equals("m")) {
                try {
                    musicId = context.getResources().getIdentifier( field.getName()
                                                                  , "raw"
                                                                  , context.getPackageName() );

                    Music music = MusicFactory.createMusicFromResource( musicManager
                                                                      , context
                                                                      , musicId );
                    loadedMusic.put(musicId, music);
                } catch (IOException e) {
                    Log.d("ship_music","failed to load music " + field.getName());
                }
            }
        }
    }

    // ============== TEXTURE ================ //

    public ITextureRegion getLoadedTextureRegion(int resourceID) {
        return loadedTextures.get(resourceID);
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
