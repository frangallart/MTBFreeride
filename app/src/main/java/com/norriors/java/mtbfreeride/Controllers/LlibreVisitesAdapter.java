package com.norriors.java.mtbfreeride.Controllers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    private ImageView imgUsuari;
    private List<UserVisites> dades;
    private ImageTool imgTool;


    public LlibreVisitesAdapter(Activity context, List<UserVisites> objects) {
        super(context,R.layout.activity_llibre_visites_adapter, objects);
        this.context = context;
        this.dades = objects;
        this.imgTool = new ImageTool();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View element = convertView;
        if(element == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            element = inflater.inflate(R.layout.activity_llibre_visites_adapter, null);
        }
        UserVisites usuari  = this.dades.get(position);


        txtNomUsuari = (TextView) element.findViewById(R.id.txtNomuser);
        txtNomUsuari.setText(usuari.getUser());


        txtData = (TextView)element.findViewById(R.id.txtDate);
        txtData.setText(""+usuari.getData());


        txtVisites = (TextView) element.findViewById(R.id.txtVisites);
        txtVisites.setText("Num. visites: " + usuari.getTotal());


        imgUsuari = (ImageView) element.findViewById(R.id.imgUser);
        imgUsuari.setImageBitmap(getBitmap(usuari.getImg()));
        return element;
    }

    public Bitmap getBitmap(String img) {
        try {
            byte[] byteData = Base64.decode(img, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteData, 0, byteData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


