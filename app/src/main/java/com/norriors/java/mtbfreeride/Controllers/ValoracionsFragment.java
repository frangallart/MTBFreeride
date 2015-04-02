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
import com.norriors.java.mtbfreeride.Models.Opinions;
import com.norriors.java.mtbfreeride.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Arnau on 02/04/2015.
 */
public class ValoracionsFragment extends android.support.v4.app.Fragment {

    private ArrayList<Opinions> dades;
    private ListView lstVisites;
    private ValoracionsAdapter adapterVisites;
    private static final String URL_DATA = "http://provesrasp.ddns.net/aplicacio/valoracions.php";
    private DescarregarDades downloadOpinions;
    private ProgressBar opinions_progress;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_llibre_visites, container, false);
        dades = new ArrayList<Opinions>();

        lstVisites = (ListView) rootView.findViewById(R.id.lstVisites);
        opinions_progress = (ProgressBar) rootView.findViewById(R.id.llibre_progress);

        downloadOpinions = new DescarregarDades();
        downloadOpinions.execute(URL_DATA);


        // Inflate the layout for this fragment
        return rootView;
    }

    private void refreshData() {
        if (dades == null) {
            dades = new ArrayList<Opinions>();
        }
        adapterVisites = new ValoracionsAdapter(getActivity(), dades);
        lstVisites.setAdapter(adapterVisites);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class DescarregarDades extends AsyncTask<String, Void, ArrayList<Opinions>> {

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
}
