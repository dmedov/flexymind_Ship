package com.example.ship;

import com.example.ship.menu.ShipMenuScene;
import com.example.ship.game.GameScene;
import com.example.ship.sceletone.SceletonScene;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 28.04.13
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class SceneSwitcher {

    private final SceletonActivity activity;
    private SceletonScene rootScene;
    private ShipMenuScene menuScene;
    private GameScene gameScene;

    public SceneSwitcher(SceletonActivity activity) {
        this.activity = activity;
        menuScene = new ShipMenuScene(activity);
        gameScene = new GameScene(activity);
        rootScene = new SceletonScene(activity);
        rootScene.setEvents(activity.getEvents());
    }

    public void switchToRootScene() {
        activity.getCamera().setHUD(null);
        rootScene.clearChildScene();
    }

    public void switchToMenuScene() {
        if (rootScene.hasChildScene()) {
            rootScene.clearChildScene();
        }
        rootScene.setChildScene(menuScene);

        MenuHUD menuHUD = new MenuHUD(activity);
        menuHUD.setEventsToChildren(activity.getEvents());

        activity.getCamera().setHUD(menuHUD);
    }

    public void switchToGameScene() {
        if (rootScene.hasChildScene()) {
            rootScene.clearChildScene();
        }
        rootScene.setChildScene(gameScene);
        activity.getCamera().setHUD(null);
    }

    public SceletonScene getRootScene() {
        return rootScene;
    }
}
