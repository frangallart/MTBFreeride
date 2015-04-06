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
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private boolean totsUsuaris;
    private int pos_inicial;
    private int pos_final;
    private boolean pararTask;
    private List<DescarregarDades> task;


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
        this.totsUsuaris = false;
        this.pos_inicial = 1;
        this.pararTask = false;
        this.task = new ArrayList<DescarregarDades>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("LLibre");
        View rootView = inflater.inflate(R.layout.fragment_llibre_visites, container, false);
        dades = new ArrayList<UserVisites>();

        lstVisites = (ListView) rootView.findViewById(R.id.lstVisites);

        // Listener que s'encarrega de l'scroll
        lstVisites.setOnScrollListener(new VisitesScrollListener() {
            @Override
            public void carregaDades() {
                // Si encara no s'han carregat tots els usuaris, podem seguir
                // fent peticions
                if (!totsUsuaris) {
                    pos_final = pos_inicial + 5;
                    while (pos_inicial < (pos_final)) {
                        task.add(new DescarregarDades());
                        task.get(pos_inicial).execute(pos_inicial);
                        pos_inicial++;
                    }
                }
            }
        });

        adapterVisites = new LlibreVisitesAdapter(getActivity(), dades);
        lstVisites.setAdapter(adapterVisites);

        llibre_progress = (ProgressBar) rootView.findViewById(R.id.llibre_progress);

        task.add(new DescarregarDades());
        task.get(0).execute(0);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        for (int i = 0; i < task.size(); i++){
            task.get(i).cancel(true);
        }
        this.task.clear();
    }

    @Override
    public void onResume() {
        super.onPause();
        if (!totsUsuaris) {
            adapterVisites.clear();

            for (int i = 0; i < (pos_final); i++) {
                task.add(new DescarregarDades());
                task.get(i).execute(i);
            }
        }
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

    class DescarregarDades extends AsyncTask<Integer, Void, ArrayList<UserVisites>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llibre_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<UserVisites> doInBackground(Integer... params) {
            ArrayList<UserVisites> llistaTitulars = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(URL_DATA);
            HttpResponse httpresponse = null;
            try {
                List<NameValuePair> parametres = new ArrayList<NameValuePair>(1);
                // Aquesta clau s'utilitza per saber quina visita hem de carregar
                parametres.add(new BasicNameValuePair("item", params[0].toString()));
                httppostreq.setEntity(new UrlEncodedFormEntity(parametres));
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

            // Si la llista no es null, carreguem els usuaris. Sinó
            // posem la booleana totsUsuaris a cert.
            if (llista != null) {
                adapterVisites.setData(llista);
            }else{
                totsUsuaris = true;
            }
            llibre_progress.setVisibility(View.GONE);
        }

        private ArrayList<UserVisites> tractarJSON(String json) {
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<UserVisites>>() {
            }.getType());
        }
    }
}
