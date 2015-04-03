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
import android.widget.Button;
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
    private EditText txtPass;

    private MLRoundedImageView imgUser;

    private Button btnPass;

    private UsuariSessionManager sessioUsuari;
    private HashMap<String, String> dadesUsuari;
    private ImageTool imgTool;
    private Toast toast;
    private Bitmap novaImatge;

    private MenuItem itemEdit;
    private MenuItem itemSave;
    private MenuItem itemCancel;


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

        //Recollim la vista
        View viewPerfil = inflater.inflate(R.layout.fragment_perfil_usuari, container, false);

        imgTool = new ImageTool();

        //Utilitzem el shared preferences per recollir les dades
        sessioUsuari = new UsuariSessionManager(getActivity().getBaseContext());
        dadesUsuari = sessioUsuari.getUserDetails();


        //EditText Nom
        txtNom = (EditText) viewPerfil.findViewById(R.id.txtNom);
        txtNom.setText(dadesUsuari.get(UsuariSessionManager.KEY_NAME));

        //EditText Primer cognom
        txtPrimerCognom = (EditText) viewPerfil.findViewById(R.id.txtPrimerCognom);
        txtPrimerCognom.setText(dadesUsuari.get(UsuariSessionManager.KEY_SURNAME1));


        //EditText Segon Cognom
        txtSegonCognom = (EditText) viewPerfil.findViewById(R.id.txtSegonCognom);
        txtSegonCognom.setText(dadesUsuari.get(UsuariSessionManager.KEY_SURNAME2));

        //EditText Email
        txtEmail = (EditText) viewPerfil.findViewById(R.id.txtEmail);
        txtEmail.setText(dadesUsuari.get(UsuariSessionManager.KEY_EMAIL));


        //EditText Password
        txtPass = (EditText) viewPerfil.findViewById(R.id.txtPass);

        //Button Password
        btnPass = (Button) viewPerfil.findViewById(R.id.btnChangePass);
        btnPass.setEnabled(false);
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPass.setVisibility(View.VISIBLE);
                btnPass.setVisibility(View.GONE);
            }
        });


        //MLRounded Image View
        novaImatge = imgTool.getBitmap(dadesUsuari.get(UsuariSessionManager.KEY_IMAGE));
        imgUser = (MLRoundedImageView) viewPerfil.findViewById(R.id.imgUser);
        imgUser.setImageBitmap(novaImatge);
        imgUser.setEnabled(false);



        return viewPerfil;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editar_user, menu);

        //Recollim i guardem els tres items del menu que necessitem
        itemEdit = menu.findItem(R.id.action_editUser);
        itemSave = menu.findItem(R.id.action_saveChange);
        itemCancel = menu.findItem(R.id.action_cancelChange);

        //Establim els menus de guardar i cancel·lar com a invisibles
        itemSave.setVisible(false);
        itemCancel.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {


            /**
             * Acció del botó editar,
             * ens posa tots els camps en editables i ens permet o guardar els canvis o cancel·lar-los.
             */
            case R.id.action_editUser:

                //Mostrem missatge  de que sens permet editar
                toast = toast.makeText(getActivity().getBaseContext(), "Ara, pots editar el teu perfil", Toast.LENGTH_SHORT);
                toast.show();

                //Posem els text views com a editables
                txtNom.setEnabled(true);
                txtPrimerCognom.setEnabled(true);
                txtSegonCognom.setEnabled(true);
                txtEmail.setEnabled(true);
                txtPass.setEnabled(true);

                btnPass.setEnabled(true);

                //Posem la imatge com a activa per poder modificar-la
                imgUser.setEnabled(true);

                //El item del menu editar el posem com a invisible ja que ja estem editant
                itemEdit.setVisible(false);

                //Els items de guardar i cancel·lar els posem com a visibles
                itemSave.setVisible(true);
                itemCancel.setVisible(true);


                return true;

            /**
             * Acció de guardar.
             * Ens guarda els canvis al shared preferences i a la DB
             * Torna a posar els TextViews com a deshabilitats
             */

            case R.id.action_saveChange:


                //Mostrem missatge de canvis guardats
                toast =  toast.makeText(getActivity().getBaseContext(), "Canvis guardats correctament", Toast.LENGTH_SHORT);
                toast.show();

                //Posems els TextViews com a deshabilitats per evitar l'edició
                txtNom.setEnabled(false);
                txtPrimerCognom.setEnabled(false);
                txtSegonCognom.setEnabled(false);
                txtEmail.setEnabled(false);
                txtPass.setEnabled(false);
                txtPass.setVisibility(View.GONE);

                //Posem el botó de canviar contrasenya deshabilitat per evitar que es torni a mostrar
                btnPass.setEnabled(false);
                btnPass.setVisibility(View.VISIBLE);

                //Posem la imatge com deshabilitada per evitar modificar-la
                imgUser.setEnabled(false);

                //Guardem les noves dades al shared preferences
                sessioUsuari.createUserLoginSession(txtNom.getText().toString(), txtPrimerCognom.getText().toString(), txtSegonCognom.getText().toString(),txtPass.getText().toString(), txtEmail.getText().toString(), imgTool.getImageString(novaImatge));


                //Com els canvis ja estan guardats, tornem a deixar invisibles els items de gaurdar i cancel·lar
                itemSave.setVisible(false);
                itemCancel.setVisible(false);

                //Tornem a posar el item d'editar com a visible
                itemEdit.setVisible(true);


                return true;


            /**
             * Acció de cancel·lar els canvis
             * Cancel·la els canvis i estableix les dades anteriors de nou als camps
             * Deshabilita l'edició dels camps
             */
            case R.id.action_cancelChange:

                //Mostrem missatge de canvis cancel·lats
                toast =  toast.makeText(getActivity().getBaseContext(), "Canvis cancel·lats", Toast.LENGTH_SHORT);
                toast.show();

                //Posem els camps que hi teniem guardats al shared preferences i els tornem a posar com a deshabilitats per evitar l'edició
                txtNom.setText(dadesUsuari.get(UsuariSessionManager.KEY_NAME));
                txtNom.setEnabled(false);

                txtPrimerCognom.setText(dadesUsuari.get(UsuariSessionManager.KEY_SURNAME1));
                txtPrimerCognom.setEnabled(false);

                txtSegonCognom.setText(dadesUsuari.get(UsuariSessionManager.KEY_SURNAME2));
                txtSegonCognom.setEnabled(false);

                txtEmail.setText(dadesUsuari.get(UsuariSessionManager.KEY_EMAIL));
                txtEmail.setEnabled(false);

                txtPass.setText("");
                txtPass.setEnabled(false);
                txtPass.setVisibility(View.GONE);


                //Posem el botó de canviar contrasenya deshabilitat per evitar que es torni a mostrar
                btnPass.setEnabled(false);
                btnPass.setVisibility(View.VISIBLE);

                //Posem la imatge com deshabilitat i establim la imatge que hi havia per defecte
                imgUser.setEnabled(false);

                //Com els canvis ja estan guardats, tornem a deixar invisibles els items de gaurdar i cancel·lar
                itemSave.setVisible(false);
                itemCancel.setVisible(false);

                //Tornem a posar el item d'editar com a visible
                itemEdit.setVisible(true);

                return true;

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
