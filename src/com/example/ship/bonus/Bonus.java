package com.example.ship.bonus;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.commons.CSprite;
import com.example.ship.game.Ship;

/**
 * Created with IntelliJ IDEA.
 * User: Denis
 * Date: 5/24/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */

public class Bonus {
    static private float bonusShipKillProbability = 0.1f;
    static private float bonusGoodPropability = 0.7f;

    private CSprite bonusSrite;
    Bonus(PointF bonusLocation, int type) {

        bonusSrite = new CSprite(bonusLocation, R.drawable.bonus);
    }

    Bonus(Ship killedShip) {
        PointF killedShipCenter =
        bonusSrite = new CSprite(killedShip.getSprite().getX, R.drawable.bonus);
    }

    boolean isGood() {

    }


    private void createSprite() {

    }

    static void setBonusShipKillProbability(float probability) {
        bonusShipKillProbability = probability;
    }

    static boolean canCreate() {

    }




}

