package com.norriors.java.mtbfreeride.Controllers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
 * Classe SampleListFragment
 */
public class SampleListFragment2 extends ScrollTabHolderFragment {

    private static final String ARG_POSITION = "position";
    private static final String URL = "http://www.infobosccoma.net/pmdm/pois.php";
    private TextView tvView;
    private int mPosition;

    public static Fragment newInstance(int position) {
        SampleListFragment2 f = new SampleListFragment2();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPosition = getArguments().getInt(ARG_POSITION);

        new DescarregarDades().execute("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.fragment_list, null);

        // fem referència a la vista
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        // textView on mostro el contingut del json
        tvView = (TextView) rootView.findViewById(R.id.textView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void adjustScroll(int scrollHeight) {

    }

    class DescarregarDades extends AsyncTask<String, Void, ArrayList<PuntsMapa>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostro la progressbar
            // bar.setVisibility(View.VISIBLE);
            // Netejo el mapa
            //mMap.clear();
        }

        /**
         * Procés de descarrega de les dades
         *
         * @param params
         * @return la llista de punts
         */
        @Override
        protected ArrayList<PuntsMapa> doInBackground(String... params) {
            ArrayList<PuntsMapa> llistaPunts = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(URL);
            HttpResponse httpresponse = null;
            try {
                List<NameValuePair> parametres = new ArrayList<NameValuePair>(1);
                // Com a paràmetre li dic que només descarregui els punts de les ciutats
                // que tinguin el valor entrat al buscador

                parametres.add(new BasicNameValuePair("city", params[0]));
                httppostreq.setEntity(new UrlEncodedFormEntity(parametres));

                httpresponse = httpclient.execute(httppostreq);

                String responseText = EntityUtils.toString(httpresponse.getEntity());
                // Retorno la llista de punts
                llistaPunts = tractarJSON(responseText);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return llistaPunts;
        }

        // Pinto el resultat de l'asyncTask
        @Override
        protected void onPostExecute(ArrayList<PuntsMapa> llista) {
            tvView.setText(llista.get(mPosition).getName());
        }

        private ArrayList<PuntsMapa> tractarJSON(String json) {
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<PuntsMapa>>() {
            }.getType());
        }
    }
}
