package com.example.ship.bonus;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 5/24/13
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class CRandom {
    static Random rnd = new Random();

    public static boolean play(float propability) {
        float randomValue = rnd.nextFloat();
        return randomValue < propability;
    }
}
