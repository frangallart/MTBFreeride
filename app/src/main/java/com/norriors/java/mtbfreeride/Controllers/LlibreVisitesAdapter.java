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
 * Created by Ruben on 28/3/15.
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


    public LlibreVisitesAdapter(Activity context, List<UserVisites> objects) {
        super(context,R.layout.activity_llibre_visites_adapter, objects);
        this.context = context;
        this.dades = objects;
        this.imgTool = new ImageTool();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View element = convertView;
        UserVisites usuari  = this.dades.get(position);

        font = Typeface.createFromAsset(getContext().getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");

        if(element == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            element = inflater.inflate(R.layout.activity_llibre_visites_adapter, null);
        }

        imgLlibreVisites = (ImageView) element.findViewById(R.id.imgLlibreVisites);
        parentLayout = (LinearLayout) element.findViewById(R.id.parentLayout);
        txtData = (TextView)element.findViewById(R.id.txtDate);
        txtNomUsuari = (TextView) element.findViewById(R.id.tvNomUser);
        txtVisites = (TextView) element.findViewById(R.id.txtVisites);

        imgLlibreVisites.setImageBitmap(ImageTool.getBitmap(usuari.getImg()));
        txtData.setText(""+usuari.getData());
        txtData.setTypeface(font);
        txtNomUsuari.setText(usuari.getUser());
        txtVisites.setText("Num. visites: " + usuari.getTotal());
        txtVisites.setTypeface(font);

        return element;
    }
}


