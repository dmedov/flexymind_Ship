package com.example.ship.level;

import android.util.Log;
import com.example.ship.R;
import com.example.ship.RootActivity;
import com.example.ship.game.GameScene;
import com.example.ship.game.Ship;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 24.05.13
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class PeriodicalShipSpawner extends ShipSpawner {

    private int numberOfShips;

    public PeriodicalShipSpawner(RootActivity activity, float firstSpawnIn, float spawnDelay, int number) {
        super(activity, firstSpawnIn, spawnDelay);
        this.numberOfShips = number;
    }

    @Override
    public void startSpawn() {
        timerHandler = new PauseableTimerHandler(delay, new TimerTask());
        super.startSpawn();
    }

    private class TimerTask implements ITimerCallback {

        Random rnd;

        private TimerTask() {
            rnd = new Random();
        }

        @Override
        public void onTimePassed(final TimerHandler timerHandler) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.d("1log", "new ship in..." + spawnDelay);
                    timerHandler.setTimerSeconds(spawnDelay);
                    timerHandler.reset();

                    int layerId = selectLayer();

                    GameScene gameScene = activity.getSceneSwitcher().getGameScene();
                    Ship ship = new Ship( activity
                            , gameScene.getShipLinePosition(layerId)
                            , selectShipType()
                            , rnd.nextBoolean());
                    gameScene.getShips().add(ship);
                    gameScene.getChildByIndex(layerId).attachChild(ship.getSprite());

                    if (--numberOfShips == 0) {
                        stopSpawn();
                    }
                }
            });
        }

        private int selectShipType() {
            int rndValue = rnd.nextInt(100) + 1;

            if (rndValue < activity.getIntResource(R.integer.SAILFISH_SPAWN_PROBABILITY)) {
                return R.drawable.sailfish;
            } else {
                rndValue -= activity.getIntResource(R.integer.SAILFISH_SPAWN_PROBABILITY);
            }

            if (rndValue < activity.getIntResource(R.integer.BATTLESHIP_SPAWN_PROBABILITY)) {
                return R.drawable.battleship;
            } else {
                rndValue -= activity.getIntResource(R.integer.BATTLESHIP_SPAWN_PROBABILITY);
            }

            return R.drawable.missileboat;
        }

        private int selectLayer() {
            int layerId = rnd.nextInt(3);
            switch (layerId) {
                case 0:
                    layerId = GameScene.LAYER_FIRST_SHIP_LINE;
                    break;
                case 1:
                    layerId = GameScene.LAYER_SECOND_SHIP_LINE;
                    break;
                case 2:
                    layerId = GameScene.LAYER_THIRD_SHIP_LINE;
                    break;
                default:
                    layerId = GameScene.LAYER_FIRST_SHIP_LINE;
                    break;
            }
            return layerId;
        }
    }
}
