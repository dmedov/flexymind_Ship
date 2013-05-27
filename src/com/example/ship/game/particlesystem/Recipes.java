package com.example.ship.game.particlesystem;


import android.graphics.Point;
import android.graphics.PointF;
import com.example.ship.R;
import org.andengine.util.color.Color;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 27.05.13
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class Recipes {
    public static Recipe missileboatLevel1 = new Recipe( R.drawable.missileboat
                                                       , 1
                                                       , 110f
                                                       , 61f
                                                       , 23
                                                       , 0f
                                                       , new Point(1, 1)
                                                       , new PointF(1f, 0.2f)
                                                       , new Color(1f, 0.3f, 0f)
                                                       , new Color(0.7f, 0.05f, 0f)
                                                       , 10f
                                                       , 10f
                                                       , 100
                                                       , -10f
                                                       , -20f );

    public static Recipe missileboatLevel2 = new Recipe( R.drawable.missileboat
                                                       , 2
                                                       , 40f
                                                       , 65f
                                                       , 100f
                                                       , 0f
                                                       , new Point(1, 2)
                                                       , new PointF(1f, 0.2f)
                                                       , new Color(1f, 0.2f, 0f)
                                                       , new Color(0.7f, 0.05f, 0f)
                                                       , 30f
                                                       , 25f
                                                       , 100
                                                       , -10f
                                                       , -20f
                                                       , 1f );

    public static Recipe missileboatLevel3 = new Recipe( R.drawable.missileboat
                                                       , 3
                                                       , 15f
                                                       , 65f
                                                       , 144f
                                                       , 0f
                                                       , new Point(3, 5)
                                                       , new PointF(2f, 0.2f)
                                                       , new Color(1f, 0.2f, 0f)
                                                       , new Color(0.7f, 0.05f, 0f)
                                                       , 20f
                                                       , 20f
                                                       , 100
                                                       , -20f
                                                       , -40f );

    public static Recipe sailfishLevel1 = new Recipe( R.drawable.sailfish
                                                    , 1
                                                    , 93f
                                                    , 127f
                                                    , 23
                                                    , 0f
                                                    , new Point(1, 1)
                                                    , new PointF(1f, 0.2f)
                                                    , new Color(1f, 0.2f, 0f)
                                                    , new Color(0.7f, 0.05f, 0f)
                                                    , 10f
                                                    , 10f
                                                    , 100
                                                    , -10f
                                                    , -20f );

    public static Recipe sailfishLevel2 = new Recipe( R.drawable.sailfish
                                                    , 2
                                                    ,80f
                                                    , 126f
                                                    , 58f
                                                    , 0f
                                                    , new Point(1, 2)
                                                    , new PointF(1f, 0.2f)
                                                    , new Color(1f, 0.5f, 0f)
                                                    , new Color(0.7f, 0.05f, 0f)
                                                    , 30f
                                                    , 25f
                                                    , 100
                                                    , -10f
                                                    , -20f
                                                    , 0.5f );

    public static Recipe sailfishLevel3 = new Recipe( R.drawable.sailfish
                                                    , 3
                                                    , 52f
                                                    , 131f
                                                    , 100f
                                                    , 0f
                                                    , new Point(3, 5)
                                                    , new PointF(2f, 0.2f)
                                                    , new Color(1f, 0.5f, 0f)
                                                    , new Color(0.7f, 0.05f, 0f)
                                                    , 20f
                                                    , 20f
                                                    , 100
                                                    , -20f
                                                    , -40f );

    public static Recipe battleshipLevel1 = new Recipe( R.drawable.battleship
                                                      , 1
                                                      , 93f
                                                      , 120f
                                                      , 23
                                                      , 0f
                                                      , new Point(1, 1)
                                                      , new PointF(1f, 0.2f)
                                                      , new Color(1f, 0.3f, 0f)
                                                      , new Color(0.7f, 0.05f, 0f)
                                                      , 10f
                                                      , 10f
                                                      , 100
                                                      , -10f
                                                      , -20f );

    public static Recipe battleshipLevel2 = new Recipe( R.drawable.battleship
                                                      , 2
                                                      , 55f
                                                      , 132f
                                                      , 100f
                                                      , 0f
                                                      , new Point(1, 2)
                                                      , new PointF(1f, 0.2f)
                                                      , new Color(1f, 0.5f, 0f)
                                                      , new Color(0.7f, 0.05f, 0f)
                                                      , 30f
                                                      , 25f
                                                      , 100
                                                      , -10f
                                                      , -20f
                                                      , 0.5f );

    public static Recipe battleshipLevel3 = new Recipe( R.drawable.battleship
                                                      , 3
                                                      , 18f
                                                      , 132f
                                                      , 146f
                                                      , 0f
                                                      , new Point(3, 5)
                                                      , new PointF(2f, 0.2f)
                                                      , new Color(1f, 0.5f, 0f)
                                                      , new Color(0.7f, 0.05f, 0f)
                                                      , 20f
                                                      , 20f
                                                      , 100
                                                      , -20f
                                                      , -40f );

    public static Recipe find(int typeId, int level){
        if(typeId == battleshipLevel1.typeId && level == battleshipLevel1.level){
            return battleshipLevel1;
        } else if (typeId == battleshipLevel2.typeId && level == battleshipLevel2.level) {
            return battleshipLevel2;
        } else if (typeId == battleshipLevel3.typeId && level == battleshipLevel3.level) {
            return battleshipLevel3;
        }else if (typeId == sailfishLevel1.typeId && level == sailfishLevel1.level) {
            return sailfishLevel1;
        } else if (typeId == sailfishLevel2.typeId && level == sailfishLevel2.level) {
            return sailfishLevel2;
        } else if (typeId == sailfishLevel3.typeId && level == sailfishLevel3.level) {
            return sailfishLevel3;
        } else if (typeId == missileboatLevel1.typeId && level == missileboatLevel1.level) {
            return missileboatLevel1;
        } else if (typeId == missileboatLevel2.typeId && level == missileboatLevel2.level) {
            return missileboatLevel2;
        } else if (typeId == missileboatLevel3.typeId && level == missileboatLevel3.level) {
            return missileboatLevel3;
        } else {
            return sailfishLevel1;
        }
    }
}
