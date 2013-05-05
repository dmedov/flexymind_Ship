package com.example.ship;

import com.example.ship.game.GameButtonSprite;
import com.example.ship.game.TouchableGameButtonSprite;
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
                             , TouchableGameButtonSprite
                             , TouchableSceletonSprite {

    private final static float RELATIVE_BUTTON_JUMP_AMPLITUDE = 0.005f;
    private final static float RELATIVE_BUTTON_PULSE_AMPLITUDE = 0.1f;
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
                        showHighscores();
                        break;
                    case R.string.MENU_CREDITS_BUTTON:
                        showCredits();
                        break;
                    case R.string.MENU_EXIT_BUTTON:
                        exitApplication();
                        break;
                }
            }
        });
    }

    @Override
    public void onAreaGameButtonTouched(final GameButtonSprite button) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuButtonPulse(button, true);
                /*switch (button.getId()) {
                    case R.string.GAME_LEFT_BUTTON:
                        moveLeft();
                        break;
                    case R.string.GAME_RIGHT_BUTTON:
                        moveRight();
                        break;
                } */
            }
        });
    }

    @Override
    public void onAreaGameButtonPressed(final GameButtonSprite button) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (button.getId()) {
                    case R.string.GAME_LEFT_BUTTON:
                        moveLeft();
                        break;
                    case R.string.GAME_RIGHT_BUTTON:
                        moveRight();
                        break;
                }
            }
        });
    }

    @Override
    public void onAreaGameButtonReleased(final GameButtonSprite button) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuButtonPulse(button, false);
                switch (button.getId()) {
                    case R.string.GAME_PAUSE_BUTTON:
                        pauseGame();
                        break;
                    case R.string.GAME_PAUSE_MENU_BUTTON:
                        backToMenu();
                        break;
                    case R.string.GAME_PAUSE_BACK_BUTTON:
                        backToGame();
                        break;
                    case R.string.GAME_FIRE_BUTTON:
                        fire();
                        break;
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

    private void menuButtonPulse (Sprite button, boolean up) {
        if (up){
            button.setScale(RELATIVE_BUTTON_PULSE_AMPLITUDE + 1);
        } else {
            button.setScale(1);
        }
    }

    private void fire() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void moveRight() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void moveLeft() {
        Sprite gunSprite = activity.getSceneSwitcher().getGameScene().getGun();
        gunSprite.setRotationCenter(gunSprite.getWidth() / 2, gunSprite.getHeight());
        gunSprite.setRotation(gunSprite.getRotation() - 2.0f);

        //To change body of created methods use File | Settings | File Templates.
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

    private void pauseGame() {
        activity.getSceneSwitcher().switchToPauseHUD();
    }

    private void backToGame() {
        activity.getSceneSwitcher().switchToGameHUD();
    }

    private void backToMenu() {
        activity.getSceneSwitcher().switchToMenuScene();
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
