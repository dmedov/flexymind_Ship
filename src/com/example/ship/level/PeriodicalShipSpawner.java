package com.example.ship.level;

import android.util.Log;
import com.example.ship.R;
import com.example.ship.RootActivity;
import com.example.ship.game.GameScene;
import com.example.ship.game.Ship;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import java.util.ArrayList;
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
    private String shipType;
    private boolean direction;
    private ArrayList<Integer> lines;

    public PeriodicalShipSpawner(RootActivity activity, float firstSpawnIn, float spawnDelay, int number) {
        super(activity, firstSpawnIn, spawnDelay);
        this.numberOfShips = number;
    }

    @Override
    public void startSpawn() {
        timerHandler = new PauseableTimerHandler(delay, new TimerTask());
        super.startSpawn();
    }


    public void setShipParameters(String shipType, String direction, String lines) {

        this.shipType = shipType;

        if (direction == null) {
            this.direction = new Random().nextBoolean();
        } else if (direction.contains("left")) {
            this.direction = Ship.TO_LEFT;
        } else {
            this.direction = Ship.TO_RIGHT;
        }

        this.lines = new ArrayList<Integer>();
        if (lines == null || !(lines.contains("1") || lines.contains("2") || lines.contains("3"))) {
            this.lines.add(GameScene.LAYER_FIRST_SHIP_LINE);
            this.lines.add(GameScene.LAYER_SECOND_SHIP_LINE);
            this.lines.add(GameScene.LAYER_THIRD_SHIP_LINE);
        } else {
            if (lines.contains("1")) {
                this.lines.add(GameScene.LAYER_FIRST_SHIP_LINE);
            }
            if (lines.contains("2")) {
                this.lines.add(GameScene.LAYER_SECOND_SHIP_LINE);
            }
            if (lines.contains("3")) {
                this.lines.add(GameScene.LAYER_THIRD_SHIP_LINE);
            }
        }
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
                                        , shipType
                                        , direction);
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
            return lines.get(new Random().nextInt(lines.size()));
        }
    }

}
