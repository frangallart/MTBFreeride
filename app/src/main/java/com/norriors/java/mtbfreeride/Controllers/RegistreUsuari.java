package com.norriors.java.mtbfreeride.Controllers;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.norriors.java.mtbfreeride.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private byte[] imatgeArray;

    private File imgCamera;


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
        roundedImageView.getLayoutParams().height = (int)getResources().getDimension(R.dimen.photo_user_registerH);
        roundedImageView.getLayoutParams().width = (int)getResources().getDimension(R.dimen.photo_user_registerW);
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
        /*switch (v.getId()) {
            case (R.id.linearImage):
                openContextMenu(roundedImageView);
                System.out.println("puta");
                break;
        }*/
    }


    /**
     * Menú contextual que ens permetrà accedir a la galeria o la càmara
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
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

    public void accessGaleria() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

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
     * mètodes per reduir la seva mida
     *
     * @param path path de la imatge
     */
    private void pintaImatge(String path) {
        Bitmap imgPerfil;
        Bitmap resized;

        imgPerfil = this.imgTool.convertImageToByte(path);
        resized = Bitmap.createScaledBitmap(imgPerfil, 200, 200, true);
        imatgeArray = this.imgTool.getBytes(resized);
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
}
