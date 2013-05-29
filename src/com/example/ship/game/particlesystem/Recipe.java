package com.example.ship.game.particlesystem;

import android.graphics.PointF;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 27.05.13
 * Time: 11:28
 * Класс рецептов, в них описываются настройки нужного эффекта
 */
public class Recipe {
    public final int      typeId;
    public final int      level;
    public final PointF   center;
    public final PointF   proportions;
    public Recipe(int typeId, int level, PointF center, PointF proportions) {
        this.typeId =           typeId;
        this.level =            level;
        this.center =          center;
        this.proportions =     proportions;
    }
    public String toString() {
        String string = "";
        string += "typeId: "        + ((Integer) typeId).toString()+"\n";
        string += "level: "         + ((Integer) level).toString()+"\n";
        string += "center: "        + center.toString()+"\n";
        string += "proportions: "   + proportions.toString()+"\n";
        return string;
    }
}


class RecipeFire extends Recipe {

    public final PointF    lifeTime;
    public final PointF   scaleRange;
    public final Color    initFireColor;
    public final Color    endFireColor;
    public final PointF    rateRange;
    public final int      particlesMaximum;
    public final PointF    velocityYRange;
    public final float    alpha;

    public RecipeFire( int typeId
                     , int level
                     , PointF center
                     , PointF proportions
                     , PointF lifeTime
                     , PointF scaleRange
                     , Color initFireColor
                     , Color endFireColor
                     , PointF rateRange
                     , int particlesMaximum
                     , PointF velocityYRange ) {
        super(typeId, level, center, proportions);
        this.lifeTime =         lifeTime;
        this.scaleRange =       scaleRange;
        this.initFireColor =    initFireColor;
        this.endFireColor =     endFireColor;
        this.rateRange =        rateRange;
        this.particlesMaximum = particlesMaximum;
        this.velocityYRange =     velocityYRange;
        this.alpha =            1f;
    }

    public RecipeFire( int typeId
                     , int level
                     , PointF center
                     , PointF proportions
                     , PointF lifeTime
                     , PointF scaleRange
                     , Color initFireColor
                     , Color endFireColor
                     , PointF rateRange
                     , int particlesMaximum
                     , PointF velocityYRange
                     , float alpha ) {
        super(typeId, level, center, proportions);
        this.lifeTime =         lifeTime;
        this.scaleRange =       scaleRange;
        this.initFireColor =    initFireColor;
        this.endFireColor =     endFireColor;
        this.rateRange =      rateRange;
        this.particlesMaximum = particlesMaximum;
        this.velocityYRange =     velocityYRange;
        this.alpha =            alpha;
    }

    public String toString() {
        String string = "";
        string += super.toString();
        string += "lifeTime: "          + lifeTime.toString()+"\n";
        string += "scaleRange: "        + scaleRange.toString()+"\n";
        string += "initFireColor: "     + initFireColor.toString()+"\n";
        string += "endFireColor: "      + endFireColor.toString()+"\n";
        string += "rateRange: "         + rateRange.toString()+"\n";
        string += "particlesMaximum: "  + ((Integer) particlesMaximum).toString()+"\n";
        string += "velocityYRange: "    + velocityYRange.toString()+"\n";
        string += "alpha: "             + ((Float) alpha).toString()+"\n";
        return string;
    }
}
class RecipeSmoke extends Recipe {
    public final PointF   velocityYRange;
    public final Color    endFireColor;
    public final float    alpha;
    public final PointF   scaleRange;
    public final PointF   scaleTime;


    RecipeSmoke(int typeId, int level, PointF center, PointF proportions) {
        super(typeId, level, center, proportions);
        this.scaleRange =       new PointF(0.2f, 1f);
        this.endFireColor =     new Color(0.1f, 0.1f, 0.1f);
        this.velocityYRange =     new PointF(-20, -15f);
        this.alpha =            1f;
        this.scaleTime =        new PointF(0f, 4.5f);
    }
    RecipeSmoke( int typeId
               , int level
               , PointF center
               , PointF proportions
               , PointF velocityRange
               , PointF scaleRange
               , Color endFireColor
               , float alpha
               , PointF scaleTime ) {
        super(typeId, level, center, proportions);
        this.scaleRange =       scaleRange;
        this.endFireColor =     endFireColor;
        this.velocityYRange =     velocityRange;
        this.alpha =            alpha;
        this.scaleTime =        scaleTime;

    }
    public String toString() {
        String string = "";
        string += super.toString();
        string += "scaleRange: "    + scaleRange.toString()+"\n";
        string += "scaleTime: "     + scaleTime.toString()+"\n";
        string += "endFireColor: "  + endFireColor.toString()+"\n";
        string += "velocityRange: " + velocityYRange.toString()+"\n";
        string += "alpha: "         + ((Float) alpha).toString()+"\n";
        return string;

    }
}
