package com.norriors.java.mtbfreeride.Controllers;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.norriors.java.mtbfreeride.R;

import java.util.HashMap;

/**
 * Classe Perfil Usuari Fragment
 * En aquesta classe sens mostren les dades bàsiques de l'usuari
 * (Imatge de perfil, nom , cognoms i correu)
 * I ens permet fer l'edició de es dades esmentades a partir del botó editar
 */
public class PerfilUsuariFragment extends android.support.v4.app.Fragment {


    private OnFragmentInteractionListener mListener;
    private EditText txtNom;
    private EditText txtPrimerCognom;
    private EditText txtSegonCognom;
    private EditText txtEmail;
    private MLRoundedImageView imgUser;

    private UsuariSessionManager sessioUsuari;
    private HashMap<String, String> dadesUsuari;
    private ImageTool imgTool;
    private Toast toast;
    private Bitmap novaImatge;


    public static PerfilUsuariFragment newInstance() {
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
        View viewPerfil = inflater.inflate(R.layout.fragment_perfil_usuari, container, false);

        imgTool = new ImageTool();

        //Utilitzem el shared preferences per recollir les dades
        sessioUsuari = new UsuariSessionManager(getActivity().getBaseContext());
        dadesUsuari = sessioUsuari.getUserDetails();


        //Text View Nom
        txtNom = (EditText) viewPerfil.findViewById(R.id.txtNom);
        txtNom.setText(dadesUsuari.get(UsuariSessionManager.KEY_NAME));

        //TextText View Primer cognom
        txtPrimerCognom = (EditText) viewPerfil.findViewById(R.id.txtPrimerCognom);
        txtPrimerCognom.setText(dadesUsuari.get(UsuariSessionManager.KEY_SURNAME1));


        //Text View Segon Cognom
        txtSegonCognom = (EditText) viewPerfil.findViewById(R.id.txtSegonCognom);
        txtSegonCognom.setText(dadesUsuari.get(UsuariSessionManager.KEY_SURNAME2));

        //Text View Email
        txtEmail = (EditText) viewPerfil.findViewById(R.id.txtEmail);
        txtEmail.setText(dadesUsuari.get(UsuariSessionManager.KEY_EMAIL));

        //MLRounded Image View
        imgUser = (MLRoundedImageView) viewPerfil.findViewById(R.id.imgUser);
        imgUser.setImageBitmap(imgTool.getBitmap(dadesUsuari.get(UsuariSessionManager.KEY_IMAGE)));


        // Inflate the layout for this fragment
        return viewPerfil;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editar_user, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_editUser:

                    toast = new Toast(getActivity().getBaseContext());
                    toast.makeText(getActivity().getBaseContext(), "Ara, pots editar el teu perfil", Toast.LENGTH_SHORT);
                    toast.show();

                    txtNom.setEnabled(true);
                    txtPrimerCognom.setEnabled(true);
                    txtSegonCognom.setEnabled(true);
                    txtEmail.setEnabled(true);

                return true;

            case R.id.action_saveChange:

                toast = new Toast(getActivity().getBaseContext());
                toast.makeText(getActivity().getBaseContext(), "Canvis gaurdats correctament", Toast.LENGTH_SHORT);
                toast.show();

                txtNom.setEnabled(false);
                txtPrimerCognom.setEnabled(false);
                txtSegonCognom.setEnabled(false);
                txtEmail.setEnabled(false);

                sessioUsuari.createUserLoginSession(txtNom.getText().toString(), txtPrimerCognom.getText().toString(), txtSegonCognom.getText().toString(), dadesUsuari.get(UsuariSessionManager.KEY_PASS), txtEmail.getText().toString(), imgTool.getImageString(novaImatge));



            default:
                return super.onOptionsItemSelected(item);

        }

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
