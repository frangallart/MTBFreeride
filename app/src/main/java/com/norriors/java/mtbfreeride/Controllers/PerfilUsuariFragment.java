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

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe Perfil Usuari Fragment
 * En aquesta classe sens mostren les dades bàsiques de l'usuari
 * (Imatge de perfil, nom , cognoms i correu)
 * I ens permet fer l'edició de es dades esmentades a partir del botó editar
 */
public class PerfilUsuariFragment extends android.support.v4.app.Fragment {

    //Elements de la vista del fragment
    //Edit Text
    private EditText txtNom;
    private EditText txtPrimerCognom;
    private EditText txtSegonCognom;
    private EditText txtEmail;
    private EditText txtPass;
    private EditText txtPassRepeteix;

    //Progress Bar
    private ProgressBar bar;

    //Custom Image View
    private MLRoundedImageView imgUser;


    //Objectes per recollir les dades del Shared Preferences
    private UsuariSessionManager sessioUsuari;
    private HashMap<String, String> dadesUsuari;

    //Objectes per recollir la imatge i tractar-la
    private ImageTool imgTool;
    private Bitmap novaImatge;
    private File imgCamera;
    private Bitmap resized;
    private byte[] byteArray;

    //Mostrar missatges
    private Toast toast;

    //Items del menú
    private MenuItem itemEdit;
    private MenuItem itemSave;
    private MenuItem itemCancel;

    //Constants
    private static final String URL = "http://mtbfreeride.hostoi.com/modificaDadesPerfil.php";
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PHOTO_CODE = 2;
    private static final String PATH_IMATGES = Environment.getExternalStorageDirectory() + File.separator + "mtbfreeride";
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    /**
     * Mètode que s'executa al crear una nova instància del fragment
     * Crida al constructor i retorna el fragment
     *
     * @return fragment
     */
    public static PerfilUsuariFragment newInstance() {
        PerfilUsuariFragment fragment = new PerfilUsuariFragment();

        return fragment;
    }

    /**
     * Constructor per defecte
     */
    public PerfilUsuariFragment() {
        // Required empty public constructor
    }

    /**
     * Mètode que s'executa al crear el fragment
     * Amb el <setHasOptionsMenu> establim el menú d'aquest fragment a l'action bar
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    /**
     * Mètode que ens crea la vista
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Perfil");

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
        txtPassRepeteix = (EditText) viewPerfil.findViewById(R.id.txtPass2);

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


    /**
     * Mètode que crea el menu de l'action Bar i instancia els tres items per poder
     * posar la seva visibilitat
     *
     * @param menu
     * @param inflater
     */
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
        // Si hi ha conexiò a internet
        if (InternetUtil.isOnline(getActivity().getBaseContext())) {
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

                    txtPass.setVisibility(View.VISIBLE);
                    txtPassRepeteix.setVisibility(View.VISIBLE);

                    //Posem els text views com a editables
                    txtNom.setEnabled(true);
                    txtPrimerCognom.setEnabled(true);
                    txtSegonCognom.setEnabled(true);
                    txtEmail.setEnabled(true);

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
                    } else {
                        sessioUsuari.createUserLoginSession(dadesUsuari.get(UsuariSessionManager.KEY_NAME), txtNom.getText().toString(), txtPrimerCognom.getText().toString(), txtSegonCognom.getText().toString(), txtPass.getText().toString(), txtEmail.getText().toString(), imgTool.getImageString(novaImatge));
                    }

                    if (!this.txtNom.getText().toString().matches("") && !this.txtPrimerCognom.getText().toString().matches("")
                            && !this.txtSegonCognom.getText().toString().matches("") && !this.txtEmail.getText().toString().matches("")) {

                        if (this.txtPass.getText().toString().equals(this.txtPassRepeteix.getText().toString())) {

                            // Utilitzem expressions regulars per verificar l'email
                            Pattern pattern = Pattern.compile(PATTERN_EMAIL);
                            Matcher matcher = pattern.matcher(this.txtEmail.getText().toString());
                            if (matcher.matches()) {

                                new ModificaDades().execute(this.txtNom.getText().toString(), this.txtPrimerCognom.getText().toString(),
                                        txtSegonCognom.getText().toString(), txtEmail.getText().toString(), txtPass.getText().toString());
                                //Posems els TextViews com a deshabilitats per evitar l'edició
                                txtNom.setEnabled(false);
                                txtPrimerCognom.setEnabled(false);
                                txtSegonCognom.setEnabled(false);
                                txtEmail.setEnabled(false);
                                txtPass.setVisibility(View.GONE);
                                txtPassRepeteix.setVisibility(View.GONE);

                                //Posem la imatge com deshabilitada per evitar modificar-la
                                imgUser.setEnabled(false);

                                //Com els canvis ja estan guardats, tornem a deixar invisibles els items de gaurdar i cancel·lar
                                itemSave.setVisible(false);
                                itemCancel.setVisible(false);

                                //Tornem a posar el item d'editar com a visible
                                itemEdit.setVisible(true);
                            } else {
                                Toast.makeText(getActivity(), "Escriu un email vàlid", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Les contrasenyes no són coincideixen", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "No hi poden haver camps buits", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                /**
                 * Acció de cancel·lar els canvis
                 * Cancel·la els canvis i estableix les dades anteriors de nou als camps
                 * Deshabilita l'edició dels camps
                 */
                case R.id.action_cancelChange:
                    android.support.v4.app.FragmentTransaction t = getFragmentManager().beginTransaction();
                    Fragment currentFragment = ModalitatsFragment.newInstance();
                    t.replace(R.id.container, currentFragment, "fragment").addToBackStack("tag4").commit();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);

            }
        } else {
            InternetUtil.showAlertDialog(getActivity().getBaseContext(), "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
            return false;
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
                resized.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byteArray = stream.toByteArray();
            }
        }

        /**
         * Procés que envia les dades a un json
         * que fa l'update a la base de dades
         *
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
                else if (resized != null) {
                    parametres.add(new BasicNameValuePair("password", null));
                    parametres.add(new BasicNameValuePair("img", Base64.encodeToString(byteArray, Base64.DEFAULT)));
                }
                // Si hi ha només contrasenya
                else if (!txtPass.getText().toString().matches("")) {
                    parametres.add(new BasicNameValuePair("password", params[4]));
                    parametres.add(new BasicNameValuePair("img", null));
                }
                // Si no hi ha ni imatge ni contrasenya
                else {
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
            } else {
                Toast.makeText(getActivity(), "S'ha produït un error", Toast.LENGTH_SHORT).show();
            }
            resized = null;
            bar.setVisibility(View.GONE);
        }
    }
}
