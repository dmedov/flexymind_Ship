package com.example.ship;

import android.widget.Toast;
import com.example.ship.menu.MenuButtonSprite;
import com.example.ship.menu.TouchableMenuButtonSprite;
import com.example.ship.sceletone.TouchableSceletonSprite;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 26.04.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class Events implements TouchableMenuButtonSprite
                             , TouchableSceletonSprite {

    private final static float BUTTON_JUMP_AMPLITUDE = 0.005f;
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
                        button.setPosition( button.getX()
                                          , button.getY() - activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
                        break;
                    case R.string.MENU_HS_BUTTON:
                        button.setPosition( button.getX()
                                          , button.getY() - activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
                        break;
                    case R.string.MENU_CREDITS_BUTTON:
                        button.setPosition( button.getX()
                                          , button.getY() - activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
                        break;
                    case R.string.MENU_EXIT_BUTTON:
                        button.setPosition( button.getX()
                                          , button.getY() - activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
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
                        button.setPosition( button.getX()
                                          , button.getY() + activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
                        startGame();
                        break;
                    case R.string.MENU_HS_BUTTON:
                        button.setPosition( button.getX()
                                          , button.getY() + activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
                        //showHighscores();
                        break;
                    case R.string.MENU_CREDITS_BUTTON:
                        button.setPosition( button.getX()
                                          , button.getY() + activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
                        //showCredits();
                        break;
                    case R.string.MENU_EXIT_BUTTON:
                        button.setPosition( button.getX()
                                          , button.getY() + activity.getTextureSize().y * BUTTON_JUMP_AMPLITUDE);
                        exitApplication();
                        break;
                    default:
                        Toast.makeText(activity.getApplicationContext(), "Unknown Button Released", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startGame() {
        activity.getSceneSwitcher().switchToGameScene();
    }

    private void showCredits() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void showHighscores() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void exitApplication() {
        activity.finish();
    }


    @Override
    public void onSceletoneSpriteTouched() {

    }

    @Override
    public void onSceletoneSpriteReleased() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getSceneSwitcher().switchToMenuScene();
            }
        });
    }
}
