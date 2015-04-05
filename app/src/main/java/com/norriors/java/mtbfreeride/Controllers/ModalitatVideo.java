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
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;

import com.norriors.java.mtbfreeride.R;

/**
 * Classe ModalitatVideo.
 * Aquesta classe ens permet veure el vídeos a pantalla completa
 */
public class ModalitatVideo extends Activity {

    private MediaController mediaController;
    private VideoViewCustom videoModal;
    private Point display;
    private int posicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detall);

        // Si hi ha conexiò a internet
        if (InternetUtil.isOnline(ModalitatVideo.this)) {
            setupGui();
        } else {
            InternetUtil.showAlertDialog(ModalitatVideo.this, "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
        }
        setupGui();
    }

    /**
     * Mètode que recupera els controls de la GUI i toca espectes de disseny
     */
    private void setupGui() {
        display = new Point();
        this.posicio = 0;
        this.getWindowManager().getDefaultDisplay().getSize(display);
        Configuration config = getResources().getConfiguration();
        Bundle extras = getIntent().getExtras();
        videoModal = (VideoViewCustom) findViewById(R.id.videoModalitatDetall);

        if (config.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            videoModal.setDimensions(display.x, (display.x-250));
        }
        else
        {
            videoModal.setDimensions(display.x, display.y);
        }


        if (extras != null) {
            String video = getIntent().getExtras().getString("video");

            mediaController = new MediaController(this);
            videoModal.setMediaController(mediaController);
            videoModal.setVideoURI(Uri.parse(video));
            videoModal.start();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        display = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(display);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            videoModal.setDimensions(display.x, display.y);
            videoModal.getHolder().setFixedSize(display.x, display.y);

        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            videoModal.setDimensions(display.x, (display.x-250));
            videoModal.getHolder().setFixedSize(display.x, (display.x-250));

        }
    }

    /**
     * Mètode que quan se surt de l'aplicació, però no es tanca,
     * para les peticions del gps per no gastar bateria.
     */
    @Override
    public void onPause() {
        super.onPause();
        posicio = videoModal.getCurrentPosition();
        videoModal.pause();
    }

    /**
     * Mètode que quan es torna a l'aplicació oberta en segon pla,
     * torna activar les peticions del gps si estaven activades
     * abans de sortir de l'aplicació.
     */
    @Override
    protected void onResume() {
        super.onResume();
        videoModal.seekTo(posicio);
        videoModal.start();
    }
}
