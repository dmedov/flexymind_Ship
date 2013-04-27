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
                String message = String.format("%s Button Pressed!", button.getName());
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAreaButtonMenuReleased(final MenuButtonSprite button) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String message = String.format("%s Button Released!", button.getName());
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSceletoneSpriteTouched() {

    }

    @Override
    public void onSceletoneSpriteReleased(final Scene child) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getEngine().setScene(child);
                activity.getCamera().setHUD(((ShipMenuScene)child).getHud());
            }
        });
    }
}
