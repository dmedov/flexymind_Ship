package com.example.ship;

import android.widget.Toast;
import com.example.ship.Menu.ButtonMenuSprite;
import com.example.ship.Menu.TouchableMenuButtonSprite;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 26.04.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class Events implements TouchableMenuButtonSprite {

    private final BaseGameActivity activity;

    public Events(BaseGameActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onAreaButtonMenuTouched(final ButtonMenuSprite button) {
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                String message = new StringBuilder().append(button.getName())
                                                    .append(" Button Released!").toString();
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAreaButtonMenuReleased(final ButtonMenuSprite button) {
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                String message = new StringBuilder().append(button.getName())
                                                    .append(" Button Released!").toString();
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
