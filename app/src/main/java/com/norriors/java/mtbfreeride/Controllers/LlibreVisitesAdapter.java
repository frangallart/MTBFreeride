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

import com.norriors.java.mtbfreeride.Models.UserVisites;
import com.norriors.java.mtbfreeride.R;

import java.util.List;

/**
 * Classe LlibreVisitesAdapter
 * Adapter que ens permet posar al llibre de visites (listView) la visita d'un usuari
 * Amb tots els seus elements: Foto, nom, num visites, data primera visita
 */
public class LlibreVisitesAdapter extends ArrayAdapter<UserVisites> {

    private Activity context;
    private TextView txtNomUsuari;
    private TextView txtData;
    private TextView txtVisites;
    private List<UserVisites> dades;
    private ImageTool imgTool;
    private Typeface font;
    private LinearLayout parentLayout;
    private ImageView imgLlibreVisites;

    /**
     * Constructor
     * @param context
     * @param objects
     */
    public LlibreVisitesAdapter(Activity context, List<UserVisites> objects) {
        super(context,R.layout.activity_llibre_visites_adapter, objects);
        this.context = context;
        this.dades = objects;
        this.imgTool = new ImageTool();
    }

    /**
     * Mètode que ens crea la vista
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent){
        View element = convertView;
        UserVisites usuari  = this.dades.get(position);

        LlibreVisitesAdapter llibre;

        font = Typeface.createFromAsset(getContext().getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");

        if(element == null) {
            llibre = new LlibreVisitesAdapter(context,dades);
            LayoutInflater inflater = context.getLayoutInflater();
            element = inflater.inflate(R.layout.activity_llibre_visites_adapter, null);
            llibre.imgLlibreVisites = (ImageView) element.findViewById(R.id.imgLlibreVisites);
            llibre.txtData = (TextView)element.findViewById(R.id.txtDate);
            llibre.txtNomUsuari = (TextView) element.findViewById(R.id.tvNomUser);
            llibre.txtVisites = (TextView) element.findViewById(R.id.txtVisites);

            element.setTag(llibre);
        }

        else{
            llibre = (LlibreVisitesAdapter)element.getTag();
        }

        llibre.imgLlibreVisites.setImageBitmap(ImageTool.getBitmap(usuari.getImg()));
        llibre.txtData.setText(""+usuari.getData());
        llibre.txtData.setTypeface(font);
        llibre.txtNomUsuari.setText(usuari.getUser());
        llibre.txtNomUsuari.setTypeface(font);
        llibre.txtVisites.setText("Num. visites: " + usuari.getTotal());
        llibre.txtVisites.setTypeface(font);

        return element;
    }
}


