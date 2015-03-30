package com.norriors.java.mtbfreeride.Controllers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private MLRoundedImageView imgUsuari;
    private List<UserVisites> dades;
    private ImageTool imgTool;
    private Typeface font;
    private LinearLayout parentLayout;
    private Bitmap fotoUser;


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

        font = Typeface.createFromAsset(getContext().getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");

        UserVisites usuari  = this.dades.get(position);


        txtNomUsuari = new TextView(getContext());
        txtNomUsuari.setText(usuari.getUser());
        txtNomUsuari.setTypeface(font);


        txtData = (TextView)element.findViewById(R.id.txtDate);
        txtData.setText(""+usuari.getData());
        txtData.setTypeface(font);


        txtVisites = (TextView) element.findViewById(R.id.txtVisites);
        txtVisites.setText("Num. visites: " + usuari.getTotal());
        txtVisites.setTypeface(font);

        parentLayout = (LinearLayout) element.findViewById(R.id.parentLayout);

        fotoUser = Bitmap.createScaledBitmap(imgTool.getBitmap(usuari.getImg()), 100, 100, false);

        imgUsuari = new MLRoundedImageView(getContext());
        imgUsuari.setImageBitmap(fotoUser);

        parentLayout.addView(imgUsuari);
        parentLayout.addView(txtNomUsuari);



        /*LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams((int) getContext().getResources().getDimension(R.dimen.photo_user_visitesH), (int) getContext().getResources().getDimension(R.dimen.photo_user_visitesW));
        imgUsuari.setLayoutParams(layoutParams);*/



        return element;
    }


}


