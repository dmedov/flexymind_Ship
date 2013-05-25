package com.example.ship.game;

import com.example.ship.R;
import com.example.ship.commons.A;
import org.andengine.entity.Entity;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseQuadOut;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 25.05.13
 * Time: 1:57
 * To change this template use File | Settings | File Templates.
 */
public class FireParticles {

    private final static float MIN_VELOCITY_Y = -15f;
    private final static float MAX_VELOCITY_Y = -25f;
    final static float LIFE_TIME = 4f;

    private ArrayList<SpriteParticleSystem> particleSystems;
    private RectangleParticleEmitter particleEmitter;
    private int fireRate;
    private Ship ship;


    public FireParticles(Ship ship) {

        this.fireRate = 0;
        this.ship = ship;
        ITextureRegion mParticleTextureRegion = A.rm.getLoadedTextureRegion(R.drawable.particle_point);
        float shipDirection;
        if (ship.getDirection()) {
            shipDirection = 1f;
        } else {
            shipDirection = -1f;
        }
        particleEmitter = new RectangleParticleEmitter( shipDirection * 0.5f * ship.getSprite().getWidthScaled()
                                                      , 0.5f * ship.getSprite().getHeightScaled()
                                                      , 0.5f * ship.getSprite().getWidthScaled()
                                                      , 0.5f * ship.getSprite().getHeightScaled());
        float pRateMinimum = 20;
        float pRateMaximum = 40;
        int pParticlesMaximum = 150;
        particleSystems = new ArrayList<SpriteParticleSystem>();
        for(int i=0; i < 4; i++){
            SpriteParticleSystem particleSystem = new SpriteParticleSystem( particleEmitter
                                                                          , pRateMinimum
                                                                          , pRateMaximum
                                                                          , pParticlesMaximum
                                                                          , mParticleTextureRegion
                                                                          , A.e.getVertexBufferObjectManager());
            particleSystems.add(particleSystem);
            pRateMaximum += 10;
            pRateMinimum += 10;
        }
        for(SpriteParticleSystem particleSystem : particleSystems) {

            particleSystem.setParticlesSpawnEnabled(false);

            particleSystem.addParticleInitializer(
                    new VelocityParticleInitializer<Sprite>( 0f
                                                           , 0f
                                                           , FireParticles.MIN_VELOCITY_Y
                                                           , FireParticles.MAX_VELOCITY_Y));
            particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(FireParticles.LIFE_TIME));
            particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f, 1f, 0f, 0.36f, 0f, 0f));
            particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(1f, 4f, 0.2f, 1f));
            particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1f, 2f, 1f, 0f, 1f, 0f, 1f, 0f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(1f, 4f, 1f, 0f, EaseQuadOut.getInstance()));
            particleSystem.addParticleModifier(new IParticleModifier<Sprite>() {
                @Override
                public void onUpdateParticle(Particle<Sprite> pParticle) {
                    Entity entity = pParticle.getEntity();
                    final float currentY = entity.getY();
                    pParticle.getPhysicsHandler().setVelocityX( 1.1f * abs(currentY - particleEmitter.getCenterY()) );
                }

                @Override
                public void onInitializeParticle(Particle<Sprite> pParticle) {
                }
            });

        }


    }
    public void updateFireRate() {
        int currentHP = ship.getHealth();
        int maxHP = findMaxHPByType( ship.getType());
        int damage = A.a.getSceneSwitcher().getGameScene().getGun().getDamage();
        int hp = maxHP;
        int mFireRate = 0;
        while (hp > currentHP){
            hp -= damage;
            mFireRate += 1;
        }
        fireRate = mFireRate;
        if(fireRate != 0){
            ship.getSprite().attachChild(particleSystems.get(fireRate-1));
        }
        updateParticalSystems();

    }
    public void finishFire() {
        for( SpriteParticleSystem particleSystem : particleSystems){
            particleSystem.setParticlesSpawnEnabled(false);
            ship.getSprite().detachChild(particleSystem);
        }

    }
    private void updateParticalSystems() {

        for(int rate = 0; rate < particleSystems.size(); rate++){
            if((fireRate - 1) == rate){
                particleSystems.get(rate).setParticlesSpawnEnabled(true);
            } else {
                particleSystems.get(rate).setParticlesSpawnEnabled(false);
            }
        }
    }
    private int findMaxHPByType(int shipType) {
        switch (shipType) {
            case R.drawable.sailfish:
                return  A.a.getIntResource(R.integer.SAILFISH_HEALTH);
            case R.drawable.missileboat:
                return  A.a.getIntResource(R.integer.MISSILEBOAT_HEALTH);
            case R.drawable.battleship:
                return  A.a.getIntResource(R.integer.BATTLESHIP_HEALTH);
            default:
                return  A.a.getIntResource(R.integer.MISSILEBOAT_HEALTH);
        }
    }
}
