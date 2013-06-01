package com.example.ship;

import android.util.Log;
import com.example.ship.game.GameScene;
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
    public static final int HIGH_SCORES_STATE = 5;

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
        menuScene.switchToMenuHud();

        manageSound(MENU_STATE);

        currentState = MENU_STATE;
    }

    public void switchToMenuHud() {
        menuScene.switchToMenuHud();

        currentState = MENU_STATE;
    }


    public void switchToHighScoresHUD() {
        menuScene.switchToHighScoresHud();

        currentState = HIGH_SCORES_STATE;
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
        manageSound(GAME_STATE);

        currentState = GAME_STATE;
    }

    public void switchToGameHUD() {
        if (gameScene.isIgnoreUpdate()) {
            gameScene.setIgnoreUpdate(false);
        }
        gameScene.switchToGameHUD();
        gameScene.getPlayer().getLevel().resumeSpawn();
        gameScene.getGun().resumeFireTimer();
        if (!activity.getEngine().isRunning()) {
            activity.getEngine().start();
        }
        manageSound(GAME_STATE);

        currentState = GAME_STATE;
    }

    public void switchToPauseHUD() {
        gameScene.setIgnoreUpdate(true);
        gameScene.switchToPauseHUD();
        gameScene.getPlayer().getLevel().pauseSpawn();
        gameScene.getGun().pauseFireTimer();
        manageSound(PAUSE_STATE);

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
        manageSound(GAME_OVER_STATE);

        currentState = GAME_OVER_STATE;
    }

    public int getCurrentState() {
        return currentState;
    }

    public RootScene getRootScene() {
        return rootScene;
    }

    public ShipMenuScene getMenuScene() {
        return menuScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void manageSound(int state) {
        ResourceManager resourceManager = activity.getResourceManager();
        resourceManager.pauseAllMusic();
        resourceManager.pauseAllSound();

        switch (state) {
            case MENU_STATE :
                resourceManager.playLoopMusic(R.raw.m_menu_music, resourceManager.FROM_THE_BEGINING);
                break;
            case GAME_STATE :
                resourceManager.playMusicRandom( activity.getIntResource(R.integer.GAME_SCENE_MUSIC_VOLUME)
                                               , R.raw.m_menu_music
                                               , R.raw.m_game_music);

                resourceManager.playLoopMusic( R.raw.m_waves
                                             , activity.getIntResource(R.integer.GAME_SCENE_WAVES_VOLUME) );
                break;
            case GAME_OVER_STATE :
                resourceManager.playLoopMusic( R.raw.m_game_over
                        , activity.getIntResource(R.integer.GAME_OVER_SCENE_VOLUME) );
                break;
        }
    }

}
