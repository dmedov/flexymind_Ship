package com.example.ship.game.particlesystem;

import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLES20;
import com.example.ship.R;
import com.example.ship.commons.A;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

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
        private static float pHeight = 0;
        private static Point LIFE_TIME;
        private static PointF SCALE_RANGE;
        private static Color INIT_FIRE_COLOR;
        private static Color END_FIRE_COLOR;
        private static float pRateMinimum;
        private static float pRateMaximum;
        private static int pParticlesMaximum;
        private static float pMinVelocityY;
        private static float pMaxVelocityY;
        private static final int BEGIN = 0;
        private static final int END = 1;
        private static final int RED = 0;
        private static final int GREEN = 1;
        private static final int BLUE = 2;

        public static SpriteParticleSystem build(Sprite sprite, int recipeNumber) {

            init(sprite, recipeNumber);

            RectangleParticleEmitter particleEmitter=
                    new RectangleParticleEmitter( pCenterX
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
                    new ExpireParticleInitializer<Sprite>( LIFE_TIME.x
                                                         , LIFE_TIME.y ));
            particleSystem.addParticleInitializer(
                    new VelocityParticleInitializer<Sprite>( 0f
                                                           , 0f
                                                           , pMinVelocityY
                                                           , pMaxVelocityY ));

            //Modifiers
            particleSystem.addParticleModifier(
                    new ScaleParticleModifier<Sprite>( LIFE_TIME.x
                                                     , LIFE_TIME.x
                                                     , SCALE_RANGE.x
                                                     , SCALE_RANGE.y ));
            particleSystem.addParticleModifier(
                    new ColorParticleModifier<Sprite>( LIFE_TIME.x
                                                     , LIFE_TIME.y
                                                     , INIT_FIRE_COLOR.getRed()
                                                     , END_FIRE_COLOR.getRed()
                                                     , INIT_FIRE_COLOR.getGreen()
                                                     , END_FIRE_COLOR.getGreen()
                                                     , INIT_FIRE_COLOR.getBlue()
                                                     , END_FIRE_COLOR.getBlue() ));

            return particleSystem;
        }

        private static void init(Sprite sprite, int recipeNumber) {
            switch (sprite.getTag()) {
                case R.drawable.sailfish:
                    pCenterX =  A.a.getIntResource(R.integer.SAILFISH_FIRE_CENTER_X);
                    pCenterY =  A.a.getIntResource(R.integer.SAILFISH_FIRE_CENTER_Y);
                    pWidth =   A.a.getIntResource(R.integer.SAILFISH_FIRE_WIDTH);
                    switch (recipeNumber) {
                        case 0:
                            LIFE_TIME = pointFromArrayResource(R.array.LIFE_TIME_1);
                            SCALE_RANGE = pointFFromArrayResource(R.array.SCALE_RANGE);
                            INIT_FIRE_COLOR = colorFromArrayResource(R.array.INIT_FIRE_COLOR_1);
                            pRateMinimum =     A.a.getIntResource(R.integer.RATE_MINIMUM_1);
                            pRateMaximum =     A.a.getIntResource(R.integer.RATE_MAXIMUM_1);
                            pParticlesMaximum =  A.a.getIntResource(R.integer.PARTICLES_MAXIMUM_1);

                            END_FIRE_COLOR =  new Color(0.7f, 0.05f, 0.0f);
                            pMinVelocityY =   -10f;
                            pMaxVelocityY =    -20f;
                            break;
                        case 1:
                            pCenterX =  A.a.getIntResource(R.integer.SAILFISH_FIRE_CENTER_X);
                            pCenterY =  A.a.getIntResource(R.integer.SAILFISH_FIRE_CENTER_Y);
                            pWidth =   A.a.getIntResource(R.integer.SAILFISH_FIRE_WIDTH);
                            LIFE_TIME = pointFromArrayResource(R.array.LIFE_TIME_2);
                            SCALE_RANGE = pointFFromArrayResource(R.array.SCALE_RANGE_2);
                            INIT_FIRE_COLOR = colorFromArrayResource(R.array.INIT_FIRE_COLOR_2);
                            pRateMinimum =     A.a.getIntResource(R.integer.RATE_MINIMUM_2);
                            pRateMaximum =     A.a.getIntResource(R.integer.RATE_MAXIMUM_2);
                            pParticlesMaximum =  A.a.getIntResource(R.integer.PARTICLES_MAXIMUM_2);

                            END_FIRE_COLOR =  new Color(0.7f, 0.05f, 0.0f);
                            pMinVelocityY =   -10f;
                            pMaxVelocityY =    -20f;
                            break;

                    }
                    break;
                case R.drawable.missileboat:
                    pCenterX =  A.a.getIntResource(R.integer.MISSILEBOAT_FIRE_CENTER_X);
                    pCenterY =  A.a.getIntResource(R.integer.MISSILEBOAT_FIRE_CENTER_Y);
                    pWidth =   A.a.getIntResource(R.integer.MISSILEBOAT_FIRE_WIDTH);
                    switch (recipeNumber) {
                        case 0:
                            LIFE_TIME = pointFromArrayResource(R.array.LIFE_TIME_1);
                            SCALE_RANGE = pointFFromArrayResource(R.array.SCALE_RANGE);
                            INIT_FIRE_COLOR = colorFromArrayResource(R.array.INIT_FIRE_COLOR_1);
                            pRateMinimum =     A.a.getIntResource(R.integer.RATE_MINIMUM_1);
                            pRateMaximum =     A.a.getIntResource(R.integer.RATE_MAXIMUM_1);
                            pParticlesMaximum =  A.a.getIntResource(R.integer.PARTICLES_MAXIMUM_1);

                            END_FIRE_COLOR =  new Color(0.7f, 0.05f, 0.0f);
                            pMinVelocityY =   -10f;
                            pMaxVelocityY =    -20f;
                            break;
                        case 1:
                            LIFE_TIME = pointFromArrayResource(R.array.LIFE_TIME_2);
                            SCALE_RANGE = pointFFromArrayResource(R.array.SCALE_RANGE_2);
                            INIT_FIRE_COLOR = colorFromArrayResource(R.array.INIT_FIRE_COLOR_2);
                            pRateMinimum =     A.a.getIntResource(R.integer.RATE_MINIMUM_2);
                            pRateMaximum =     A.a.getIntResource(R.integer.RATE_MAXIMUM_2);
                            pParticlesMaximum =  A.a.getIntResource(R.integer.PARTICLES_MAXIMUM_2);

                            END_FIRE_COLOR =  new Color(0.7f, 0.05f, 0.0f);
                            pMinVelocityY =   -10f;
                            pMaxVelocityY =    -20f;
                            break;
                    }
                    break;
                case R.drawable.battleship:
                    pCenterX =  A.a.getIntResource(R.integer.BATTLESHIP_FIRE_CENTER_X);
                    pCenterY =  A.a.getIntResource(R.integer.BATTLESHIP_FIRE_CENTER_Y);
                    pWidth =   A.a.getIntResource(R.integer.BATTLESHIP_FIRE_WIDTH);
                    switch (recipeNumber) {
                        case 0:
                            LIFE_TIME = pointFromArrayResource(R.array.LIFE_TIME_1);
                            SCALE_RANGE = pointFFromArrayResource(R.array.SCALE_RANGE);
                            INIT_FIRE_COLOR = colorFromArrayResource(R.array.INIT_FIRE_COLOR_1);
                            pRateMinimum =     A.a.getIntResource(R.integer.RATE_MINIMUM_1);
                            pRateMaximum =     A.a.getIntResource(R.integer.RATE_MAXIMUM_1);
                            pParticlesMaximum =  A.a.getIntResource(R.integer.PARTICLES_MAXIMUM_1);

                            END_FIRE_COLOR =  new Color(0.7f, 0.05f, 0.0f);
                            pMinVelocityY =   -10f;
                            pMaxVelocityY =    -20f;

                            break;
                        case 1:
                            LIFE_TIME = pointFromArrayResource(R.array.LIFE_TIME_2);
                            SCALE_RANGE = pointFFromArrayResource(R.array.SCALE_RANGE_2);
                            INIT_FIRE_COLOR = colorFromArrayResource(R.array.INIT_FIRE_COLOR_2);
                            pRateMinimum =     A.a.getIntResource(R.integer.RATE_MINIMUM_2);
                            pRateMaximum =     A.a.getIntResource(R.integer.RATE_MAXIMUM_2);
                            pParticlesMaximum =  A.a.getIntResource(R.integer.PARTICLES_MAXIMUM_2);

                            END_FIRE_COLOR =  new Color(0.7f, 0.05f, 0.0f);
                            pMinVelocityY =   -10f;
                            pMaxVelocityY =    -20f;

                            break;
                    }
                    break;
                default:
                    pCenterX =  A.a.getIntResource(R.integer.MISSILEBOAT_FIRE_CENTER_X);
                    pCenterY =  A.a.getIntResource(R.integer.MISSILEBOAT_FIRE_CENTER_Y);
                    pWidth =   A.a.getIntResource(R.integer.MISSILEBOAT_FIRE_WIDTH);
                    LIFE_TIME =       new Point(3, 5);
                    SCALE_RANGE =    new PointF(2f, 0.2f);
                    INIT_FIRE_COLOR = new Color(1f, 0.5f, 0.0f);
                    END_FIRE_COLOR =  new Color(0.7f, 0.05f, 0.0f);
                    pRateMinimum =    40f;
                    pRateMaximum =    50f;
                    pParticlesMaximum = 500;
                    pMinVelocityY =   -10f;
                    pMaxVelocityY =  -20f;
                    break;
            }


        }
        private static Point pointFromArrayResource(int id) {
            return new Point( A.a.getIntArrayResource(id)[BEGIN]
                            , A.a.getIntArrayResource(id)[END]);
        }
        private static PointF pointFFromArrayResource(int id) {
            return new PointF( 0.01f * A.a.getIntArrayResource(id)[BEGIN]
                             , 0.01f * A.a.getIntArrayResource(id)[END]);
        }
        private static Color colorFromArrayResource(int id) {
            return new Color( 0.01f * A.a.getIntArrayResource(id)[RED]
                            , 0.01f * A.a.getIntArrayResource(id)[GREEN]
                            , 0.01f * A.a.getIntArrayResource(id)[BLUE] );
        }



    }
}
