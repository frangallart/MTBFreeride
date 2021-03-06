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
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.norriors.java.mtbfreeride.Models.Opinions;
import com.norriors.java.mtbfreeride.R;

import java.util.List;

/**
 * Classe ValoracionsAdapter
 * <p/>
 * Classe que modela una fila de l'apartat de valoracions
 */
public class ValoracionsAdapter extends ArrayAdapter<Opinions> {

    private Activity context;
    private TextView txtNomUsuari;
    private TextView txtData;
    private TextView txtVisites;
    private List<Opinions> dades;
    private ImageTool imgTool;
    private Typeface font;
    private LinearLayout layoutValoracio;
    private ImageView imgLlibreVisites;


    public ValoracionsAdapter(Activity context, List<Opinions> objects) {
        super(context, R.layout.activity_valoracions_adapter, objects);
        this.context = context;
        this.dades = objects;
        this.imgTool = new ImageTool();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        Opinions opinions = this.dades.get(position);

        ValoracionsAdapter opinionsAdapter;

        font = Typeface.createFromAsset(getContext().getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");

        if (element == null) {
            opinionsAdapter = new ValoracionsAdapter(context, dades);
            LayoutInflater inflater = context.getLayoutInflater();
            element = inflater.inflate(R.layout.activity_valoracions_adapter, null);
            opinionsAdapter.txtData = (TextView) element.findViewById(R.id.txtDate);
            opinionsAdapter.txtNomUsuari = (TextView) element.findViewById(R.id.tvNomUser);
            opinionsAdapter.layoutValoracio = (LinearLayout) element.findViewById(R.id.layoutValoracio);
            element.setTag(opinionsAdapter);

        } else {
            opinionsAdapter = (ValoracionsAdapter) element.getTag();
        }

        opinionsAdapter.txtData.setText("" + opinions.getData());
        opinionsAdapter.txtData.setTypeface(font);
        opinionsAdapter.txtNomUsuari.setText(opinions.getUser());
        opinionsAdapter.txtNomUsuari.setTypeface(font);

        return element;
    }
}
