package com.norriors.java.mtbfreeride.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.norriors.java.mtbfreeride.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private ProgressBar bar;

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

    private File imgCamera;
    private Bitmap resized;
    private byte[] byteArray;

    private static final String URL = "http://provesrasp.ddns.net/aplicacio/modificaDadesPerfil.php";
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PHOTO_CODE = 2;
    private static final String PATH_IMATGES = Environment.getExternalStorageDirectory() + File.separator + "mtbfreeride";


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
        txtNom.setText(dadesUsuari.get(UsuariSessionManager.KEY_REAL_NAME));

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
        registerForContextMenu(imgUser);

        imgUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getActivity().openContextMenu(imgUser);
            }
        });

        bar = (ProgressBar) viewPerfil.findViewById(R.id.bar);

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

                if (resized != null) {
                    //Guardem les noves dades al shared preferences
                    sessioUsuari.createUserLoginSession(dadesUsuari.get(UsuariSessionManager.KEY_NAME), txtNom.getText().toString(), txtPrimerCognom.getText().toString(), txtSegonCognom.getText().toString(), txtPass.getText().toString(), txtEmail.getText().toString(), imgTool.getImageString(resized));
                }else{
                    sessioUsuari.createUserLoginSession(dadesUsuari.get(UsuariSessionManager.KEY_NAME), txtNom.getText().toString(), txtPrimerCognom.getText().toString(), txtSegonCognom.getText().toString(), txtPass.getText().toString(), txtEmail.getText().toString(), imgTool.getImageString(novaImatge));
                }

                if (!this.txtNom.getText().toString().matches("") && !this.txtPrimerCognom.getText().toString().matches("")
                        && !this.txtSegonCognom.getText().toString().matches("") && !this.txtEmail.getText().toString().matches("")){

                    //if (txtPass != null){
                        new ModificaDades().execute(this.txtNom.getText().toString(), this.txtPrimerCognom.getText().toString(),
                                txtSegonCognom.getText().toString(), txtEmail.getText().toString(), txtPass.getText().toString());
                    /*}else{
                        new ModificaDades().execute(this.txtNom.getText().toString(), this.txtPrimerCognom.getText().toString(),
                                txtSegonCognom.getText().toString(), txtEmail.getText().toString());
                    }*/
                }else{
                    Toast.makeText(getActivity(),"No hi poden haver camps buits", Toast.LENGTH_SHORT).show();
                }

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

    /**
     * Menú contextual que ens permetrà accedir a la galeria o la càmara
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Opcions multimèdia");
        menu.setHeaderIcon(R.drawable.ic_action_crop);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Events dels Items del menú contextual
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_galeria:
                accessGaleria();
                return true;
            case R.id.action_camara:
                accessCamara();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Accedir a la galeria d'imatges
     */
    public void accessGaleria() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // startActivityForResult del pare (MainActivity)
        getActivity().startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    /**
     * Accedir a la càmera del dispostiu
     */
    private void accessCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Comprovo si existeix la carpeta de l'aplicació
        comprovaPath();

        // instancia de date per guardar les imatges amb la data i hora
        // que s'ha tirat la imatge
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        imgCamera = new File(PATH_IMATGES + File.separator + dateFormat.format(date) + "imgPerfil.jpg");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgCamera));

        // startActivityForResult del pare (MainActivity)
        getActivity().startActivityForResult(takePictureIntent, TAKE_PHOTO_CODE);
    }

    /**
     * Mètode que comprova si existeix la carpeta de l'aplicació i sinó
     * existeix la crea per poder-hi guardar les fotos obtingudes de
     * la càmera
     */
    private void comprovaPath() {
        File dir = new File(PATH_IMATGES);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * Mètode que pinta la imatge a dintre l'imageView cridant els
     * mètodes per reduir la seva mida.
     *
     * @param path path de la imatge.
     */
    public void pintaImatge(String path) {
        resized = this.imgTool.convertImageToByte(path);
        imgUser.setImageBitmap(resized);
    }

    public void pintaImatge() {
        resized = this.imgTool.convertImageToByte(imgCamera.getAbsolutePath());
        imgUser.setImageBitmap(resized);
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

    /**
     * Classe que registre l'usuari paral·lelament
     */
    class ModificaDades extends AsyncTask<String, Void, String> {

        String responseText;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Pintem el progressbar
            bar.setVisibility(View.VISIBLE);

            if (resized != null) {
                // Passem la imatge a una cadena de bytes
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteArray = stream.toByteArray();
            }
        }

        /**
         * Procés que envia les dades a un json
         * que fa l'update a la base de dades
         * @param params
         * @return una string true o false
         */
        @Override
        protected String doInBackground(String... params) {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(URL);
            HttpResponse httpresponse = null;
            try {
                List<NameValuePair> parametres = new ArrayList<NameValuePair>(1);

                // Si hi ha imatge i contrasenya
                if (resized != null && !txtPass.getText().toString().matches("")) {
                    parametres.add(new BasicNameValuePair("password", params[4]));
                    parametres.add(new BasicNameValuePair("img", Base64.encodeToString(byteArray, Base64.DEFAULT)));
                }
                // Si hi ha només imatge
                else if (resized != null){
                    parametres.add(new BasicNameValuePair("password", null));
                    parametres.add(new BasicNameValuePair("img", Base64.encodeToString(byteArray, Base64.DEFAULT)));
                }
                // Si hi ha només contrasenya
                else if (!txtPass.getText().toString().matches("")){
                    parametres.add(new BasicNameValuePair("password", params[4]));
                    parametres.add(new BasicNameValuePair("img", null));
                }
                // Si no hi ha ni imatge ni contrasenya
                else{
                    parametres.add(new BasicNameValuePair("password", null));
                    parametres.add(new BasicNameValuePair("img", null));
                }

                // Dades que sempre hi són
                parametres.add(new BasicNameValuePair("user", dadesUsuari.get(UsuariSessionManager.KEY_NAME)));
                parametres.add(new BasicNameValuePair("nom", params[0]));
                parametres.add(new BasicNameValuePair("cognom1", params[1]));
                parametres.add(new BasicNameValuePair("cognom2", params[2]));
                parametres.add(new BasicNameValuePair("email", params[3]));

                httppostreq.setEntity(new UrlEncodedFormEntity(parametres));

                httpresponse = httpclient.execute(httppostreq);

                // Resposta del json
                responseText = EntityUtils.toString(httpresponse.getEntity());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseText;
        }

        /**
         * Mètode que recull la resposta del json. Si es true tornem al login sinó
         * mostrem el missatge d'error
         *
         * @param resposta
         */
        @Override
        protected void onPostExecute(String resposta) {

            // Si es true retornem al login
            if (resposta.trim().equals("true")) {

                if (resized != null) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    NavigationDrawerFragment fragment;
                    fragment = (NavigationDrawerFragment) fm.findFragmentById(R.id.navigation_drawer);
                    fragment.imatgeRodona(resized);
                }
                Toast.makeText(getActivity(), "Dades modificades correctament", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "S'ha produït un error", Toast.LENGTH_SHORT).show();
            }
            bar.setVisibility(View.GONE);
        }
    }
}
