package com.example.ship.game.particlesystem;


import android.graphics.PointF;
import com.example.ship.R;
import org.andengine.util.color.Color;


/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 27.05.13
 * Time: 11:45
 * Класс для удобного хранения настроек эффектов
 */
public class Recipes {
    public static class Fire {
        public static RecipeFire missileboatLevel1 = new RecipeFire( R.drawable.missileboat
                                                                   , 1
                                                                   , new PointF(110f, 61f)
                                                                   , new PointF(23, 0f)
                                                                   , new PointF(1f, 1f)
                                                                   , new PointF(1f, 0.2f)
                                                                   , new Color(1f, 0.3f, 0f)
                                                                   , new Color(0.7f, 0.05f, 0f)
                                                                   , new PointF(10f, 10f)
                                                                   , 100
                                                                   , new PointF(-10f, -20f) );

        public static RecipeFire missileboatLevel2 = new RecipeFire( R.drawable.missileboat
                                                                   , 2
                                                                   , new PointF(40f, 65f)
                                                                   , new PointF(100f, 0f)
                                                                   , new PointF(1f, 2f)
                                                                   , new PointF(1f, 0.2f)
                                                                   , new Color(1f, 0.2f, 0f)
                                                                   , new Color(0.7f, 0.05f, 0f)
                                                                   , new PointF(30f, 25f)
                                                                   , 100
                                                                   , new PointF(-10f, -20f)
                                                                   , 1f );

        public static RecipeFire missileboatLevel3 = new RecipeFire( R.drawable.missileboat
                                                                   , 3
                                                                   , new PointF(15f, 65f)
                                                                   , new PointF(144f, 0f)
                                                                   , new PointF(3f, 5f)
                                                                   , new PointF(2f, 0.2f)
                                                                   , new Color(1f, 0.2f, 0f)
                                                                   , new Color(0.7f, 0.05f, 0f)
                                                                   , new PointF(20f, 20f)
                                                                   , 100
                                                                   , new PointF(-20f, -40f) );

        public static RecipeFire sailfishLevel1 = new RecipeFire( R.drawable.sailfish
                                                                , 1
                                                                , new PointF(93f, 127f)
                                                                , new PointF(23, 0f)
                                                                , new PointF(1f, 1f)
                                                                , new PointF(1f, 0.2f)
                                                                , new Color(1f, 0.2f, 0f)
                                                                , new Color(0.7f, 0.05f, 0f)
                                                                , new PointF(10f, 10f)
                                                                , 100
                                                                , new PointF(-10f, -20f) );

        public static RecipeFire sailfishLevel2 = new RecipeFire( R.drawable.sailfish
                                                                , 2
                                                                , new PointF(80f, 126f)
                                                                , new PointF(58f, 0f)
                                                                , new PointF(1f, 2f)
                                                                , new PointF(1f, 0.2f)
                                                                , new Color(1f, 0.5f, 0f)
                                                                , new Color(0.7f, 0.05f, 0f)
                                                                , new PointF(30f, 25f)
                                                                , 100
                                                                , new PointF(-10f, -20f)
                                                                , 0.5f );

        public static RecipeFire sailfishLevel3 = new RecipeFire( R.drawable.sailfish
                                                                , 3
                                                                , new PointF(52f, 131f)
                                                                , new PointF(100f, 0f)
                                                                , new PointF(3f, 5f)
                                                                , new PointF(2f, 0.2f)
                                                                , new Color(1f, 0.5f, 0f)
                                                                , new Color(0.7f, 0.05f, 0f)
                                                                , new PointF(20f, 20f)
                                                                , 100
                                                                , new PointF(-20f, -40f) );

        public static RecipeFire battleshipLevel1 = new RecipeFire( R.drawable.battleship
                                                                  , 1
                                                                  , new PointF(93f, 120f)
                                                                  , new PointF(23, 0f)
                                                                  , new PointF(1f, 1f)
                                                                  , new PointF(1f, 0.2f)
                                                                  , new Color(1f, 0.3f, 0f)
                                                                  , new Color(0.7f, 0.05f, 0f)
                                                                  , new PointF(10f, 10f)
                                                                  , 100
                                                                  , new PointF(-10f, -20f) );

