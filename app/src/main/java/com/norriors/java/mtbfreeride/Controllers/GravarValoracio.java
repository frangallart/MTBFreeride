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

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.norriors.java.mtbfreeride.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe que ens permet gravar una valoració
 */
public class GravarValoracio extends ActionBarActivity {

    private MediaRecorder gravador;
    private String nomGravacio = null;
    private String so;
    private MediaPlayer mPlayer;
    private Button btnPlay;
    private Button btnPenjaSo;
    private ToggleButton btnGravar;
    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;
    private final String URL = "http://provesrasp.ddns.net/aplicacio/insertValoracio.php";
    private ProgressBar bar;

    public GravarValoracio() {
    }

    /**
     * Mètode onCreate
     * Instancia tots els elements de la vista i les seves accions
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoracions_gravar);
        btnGravar = (ToggleButton) findViewById(R.id.btnGravar);
        btnPenjaSo = (Button) findViewById(R.id.btnPenjaSo);
        bar = (ProgressBar) findViewById(R.id.bar);

        btnGravar.setText("Start Recording");

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    btnGravar.setText("Stop recording");
                } else {
                    btnGravar.setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setText("Start playing");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomGravacio != null) {
                    onPlay();
                }else{
                    Toast.makeText(GravarValoracio.this,"No has gravat la valoració", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPenjaSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomGravacio != null) {
                    new PenjaValoracio().execute();
                }else{
                    Toast.makeText(GravarValoracio.this,"Has de gravar una valoració", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    /**
     * Mètode que inicia la gravació d'àudio.
     */
    private void iniciGravacio() {
        nomGravacio = this.getApplicationContext().getApplicationInfo().dataDir + "/test.3gp";
        gravador = new MediaRecorder();
        gravador.reset();
        gravador.setAudioSource(MediaRecorder.AudioSource.MIC);
        gravador.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        gravador.setOutputFile(nomGravacio);
        gravador.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            gravador.prepare();
        } catch (IOException e) {
            Log.e("Gravacio", "Error en la preparació de la gravació");
        }

        gravador.start();
    }

    /**
     * Mètode que ens para de gravar i ens deixa el gravador a null,
     * a punt per tornar a gravar
     */
    private void pararGravacio() {
        gravador.stop();
        gravador.release();
        gravador = null;
    }

    /**
     * Mètode que ens inicia la gravació o ens la para
     * en el cas de què estigui encesa la gravació
     * @param start
     */
    private void onRecord(boolean start) {
        if (start) {
            iniciGravacio();
        } else {
            pararGravacio();
        }
    }

    /**
     * Mètode que inicia la reproducció de la gravació realitzada
     */
    private void onPlay() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(nomGravacio);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("ERROR", "prepare() failed");
        }
    }

    public static byte[] readFile(String file) throws IOException {
        return readFile(new File(file));
    }

    public static byte[] readFile(File file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            file.delete();
            return data;
        } finally {
            f.close();
        }
    }


    /**
     * Classe que ens emmagatzema la valoració en el servidor i així mostrar-la en el ListView
     */
    class PenjaValoracio extends AsyncTask<String, Void, String> {

        String responseText;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Pintem el progressbar
            bar.setVisibility(View.VISIBLE);
            try {
                byte[] soBytes = readFile(nomGravacio);
                so = new String(Base64.encodeToString(soBytes, Base64.DEFAULT));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        /**
         * Procés que envia les dades
         * a un json que fa l'insert a la base de dades
         * @param params
         * @return una string true o false
         */
        @Override
        protected String doInBackground(String... params) {
            // User Session Manager Class
            UsuariSessionManager sessioUsuari;
            // get user data from session
            HashMap<String, String> dadesUsuari;

            sessioUsuari = new UsuariSessionManager(GravarValoracio.this);
            dadesUsuari = sessioUsuari.getUserDetails();

            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(URL);
            HttpResponse httpresponse = null;
            try {
                List<NameValuePair> parametres = new ArrayList<NameValuePair>(1);
                parametres.add(new BasicNameValuePair("nomUsuari", dadesUsuari.get(UsuariSessionManager.KEY_NAME)));
                parametres.add(new BasicNameValuePair("so", so));
                httppostreq.setEntity(new UrlEncodedFormEntity(parametres));
                httpresponse = httpclient.execute(httppostreq);

                // Resposta del json
                responseText = EntityUtils.toString(httpresponse.getEntity());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseText;
        }

        /**
         * Mètode que recull la resposta del json. Si es true tornem al login sinó
         * mostrem el missatge d'error
         *
         * @param resposta
         */
        @Override
        protected void onPostExecute(String resposta) {

            if (resposta.trim().equals("true")){
                setResult(RESULT_OK);
                finish();
            }
            if (mPlayer != null) {
                mPlayer.release();
            }
            bar.setVisibility(View.GONE);
        }
    }
}

