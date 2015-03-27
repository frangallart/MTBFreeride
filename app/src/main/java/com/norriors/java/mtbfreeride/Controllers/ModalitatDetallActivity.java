package com.norriors.java.mtbfreeride.Controllers;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.norriors.java.mtbfreeride.R;

/**
 * Classe ModalitatDetallActivity
 */
public class ModalitatDetallActivity extends ActionBarActivity implements View.OnClickListener {

    // UI references.
    private TextView tvModalTitol, tvModalDetall;
    private VideoView videoModal;
    private ImageView ivModal;
    private Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_detall);

        setupGui();
    }

    /**
     * Mètode que recupera els controls de la GUI i toca espectes de disseny
     */
    private void setupGui() {

        tvModalTitol = (TextView) findViewById(R.id.tvModalitatTitol);
        tvModalDetall = (TextView) findViewById(R.id.tvModalitatDetall);

        font = Typeface.createFromAsset(getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");
        tvModalTitol.setTypeface(font);
        tvModalDetall.setTypeface(font);

    }

    /**
     * Mètode que controla l'event onClick
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        // Si hi ha conexiò a internet
        /*if (InternetUtil.isOnline(LoginUsuari.this)) {
            switch (v.getId()) {
                case (R.id.btnLogin):
                    //new UserLoginTask(etUsuari.getText().toString(), etPassword.getText().toString()).execute();
                    break;
                case (R.id.btnRegistre):
                    Intent intent = new Intent(LoginUsuari.this, RegistreUsuari.class);
                    startActivity(intent);
                    break;
            }
        } else {
            InternetUtil.showAlertDialog(LoginUsuari.this, "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
        }*/
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
   /* public class UserLoginTask extends AsyncTask<String, Void, ArrayList<User>> {

        private String usuari;
        private String contrasenya;

        UserLoginTask(String usuari, String contrasenya) {
            this.usuari = usuari;
            this.contrasenya = contrasenya;
        }*/

        /**
         * Mètode que s'executa abans de començar amb la tasca
         */
       /* @Override
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
                sessioUsuari.createUserLoginSession(usuaris.get(0).getNom(), usuaris.get(0).getEmail());

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
    }*/
}
