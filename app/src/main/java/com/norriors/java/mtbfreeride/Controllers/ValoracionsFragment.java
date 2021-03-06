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

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.norriors.java.mtbfreeride.Models.Opinions;
import com.norriors.java.mtbfreeride.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe ValoracionsFragment
 * <p/>
 * En aquesta classe podem escoltar totes les valoracions dels usuaris
 */
public class ValoracionsFragment extends android.support.v4.app.Fragment implements MediaPlayer.OnCompletionListener {

    private ArrayList<Opinions> dades;
    private ListView lstValoracions;
    private ValoracionsAdapter adapterVisites;
    private static final String URL_DATA = "http://mtbfreeride.hostoi.com/valoracions.php";
    private static final String URL_SO = "http://mtbfreeride.hostoi.com/valoracions_so.php";
    private ProgressBar opinions_progress;
    private MediaPlayer mediaPlayer;


    public static ValoracionsFragment newInstance() {
        ValoracionsFragment fragment = new ValoracionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ValoracionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_editUser:
                mediaPlayer.reset();
                Intent intent = new Intent(getActivity(), GravarValoracio.class);
                getActivity().startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Valoracions");

        View rootView = inflater.inflate(R.layout.fragment_llibre_visites, container, false);
        dades = new ArrayList<Opinions>();

        lstValoracions = (ListView) rootView.findViewById(R.id.lstVisites);
        opinions_progress = (ProgressBar) rootView.findViewById(R.id.llibre_progress);

        new DescarregarDades().execute(URL_DATA);

        lstValoracions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obrir una activity
                new DescarregaSo().execute(dades.get(position).getId());
            }
        });

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_valoracions, menu);
    }

    private void refreshData() {
        if (dades == null) {
            dades = new ArrayList<Opinions>();
        }
        adapterVisites = new ValoracionsAdapter(getActivity(), dades);
        lstValoracions.setAdapter(adapterVisites);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        MainActivity.musicConfiguration();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class DescarregarDades extends AsyncTask<String, Void, ArrayList<Opinions>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            opinions_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Opinions> doInBackground(String... params) {
            ArrayList<Opinions> opinions = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(URL_DATA);
            HttpResponse httpresponse = null;
            try {
                httpresponse = httpclient.execute(httppostreq);
                String responseText = EntityUtils.toString(httpresponse.getEntity());
                opinions = tractarJSON(responseText);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return opinions;
        }

        @Override
        protected void onPostExecute(ArrayList<Opinions> llista) {
            dades = llista;
            refreshData();
            opinions_progress.setVisibility(View.GONE);
        }


        private ArrayList<Opinions> tractarJSON(String json) {
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<Opinions>>() {
            }.getType());
        }
    }

    class DescarregaSo extends AsyncTask<Integer, Void, ArrayList<Opinions>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mediaPlayer.reset();
            opinions_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Opinions> doInBackground(Integer... params) {
            ArrayList<Opinions> opinions = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(URL_SO);
            HttpResponse httpresponse = null;
            try {
                List<NameValuePair> parametres = new ArrayList<NameValuePair>(1);
                // Com només ha de descarregar l'usuari que s'ha identificat
                parametres.add(new BasicNameValuePair("idUsuari", params[0].toString()));
                httppostreq.setEntity(new UrlEncodedFormEntity(parametres));
                httpresponse = httpclient.execute(httppostreq);
                String responseText = EntityUtils.toString(httpresponse.getEntity());
                opinions = tractarJSON(responseText);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return opinions;
        }

        @Override
        protected void onPostExecute(ArrayList<Opinions> llista) {
            opinions_progress.setVisibility(View.GONE);
            try {
                if (llista != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                    byte[] data = Base64.decode(llista.get(0).getSo(), Base64.DEFAULT);
                    File path = new File(getActivity().getCacheDir() + File.separator + dateFormat.format(new Date()) + "musicfile.3gp");
                    FileOutputStream fos = new FileOutputStream(path);
                    fos.write(data);
                    fos.close();
                    mediaPlayer.setDataSource(getActivity().getCacheDir() + File.separator + dateFormat.format(new Date()) + "musicfile.3gp");
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    if (mediaPlayer.isPlaying()) {
                        MainActivity.musicComentaris(false);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        private ArrayList<Opinions> tractarJSON(String json) {
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<Opinions>>() {
            }.getType());
        }
    }
}
