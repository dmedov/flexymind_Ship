package com.example.ship;

import android.widget.Toast;
import com.example.ship.Menu.MenuButtonSprite;
import com.example.ship.Menu.ShipMenuScene;
import com.example.ship.Menu.TouchableMenuButtonSprite;
import com.example.ship.sceletone.TouchableSceletonSprite;
import org.andengine.entity.scene.Scene;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 26.04.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class Events implements TouchableMenuButtonSprite
                             , TouchableSceletonSprite {

    private final SceletonActivity activity;

    public Events(SceletonActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onAreaButtonMenuTouched(final MenuButtonSprite button) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (button.getId()) {
                    case R.string.MENU_START_BUTTON:
                        button.setPosition(button.getX(), button.getY() - 10);
                        startGame(button);
                        break;
                    case R.string.MENU_HS_BUTTON:
                        button.setPosition(button.getX(), button.getY() - 10);
                        showHighscores(button);
                        break;
                    case R.string.MENU_CREDITS_BUTTON:
                        button.setPosition(button.getX(), button.getY() - 10);
                        showCredits(button);
                        break;
                    case R.string.MENU_EXIT_BUTTON:
                        button.setPosition(button.getX(), button.getY() - 10);
                        exitApplication(button);
                        break;
                    default:
                        Toast.makeText(activity.getApplicationContext(), "Unknown Button Pressed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAreaButtonMenuReleased(final MenuButtonSprite button) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (button.getId()) {
                    case R.string.MENU_START_BUTTON:
                        button.setPosition(button.getX(), button.getY() + 10);
                        startGame(button);
                        break;
                    case R.string.MENU_HS_BUTTON:
                        button.setPosition(button.getX(), button.getY() + 10);
                        //showHighscores(button);
                        break;
                    case R.string.MENU_CREDITS_BUTTON:
                        button.setPosition(button.getX(), button.getY() + 10);
                        //showCredits(button);
                        break;
                    case R.string.MENU_EXIT_BUTTON:
                        button.setPosition(button.getX(), button.getY() + 10);
                        exitApplication(button);
                        break;
                    default:
                        Toast.makeText(activity.getApplicationContext(), "Unknown Button Pressed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startGame(MenuButtonSprite button) {
        activity.getRootScene().clearChildScene();
        //To change body of created methods use File | Settings | File Templates.
    }

    private void showCredits(MenuButtonSprite button) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void showHighscores(MenuButtonSprite button) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void exitApplication(MenuButtonSprite button) {

        activity.finish();
    }


    @Override
    public void onSceletoneSpriteTouched() {

    }

    @Override
    public void onSceletoneSpriteReleased(final Scene child) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getRootScene().setChildScene(child);
                activity.getCamera().setHUD(((ShipMenuScene)child).getHud());
            }
        });
    }
}
