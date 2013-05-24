package com.example.ship;

import android.util.Log;
import com.example.ship.game.GameScene;
import com.example.ship.menu.MenuHUD;
import com.example.ship.menu.ShipMenuScene;
import com.example.ship.sceletone.SceletonScene;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 28.04.13
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class SceneSwitcher {

    public static final int ROOT_STATE = 0;
    public static final int MENU_STATE = 1;
    public static final int GAME_STATE = 2;
    public static final int PAUSE_STATE = 3;
    public static final int GAME_OVER_STATE = 4;

    private final RootActivity activity;
    private SceletonScene rootScene;
    private ShipMenuScene menuScene;
    private GameScene gameScene;
    private int currentState;

    public SceneSwitcher(RootActivity activity) {
        this.activity = activity;
        menuScene = new ShipMenuScene(activity);
        gameScene = new GameScene(activity);
        rootScene = new SceletonScene(activity);
        rootScene.setEvents(activity.getEvents());

        currentState = ROOT_STATE;
    }

    public void switchToRootScene() {
        activity.getCamera().setHUD(null);
        rootScene.clearChildScene();
        rootScene.registerTouchArea();

        currentState = ROOT_STATE;
    }

    public void switchToMenuScene() {
        if (rootScene.hasChildScene()) {
            rootScene.clearChildScene();
        }
        rootScene.unregisterTouchArea();
        rootScene.setChildScene(menuScene);

        MenuHUD menuHUD = new MenuHUD(activity);
        menuHUD.setEventsToChildren(activity.getEvents());
        activity.getCamera().setHUD(menuHUD);

        currentState = MENU_STATE;
    }

    public void switchToGameScene() {
        if (rootScene.hasChildScene()) {
            rootScene.clearChildScene();
        }
        rootScene.unregisterTouchArea();
        gameScene.resetGame();
        rootScene.setChildScene(gameScene);
        gameScene.getPlayer().getLevel().startLevel(1);
        switchToGameHUD();
        currentState = GAME_STATE;
    }

    public void switchToGameHUD() {
        if (gameScene.isIgnoreUpdate()) {
            gameScene.setIgnoreUpdate(false);
        }
        gameScene.switchToGameHUD();
        gameScene.getPlayer().getLevel().resumeSpawn();
        if (!activity.getEngine().isRunning()) {
            activity.getEngine().start();
        }

        currentState = GAME_STATE;
    }

    public void switchToPauseHUD() {
        gameScene.setIgnoreUpdate(true);
        gameScene.switchToPauseHUD();
        gameScene.getPlayer().getLevel().pauseSpawn();
        currentState = PAUSE_STATE;
    }

    public void switchToGameOverHUD() {
        gameScene.getPlayer().getLevel().pauseSpawn();
        gameScene.setIgnoreUpdate(true);
        if (gameScene.getPlayer().getHealth() > 0) {
            gameScene.getGameOverHUD().setWinOrLooseText(activity.getStringResource(R.string.WIN_LABEL));
            Log.d("1log", "победа");
        } else {
            gameScene.getGameOverHUD().setWinOrLooseText(activity.getStringResource(R.string.LOOSE_LABEL));
            Log.d("1log", "поражение");
        }
        gameScene.switchToGameOverHUD();
        currentState = GAME_OVER_STATE;
    }

    public int getCurrentState() {
        return currentState;
    }

    public SceletonScene getRootScene() {
        return rootScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }
}
