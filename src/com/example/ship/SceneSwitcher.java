package com.example.ship;

import com.example.ship.game.GameScene;
import com.example.ship.game.ShipSpawner;
import com.example.ship.menu.MenuHUD;
import com.example.ship.menu.ShipMenuScene;
import com.example.ship.resource.ResourceManager;
import com.example.ship.root.RootScene;

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
    private RootScene rootScene;
    private ShipMenuScene menuScene;
    private GameScene gameScene;
    private int currentState;

    public SceneSwitcher(RootActivity activity) {
        this.activity = activity;
        menuScene = new ShipMenuScene(activity);
        gameScene = new GameScene(activity);
        rootScene = new RootScene(activity);
        rootScene.setEvents(activity.getEvents());
        manageSound(ROOT_STATE);

        currentState = ROOT_STATE;
    }

    public void switchToRootScene() {
        activity.getCamera().setHUD(null);
        rootScene.clearChildScene();
        rootScene.registerTouchArea();
        manageSound(ROOT_STATE);

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
        manageSound(MENU_STATE);

        currentState = MENU_STATE;
    }

    public void switchToGameScene() {
        if (rootScene.hasChildScene()) {
            rootScene.clearChildScene();
        }
        rootScene.unregisterTouchArea();
        gameScene.resetGame();
        rootScene.setChildScene(gameScene);
        ShipSpawner shipSpawner = new ShipSpawner(activity);
        gameScene.setShipSpawner(shipSpawner);
        gameScene.getPlayer().getLevel().startLevel(1);
        switchToGameHUD();
        manageSound(GAME_STATE);

        currentState = GAME_STATE;
    }

    public void switchToGameHUD() {
        if (gameScene.isIgnoreUpdate()) {
            gameScene.setIgnoreUpdate(false);
        }
        gameScene.switchToGameHUD();
        gameScene.getShipSpawner().startSpawn();
        if (!activity.getEngine().isRunning()) {
            activity.getEngine().start();
        }
        manageSound(GAME_STATE);

        currentState = GAME_STATE;
    }

    public void switchToPauseHUD() {
        gameScene.setIgnoreUpdate(true);
        gameScene.switchToPauseHUD();
        gameScene.getShipSpawner().stopSpawn();
        manageSound(PAUSE_STATE);

        currentState = PAUSE_STATE;
    }

    public void switchToGameOverHUD() {
        gameScene.getShipSpawner().stopSpawn();
        gameScene.setIgnoreUpdate(true);
        gameScene.switchToGameOverHUD();
        manageSound(GAME_OVER_STATE);

        currentState = GAME_OVER_STATE;
    }

    public int getCurrentState() {
        return currentState;
    }

    public RootScene getRootScene() {
        return rootScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void manageSound(int state) {
        ResourceManager resourceManager = activity.getResourceManager();
        activity.getResourceManager().pauseAllMusic();
        switch (state) {
            case MENU_STATE :
                resourceManager.playLoopMusic(R.raw.m_menu_music, resourceManager.FROM_THE_BEGINING );
                break;
            case GAME_STATE :
                resourceManager.playLoopMusic(R.raw.m_game_music, 0.4f);
                break;
        }
    }
}
