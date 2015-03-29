package com.norriors.java.mtbfreeride.Controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

// TODO : Que entri Alhora de entrar mitjançant el teclat
// TODO : Per a la tasca que permet acccedir a un usuari intentar que no sigui una llista ArrayList
/**
 * Classe LoginUsuaris
 * <p/>
 * Classe que controla l'entrada d'un usuari o el registre d'un usuari nou
 */
public class LoginUsuari extends ActionBarActivity implements OnClickListener {

    private static final String URL = "http://provesrasp.ddns.net/aplicacio/usuaris.php";

    private UserLoginTask mAuthTask = null;

    private UsuariSessionManager sessioUsuari;

    // UI references.
    private EditText etUsuari;
    private EditText etPassword;
    private View mProgressView;
    private TextView tvSeparador;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        sessioUsuari = new UsuariSessionManager(getApplicationContext());

        setupGui();
    }

    /**
     * Mètode que recupera els controls de la GUI i toca espectes de disseny
     */
    private void setupGui() {
        etUsuari = (EditText) findViewById(R.id.etUsuari);
        etPassword = (EditText) findViewById(R.id.etPassword);
        mProgressView = findViewById(R.id.login_progress);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnRegistre);
        tvSeparador = (TextView) findViewById(R.id.tvSeparador);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");
        etUsuari.setTypeface(font);
        etPassword.setTypeface(font);
        tvSeparador.setTypeface(font);
    }

    /**
     * Mètode que controla l'event onClick
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        // Si hi ha conexiò a internet
        if (InternetUtil.isOnline(LoginUsuari.this)) {
            switch (v.getId()) {
                case (R.id.btnLogin):
                    new UserLoginTask(etUsuari.getText().toString(), etPassword.getText().toString()).execute();
                    break;
                case (R.id.btnRegistre):
                    Intent intent = new Intent(LoginUsuari.this, RegistreUsuari.class);
                    startActivity(intent);
                    break;
            }
        } else {
            InternetUtil.showAlertDialog(LoginUsuari.this, "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String, Void, ArrayList<User>> {

        private String usuari;
        private String contrasenya;

        UserLoginTask(String usuari, String contrasenya) {
            this.usuari = usuari;
            this.contrasenya = contrasenya;
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
            HttpPost httppostreq = new HttpPost(URL);
            HttpResponse httpresponse = null;
            try {
                List<NameValuePair> parametres = new ArrayList<NameValuePair>(1);
                // Com només ha de descarregar l'usuari que s'ha identificat
                parametres.add(new BasicNameValuePair("nom", usuari));
                parametres.add(new BasicNameValuePair("password", contrasenya));

                httppostreq.setEntity(new UrlEncodedFormEntity(parametres));
                httpresponse = httpclient.execute(httppostreq);
                String responseText = EntityUtils.toString(httpresponse.getEntity());
                // Retorno la llista de punts
                usuaris = tractarJSON(responseText);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return usuaris;
        }

        @Override
        protected void onPostExecute(ArrayList<User> usuaris) {

            if (usuaris != null) {
                // Crear user login session
                sessioUsuari.createUserLoginSession(usuaris.get(0).getUser(),
                                                    usuaris.get(0).getEmail(),
                                                    usuaris.get(0).getImg());

                Intent act = new Intent(getApplicationContext(), MainActivity.class);
                act.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(act);
                finish();
            } else {
                err_login();
                Toast toast = Toast.makeText(getApplicationContext(), "Nom d'usuari o contrasenya incorrectes", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 200);
                toast.show();
            }

            mProgressView.setVisibility(View.GONE);
        }

        private ArrayList<User> tractarJSON(String json) {
            System.out.println(json.toString());
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<User>>() {
            }.getType());
        }
    }

    /**
     * Mètode que fa vibrar el mòbil
     */
    public void err_login() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}