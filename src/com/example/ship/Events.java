package com.example.ship;

import android.util.Log;
import com.example.ship.commons.A;
import com.example.ship.game.Gun;
import com.example.ship.game.hud.GameButtonSprite;
import com.example.ship.game.hud.TouchableGameButtonSprite;
import com.example.ship.menu.MenuButtonSprite;
import com.example.ship.menu.TouchableMenuButtonSprite;
import com.example.ship.root.TouchableSceletonSprite;
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
    private final RootActivity activity;

    public Events(RootActivity activity) {
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
                    case R.string.GAME_OVER_RESTART_BUTTON:
                        A.a.getSceneSwitcher().getGameScene().getGameOverHUD().addHighScore();
                        startGame();
                        break;
                    case R.string.GAME_OVER_EXIT_BUTTON:
                        A.a.getSceneSwitcher().getGameScene().getGameOverHUD().addHighScore();
                        backToMenu();
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
                switch (button.getId()) {
                    case R.string.GAME_FIRE_BUTTON:
                        fire();
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
                    case R.string.HIGHSCORES_BACK_BUTTON:
                        backToMenuHud();
                        break;
                }
            }
        });
    }

    private void backToMenuHud() {
        activity.getSceneSwitcher().switchToMenuHud();
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


    private Gun getGun() {
        return activity.getSceneSwitcher().getGameScene().getGun();
    }

    private void fire() {
        getGun().fire();
    }

    private void startGame() {
        activity.getSceneSwitcher().switchToGameScene();
    }

    private void showCredits() {

    }

    private void showHighscores() {
        activity.getSceneSwitcher().switchToHighScoresHUD();
        Log.d("1log", A.a.getHighScoresManager().getHighScores().toString());
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
