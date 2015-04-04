/*************************************************************************************
 *                                                                                   *
 *  MTB FreeRide por Java Norriors se distribuye bajo una                            *
 *  Licencia Creative Commons Atribución-NoComercial-SinDerivar 4.0 Internacional.   *
 *                                                                                   *
 *  http://creativecommons.org/licenses/by-nc-nd/4.0/                                *
 *                                                                                   *
 *  @author: Arnau Roma Vidal  - aroma@infoboscoma.net                               *
 *  @author: Rubén Garcia Torres - rgarcia@infobosccoma.net                          *
 *  @author: Francesc Gallart Vila - fgallart@infobosccoma.net                       *
 *                                                                                   *
/************************************************************************************/
package com.norriors.java.mtbfreeride.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.norriors.java.mtbfreeride.R;

/**
 * Classe SplashScreenActivity
 *
 * Classe que model l'animació d'inici de l'aplicació
 */
public class SplashScreenActivity extends Activity implements AnimationListener {

    // Duració de l'splash screen
    private static final long SPLASH_SCREEN_DELAY = 6000;

    // Animation
    Animation animFadein, animFadeOut, animSequen;

    // ImageView
    ImageView imageSplash;

    // The thread to process splash screen events
    private Thread mSplashThread;

    /**
     * Mètode que s'executa en crear l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupGui();

        // Iniciem l'animació d'entrada
        imageSplash.startAnimation(animFadein);

        // Nou thread a l'espera d'event
        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(SPLASH_SCREEN_DELAY);
                    }
                } catch (InterruptedException ex) {
                    // volem reflectir en el log que hi ha agut un error
                    Log.e("Thread splash screen", ex.getMessage());
                }

                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        mSplashThread.start();
    }

    /**
     * Mètode que recupera els controls de la GUI
     */
    private void setupGui() {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Cridem al layout
        setContentView(R.layout.activity_splash_screen);
        imageSplash = (ImageView) findViewById(R.id.imageSplashB);

        // Load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        animSequen = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.sequential);

        animFadein.setAnimationListener(this);
        animFadeOut.setAnimationListener(this);
        animSequen.setAnimationListener(this);
    }

    /**
     * Processa splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplashThread) {
                mSplashThread.notifyAll();
            }
        }
        return true;
    }

    /**
     * Processa l'event d'inici de l'animació
     *
     * @param animation
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * Processa l'event d'acabament de l'animació
     *
     * @param animation
     */
    @Override
    public void onAnimationEnd(Animation animation) {

        // check for fade in animation
        if (animation == animFadein) {
            // S'acaba l'animació d'entrada
            // Comença la de moviment
            imageSplash.startAnimation(animSequen);

        }
        if (animation == animSequen) {
            // S'acava l'animació de moviment
            // Comença la de sortida
            imageSplash.setImageResource(R.drawable.img_splash_screen);
            imageSplash.startAnimation(animFadeOut);

        }
    }

    /**
     * Processa l'event de repetició de l'animació
     *
     * @param animation
     */
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
