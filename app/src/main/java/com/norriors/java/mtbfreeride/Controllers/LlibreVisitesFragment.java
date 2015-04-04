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

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.norriors.java.mtbfreeride.Models.UserVisites;
import com.norriors.java.mtbfreeride.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe LlibreVisites Fragment,
 * Fragment que ens permet veure les visites dels usuaris a l'aplicació
 */
public class LlibreVisitesFragment extends android.support.v4.app.Fragment {

    private ArrayList<UserVisites> dades;
    private ListView lstVisites;
    private LlibreVisitesAdapter adapterVisites;
    private static final String URL_DATA = "http://provesrasp.ddns.net/aplicacio/llibreVisites.php";
    private DescarregarDades downloadVisites;
    private ProgressBar llibre_progress;


    public static LlibreVisitesFragment newInstance() {
        LlibreVisitesFragment fragment = new LlibreVisitesFragment();
        return fragment;
    }

    public LlibreVisitesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("LLibre de Visites");
        View rootView = inflater.inflate(R.layout.fragment_llibre_visites, container, false);
        dades = new ArrayList<UserVisites>();

        lstVisites = (ListView) rootView.findViewById(R.id.lstVisites);
        llibre_progress = (ProgressBar) rootView.findViewById(R.id.llibre_progress);

        downloadVisites = new DescarregarDades();
        downloadVisites.execute(URL_DATA);


        // Inflate the layout for this fragment
        return rootView;
    }

    private void refreshData() {
        if (dades == null) {
            dades = new ArrayList<UserVisites>();
        }
        adapterVisites = new LlibreVisitesAdapter(getActivity(), dades);
        lstVisites.setAdapter(adapterVisites);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class DescarregarDades extends AsyncTask<String, Void, ArrayList<UserVisites>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llibre_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<UserVisites> doInBackground(String... params) {
            ArrayList<UserVisites> llistaTitulars = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(URL_DATA);
            HttpResponse httpresponse = null;
            try {
                httpresponse = httpclient.execute(httppostreq);
                String responseText = EntityUtils.toString(httpresponse.getEntity());
                llistaTitulars = tractarJSON(responseText);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return llistaTitulars;
        }

        @Override
        protected void onPostExecute(ArrayList<UserVisites> llista) {
            dades = llista;
            refreshData();
            llibre_progress.setVisibility(View.GONE);
        }


        private ArrayList<UserVisites> tractarJSON(String json) {
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<UserVisites>>() {
            }.getType());
        }
    }
}
