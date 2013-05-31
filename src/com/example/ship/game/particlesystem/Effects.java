package com.example.ship.game.particlesystem;

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
import org.andengine.entity.particle.modifier.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseQuadOut;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: Gregory
 * Date: 26.05.13
 * Time: 20:43
 * Фабрика рецептов
 */
public class Effects {
    public static final class FireParticleSystemFactory {

        private static PointF pCenter;
        private static PointF pProportions;
        private static float pAlpha;
        private static PointF lifeTime;
        private static PointF scaleRange;
        private static Color initFireColor;
        private static Color endFireColor;
        private static PointF pRateRange;
        private static int pParticlesMaximum;
        private static PointF pVelocityYRange;

        public static SpriteParticleSystem build(FireRecipe recipe) {

            init(recipe);

            RectangleParticleEmitter particleEmitter=
                    new RectangleParticleEmitter( pCenter.x + 0.5f * pProportions.x
                                                , pCenter.y
                                                , pProportions.x
                                                , pProportions.y );
            SpriteParticleSystem particleSystem =
                    new SpriteParticleSystem( particleEmitter
                                            , pRateRange.x
                                            , pRateRange.y
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
                                                           , pVelocityYRange.x
                                                           , pVelocityYRange.y ));
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

        private static void init(FireRecipe recipe) {

            Log.d("ship", recipe.toString());
            pCenter             = recipe.center;
            pProportions        = recipe.proportions;
            lifeTime            = recipe.lifeTime;
            scaleRange          = recipe.scaleRange;
            pAlpha              = recipe.alpha;
            initFireColor       = recipe.initFireColor;
            pRateRange          = recipe.rateRange;
            pParticlesMaximum   = recipe.particlesMaximum;
            endFireColor        = recipe.endFireColor;
            pVelocityYRange     = recipe.velocityYRange;
        }
    }
    public static final class SmokeParticleSystemFactory {

        private static PointF pCenter;
        private static PointF pProportions;
        private static float  pAlphaInit        = 0.5f;
        private static PointF lifeTime          = new PointF(5, 6);
        private static PointF scaleRange        = new PointF(0.2f, 3f);
        private static Color  initFireColor     = new Color(1f, 1f, 1f);
        private static Color  endFireColor      = new Color(0.3f, 0.3f, 0.3f);  //0.1
        private static PointF  pRateRange       = new PointF(25f, 30f);
        private static int    pParticlesMaximum = 200;
        private static PointF  pVelocityYRange   = new PointF(-20f,-15f);
        private static float  WIND_FORCE        = 1.5f;
        private static PointF scaleTime;
        private static PointF colorTime         = new PointF(1f, 5f);
        private static PointF alphaTime         = new PointF(4.5f, 6f);
        private static PointF alphaRange        = new PointF(0.5f, 0f);

        public static SpriteParticleSystem build(SmokeRecipe recipe) {

            init(recipe);

            final RectangleParticleEmitter particleEmitter =
                    new RectangleParticleEmitter( pCenter.x + 0.5f * pProportions.x
                                                , pCenter.y
                                                , pProportions.x
                                                , pProportions.y );
            SpriteParticleSystem particleSystem =
                    new SpriteParticleSystem( particleEmitter
                                            , pRateRange.x
                                            , pRateRange.y
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
                                                           , pVelocityYRange.x
                                                           , pVelocityYRange.y));
            particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(pAlphaInit));

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

            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>( alphaTime.x
                                                                                , alphaTime.y
                                                                                , alphaRange.x
                                                                                , alphaRange.y
                                                                                , EaseQuadOut.getInstance()));
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

        private static void init(SmokeRecipe recipe) {

            Log.d("ship", recipe.toString());
            pCenter         = recipe.center;
            pProportions    = recipe.proportions;
            pVelocityYRange   = recipe.velocityYRange;
            endFireColor    = recipe.endFireColor;
            pAlphaInit      = recipe.alpha;
            scaleRange      = recipe.scaleRange;
            scaleTime       = recipe.scaleTime;
        }
    }

}

