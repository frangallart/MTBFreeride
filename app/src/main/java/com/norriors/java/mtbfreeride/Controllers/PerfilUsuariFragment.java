package com.norriors.java.mtbfreeride.Controllers;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.norriors.java.mtbfreeride.R;

import java.util.HashMap;

/**
 * Classe Perfil Usuari Fragment
 * En aquesta classe sens mostren les dades bàsiques de l'usuari
 * (Imatge de perfil, nom , cognoms i correu)
 * I ens permet fer l'edició de es dades esmentades a partir del botó editar
 *
 */
public class PerfilUsuariFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private TextView txtNom;
    private TextView txtPrimerCognom;
    private TextView txtSegonCognom;
    private TextView  txtEmail;
    private MLRoundedImageView imgUser;

    private UsuariSessionManager sessioUsuari;
    private HashMap<String, String> dadesUsuari;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUsuariFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilUsuariFragment newInstance(String param1, String param2) {
        PerfilUsuariFragment fragment = new PerfilUsuariFragment();

        return fragment;
    }

    public PerfilUsuariFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Recullim la vista
        View viewPerfil =  inflater.inflate(R.layout.fragment_perfil_usuari, container, false);

        //Utilitzem el shared preferences per recollir les dades
        sessioUsuari = new UsuariSessionManager(getActivity().getBaseContext());
        dadesUsuari = sessioUsuari.getUserDetails();


        //Text View Nom
        txtNom = (TextView) viewPerfil.findViewById(R.id.txtNom);
        txtNom.setText(dadesUsuari.get("name"));

        //TextText View Primer cognom
        txtPrimerCognom = (TextView) viewPerfil.findViewById(R.id.txtPrimerCognom);


        //Text View Segon Cognom
        txtSegonCognom = (TextView) viewPerfil.findViewById(R.id.txtSegonCognom);

        //Text View Email
        txtEmail = (TextView) viewPerfil.findViewById(R.id.txtEmail);
        txtEmail.setText(dadesUsuari.get("email"));

        //MLRounded Image View
        imgUser = (MLRoundedImageView) viewPerfil.findViewById(R.id.imgUser);









        // Inflate the layout for this fragment
        return viewPerfil;
    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editar_user, menu);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

}
