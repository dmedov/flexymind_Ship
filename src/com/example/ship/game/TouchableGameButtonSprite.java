package com.example.ship.game;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 03.05.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public interface TouchableGameButtonSprite {
    public void onAreaGameButtonTouched(GameButtonSprite button);
    public void onAreaGameButtonTouched();
    public void onAreaGameButtonReleased(GameButtonSprite button);
}
