package com.example.ship;

import android.widget.Toast;
import com.example.ship.menu.MenuButtonSprite;
import com.example.ship.menu.TouchableMenuButtonSprite;
import com.example.ship.sceletone.TouchableSceletonSprite;
import org.andengine.entity.sprite.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 26.04.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class Events implements TouchableMenuButtonSprite
                             , TouchableSceletonSprite {

    private final static float RELATIVE_BUTTON_JUMP_AMPLITUDE = 0.005f;
    private final SceletonActivity activity;

    public Events(SceletonActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onAreaButtonMenuTouched(final MenuButtonSprite button) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuButtonJump(button, true);
                switch (button.getId()) {
                    case R.string.MENU_START_BUTTON:
                        break;
                    case R.string.MENU_HS_BUTTON:
                        break;
                    case R.string.MENU_CREDITS_BUTTON:
                        break;
                    case R.string.MENU_EXIT_BUTTON:
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
                menuButtonJump(button, false);
                switch (button.getId()) {
                    case R.string.MENU_START_BUTTON:
                        startGame();
                        break;
                    case R.string.MENU_HS_BUTTON:
                        //showHighscores();
                        break;
                    case R.string.MENU_CREDITS_BUTTON:
                        //showCredits();
                        break;
                    case R.string.MENU_EXIT_BUTTON:
                        exitApplication();
                        break;
                    default:
                        Toast.makeText(activity.getApplicationContext(), "Unknown Button Released", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void menuButtonJump (Sprite button, boolean up) {
        float jumpAmplitude = activity.getTextureSize().y * RELATIVE_BUTTON_JUMP_AMPLITUDE;
        if (up){
            button.setPosition( button.getX(), button.getY() - jumpAmplitude);
        } else {
            button.setPosition( button.getX(), button.getY() + jumpAmplitude);
        }
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
