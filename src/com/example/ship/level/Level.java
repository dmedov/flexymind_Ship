package com.example.ship.level;

import android.util.Log;
import com.example.ship.R;
import com.example.ship.RootActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 16.05.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class Level {

    public static final int   FIRST_LEVEL_GOAL = 10;
    public static final float LEVEL_GOAL_MULTIPLIER = 1.2f;
    public static final float LEVEL_SCORE_MULTIPLIER = 1.1f;
    public static final float LEVEL_SPAWN_DELAY_MULTIPLIER = 0.95f;
    public static final float LEVEL_SHIP_SPEED_MULTIPLIER = 1.1f;

    private final RootActivity activity;

    private int currentLevel;
    private int levelGoal;
    private int levelProgress;
    private XmlPullParser parser = null;
    private ArrayList<FullRandomShipSpawner> fullRandomShipSpawners;

    public Level(RootActivity activity) {
        this.activity = activity;
        this.currentLevel = 0;
        fullRandomShipSpawners = new ArrayList<FullRandomShipSpawner>();
    }

    public void startLevel(int level) {
        currentLevel = level;
        initLevelFromXml(currentLevel);
//
//     DeathMatch
//        levelGoal = (int) (FIRST_LEVEL_GOAL * (1 + LEVEL_GOAL_MULTIPLIER * (currentLevel - 1)));
//        levelProgress = 0;
//
//        float newSpawnDelay = (float) ( (FullRandomShipSpawner.MAX_SPAWN_DELAY - FullRandomShipSpawner.MIN_SPAWN_DELAY)
//                                        * Math.pow(LEVEL_SPAWN_DELAY_MULTIPLIER, currentLevel - 1));
//        activity.getSceneSwitcher().getGameScene().getFullRandomShipSpawner()
//                .setSpawnDelay(newSpawnDelay);
//
//        Ship.setVelocityDivider((float) Math.pow(LEVEL_SHIP_SPEED_MULTIPLIER, currentLevel - 1));

        levelProgress = 0;

        activity.getSceneSwitcher().getGameScene().getGameHUD().showNewLevelMessage(currentLevel);
        updateLevelInfoInHud();

    }

    public void incrementLevelProgress() {
        levelProgress++;
        Log.d("1log", "level progress..." + levelProgress);
        if (levelProgress >= levelGoal) {
            activity.getSceneSwitcher().getGameScene().getPlayer().addHealth();
            startLevel(++currentLevel);
        } else {
            updateLevelInfoInHud();
        }
    }

    public float getScoreMultiplier() {
        return (float) Math.pow(LEVEL_SCORE_MULTIPLIER, currentLevel - 1);
    }

    private void updateLevelInfoInHud() {
        String info;
        info = String.format( "%s: %d\n%s: %d (%d)"
                            , activity.getStringResource(R.string.LEVEL)
                            , currentLevel
                            , activity.getStringResource(R.string.GOAL)
                            , levelGoal
                            , levelProgress);
        activity.getSceneSwitcher().getGameScene().getGameHUD().updateLevelInfo(info);
    }

    private void initLevelFromXml(int level) {

        levelGoal = 0;

        int eventType = 0;

        try {
            parser = activity.getResources().getXml(R.xml.levels);
            eventType = parser.getEventType();
        } catch (XmlPullParserException e) {
            Log.d("1log", e + "xml parser initialization error");
        }

        while (eventType != XmlPullParser.END_DOCUMENT) {
            try {

                if (  eventType == XmlPullParser.START_TAG
                   && parser.getName().equals("level")
                   && parser.getDepth() == 2) {

                    parseLevelTag(level);
                }

                parser.next();
                eventType = parser.getEventType();

            } catch (XmlPullParserException e) {
                Log.d("1log", e + "xml parser error");
            } catch (IOException e) {
                Log.d("1log", e + "xml parser error");
            }
        }
    }

    private void parseLevelTag(float level) throws XmlPullParserException, IOException {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attribute = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            if (attribute.equals("id") && level == Integer.parseInt(value)) {
                while (!(  parser.getEventType() == XmlPullParser.END_TAG
                        && parser.getName().equals("level")
                        && parser.getDepth() == 2)) {

                    if (  parser.getEventType() == XmlPullParser.START_TAG
                       && parser.getName().equals("enemy")
                       && parser.getDepth() == 3) {

                        parseEnemyTag();

                    }
                    parser.next();
                }
            }
        }
    }

    private void parseEnemyTag() {

        int number = 0;
        float velocity = 0;
        float spawnDelay = 0;
        float firstSpawnIn = 0;
        String direction = null;

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attribute = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            if (attribute.equals("number")) {
                number = Integer.parseInt(value);
            } else if (attribute.equals("velocity")) {
                velocity = Float.parseFloat(value);
            } else if (attribute.equals("constSpawnDelay")) {
                spawnDelay = Float.parseFloat(value);
            } else if (attribute.equals("firstSpawnIn")) {
                firstSpawnIn = Float.parseFloat(value);
            } else if (attribute.equals("direction")) {
                direction = value;
            }

            fullRandomShipSpawners.add(new FullRandomShipSpawner(activity));

        }

        levelGoal += number;

        Log.d("1log", String.format( "num=%d...vel=%s...spaD=%s...fspa=%s...dir=%s"
                                   , number, velocity, spawnDelay, firstSpawnIn, direction));
    }

}
