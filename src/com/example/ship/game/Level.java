package com.example.ship.game;

import android.util.Log;
import com.example.ship.R;
import com.example.ship.RootActivity;
import com.example.ship.game.spawn.FullRandomShipSpawner;
import com.example.ship.game.spawn.PeriodicalShipSpawner;
import com.example.ship.game.spawn.RandomDelayShipSpawner;
import com.example.ship.game.spawn.ShipSpawner;
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
    private float scoreMultiplier;
    private XmlPullParser parser = null;
    private ArrayList<ShipSpawner> shipSpawners;
    private boolean isBonus = false;

    public Level(RootActivity activity) {
        this.activity = activity;
        this.currentLevel = 0;
        shipSpawners = new ArrayList<ShipSpawner>();
    }

    public void startLevel(int level) {
        currentLevel = level;
        shipSpawners.clear();
        isBonus = false;

        if (!initLevelFromXml(currentLevel)){
            activity.getSceneSwitcher().switchToGameOverHUD();
            return;
        }

        for (ShipSpawner spawner: shipSpawners) {
            spawner.startSpawn();
        }

        levelProgress = 0;

        activity.getSceneSwitcher().getGameScene().getGameHUD().showNewLevelMessage(currentLevel);
        updateLevelInfoInHud();

        activity.getResourceManager().playOnceSound(R.raw.s_gong
                , activity.getIntResource(R.integer.NEW_LEVEL_GONG_VOLUME));

    }

    public void incrementLevelProgress() {
        levelProgress++;
        Log.d("1log", "level progress..." + levelProgress);
        if (levelProgress >= levelGoal && activity.getSceneSwitcher().getGameScene().getPlayer().getHealth() > 0) {
            activity.getSceneSwitcher().getGameScene().getPlayer().addHealth();
            startLevel(++currentLevel);
        } else {
            updateLevelInfoInHud();
        }
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void pauseSpawn() {
        for (ShipSpawner spawner: shipSpawners) {
            spawner.pauseSpawn();
        }
    }

    public void resumeSpawn() {
        for (ShipSpawner spawner: shipSpawners) {
            spawner.resumeSpawn();
        }
    }

    public boolean isBonus() {
        return isBonus;
    }

    private void updateLevelInfoInHud() {
        String levelString;
        levelString = String.format( "%s: %d"
                                   , activity.getStringResource(R.string.LEVEL)
                                   , currentLevel );
        String shipsToDestroyString = String.format( "%d"
                                                   , levelGoal - levelProgress);

        activity.getSceneSwitcher().getGameScene().getGameHUD().updateLevelInfo(levelString, shipsToDestroyString);
    }

    private boolean initLevelFromXml(int level) {

        levelGoal = 0;

        int eventType = 0;
        boolean levelFound = false;

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

                    levelFound = parseLevelTag(level);
                }

                if (levelFound) {
                    break;
                } else {
                    parser.next();
                    eventType = parser.getEventType();
                }

            } catch (XmlPullParserException e) {
                Log.d("1log", e + "xml parser error");
            } catch (IOException e) {
                Log.d("1log", e + "xml parser error");
            }
        }
        return levelFound;
    }

    private boolean parseLevelTag(float level) throws XmlPullParserException, IOException {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attribute = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            if (attribute.equals("id") && level == Integer.parseInt(value)) {
                scoreMultiplier = 0.0f;
                int number = 10;
                float spawnDelay = 1.0f;

                for (int j = 0; j < parser.getAttributeCount(); j++) {
                    if (parser.getAttributeName(j).equals("scoreMultiplier")) {
                        scoreMultiplier = Float.parseFloat(parser.getAttributeValue(j));
                    }
                    if (parser.getAttributeName(j).equals("type") && parser.getAttributeValue(j).equals("bonus")) {
                        isBonus = true;
                    }
                    if (parser.getAttributeName(j).equals("number")) {
                        number = Integer.parseInt(parser.getAttributeValue(j));
                    }
                    if (parser.getAttributeName(j).equals("delay")) {
                        spawnDelay = Float.parseFloat(parser.getAttributeValue(j));
                    }
                }

                if (scoreMultiplier == 0.0f) {
                    scoreMultiplier = 1.0f;
                }

                if (isBonus) {
                    levelGoal = number;
                    FullRandomShipSpawner spawner = new FullRandomShipSpawner(number, spawnDelay);
                    shipSpawners.add(spawner);
                    return true;
                }

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

                return true;
            }
        }

        return false;
    }

    private void parseEnemyTag() {

        String shipType = null;
        int number = 0;
        float spawnDelay = 0;
        float firstSpawnIn = 0;
        String direction = null;
        String lines = null;
        boolean periodicSpawn = false;

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attribute = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            if (attribute.equals("type")) {
                shipType = value;
            } else if (attribute.equals("number")) {
                number = Integer.parseInt(value);
            } else if (attribute.equals("periodicSpawnDelay")) {
                spawnDelay = Float.parseFloat(value);
                periodicSpawn = true;
            } else if (attribute.equals("randomSpawnDelay")) {
                spawnDelay = Float.parseFloat(value);
                periodicSpawn = false;
            } else if (attribute.equals("firstSpawnIn")) {
                firstSpawnIn = Float.parseFloat(value);
            } else if (attribute.equals("direction")) {
                direction = value;
            } else if (attribute.equals("lines")) {
                lines = value;
            }
        }

        if (!(number == 0 || (number > 1 && spawnDelay == 0) || shipType == null || firstSpawnIn == 0)) {

            if (periodicSpawn) {
                PeriodicalShipSpawner spawner =
                        new PeriodicalShipSpawner(activity, firstSpawnIn, spawnDelay, number);
                spawner.setShipParameters(shipType, direction, lines);
                shipSpawners.add(spawner);
            } else {
                RandomDelayShipSpawner spawner =
                        new RandomDelayShipSpawner(activity, firstSpawnIn, spawnDelay, number);
                spawner.setShipParameters(shipType, direction, lines);
                shipSpawners.add(spawner);
            }

            levelGoal += number;
        }
    }

}
