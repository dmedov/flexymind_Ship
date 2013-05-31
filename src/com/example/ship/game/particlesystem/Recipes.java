package com.example.ship.game.particlesystem;


import android.graphics.PointF;
import com.example.ship.R;
import org.andengine.util.color.Color;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 27.05.13
 * Time: 11:45
 * Класс для удобного хранения настроек эффектов
 */
public class Recipes {
    public static class Fire {
        private static ArrayList<RecipeFire> fieryRecipes;

        static {
            fieryRecipes = new ArrayList<RecipeFire>();
            fieryRecipes.add(new RecipeFire( R.drawable.missileboat
                                           , 1
                                           , new PointF(110f, 61f)
                                           , new PointF(23, 0f)
                                           , new PointF(1f, 1f)
                                           , new PointF(1f, 0.2f)
                                           , new Color(1f, 0.3f, 0f)
                                           , new Color(0.7f, 0.05f, 0f)
                                           , new PointF(10f, 10f)
                                           , 100
                                           , new PointF(-10f, -20f) ));
            fieryRecipes.add(new RecipeFire( R.drawable.missileboat
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
                                           , 1f ));
            fieryRecipes.add(new RecipeFire( R.drawable.missileboat
                                           , 3
                                           , new PointF(15f, 65f)
                                           , new PointF(144f, 0f)
                                           , new PointF(3f, 5f)
                                           , new PointF(2f, 0.2f)
                                           , new Color(1f, 0.2f, 0f)
                                           , new Color(0.7f, 0.05f, 0f)
                                           , new PointF(20f, 20f)
                                           , 100
                                           , new PointF(-20f, -40f) ));
            fieryRecipes.add(new RecipeFire( R.drawable.sailfish
                                           , 1
                                           , new PointF(93f, 127f)
                                           , new PointF(23, 0f)
                                           , new PointF(1f, 1f)
                                           , new PointF(1f, 0.2f)
                                           , new Color(1f, 0.2f, 0f)
                                           , new Color(0.7f, 0.05f, 0f)
                                           , new PointF(10f, 10f)
                                           , 100
                                           , new PointF(-10f, -20f) ));
            fieryRecipes.add(new RecipeFire( R.drawable.sailfish
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
                                           , 0.5f ));
            fieryRecipes.add(new RecipeFire( R.drawable.sailfish
                                           , 3
                                           , new PointF(52f, 131f)
                                           , new PointF(100f, 0f)
                                           , new PointF(3f, 5f)
                                           , new PointF(2f, 0.2f)
                                           , new Color(1f, 0.5f, 0f)
                                           , new Color(0.7f, 0.05f, 0f)
                                           , new PointF(20f, 20f)
                                           , 100
                                           , new PointF(-20f, -40f) ));
            fieryRecipes.add(new RecipeFire( R.drawable.battleship
                                           , 1
                                           , new PointF(93f, 120f)
                                           , new PointF(23, 0f)
                                           , new PointF(1f, 1f)
                                           , new PointF(1f, 0.2f)
                                           , new Color(1f, 0.3f, 0f)
                                           , new Color(0.7f, 0.05f, 0f)
                                           , new PointF(10f, 10f)
                                           , 100
                                            , new PointF(-10f, -20f) ));
            fieryRecipes.add(new RecipeFire( R.drawable.battleship
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
                                           , 0.5f ));
            fieryRecipes.add(new RecipeFire( R.drawable.battleship
                                           , 3
                                           , new PointF(18f, 132f)
                                           , new PointF(146f, 0f)
                                           , new PointF(3f, 5f)
                                           , new PointF(2f, 0.2f)
                                           , new Color(1f, 0.5f, 0f)
                                           , new Color(0.7f, 0.05f, 0f)
                                           , new PointF(20f, 20f)
                                           , 100
                                           , new PointF(-20f, -40f) ));
        }

        public static RecipeFire find(int typeId, int level) {
            final int FIRST = 0;
            for (RecipeFire recipe : fieryRecipes) {
                if (typeId == recipe.typeId && level == recipe.level) {
                    return recipe;
                }
            }
            return fieryRecipes.get(FIRST);
        }
    }
    public static class Smoke {

        private static ArrayList<RecipeSmoke> smokyRecipes;

        static {
            smokyRecipes = new ArrayList<RecipeSmoke>();
            smokyRecipes.add(new RecipeSmoke( R.drawable.missileboat
                                            , 1
                                            , new PointF(110f, 61f)
                                            , new PointF(23, 0f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.missileboat
                                            , 2
                                            , new PointF(40f, 65f)
                                            , new PointF(100f, 0f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.missileboat
                                            , 3
                                            , new PointF(15f, 65f)
                                            , new PointF(144f, 0f)
                                            , new PointF(-35f, -45f)
                                            , new PointF(0.2f, 3f)
                                            , new Color(0.1f, 0.1f, 0.1f)
                                            , 0.5f
                                            , new PointF(0f, 2f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.sailfish
                                            , 1
                                            , new PointF(93f, 127f)
                                            , new PointF(23, 0f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.sailfish
                                            , 2
                                            , new PointF(80f, 126f)
                                            , new PointF(58f, 0f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.sailfish
                                            , 3
                                            , new PointF(52f, 131f)
                                            , new PointF(100f, 0f)
                                            , new PointF(-35f, -45f)
                                            , new PointF(0.2f, 3f)
                                            , new Color(0.1f, 0.1f, 0.1f)
                                            , 0.5f
                                            , new PointF(0f, 2f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.battleship
                                            , 1
                                            , new PointF(93f, 120f)
                                            , new PointF(23, 0f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.battleship
                                            , 2
                                            , new PointF(55f, 132f)
                                            , new PointF(100f, 0f) ));
            smokyRecipes.add(new RecipeSmoke( R.drawable.battleship
                                            , 3
                                            , new PointF(18f, 132f)
                                            , new PointF(146f, 0f)
                                            , new PointF(-35f, -45f)
                                            , new PointF(0.2f, 3f)
                                            , new Color(0.1f, 0.1f, 0.1f)
                                            , 0.5f
                                            , new PointF(0f, 2f) ));

        }

        public static RecipeSmoke find(int typeId, int level) {
            final int FIRST = 0;
            for (RecipeSmoke recipe : smokyRecipes) {
                if (typeId == recipe.typeId && level == recipe.level) {
                    return recipe;
                }
            }
            return smokyRecipes.get(FIRST);
        }
    }
}
