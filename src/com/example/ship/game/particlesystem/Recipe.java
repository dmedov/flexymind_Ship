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
    public int      typeId;
    public int      level;
    public float    centerX;
    public float    centerY;
    public float    width;
    public float    height;
    public Point    lifeTime;
    public PointF   scaleRange;
    public Color    initFireColor;
    public Color    endFireColor;
    public float    rateMinimum;
    public float    rateMaximum;
    public int      particlesMaximum;
    public float    minVelocityY;
    public float    maxVelocityY;
    public float    alpha;

    public Recipe( int typeId, int level, float centerX, float centerY, float width, float height, Point lifeTime
                 , PointF scaleRange, Color initFireColor, Color endFireColor, float rateMinimum, float rateMaximum
                 , int particlesMaximum, float minVelocityY, float maxVelocityY) {
        this.typeId =           typeId;
        this.level =            level;
        this.centerX =          centerX;
        this.centerY =          centerY;
        this.width =            width;
        this.height =           height;
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
    public Recipe( int typeId, int level, float centerX, float centerY, float width, float height, Point lifeTime
            , PointF scaleRange, Color initFireColor, Color endFireColor, float rateMinimum, float rateMaximum
            , int particlesMaximum, float minVelocityY, float maxVelocityY, float alpha) {
        this.typeId =           typeId;
        this.level =            level;
        this.centerX =          centerX;
        this.centerY =          centerY;
        this.width =            width;
        this.height =           height;
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
        String string = new String();
        string += "typeId: " + ((Integer) typeId).toString()+"\n";
        string += "level: " + ((Integer) level).toString()+"\n";
        string += "centerX: " + ((Float) centerX).toString()+"\n";
        string += "centerY: " + ((Float) centerY).toString()+"\n";
        string += "width: " + ((Float) width).toString()+"\n";
        string += "height: " + ((Float) height).toString()+"\n";
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
