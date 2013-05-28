package com.example.ship.game.particlesystem;

import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.util.Log;
import com.example.ship.R;
import com.example.ship.commons.A;
import org.andengine.entity.Entity;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.IParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 26.05.13
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */
public class Effects {
    public static final class FireParticleSystemFactory {

        private static float pCenterX;
        private static float pCenterY;
        private static float pWidth;
        private static float pHeight;
        private static float pAlpha;
        private static Point lifeTime;
        private static PointF scaleRange;
        private static Color initFireColor;
        private static Color endFireColor;
        private static float pRateMinimum;
        private static float pRateMaximum;
        private static int pParticlesMaximum;
        private static float pMinVelocityY;
        private static float pMaxVelocityY;

        public static SpriteParticleSystem build(RecipeFire recipe) {

            init(recipe);

            RectangleParticleEmitter particleEmitter=
                    new RectangleParticleEmitter( pCenterX + 0.5f * pWidth
                                                , pCenterY
                                                , pWidth
                                                , pHeight );
            SpriteParticleSystem particleSystem =
                    new SpriteParticleSystem( particleEmitter
                                            , pRateMinimum
                                            , pRateMaximum
                                            , pParticlesMaximum
                                            , A.rm.getLoadedTextureRegion(R.drawable.particle_point)
                                            , A.e.getVertexBufferObjectManager());

            //Initializers
            particleSystem.addParticleInitializer(
                    new BlendFunctionParticleInitializer<Sprite>( GLES20.GL_SRC_ALPHA
                                                                , GLES20.GL_ONE ));

            particleSystem.addParticleInitializer(
                    new ExpireParticleInitializer<Sprite>( lifeTime.x
                                                         , lifeTime.y ));
            particleSystem.addParticleInitializer(
                    new VelocityParticleInitializer<Sprite>( 0f
                                                           , 0f
                                                           , pMinVelocityY
                                                           , pMaxVelocityY ));
            particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(pAlpha));

            //Modifiers
            particleSystem.addParticleModifier(
                    new ScaleParticleModifier<Sprite>( lifeTime.x
                                                     , lifeTime.x
                                                     , scaleRange.x
                                                     , scaleRange.y ));
            particleSystem.addParticleModifier(
                    new ColorParticleModifier<Sprite>( lifeTime.x
                                                     , lifeTime.y
                                                     , initFireColor.getRed()
                                                     , endFireColor.getRed()
                                                     , initFireColor.getGreen()
                                                     , endFireColor.getGreen()
                                                     , initFireColor.getBlue()
                                                     , endFireColor.getBlue()));

            return particleSystem;
        }

        private static void init(RecipeFire recipe) {

            Log.d("ship", recipe.toString());
            pCenterX = recipe.centerX;
            pCenterY = recipe.centerY;
            pWidth = recipe.width;
            pHeight = recipe.height;
            lifeTime = recipe.lifeTime;
            scaleRange = recipe.scaleRange;
            pAlpha = recipe.alpha;
            initFireColor = recipe.initFireColor;
            pRateMinimum = recipe.rateMinimum;
            pRateMaximum = recipe.rateMaximum;
            pParticlesMaximum = recipe.particlesMaximum;
            endFireColor = recipe.endFireColor;
            pMinVelocityY = recipe.minVelocityY;
            pMaxVelocityY = recipe.maxVelocityY;
        }
    }
    public static final class SmokeParticleSystemFactory {

        private static float pCenterX;
        private static float pCenterY;
        private static float pWidth;
        private static float pHeight;
        private static float  pAlpha            = 0.5f;
        private static Point  lifeTime          = new Point(5, 6);
        private static PointF scaleRange        = new PointF(0.2f, 3f);
        private static Color  initFireColor     = new Color(1f, 1f, 1f);
        private static Color  endFireColor      = new Color(0.3f, 0.3f, 0.3f);  //0.1
        private static float  pRateMinimum      = 25f;
        private static float  pRateMaximum      = 30f;
        private static int    pParticlesMaximum = 200;
        private static float  pMinVelocityY     = -15f;
        private static float  pMaxVelocityY     = -20f;
        private static float  WIND_FORCE        = 1.5f;
        private static PointF scaleTime;
        private static PointF colorTime = new PointF(1f, 5f);


        public static SpriteParticleSystem build(RecipeSmoke recipe) {

            init(recipe);

            final RectangleParticleEmitter particleEmitter =
                    new RectangleParticleEmitter( pCenterX + 0.5f * pWidth
                                                , pCenterY
                                                , pWidth
                                                , pHeight);
            SpriteParticleSystem particleSystem =
                    new SpriteParticleSystem( particleEmitter
                                            , pRateMinimum
                                            , pRateMaximum
                                            , pParticlesMaximum
                                            , A.rm.getLoadedTextureRegion(R.drawable.particle_point)
                                            , A.e.getVertexBufferObjectManager());

            //Initializers
            particleSystem.addParticleInitializer(
                    new BlendFunctionParticleInitializer<Sprite>( GLES20.GL_SRC_ALPHA
                                                                , GLES20.GL_ONE_MINUS_SRC_ALPHA));

            particleSystem.addParticleInitializer(
                    new ExpireParticleInitializer<Sprite>( lifeTime.x
                                                         , lifeTime.y));
            particleSystem.addParticleInitializer(
                    new VelocityParticleInitializer<Sprite>( 0f
                                                           , 0f
                                                           , pMinVelocityY
                                                           , pMaxVelocityY));
            particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(pAlpha));

            //Modifiers
            particleSystem.addParticleModifier(
                    new ScaleParticleModifier<Sprite>( scaleTime.x
                                                     , scaleTime.y
                                                     , scaleRange.x
                                                     , scaleRange.y));
            particleSystem.addParticleModifier(
                    new ColorParticleModifier<Sprite>( colorTime.x
                                                     , colorTime.y
                                                     , initFireColor.getRed()
                                                     , endFireColor.getRed()
                                                     , initFireColor.getGreen()
                                                     , endFireColor.getGreen()
                                                     , initFireColor.getBlue()
                                                     , endFireColor.getBlue()));

            particleSystem.addParticleModifier(new IParticleModifier<Sprite>() {
                @Override
                public void onUpdateParticle(Particle<Sprite> pParticle) {
                    Entity entity = pParticle.getEntity();
                    final float currentY = entity.getY();
                    pParticle.getPhysicsHandler()
                             .setVelocityX(WIND_FORCE
                                           * (float) Math.sqrt(abs(currentY - particleEmitter.getCenterY())));
                }

                @Override
                public void onInitializeParticle(Particle<Sprite> pParticle) {
                }
            });


            return particleSystem;
        }

        private static void init(RecipeSmoke recipe) {

            Log.d("ship", recipe.toString());
            pCenterX = recipe.centerX;
            pCenterY = recipe.centerY;
            pWidth = recipe.width;
            pHeight = recipe.height;
            pMinVelocityY = recipe.minVelocityY;
            pMaxVelocityY = recipe.maxVelocityY;
            endFireColor = recipe.endFireColor;
            pAlpha = recipe.alpha;
            scaleRange = recipe.scaleRange;
            scaleTime = recipe.scaleTime;
        }
    }

}

