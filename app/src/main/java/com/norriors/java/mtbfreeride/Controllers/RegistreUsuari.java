package com.norriors.java.mtbfreeride.Controllers;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe RegistreUsuari
 * <p/>
 * Ens permet registrar un nou usuari al sistema
 * <p/>
 * L'usuari ha d'escollir una imatge de la galeria o de la càmara i introduïrs diversos paràmetres:
 * Nom usuari, Contrassenya, Nom, Cognoms i email
 * Un cop fet clic al botó registrar, si les dades són vàlides, les dades es traspassen amb JSON fins
 * A una base de dades MySQL allotjada en un servidor extern.
 */
public class RegistreUsuari extends ActionBarActivity implements OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PHOTO_CODE = 100;
    private static final String PATH_IMATGES = Environment.getExternalStorageDirectory() + File.separator + "mtbfreeride";

    private MLRoundedImageView roundedImageView;

    private LinearLayout linearImage;
    private ViewGroup editText;

    private ImageTool imgTool;

    private Bitmap resized;

    private byte[] byteArray;

    private File imgCamera;

    private EditText editNomUsuari;
    private EditText editPassword;
    private EditText editPassword2;
    private EditText editNom;
    private EditText editPrimerCognom;
    private EditText editSegonCognom;
    private EditText editEmail;

    private Button btnRegistre;

    private ProgressBar bar;

    private final String URL = "http://provesrasp.ddns.net/aplicacio/insertUsuari.php";
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre_usuari);
        this.imgTool = new ImageTool();

        setupGui();
    }

    /**
     * Mètode que recupera els controls de la GUI i toca espectes de disseny
     */
    private void setupGui() {

        editNomUsuari = (EditText) findViewById(R.id.editNomUsuari);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPassword2 = (EditText) findViewById(R.id.editPassword2);
        editNom = (EditText) findViewById(R.id.editNom);
        editPrimerCognom = (EditText) findViewById(R.id.editPrimerCognom);
        editSegonCognom = (EditText) findViewById(R.id.editSegonCognom);
        editEmail = (EditText) findViewById(R.id.editEmail);
        btnRegistre = (Button) findViewById(R.id.btnRegistre);
        bar = (ProgressBar) this.findViewById(R.id.progressBar);

        btnRegistre.setOnClickListener(this);

        //Instanciem el linear de la vista que contindrà la imatge
        linearImage = (LinearLayout) findViewById(R.id.linearImage);

        //Instanciem un objecte del tipus MLRoundedImageView(Exten de ImageView) que ens permetrà tenir un imageView Rodó
        roundedImageView = new MLRoundedImageView(this);

        //Assignant el menu Contextual a la ImageView
        registerForContextMenu(roundedImageView);

        //Assignem la imatge al imageView
        roundedImageView.setImageResource(R.drawable.ic_camera);
        roundedImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                openContextMenu(roundedImageView);
            }
        });

        //Afegim l'ImageView al Linear
        linearImage.addView(roundedImageView);
        //Paràmetres per decidir la mida
        roundedImageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.photo_user_registerH);
        roundedImageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.photo_user_registerW);
        //Paràmetre per indicar la gravetat
        linearImage.setGravity(Gravity.CENTER_HORIZONTAL);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/Open_Sans/OpenSans-Regular.ttf");

        // Mitjançant el següent recorregut assignem una font a tots els editText d'un layout
        editText = (ViewGroup) findViewById(R.id.editTxtsRegistre);
        for (int i = 0, count = editText.getChildCount(); i < count; ++i) {
            View view = editText.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setTypeface(font);
            }
        }

    }

    /**
     * Mètode que controla l'event onClick
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        // Si hi ha conexiò a internet
        if (InternetUtil.isOnline(RegistreUsuari.this)) {
            switch (v.getId()) {

                // onClick registre
                case (R.id.btnRegistre):
                    controlCampsRegistre();
                    break;
            }
        } else {
            InternetUtil.showAlertDialog(RegistreUsuari.this, "Servei de connexió",
                    "El teu dispositiu no té connexió a Internet.");
        }

    }

    /**
     * Mètode que comprova si els camps són vàlids, en cas cotrari mostra un Toast amb l'error
     */
    public void controlCampsRegistre() {
        // Control que tots els editText estiguin plens
        if (!this.editNom.getText().toString().matches("") && !this.editEmail.getText().toString().matches("") &&
                !this.editNomUsuari.getText().toString().matches("") && !this.editPassword.getText().toString().matches("")
                && !this.editPassword2.getText().toString().matches("") && !this.editPrimerCognom.getText().toString().matches("")
                && !this.editSegonCognom.getText().toString().matches("")) {

            // Control si hi ha imatge
            if (resized != null) {

                // Control que les contrasenyes siguin iguals
                if (this.editPassword.getText().toString().matches(this.editPassword2.getText().toString())) {

                    // Utilitzem expressions regulars per verificar l'email
                    Pattern pattern = Pattern.compile(PATTERN_EMAIL);
                    Matcher matcher = pattern.matcher(this.editEmail.getText().toString());
                    if (matcher.matches()) {
                        new InsertaUsuari().execute(this.editNomUsuari.getText().toString(), this.editNom.getText().toString(),
                                this.editEmail.getText().toString(), this.editPassword.getText().toString(),
                                this.editPrimerCognom.getText().toString(), this.editSegonCognom.getText().toString());
                        btnRegistre.setEnabled(false);
                    } else {
                        Toast.makeText(RegistreUsuari.this, "L'email no es correcta.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistreUsuari.this, "Les contrasenyes no són iguals.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegistreUsuari.this, "Has d'insertar una imatge.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegistreUsuari.this, "S'han d'omplir tots els camps.", Toast.LENGTH_SHORT).show();
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

        MenuInflater inflater = getMenuInflater();
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

        startActivityForResult(i, RESULT_LOAD_IMAGE);
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
        startActivityForResult(takePictureIntent, TAKE_PHOTO_CODE);
    }

    /**
     * Mètode que s'exeuta en retornar a aquesta activitat
     * En auqest cas, comprova si s'ha carregat una imatge de la galeria de fotos o de la
     * càmara per així poder assignar-la com a fons del botó.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case RESULT_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    String result;
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor == null) {
                        result = selectedImage.getPath();
                    } else {
                        cursor.moveToFirst();
                        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        result = cursor.getString(idx);
                        cursor.close();
                    }

                    cursor.close();

                    pintaImatge(result);
                    break;

                case TAKE_PHOTO_CODE:
                    pintaImatge(imgCamera.getAbsolutePath());
                    break;
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }

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
    private void pintaImatge(String path) {
        Bitmap imgPerfil;

        imgPerfil = this.imgTool.convertImageToByte(path);
        resized = Bitmap.createScaledBitmap(imgPerfil, 200, 200, true);
        //imatgeArray = this.imgTool.getBytes(resized);
        roundedImageView.setImageBitmap(resized);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registre_usuari, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    /**
     * Classe que registre l'usuari paral·lelament
     */
    class InsertaUsuari extends AsyncTask<String, Void, String> {

        String responseText;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Pintem el progressbar
            bar.setVisibility(View.VISIBLE);

            // Passem la imatge a una cadena de bytes
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();
        }

        /**
         * Procés que envia les dades dels editText i la imatge
         * a un json que fa l'insert a la base de dades
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
                parametres.add(new BasicNameValuePair("user", params[0]));
                parametres.add(new BasicNameValuePair("password", params[3]));
                parametres.add(new BasicNameValuePair("img", Base64.encodeToString(byteArray, Base64.DEFAULT)));
                parametres.add(new BasicNameValuePair("nom", params[1]));
                parametres.add(new BasicNameValuePair("cognom1", params[4]));
                parametres.add(new BasicNameValuePair("cognom2", params[5]));
                parametres.add(new BasicNameValuePair("email", params[2]));

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
                Toast.makeText(RegistreUsuari.this, "Usuari registrat correctament", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent().setClass(
                        RegistreUsuari.this, LoginUsuari.class);
                startActivity(mainIntent);
                finish();
            }
            // Sinó mostrem el missatge de l'error
            else {
                Toast.makeText(RegistreUsuari.this, "S'ha produït un error", Toast.LENGTH_SHORT).show();
                btnRegistre.setEnabled(true);
            }
            bar.setVisibility(View.GONE);
        }
    }
}
