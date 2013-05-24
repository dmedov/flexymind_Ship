package com.example.ship.bonus;

import android.graphics.PointF;
import com.example.ship.R;
import com.example.ship.commons.CSprite;
import com.example.ship.game.Ship;
import org.andengine.entity.sprite.Sprite;

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

    public Bonus(PointF bonusLocation, int type) {
        bonusSrite = new CSprite(bonusLocation, R.drawable.bonus);

    }

    public Bonus(Ship killedShip) {
        Sprite killedShipSprite = killedShip.getSprite();
        PointF killedShipCenter = ((CSprite)killedShipSprite).getCenter();
        bonusSrite = new CSprite(killedShipCenter, R.drawable.bonus);
        killedShip.getSprite().getParent().attachChild(bonusSrite);
    }

    static void setBonusShipKillProbability(float probability) {
        bonusShipKillProbability = probability;
    }

    public static boolean canCreate() {
        return true;
    }
}

