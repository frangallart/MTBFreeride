package com.norriors.java.mtbfreeride.Controllers;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.norriors.java.mtbfreeride.R;

/**
 * Classe RegistreUsuari
 */
public class RegistreUsuari extends ActionBarActivity {

    private MLRoundedImageView roundedImageView;
    private LinearLayout linearImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre_usuari);


        //Instanciem el linear de la vista que contindrà la imatge
         linearImage = (LinearLayout) findViewById(R.id.linearImage);

        //Instanciem un objecte del tipus MLRoundedImageView(Exten de ImageView) que ens permetrà tenir un imageView Rodó
        roundedImageView = new MLRoundedImageView(this);


        //Assignem la imatge al imageView
        roundedImageView.setImageResource(R.drawable.img_splash_screen);

        //Afegim l'ImageView al Linear
        linearImage.addView(roundedImageView);
        //Paràmetres per decidir la mida
        roundedImageView.getLayoutParams().height = 300;
        roundedImageView.getLayoutParams().width = 300;
        //Paràmetre per indicar la gravetat
        linearImage.setGravity(Gravity.CENTER_HORIZONTAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registre_usuari, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
