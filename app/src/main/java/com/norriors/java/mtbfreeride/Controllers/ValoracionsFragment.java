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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arnau on 02/04/2015.
 */
public class ValoracionsFragment extends android.support.v4.app.Fragment {

    private ArrayList<Opinions> dades;
    private ListView lstValoracions;
    private ValoracionsAdapter adapterVisites;
    private static final String URL_DATA = "http://provesrasp.ddns.net/aplicacio/valoracions.php";
    private static final String URL_SO = "http://provesrasp.ddns.net/aplicacio/valoracions_so.php";
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
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_editUser:
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

        // Inflate the layout for this fragment
        return rootView;
    }

    public void descarregaDades(){
        new DescarregarDades().execute(URL_DATA);
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
            try
            {
                byte[] data = Base64.decode(llista.get(0).getSo(), Base64.DEFAULT);
                File path=new File(getActivity().getCacheDir()+"/musicfile.3gp");
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(data);
                fos.close();
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getActivity().getCacheDir()+"/musicfile.3gp");
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
            catch (IOException ex)
            {
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
