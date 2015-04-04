package com.norriors.java.mtbfreeride.Controllers;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.norriors.java.mtbfreeride.Models.Modalitat;
import com.norriors.java.mtbfreeride.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe ModalitatDetallActivity
 */
public class ModalitatDetallActivity extends ActionBarActivity implements View.OnClickListener {

    // UI references.
    private TextView tvModalTitol, tvModalDetall;
    private ScrollView svModaldetall;
    private VideoView videoModal;
    private ImageView ivModal;
    private Typeface font;

    private Modalitat modalitat;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_detall);

        // Si hi ha conexiò a internet
        if (InternetUtil.isOnline(ModalitatDetallActivity.this)) {
            setupGui();
        } else {
            InternetUtil.showAlertDialog(ModalitatDetallActivity.this, "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
        }

        setupGui();
    }

    /**
     * Mètode que recupera els controls de la GUI i toca espectes de disseny
     */
    private void setupGui() {

        tvModalTitol = (TextView) findViewById(R.id.tvModalitatDetallTitol);
        tvModalDetall = (TextView) findViewById(R.id.tvModalitatDetallDescripcio);
        videoModal = (VideoView) findViewById(R.id.videoModalitatDetall);
        ivModal = (ImageView) findViewById(R.id.ivModalDetall);

        font = Typeface.createFromAsset(getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");
        tvModalTitol.setTypeface(font);
        tvModalDetall.setTypeface(font);

        svModaldetall = (ScrollView) findViewById(R.id.svModalDetall);

        modalitat = null;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            modalitat = (Modalitat) getIntent().getExtras().getSerializable("modalitat");
            setTitle(modalitat.getTitol());
            tvModalTitol.setText(modalitat.getTitol());
            tvModalDetall.setText(modalitat.getDescripcio());
            ivModal.setImageBitmap(recollirImatge(modalitat.getImatge2()));

            mediaController = new MediaController(this);
            videoModal.setMediaController(mediaController);
            //videoModal.setVideoURI(Uri.parse("http://techslides.com/demos/sample-videos/small.mp4"));
            //System.out.println(modalitat.getUrl_video());
            videoModal.setVideoURI(Uri.parse(modalitat.getUrl_video()));
            //videoModal.pause();
            videoModal.setOnClickListener(this);
        }

        //TODO : Fer que l'escroll començi a la imatge de dalt'

        // Ens situa l'scroll en la posició superior
        svModaldetall.smoothScrollTo(0, 0);
    }

    /**
     * Recull la imatge d'una modalitat i la retorna amb bitmap
     *
     * @param path
     * @return
     */
    public Bitmap recollirImatge(String path) {
        AssetManager assetManager = this.getBaseContext().getAssets();
        Bitmap imatge = null;
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(path);
            imatge = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return imatge;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.videoModalitatDetall):
                if (videoModal.isPlaying()) {
                    videoModal.pause();
                } else {
                    videoModal.start();
                }
                break;
        }
    }
}
