package com.example.ship.game.particlesystem;

import android.graphics.Point;
import android.graphics.PointF;
import org.andengine.util.color.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 27.05.13
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class Recipe {
    public final int      typeId;
    public final int      level;
    public final float    centerX;
    public final float    centerY;
    public final float    width;
    public final float    height;
    public Recipe(int typeId, int level, float centerX, float centerY, float width, float height) {
        this.typeId =           typeId;
        this.level =            level;
        this.centerX =          centerX;
        this.centerY =          centerY;
        this.width =            width;
        this.height =           height;
    }
    public String toString() {
        String string = "";
        string += "typeId: "    + ((Integer) typeId).toString()+"\n";
        string += "level: "     + ((Integer) level).toString()+"\n";
        string += "centerX: "   + ((Float) centerX).toString()+"\n";
        string += "centerY: "   + ((Float) centerY).toString()+"\n";
        string += "width: "     + ((Float) width).toString()+"\n";
        string += "height: "    + ((Float) height).toString()+"\n";
        return string;
    }
}


class RecipeFire extends Recipe {

    public final Point    lifeTime;
    public final PointF   scaleRange;
    public final Color    initFireColor;
    public final Color    endFireColor;
    public final float    rateMinimum;
    public final float    rateMaximum;
    public final int      particlesMaximum;
    public final float    minVelocityY;
    public final float    maxVelocityY;
    public final float    alpha;

    public RecipeFire( int typeId
                     , int level
                     , float centerX
                     , float centerY
                     , float width
                     , float height
                     , Point lifeTime
                     , PointF scaleRange
                     , Color initFireColor
                     , Color endFireColor
                     , float rateMinimum
                     , float rateMaximum
                     , int particlesMaximum
                     , float minVelocityY
                     , float maxVelocityY ) {
        super(typeId, level, centerX, centerY, width, height);
        this.lifeTime =         lifeTime;
        this.scaleRange =       scaleRange;
        this.initFireColor =    initFireColor;
        this.endFireColor =     endFireColor;
        this.rateMinimum =      rateMinimum;
        this.rateMaximum =      rateMaximum;
        this.particlesMaximum = particlesMaximum;
        this.minVelocityY =     minVelocityY;
        this.maxVelocityY =     maxVelocityY;
        this.alpha =            1f;
    }

    public RecipeFire( int typeId
                     , int level
                     , float centerX
                     , float centerY
                     , float width
                     , float height
                     , Point lifeTime
                     , PointF scaleRange
                     , Color initFireColor
                     , Color endFireColor
                     , float rateMinimum
                     , float rateMaximum
                     , int particlesMaximum
                     , float minVelocityY
                     , float maxVelocityY
                     , float alpha ) {
        super(typeId, level, centerX, centerY, width, height);
        this.lifeTime =         lifeTime;
        this.scaleRange =       scaleRange;
        this.initFireColor =    initFireColor;
        this.endFireColor =     endFireColor;
        this.rateMinimum =      rateMinimum;
        this.rateMaximum =      rateMaximum;
        this.particlesMaximum = particlesMaximum;
        this.minVelocityY =     minVelocityY;
        this.maxVelocityY =     maxVelocityY;
        this.alpha =            alpha;
    }

    public String toString() {
        String string = "";
        string += super.toString();
        string += "lifeTime: " + lifeTime.toString()+"\n";
        string += "scaleRange: " + scaleRange.toString()+"\n";
        string += "initFireColor: " + initFireColor.toString()+"\n";
        string += "endFireColor: " + endFireColor.toString()+"\n";
        string += "rateMinimum: " + ((Float) rateMinimum).toString()+"\n";
        string += "rateMaximum: " + ((Float) rateMaximum).toString()+"\n";
        string += "particlesMaximum: " + ((Integer) particlesMaximum).toString()+"\n";
        string += "minVelocityY: " + ((Float) minVelocityY).toString()+"\n";
        string += "maxVelocityY: " + ((Float) maxVelocityY).toString()+"\n";
        string += "alpha: " + ((Float) alpha).toString()+"\n";
        return string;
    }
}
class RecipeSmoke extends Recipe {
    public final float    minVelocityY;
    public final float    maxVelocityY;
    public final Color    endFireColor;
    public final float    alpha;
    public final PointF   scaleRange;
    public final PointF   scaleTime;


    RecipeSmoke(int typeId, int level, float centerX, float centerY, float width, float height){
        super(typeId, level, centerX, centerY, width, height);
        this.scaleRange =       new PointF(0.2f, 1f);
        this.endFireColor =     new Color(0.3f, 0.3f, 0.3f);
        this.minVelocityY =     -15f;
        this.maxVelocityY =     -20f;
        this.alpha =            1f;
        this.scaleTime =        new PointF(0f, 4.5f);
    }
    RecipeSmoke( int typeId
               , int level
               , float centerX
               , float centerY
               , float width
               , float height
               , float minVelocityY
               , float maxVelocityY
               , PointF scaleRange
               , Color endFireColor
               , float alpha
               , PointF scaleTime ) {
        super(typeId, level, centerX, centerY, width, height);
        this.scaleRange =       scaleRange;
        this.endFireColor =     endFireColor;
        this.minVelocityY =     minVelocityY;
        this.maxVelocityY =     maxVelocityY;
        this.alpha =            alpha;
        this.scaleTime =        scaleTime;

    }
    public String toString() {
        String string = "";
        string += super.toString();
        string += "scaleRange: "    + scaleRange.toString()+"\n";
        string += "scaleTime: "     + scaleTime.toString()+"\n";
        string += "endFireColor: "  + endFireColor.toString()+"\n";
        string += "minVelocityY: "  + ((Float) minVelocityY).toString()+"\n";
        string += "maxVelocityY: "  + ((Float) maxVelocityY).toString()+"\n";
        string += "alpha: "         + ((Float) alpha).toString()+"\n";
        return string;

    }
}
