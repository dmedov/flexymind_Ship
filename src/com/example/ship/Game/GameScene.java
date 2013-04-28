package com.example.ship.Game;

import com.example.ship.Atlas.ResourceManager;
import com.example.ship.SceletonActivity;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

public class GameScene extends Scene {

    private final SceletonActivity activity;
    private final Engine mEngine;
    private final ResourceManager resourceManager;

    public GameScene(final SceletonActivity activity){
        super();
        this.activity = activity;
        this.mEngine = activity.getEngine();
        this.resourceManager = activity.getResourceManager();

        creatBackfround();
    }

    private void creatBackfround() {
        Color backgroundColor = new Color(0.09804f, 0.6274f, 0.8784f);
        this.setBackground(new Background(backgroundColor));
    }

}
