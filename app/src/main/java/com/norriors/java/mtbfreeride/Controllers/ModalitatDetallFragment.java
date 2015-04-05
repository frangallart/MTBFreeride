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

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;

import com.norriors.java.mtbfreeride.Models.Modalitat;
import com.norriors.java.mtbfreeride.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Classe ModalitatsFragment,
 * Fragment que ens permet veure el detall de les modalitats
 */
public class ModalitatDetallFragment extends Fragment implements View.OnClickListener {

    // UI references.
    private TextView tvModalTitol, tvModalDetall;
    private ScrollView svModaldetall;
    //private VideoView videoModal;
    private ImageView ivModal;
    private ImageView imgVideo;
    private Typeface font;
    private View rootView;

    private Modalitat modalitat;
    private Serializable mod;
    private MediaController mediaController;

    public static ModalitatDetallFragment newInstance(Modalitat mod) {
        ModalitatDetallFragment fragment = new ModalitatDetallFragment();
        Bundle b = new Bundle();
        b.putSerializable("Modalitat", mod);
        fragment.setArguments(b);
        return fragment;
    }

    public ModalitatDetallFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        modalitat = (Modalitat) getArguments().getSerializable("Modalitat");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Modalitats");

        rootView = inflater.inflate(R.layout.activity_modal_detall, container, false);

        tvModalTitol = (TextView) rootView.findViewById(R.id.tvModalitatDetallTitol);
        tvModalDetall = (TextView) rootView.findViewById(R.id.tvModalitatDetallDescripcio);
        ivModal = (ImageView) rootView.findViewById(R.id.ivModalDetall);
        imgVideo = (ImageView) rootView.findViewById(R.id.imgVideo);

        imgVideo.setOnClickListener(this);

        font = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");
        tvModalTitol.setTypeface(font);
        tvModalDetall.setTypeface(font);

        svModaldetall = (ScrollView) rootView.findViewById(R.id.svModalDetall);

        getActivity().setTitle(modalitat.getTitol());
        tvModalTitol.setText(modalitat.getTitol());
        tvModalDetall.setText(modalitat.getDescripcio());
        ivModal.setImageBitmap(recollirImatge(modalitat.getImatge2()));

        mediaController = new MediaController(getActivity());

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.imgVideo):
                Intent intent = new Intent(getActivity().getBaseContext(), ModalitatVideo.class);
                Bundle bundle = new Bundle();
                bundle.putString("video",modalitat.getUrl_video());
                intent.putExtras(bundle);
                startActivity(intent);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * Recull la imatge d'una modalitat i la retorna amb bitmap
     *
     * @param path
     * @return
     */
    public Bitmap recollirImatge(String path) {
        AssetManager assetManager = getActivity().getBaseContext().getAssets();
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
}