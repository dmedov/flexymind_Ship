package com.example.ship.sceletone;

import com.example.ship.Menu.ButtonMenuSprite;
import org.andengine.entity.scene.Scene;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 26.04.13
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
public interface TouchableSceletobeSprite {
    public void onAreaButtonMenuTouched();
    public void onAreaButtonMenuReleased(Scene child);
}
