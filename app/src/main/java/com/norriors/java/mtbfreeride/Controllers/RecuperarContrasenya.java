package com.norriors.java.mtbfreeride.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.norriors.java.mtbfreeride.Models.User;
import com.norriors.java.mtbfreeride.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecuperarContrasenya extends ActionBarActivity implements View.OnClickListener {

    private EditText edNick;
    private Button btnSolicitar;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasenya);

        this.btnSolicitar = (Button)findViewById(R.id.btnSolicitar);
        this.edNick = (EditText)findViewById(R.id.edNick);
        this.mProgressView = (ProgressBar)findViewById(R.id.login_progress);

        this.btnSolicitar.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_recuperar_contrasenya, menu);
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

    @Override
    public void onClick(View v) {

        if (InternetUtil.isOnline(RecuperarContrasenya.this)) {
            switch (v.getId()) {
                case (R.id.btnSolicitar):
                    if (!this.edNick.getText().toString().equals("")) {
                        this.btnSolicitar.setEnabled(false);
                        new ComprovaNick(this.edNick.getText().toString()).execute();
                    } else {
                        err_login("user");
                    }
                    break;
            }
        } else {
            InternetUtil.showAlertDialog(RecuperarContrasenya.this, "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
        }
    }

    /**
     * Tasca asíncrona per validar l'usuari del login
     */
    public class ComprovaNick extends AsyncTask<String, Void, ArrayList<User>> {

        private String usuari;

        ComprovaNick(String usuari) {
            this.usuari = usuari;
        }

        /**
         * Mètode que s'executa abans de començar amb la tasca
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<User> doInBackground(String... strings) {
            ArrayList<User> usuaris = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost("http://mtbfreeride.hostoi.com/recuperaPass.php");
            HttpResponse httpresponse = null;
            try {
                List<NameValuePair> parametres = new ArrayList<NameValuePair>(1);
                // Com només ha de descarregar l'usuari que s'ha identificat
                parametres.add(new BasicNameValuePair("nom", usuari));

                httppostreq.setEntity(new UrlEncodedFormEntity(parametres));
                httpresponse = httpclient.execute(httppostreq);
                String responseText = EntityUtils.toString(httpresponse.getEntity());
                usuaris = tractarJSON(responseText);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return usuaris;
        }

        @Override
        protected void onPostExecute(ArrayList<User> usuaris) {

            if (usuaris != null) {
                new EnviaCorreu().execute(usuaris.get(0));
            } else {
                err_login("noUser");
            }
        }

        private ArrayList<User> tractarJSON(String json) {
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<User>>() {
            }.getType());
        }
    }


    /**
     * Tasca asíncrona que envia el correu a l'usuari
     */
    public class EnviaCorreu extends AsyncTask<User, Void, Boolean> {

        /**
         * Mètode que s'executa abans de començar amb la tasca
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(User... user) {
            try {
                String missatge = "Hola " + user[0].getNom() + " " + user[0].getCognom1() + " " + user[0].getCognom2() + ".\n\nAquesta és la teva contrasenya: " + user[0].getPassword() + "\n\nMTB Freeride.";
                com.norriors.java.mtbfreeride.Controllers.EnviaMail sender = new com.norriors.java.mtbfreeride.Controllers.EnviaMail("mtbfreerideapp@gmail.com", "mtbfreeride");
                sender.sendMail("Recupera Contrasenya", missatge, user[0].getEmail().trim());
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean usuaris) {

            if (usuaris) {
                mProgressView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "El correu s'ha enviat correctament", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                err_login("noMail");
            }
        }
    }


    /**
     * Mètode que fa vibrar el mòbil
     */
    public void err_login(String error) {

        switch (error) {

            case "user":
                Toast.makeText(this, "Has d'escriure un nick.", Toast.LENGTH_SHORT).show();
                break;
            case "noUser":
                Toast.makeText(this, "No hi ha cap usuari registrat amb aquest nick.", Toast.LENGTH_SHORT).show();
                break;
            case "noMail":
                Toast.makeText(this, "Error a l'enviar el mail. Torno a provar", Toast.LENGTH_SHORT).show();
                break;
        }
        this.btnSolicitar.setEnabled(true);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