        public static RecipeFire battleshipLevel2 = new RecipeFire( R.drawable.battleship
                                                                  , 2
                                                                  , new PointF(55f, 132f)
                                                                  , new PointF(100f, 0f)
                                                                  , new PointF(1f, 2f)
                                                                  , new PointF(1f, 0.2f)
                                                                  , new Color(1f, 0.5f, 0f)
                                                                  , new Color(0.7f, 0.05f, 0f)
                                                                  , new PointF(30f, 25f)
                                                                  , 100
                                                                  , new PointF(-10f, -20f)
                                                                  , 0.5f );

        public static RecipeFire battleshipLevel3 = new RecipeFire( R.drawable.battleship
                                                                  , 3
                                                                  , new PointF(18f, 132f)
                                                                  , new PointF(146f, 0f)
                                                                  , new PointF(3f, 5f)
                                                                  , new PointF(2f, 0.2f)
                                                                  , new Color(1f, 0.5f, 0f)
                                                                  , new Color(0.7f, 0.05f, 0f)
                                                                  , new PointF(20f, 20f)
                                                                  , 100
                                                                  , new PointF(-20f, -40f) );

        public static RecipeFire find(int typeId, int level){
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
    public static class Smoke {
        public static RecipeSmoke missileboatLevel1 = new RecipeSmoke( R.drawable.missileboat
                                                                     , 1
                                                                     , new PointF(110f, 61f)
                                                                     , new PointF(23, 0f) );

        public static RecipeSmoke missileboatLevel2 = new RecipeSmoke( R.drawable.missileboat
                                                                     , 2
                                                                     , new PointF(40f, 65f)
                                                                     , new PointF(100f, 0f) );

        public static RecipeSmoke missileboatLevel3 = new RecipeSmoke( R.drawable.missileboat
                                                                     , 3
                                                                     , new PointF(15f, 65f)
                                                                     , new PointF(144f, 0f)
                                                                     , new PointF(-35f, -45f)
                                                                     , new PointF(0.2f, 3f)
                                                                     , new Color(0.1f, 0.1f, 0.1f)
                                                                     , 0.5f
                                                                     , new PointF(0f, 2f));

        public static RecipeSmoke sailfishLevel1 = new RecipeSmoke( R.drawable.sailfish
                                                                  , 1
                                                                  , new PointF(93f, 127f)
                                                                  , new PointF(23, 0f) );

        public static RecipeSmoke sailfishLevel2 = new RecipeSmoke( R.drawable.sailfish
                                                                  , 2
                                                                  , new PointF(80f, 126f)
                                                                  , new PointF(58f, 0f) );

        public static RecipeSmoke sailfishLevel3 = new RecipeSmoke( R.drawable.sailfish
                                                                  , 3
                                                                  , new PointF(52f, 131f)
                                                                  , new PointF(100f, 0f)
                                                                  , new PointF(-35f, -45f)
                                                                  , new PointF(0.2f, 3f)
                                                                  , new Color(0.1f, 0.1f, 0.1f)
                                                                  , 0.5f
                                                                  , new PointF(0f, 2f));

        public static RecipeSmoke battleshipLevel1 = new RecipeSmoke( R.drawable.battleship
                                                                    , 1
                                                                    , new PointF(93f, 120f)
                                                                    , new PointF(23, 0f) );

        public static RecipeSmoke battleshipLevel2 = new RecipeSmoke( R.drawable.battleship
                                                                    , 2
                                                                    , new PointF(55f, 132f)
                                                                    , new PointF(100f, 0f) );

        public static RecipeSmoke battleshipLevel3 = new RecipeSmoke( R.drawable.battleship
                                                                    , 3
                                                                    , new PointF(18f, 132f)
                                                                    , new PointF(146f, 0f)
                                                                    , new PointF(-35f, -45f)
                                                                    , new PointF(0.2f, 3f)
                                                                    , new Color(0.1f, 0.1f, 0.1f)
                                                                    , 0.5f
                                                                    , new PointF(0f, 2f));

        public static RecipeSmoke find(int typeId, int level) {
            if (typeId == battleshipLevel1.typeId && level == battleshipLevel1.level) {
                return battleshipLevel1;
            } else if (typeId == battleshipLevel2.typeId && level == battleshipLevel2.level) {
                return battleshipLevel2;
            } else if (typeId == battleshipLevel3.typeId && level == battleshipLevel3.level) {
                return battleshipLevel3;
            } else if (typeId == sailfishLevel1.typeId && level == sailfishLevel1.level) {
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
}
