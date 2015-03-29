package com.norriors.java.mtbfreeride.Controllers;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LlibreVisitesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LlibreVisitesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LlibreVisitesFragment extends  android.support.v4.app.Fragment{

    private ArrayList<UserVisites> dades;
    private ListView lstVisites;
    private LlibreVisitesAdapter adapterVisites;
    private static final String URL_DATA = "http://provesrasp.ddns.net/aplicacio/llibreVisites.php";
    private DescarregarDades downloadVisites;


    public static LlibreVisitesFragment newInstance() {
        LlibreVisitesFragment fragment = new LlibreVisitesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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

        View rootView = inflater.inflate(R.layout.fragment_llibre_visites, container, false);
        dades = new ArrayList<UserVisites>();

        lstVisites = (ListView) rootView.findViewById(R.id.lstVisites);

        downloadVisites = new DescarregarDades();
        downloadVisites.execute(URL_DATA);


        // Inflate the layout for this fragment
        return rootView;
    }

    private void refreshData() {
        if(dades==null) {
            dades = new ArrayList<UserVisites>();
        }
        adapterVisites = new LlibreVisitesAdapter(getActivity(), dades);
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

    class DescarregarDades extends AsyncTask<String, Void, ArrayList<UserVisites>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
        }


        private ArrayList<UserVisites> tractarJSON(String json) {
            Gson converter = new Gson();

            return converter.fromJson(json, new TypeToken<ArrayList<UserVisites>>(){}.getType());
        }

    }

}
