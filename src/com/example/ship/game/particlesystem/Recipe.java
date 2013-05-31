package com.example.ship.game.particlesystem;

import android.graphics.PointF;
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


