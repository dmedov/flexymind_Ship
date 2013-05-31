package com.example.ship.game.particlesystem;

import android.graphics.PointF;
import org.andengine.util.color.Color;

public class FireRecipe extends Recipe {

    public final PointF lifeTime;
    public final PointF scaleRange;
    public final Color  initFireColor;
    public final Color  endFireColor;
    public final PointF rateRange;
    public final int    particlesMaximum;
    public final PointF velocityYRange;
    public final float  alpha;

    public FireRecipe( int typeId
                     , int level
                     , PointF center
                     , PointF proportions
                     , PointF lifeTime
                     , PointF scaleRange
                     , Color initFireColor
                     , Color endFireColor
                     , PointF rateRange
                     , int particlesMaximum
                     , PointF velocityYRange) {
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

    public FireRecipe( int typeId
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
                     , float alpha) {
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
