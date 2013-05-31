package com.example.ship.game.particlesystem;

import android.graphics.PointF;
import org.andengine.util.color.Color;

public class SmokeRecipe extends Recipe {
    public final PointF velocityYRange;
    public final Color  endFireColor;
    public final float  alpha;
    public final PointF scaleRange;
    public final PointF scaleTime;


    SmokeRecipe(int typeId, int level, PointF center, PointF proportions) {
        super(typeId, level, center, proportions);
        this.scaleRange =       new PointF(0.2f, 1f);
        this.endFireColor =     new Color(0.1f, 0.1f, 0.1f);
        this.velocityYRange =   new PointF(-20, -15f);
        this.alpha =            1f;
        this.scaleTime =        new PointF(0f, 4.5f);
    }
    SmokeRecipe( int typeId
               , int level
               , PointF center
               , PointF proportions
               , PointF velocityRange
               , PointF scaleRange
               , Color endFireColor
               , float alpha
               , PointF scaleTime) {
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
