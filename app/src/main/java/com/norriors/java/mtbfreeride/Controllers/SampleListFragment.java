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
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.norriors.java.mtbfreeride.Models.Modalitat;
import com.norriors.java.mtbfreeride.R;
import com.norriors.java.mtbfreeride.dao.ModalitatsConversor;
import com.norriors.java.mtbfreeride.dao.ModalitatsSQLiteHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Classe SampleListFragment
 * <p/>
 * Classe que modela el fragment d'una modalitat i descarrega i mostra les seves dades
 */
public class SampleListFragment extends ScrollTabHolderFragment implements View.OnClickListener {

    private ModalitatsSQLiteHelper factsHelper;
    private SQLiteDatabase db;
    private ModalitatsConversor modalitatsConversor;
    private ArrayList<Modalitat> dades;

    private static final String ARG_POSITION = "position";
    private TextView tvTitolMain;
    private int mPosition;
    private Typeface font;
    private View rootView;
    private ImageButton btnModalitatDetall;
    private ScrollView svModalMain;
    private ImageView ivModalMain;

    public static Fragment newInstance(int position) {
        SampleListFragment f = new SampleListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPosition = getArguments().getInt(ARG_POSITION);

        // crear l'objecte que crea a la connexió amb la BD
        factsHelper = new ModalitatsSQLiteHelper(getActivity().getBaseContext(), "MTBModalitats.db", null, 1);
        // obtenir l'objecte BD
        db = factsHelper.getReadableDatabase();
        modalitatsConversor = new ModalitatsConversor(factsHelper);
        dades = modalitatsConversor.getAllAsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // fem referència a la vista
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // textView on mostro el nom de la modalitat
        tvTitolMain = (TextView) rootView.findViewById(R.id.tvModalitat);
        // imageView on es veu la imatge de la modalitat
        ivModalMain = (ImageView) rootView.findViewById(R.id.ivModalMain);
        svModalMain = (ScrollView) rootView.findViewById(R.id.svMainFragment);
        btnModalitatDetall = (ImageButton) rootView.findViewById(R.id.btnDetallsModalitat);
        font = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");

        tvTitolMain.setTypeface(font);
        // Ens situa l'scroll en la posició superior
        svModalMain.smoothScrollTo(0, 0);

        btnModalitatDetall.setOnClickListener(this);
        tvTitolMain.setText(dades.get(mPosition).getTitol());
        ivModalMain.setImageBitmap(recollirImatge());

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void adjustScroll(int scrollHeight) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnDetallsModalitat):

                Modalitat m = dades.get(mPosition);

                Intent intent = new Intent(getActivity().getBaseContext(), ModalitatDetallActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("modalitat",m);
                intent.putExtras(bundle);

                startActivity(intent);
                break;
        }
    }

    /**
     * Recull la imatge corresponent de la carpeta assets
     *
     * @return bitmap
     */
    public Bitmap recollirImatge() {
        AssetManager assetManager = getActivity().getBaseContext().getAssets();
        Bitmap imatge = null;
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(dades.get(mPosition).getImatge1());
            imatge = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return imatge;
        }
    }
}